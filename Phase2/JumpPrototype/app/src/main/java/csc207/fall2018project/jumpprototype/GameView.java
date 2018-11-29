package csc207.fall2018project.jumpprototype;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
    /**
     * The current state of the game
     */
    private GameState gameState;

    //rabbit
    private Bitmap rabbit;

    //poison
    private Bitmap poison;
    private int poisonVelocity = 25;
    private int poisonX;
    private int poisonY;
    //carrot
    private Bitmap carrot;
    private int carrotVelocity = 17;
    private int carrotX;
    private int carrotY;

    private Bitmap background;
    //life
    private Bitmap life;
    //score
    private Paint paintScore = new Paint();

    public GameView(Context context) {
        super(context);

        rabbit = BitmapFactory.decodeResource(getResources(), R.drawable.rabbit);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        carrot = BitmapFactory.decodeResource(getResources(), R.drawable.carrot);
        poison = BitmapFactory.decodeResource(getResources(), R.drawable.poison);

        PlayerCharacter player = new PlayerCharacter(
                new MassivePoint(PlayerCharacter.DEFAULT_PLAYER_MASS, 50, 0),
                PlayerCharacter.DEFAULT_BOUNDING_BOX_X_RADIUS,
                PlayerCharacter.DEFAULT_BOUNDING_BOX_Y_RADIUS,
                5, 5
                );
        gameState = new GameState(player, getWidth(), getHeight());

        paintScore.setColor(Color.GREEN);
        paintScore.setTextSize(64);
        paintScore.setTypeface(Typeface.DEFAULT);
        paintScore.setAntiAlias(true);

        life = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
    }

    /**
     * Draw the amount of lives remaining
     * @param canvas canvas to draw on
     */
    private void drawLives(Canvas canvas) {
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
     * Draw the rabbit's coordinates on the bottom of the screen
     */
    private void drawCoordinates(Canvas canvas) {
        canvas.drawText(
                "Poison: ("
                        + gameState.getPoisonDrawX()
                        + ", " + gameState.getPoisonDrawY() + ")",
                20, getHeight() - 120, paintScore);
        canvas.drawText(
                "Position: ("
                        + gameState.getPlayer().getCentreX()
                        + ", " + gameState.getPlayer().getCentreY() + ")",
                20, getHeight() - 60, paintScore);
    }

    /**
     * Draw the background
     * @param canvas canvas to draw on
     */
    private void drawBackground(Canvas canvas) {
        canvas.drawBitmap(background, 0, 0, null);
    }

    /**
     * Draw the rabbit
     * @param canvas the canvas to draw on
     * @param x x coordinate to draw the rabbit at
     * @param y y coordinate to draw the rabbit at
     */
    private void drawRabbit(Canvas canvas, int x, int y) {
        canvas.drawBitmap(rabbit, x, y, null);
    }

    /**
     * Draw a carrot
     * @param canvas the canvas to draw on
     * @param x x coordinate to draw the carrot at
     * @param y y coordinate to draw the carrot at
     */
    private void drawCarrot(Canvas canvas, int x, int y) {
        canvas.drawBitmap(carrot, x, y, null);
    }

    /**
     * Draw a poison bottle
     * @param canvas the canvas to draw on
     * @param x x coordinate to draw the poison bottle at
     * @param y y coordinate to draw the poison bottle at
     */
    private void drawPoison(Canvas canvas, int x, int y) {
        canvas.drawBitmap(poison, x, y, null);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int canvasWidth = getWidth();
        int canvasHeight = getHeight();
        gameState.setScreenSize(canvasWidth, canvasHeight);
        gameState.runTick();

        drawBackground(canvas);
        drawScore(canvas);
        drawCoordinates(canvas);
        drawLives(canvas);

        int randomGeneration = (int)(canvasHeight * Math.random());

        //rabbit
        drawRabbit(canvas, gameState.getPlayerDrawX(), gameState.getPlayerDrawY());

        //poison
        poisonX -= poisonVelocity;
        if ((new ShiftablePoint(poisonX, poisonY)).collidesWith(gameState.getPlayer())){
            poisonX = -20;
            gameState.getPlayer().decrementHealth();
            if(!gameState.getPlayer().hasHealth()){
                //TODO: make a game over layout
                Log.v("Message", "GAME OVER!");
            }
        }
        if (poisonX < 0){
            poisonX = canvasWidth + 100;
            poisonY = randomGeneration;
        }
        drawPoison(canvas, gameState.getPoisonDrawX(), gameState.getPoisonDrawY());

        //carrot
        carrotX -= carrotVelocity;
        if ((new ShiftablePoint(carrotX, carrotY)).collidesWith(gameState.getPlayer())){
            gameState.getPlayer().incrementScore();
            carrotX = -50;
        }
        if (carrotX < 0) {
            carrotX = canvasWidth + 20;

            carrotY = randomGeneration;
        }
        drawCarrot(canvas, carrotX, carrotY);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){

        int eventAction = event.getAction();
        if(eventAction == MotionEvent.ACTION_DOWN){
            //check whether screen is touched
            gameState.getPlayer().accY(50);
        }
        return true;
    }
}



