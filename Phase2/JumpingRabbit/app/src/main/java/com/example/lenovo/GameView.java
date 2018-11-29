package com.example.lenovo;

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
import com.example.lenovo.jump2.R;


public class GameView extends View {
    //canvas
    private Canvas canvas;
    private int canvasHeight;
    private int canvasWidth;
    //rabbit
    private RabbitModel rabbitModel = new RabbitModel();
    //poison(velocity = 25)
    private PoisonTargetModel poisonTargetModel = new PoisonTargetModel();
    //carrot(velocity = 17)
    private CarrotTargetModel carrotTargetModel = new CarrotTargetModel();

    private StateModel stateModel = new StateModel();


    public GameView(Context context) {
        super(context);
        rabbitModel.setRabbit(BitmapFactory.decodeResource(getResources(), R.drawable.rabbit));
        stateModel.setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        CarrotTargetModel.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.carrot));
        PoisonTargetModel.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.poison));
        stateModel.generatePaint();

        stateModel.setLife(BitmapFactory.decodeResource(getResources(), R.drawable.heart));
        //Initial status;
        rabbitModel.setRabbitY(500);
//        score = 0;
//        numLife = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasWidth = getWidth();
        canvasHeight = getHeight();

        canvas.drawBitmap(stateModel.getBackground(), 0, 0, null);
        canvas.drawText("Score:" + stateModel.getScore(), 20, 60, stateModel.getPaintScore());
        //life
        stateModel.drawLife(canvas);

        //rabbit move method
        RabbitModel.rabbitMove(RabbitModel.getRabbitY());
        RabbitModel.addRabbitVelocity(2);
        canvas.drawBitmap(RabbitModel.getRabbit(), RabbitModel.getRabbitX(), RabbitModel.getRabbitY(), null);

        //poison method

        int randomGeneration = (int) Math.floor(Math.random() * (RabbitModel.getMaxRabbitY() -
                RabbitModel.getMinRabbitY())) + RabbitModel.getMinRabbitY();;
        PoisonTargetModel.imageX -= PoisonTargetModel.getVelocity();
        if (RabbitModel.checkCollision(PoisonTargetModel.imageX, PoisonTargetModel.imageY)) {
            PoisonTargetModel.imageX = -20;
            stateModel.changeLife();
            if (stateModel.getNumLife() == 0) {
                stateModel.changeLife();
                if (stateModel.getNumLife() == 0) {
                    //TODO: make a game over layout
                    Log.v("Message", "game over");
                }
            }
            if (PoisonTargetModel.imageX < 0) {
                PoisonTargetModel.imageX = canvasWidth + 100;
                PoisonTargetModel.imageY = randomGeneration;
            }
            canvas.drawBitmap(PoisonTargetModel.image, PoisonTargetModel.imageX,
                    PoisonTargetModel.imageY, null);

            //carrot
            CarrotTargetModel.imageX -= CarrotTargetModel.getVelocity();
            if (RabbitModel.checkCollision(CarrotTargetModel.imageX, CarrotTargetModel.imageY)) {
                stateModel.changeScore();
                CarrotTargetModel.imageX = -50;
            }
            if (CarrotTargetModel.imageX < 0) {
                CarrotTargetModel.imageX = canvasWidth + 20;
                CarrotTargetModel.imageY = randomGeneration;
            }

            canvas.drawBitmap(CarrotTargetModel.image, CarrotTargetModel.imageX,
                    CarrotTargetModel.imageY, null);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){

        int eventAction = event.getAction();
        if(eventAction == MotionEvent.ACTION_DOWN){
            //check whether screen is touched
            RabbitModel.setRabbitVelocity(-20);
            //performClick();
        }
        return true;
    }


}
