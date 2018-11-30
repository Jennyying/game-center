package fall18project.gamecentre.reactorcontrol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

import fall18project.gamecentre.R;
import fall18project.gamecentre.reactorcontrol.physics.CoinBox;
import fall18project.gamecentre.reactorcontrol.physics.DamagingBox;
import fall18project.gamecentre.reactorcontrol.physics.GameState;
import fall18project.gamecentre.reactorcontrol.physics.MassivePoint;
import fall18project.gamecentre.reactorcontrol.physics.PlayerCharacter;
import fall18project.gamecentre.user_management.GameOverActivity;

public class GameView extends View {
    /**
     * The current state of the game
     */
    private GameState gameState;

    private int poisonVelocity = 25;
    private int poisonX;
    private int poisonY;

    /**
     * Bitmaps to display
     */
    private Bitmap player;
    private Bitmap coin;
    private Bitmap laser;

    //carrot
    private int carrotVelocity = 17;
    private int carrotX;
    private int carrotY;

    //life
    private Bitmap life;
    //score
    private Paint paintScore = new Paint();

    /**
     * Load bitmap resources
     */
    private void loadBitmaps() {
        player = BitmapFactory.decodeResource(getResources(), R.drawable.player);
        coin = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
        laser = BitmapFactory.decodeResource(getResources(), R.drawable.laser);
        life = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
    }

    public GameView(Context context) {
        super(context);
        loadBitmaps();

        PlayerCharacter playerCharacter = new PlayerCharacter(
                new MassivePoint(PlayerCharacter.DEFAULT_PLAYER_MASS, 0, 0),
                player.getWidth()/2,
                player.getHeight()/2,
                0, 5
                );
        DamagingBox laserBox =
                new DamagingBox(1, GameState.MASSLESS_OBJECT, laser.getWidth()/2, laser.getHeight()/2);
        CoinBox coinBox =
                new CoinBox(coin.getWidth(), coin.getHeight());

        gameState = new GameState(playerCharacter, laserBox, coinBox, getWidth(), getHeight());

        paintScore.setColor(Color.YELLOW);
        paintScore.setTextSize(32);
        paintScore.setTypeface(Typeface.MONOSPACE);
        paintScore.setAntiAlias(true);
    }

    /**
     * Return whether the game in this GameView is over
     * @return whether the game is over
     */
    public boolean isOver() {
        return gameState.isOver();
    }

    /**
     * Return the score of this game
     * @return the score
     */
    public long getScore() {
        return gameState.getScore();
    }

    /**
     * Draw the amount of lives remaining
     * @param canvas canvas to draw on
     */
    private void drawLives(Canvas canvas) {
        canvas.drawText("Cores: " + gameState.getPlayer().getHealth(), 20, 120, paintScore);
        for (int i = 0; i < gameState.getPlayer().getHealth(); i++) {
            int x = (int) (560 + life.getWidth() * 1.5 * i);
            int y = 30;
            canvas.drawBitmap(life, x, y, null);
        }
    }

    /**
     * Draw the score
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
     * @param canvas the canvas to draw on
     * @param bitmap the bitmap to draw
     * @param x the x coordinate of the center
     * @param y the y coordinate of the center
     */
    private void drawCenterBitmap(Canvas canvas, Bitmap bitmap, int x, int y) {
        canvas.drawBitmap(
                bitmap, x - bitmap.getWidth()/2, y - bitmap.getHeight()/2, null);
    }

    /**
      * Draw the player
      * @param canvas the canvas to draw on
      * @param x x coordinate to draw the center of the player icon at
      * @param y y coordinate to draw the center of the player icon at
      */
    private void drawPlayer(Canvas canvas, int x, int y) {
        drawCenterBitmap(canvas, player, x, y);
    }

    /**
     * Draw the player
     * @param canvas the canvas to draw on
     */
    private void drawPlayer(Canvas canvas) {
        drawPlayer(canvas, gameState.getPlayerDrawX(), gameState.getPlayerDrawY());
    }

    /**
     * Draw a coin
     * @param canvas the canvas to draw on
     * @param x x coordinate to draw the center of the coin at
     * @param y y coordinate to draw the center of the coin at
     */
    private void drawCoin(Canvas canvas, int x, int y) {
        drawCenterBitmap(canvas, coin, x, y);
    }

    /**
     * Draw any coins on screen
     */
    private void drawCoins(Canvas canvas) {
        if(gameState.shouldDrawCoins())
            drawCoin(canvas, gameState.getCoinDrawX(), gameState.getCoinDrawY());
    }

    /**
     * Draw a laser
     * @param canvas the canvas to draw on
     * @param x x coordinate to draw the center of the laser at
     * @param y y coordinate to draw the center of the laser at
     */
    private void drawLaser(Canvas canvas, int x, int y) {
        drawCenterBitmap(canvas, laser, x, y);
    }

    /**
     * Draw any lasers on screen
     * @param canvas the canvas to draw on
     */
    private void drawLasers(Canvas canvas) {
        if(gameState.shouldDrawLaser())
            drawLaser(canvas, gameState.getLaserDrawX(), gameState.getLaserDrawY());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int canvasWidth = getWidth();
        int canvasHeight = getHeight();
        gameState.setScreenSize(canvasWidth, canvasHeight);
        gameState.runTick();

        drawScore(canvas);
        drawCoordinates(canvas);
        drawLives(canvas);
        drawPlayer(canvas);
        drawLasers(canvas);
        drawCoins(canvas);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){

        int eventAction = event.getAction();
        if(eventAction == MotionEvent.ACTION_DOWN){
            //check whether screen is touched
            gameState.getPlayer().jump();
        }
        return true;
    }
}



