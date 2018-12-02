package fall18project.gamecentre.reactorcontrol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import fall18project.gamecentre.R;
import fall18project.gamecentre.game_management.GameScoreboardManager;
import fall18project.gamecentre.game_management.SessionScore;
import fall18project.gamecentre.reactorcontrol.physics.CoinBox;
import fall18project.gamecentre.reactorcontrol.physics.DamagingBox;
import fall18project.gamecentre.reactorcontrol.physics.GameState;
import fall18project.gamecentre.reactorcontrol.physics.MassivePoint;
import fall18project.gamecentre.reactorcontrol.physics.PlayerCharacter;
import fall18project.gamecentre.user_management.UserManager;

public class GameView extends View {
    /**
     * The current state of the game
     */
    private GameState gameState;

    /**
     * Bitmaps to display
     */
    private Bitmap player;
    private Bitmap coin;
    private Bitmap laser;
    private Bitmap life;
    private Bitmap gameOver;

    /**
     * The score manager
     */
    private GameScoreboardManager gameScoreboardManager;

    /**
     * The user mananger
     */
    private UserManager userManager;

    /**
     * Whether the score has been recorded
     */
    private boolean scoreRecorded = false;

    /**
     * Paint for displaying the score
     */
    private Paint paintScore = new Paint();

    /**
     * Paint for displaying the top of the reactor bar
     */
    private Paint topBarPaint = new Paint();

    /**
     * Paint for displaying the bottom of the reactor bar
     */
    private Paint bottomBarPaint = new Paint();

    /**
     * A gradient background
     */
    private GradientDrawable gradientBackground;

    public GameView(Context context) {
        super(context);
        loadBitmaps();

        gameScoreboardManager = new GameScoreboardManager(context);
        userManager = new UserManager(context);

        PlayerCharacter playerCharacter = new PlayerCharacter(
                new MassivePoint(PlayerCharacter.DEFAULT_PLAYER_MASS, 0, 0),
                player.getWidth() / 2,
                player.getHeight() / 2,
                1.5, 4
        );
        DamagingBox laserBox =
                new DamagingBox(1, GameState.MASSLESS_OBJECT, laser.getWidth() / 2, laser.getHeight() / 2);
        CoinBox coinBox =
                new CoinBox(coin.getWidth(), coin.getHeight());

        gameState = new GameState(playerCharacter, laserBox, coinBox, getWidth(), getHeight());

        paintScore.setColor(Color.YELLOW);
        paintScore.setTextSize(32);
        paintScore.setTypeface(Typeface.MONOSPACE);
        paintScore.setAntiAlias(true);

        topBarPaint.setColor(getResources().getColor(R.color.amber_700, null));
        bottomBarPaint.setColor(getResources().getColor(R.color.cyan_200, null));
    }

    /**
     * Load bitmap resources
     */
    private void loadBitmaps() {
        player = BitmapFactory.decodeResource(getResources(), R.drawable.player);
        coin = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
        laser = BitmapFactory.decodeResource(getResources(), R.drawable.laser);
        life = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        gameOver = BitmapFactory.decodeResource(getResources(), R.drawable.gameover);
    }

    /**
     * Return the score of this game
     *
     * @return the score
     */
    public long getScore() {
        return gameState.getScore();
    }

    /**
     * Draw the amount of lives remaining, and the amount of shield remaining
     *
     * @param canvas canvas to draw on
     */
    private void drawLives(Canvas canvas) {
        canvas.drawText(
                "Cores remaining: " + gameState.getPlayer().getHealth(),
                20, 120, paintScore);
        canvas.drawText(
                "Shield Level: " + (int)(100*gameState.getPlayer().getShield()) + "%",
                20, 180, paintScore
        );
        for (int i = 0; i < gameState.getPlayer().getHealth(); i++) {
            int x = (int) (getWidth() - life.getWidth() * (1 + 1.5 * i) - 15);
            int y = 30;
            canvas.drawBitmap(life, x, y, null);
        }
    }

    /**
     * Draw the score
     *
     * @param canvas canvas to draw on
     */
    private void drawScore(Canvas canvas) {
        canvas.drawText("Score:" + gameState.getPlayer().getScore(), 20, 60, paintScore);
    }

    /**
     * Draw the rabbit and poison's coordinates on the bottom of the screen. For debugging purposes
     */
    private void drawCoordinates(Canvas canvas) {
        canvas.drawText(
                "Coins: ("
                        + gameState.getCoinDrawX()
                        + ", " + gameState.getCoinDrawY() + ")",
                20, getHeight() - 180, paintScore);
        canvas.drawText(
                "Laser: ("
                        + gameState.getLaserDrawX()
                        + ", " + gameState.getLaserDrawY() + ")",
                20, getHeight() - 120, paintScore);
        canvas.drawText(
                "Position: ("
                        + gameState.getPlayerDrawX()
                        + ", " + gameState.getPlayerDrawY() + ")",
                20, getHeight() - 60, paintScore);
    }


