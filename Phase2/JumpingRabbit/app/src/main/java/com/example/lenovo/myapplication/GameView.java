package com.example.lenovo.myapplication;

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
    //canvas
    private int canvasHeight;
    private int canvasWidth;
    //rabbit
    private Bitmap rabbit;
    private int rabbitVelocity;
    private int rabbitX = 10;
    private int rabbitY;
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
    private int numLife;
    //score
    private int score;
    private Paint paintScore = new Paint();

    public GameView(Context context) {
        super(context);
        rabbit = BitmapFactory.decodeResource(getResources(), R.drawable.rabbit);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        carrot = BitmapFactory.decodeResource(getResources(), R.drawable.carrot);
        poison = BitmapFactory.decodeResource(getResources(), R.drawable.poison);

        Paint paintScore = new Paint();
        paintScore.setColor(Color.GREEN);
        paintScore.setTextSize(32);
        paintScore.setTypeface(Typeface.DEFAULT);
        paintScore.setAntiAlias(true);

        life = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        //Initial status;
        rabbitY = 500;
        score = 0;
        numLife = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasWidth = getWidth();
        canvasHeight = getHeight();

        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawText("Score:" + score, 20, 60, paintScore);
        //life
        for (int i = 0; i < numLife; i++) {
            int x = (int) (560 + life.getWidth() * 1.5 * i);
            int y = 30;
            canvas.drawBitmap(life, x, y, null);
        }

        //rabbit
        int minRabbitY = 150;
        int maxRabbitY = canvasHeight - 100;
        rabbitY += rabbitVelocity;
        if(rabbitY > maxRabbitY){
            rabbitY = maxRabbitY;
        }
        if (rabbitY < minRabbitY){
            rabbitY = minRabbitY;
        }
        rabbitVelocity += 2;
        canvas.drawBitmap(rabbit, rabbitX, rabbitY, null);
        int randomGeneration = (int) Math.floor(Math.random() * (maxRabbitY - minRabbitY)) + minRabbitY;;
        //poison
//        move(canvas, poison, poisonVelocity, poisonX, poisonY, 250,
//                maxRabbitY, minRabbitY , 2, numLife);
        poisonX -= poisonVelocity;
        if (checkCollision(poisonX, poisonY)){
            poisonX = -20;
            numLife --;
            if(numLife == 0){
                //TODO: make a game over layout
                Log.v("Message", "GAME OVER!");
            }
        }
        if (poisonX < 0){
            poisonX = canvasWidth + 100;
            poisonY = randomGeneration;
        }
        canvas.drawBitmap(poison, poisonX, poisonY, null);

        //carrot
        carrotX -= carrotVelocity;
        if (checkCollision(carrotX, carrotY)){
            score += 1;
            carrotX = -50;
        }
        if (carrotX < 0) {
            carrotX = canvasWidth + 20;

            carrotY = randomGeneration;
        }
//        move(canvas, carrot, carrotVelocity, carrotX, carrotY, 20,
//                maxRabbitY, minRabbitY, 1, score);
        canvas.drawBitmap(carrot, carrotX, carrotY, null);
    }

    public boolean checkCollision(int x, int y) {
        return rabbitX < x && x < (rabbitX + rabbit.getWidth()) &&
                (rabbitY < y && y < (rabbitY + rabbit.getHeight()));
    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){

        int eventAction = event.getAction();
        if(eventAction == MotionEvent.ACTION_DOWN){
            //check whether screen is touched
            rabbitVelocity = -20;
            //performClick();
        }
        return true;
    }
//    @Override
//    public boolean performClick(){
//        rabbitVelocity = 20;
//        return true;
//    }

//    public void move(Canvas canvas, Bitmap item, int velocity, int x, int y, int startingPosition,
//                     int maxRabbitY, int minRabbitY, int function, int attribute){
//        x -= velocity;
//        if (x < 0){
//            x = canvasWidth + startingPosition;
//            y = (int) Math.floor(Math.random() * (maxRabbitY - minRabbitY)) + minRabbitY;
//        }
//        else if(checkCollision(x, y)){
//            x = -50;
//        }
//        canvas.drawBitmap(item, x, y, null);
//        if (function == 1){
//            attribute += 10;
//        }
//        else if (function == 2){
//            attribute -= 1;
//            if (attribute == 0){
//                Log.v("MESSAGE", "GAME OVER!!!");
//            }
//        }
//    }
}