    /**
     * Draw a bitmap at center coordinates x, y on the given canvas
     *
     * @param canvas the canvas to draw on
     * @param bitmap the bitmap to draw
     * @param x      the x coordinate of the center
     * @param y      the y coordinate of the center
     */
    private void drawCenterBitmap(Canvas canvas, Bitmap bitmap, int x, int y) {
        canvas.drawBitmap(
                bitmap, x - bitmap.getWidth() / 2, y - bitmap.getHeight() / 2, null);
    }

    /**
     * Draw the player
     *
     * @param canvas the canvas to draw on
     * @param x      x coordinate to draw the center of the player icon at
     * @param y      y coordinate to draw the center of the player icon at
     */
    private void drawPlayer(Canvas canvas, int x, int y) {
        if(gradientBackground != null) gradientBackground.setLevel(y);
        canvas.drawRect(
                x - (2*player.getWidth())/6,
                y - (2*player.getHeight())/6,
                x + (2*player.getWidth())/6,
                getHeight(),
                topBarPaint
        );
        canvas.drawRect(
                x - (2*player.getWidth())/6,
                0,
                x + (2*player.getWidth())/6,
                y - (2*player.getHeight())/6,
                bottomBarPaint
        );
        drawCenterBitmap(canvas, player, x, y);
    }

    /**
     * Draw the player
     *
     * @param canvas the canvas to draw on
     */
    private void drawPlayer(Canvas canvas) {
        if (!gameState.isOver())
            drawPlayer(canvas, gameState.getPlayerDrawX(), gameState.getPlayerDrawY());
    }

    /**
     * Draw a coin
     *
     * @param canvas the canvas to draw on
     * @param x      x coordinate to draw the center of the coin at
     * @param y      y coordinate to draw the center of the coin at
     */
    private void drawCoin(Canvas canvas, int x, int y) {
        drawCenterBitmap(canvas, coin, x, y);
    }

    /**
     * Draw game over effects (game over title on screen, score under it and instructions)
     */
    private void drawGameOverEffects(Canvas canvas) {
        drawCenterBitmap(canvas, gameOver, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Draw any coins on screen
     */
    private void drawCoins(Canvas canvas) {
        if (gameState.shouldDrawCoins())
            drawCoin(canvas, gameState.getCoinDrawX(), gameState.getCoinDrawY());
    }

    /**
     * Draw a laser
     *
     * @param canvas the canvas to draw on
     * @param x      x coordinate to draw the center of the laser at
     * @param y      y coordinate to draw the center of the laser at
     */
    private void drawLaser(Canvas canvas, int x, int y) {
        drawCenterBitmap(canvas, laser, x, y);
    }

    /**
     * Draw any lasers on screen
     *
     * @param canvas the canvas to draw on
     */
    private void drawLasers(Canvas canvas) {
        if (gameState.shouldDrawLaser())
            drawLaser(canvas, gameState.getLaserDrawX(), gameState.getLaserDrawY());
    }

    /**
     * Draw information on the screen, which is either the game over information or the current
     * score and lives
     *
     * @param canvas canvas to draw on
     */
    private void drawInformation(Canvas canvas) {
        drawScore(canvas);
        if (gameState.isOver()) {
            drawGameOverEffects(canvas);
        }
    }

    private void recordScore() {
        if (!scoreRecorded) {
            gameScoreboardManager.addScoreForGame(userManager, new SessionScore(
                    userManager.loadCurrentUserName(),
                    GameActivity.GAME_NAME,
                    gameState.getScore()
            ));
            scoreRecorded = true;
            Toast.makeText(getContext(),
                    "You earned " + gameState.getScore() + " points",
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int canvasWidth = getWidth();
        int canvasHeight = getHeight();
        gameState.setScreenSize(canvasWidth, canvasHeight);
        gameState.runTick();

        //drawCoordinates(canvas);
        drawLives(canvas);
        drawPlayer(canvas);
        drawLasers(canvas);
        drawCoins(canvas);
        drawInformation(canvas);
        if (gameState.isOver()) recordScore();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int eventAction = event.getAction();
        if (eventAction == MotionEvent.ACTION_DOWN) {
            //check whether screen is touched
            gameState.getPlayer().jump();
        }
        return true;
    }

    /**
     * Set the background to be the gradient gradientBackground
     * @param gradientBackground the gradient to use as background
     */
    public void setGradientBackground(GradientDrawable gradientBackground) {
        setBackground(gradientBackground);
        this.gradientBackground = gradientBackground;
    }
}



