package JumpingRabbit;

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
import fall18project.gamecentre.R;


public class GameView extends View {
    //canvas
    private int canvasHeight;
    private int canvasWidth;
    //rabbit
    private RabbitModel rabbitModel;
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

    private StateModel stateModel;


    public GameView(Context context) {
        super(context);
        rabbitModel.setRabbit(BitmapFactory.decodeResource(getResources(), R.drawable.rabbit));
        stateModel.setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        carrot = BitmapFactory.decodeResource(getResources(), R.drawable.carrot);
        poison = BitmapFactory.decodeResource(getResources(), R.drawable.poison);

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
        rabbitModel.rabbitMove(rabbitModel.getRabbitY());

        rabbitModel.addRabbitVelocity(2);
        canvas.drawBitmap(rabbitModel.getRabbit(), rabbitModel.getRabbitX(), rabbitModel.getRabbitY(), null);
        int randomGeneration = (int) Math.floor(Math.random() * (rabbitModel.getMaxRabbitY() - rabbitModel.minRabbitY)) + rabbitModel.minRabbitY;;
        poisonX -= poisonVelocity;
        if (rabbitModel.checkCollision(poisonX, poisonY, rabbitModel)){
            poisonX = -20;
            stateModel.changeLife();
            if(stateModel.getNumLife() == 0){
                //TODO: make a game over layout
                Log.v("Message", "game over");
            }
        }
        if (poisonX < 0){
            poisonX = canvasWidth + 100;
            poisonY = randomGeneration;
        }
        canvas.drawBitmap(poison, poisonX, poisonY, null);

        //carrot
        carrotX -= carrotVelocity;
        if (rabbitModel.checkCollision(carrotX, carrotY, rabbitModel)){
            stateModel.changeScore();
            carrotX = -50;
        }
        if (carrotX < 0) {
            carrotX = canvasWidth + 20;

            carrotY = randomGeneration;
        }

        canvas.drawBitmap(carrot, carrotX, carrotY, null);
    }

    
   



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){

        int eventAction = event.getAction();
        if(eventAction == MotionEvent.ACTION_DOWN){
            //check whether screen is touched
            rabbitModel.setRabbitVelocity(-20);
            //performClick();
        }
        return true;
    }


}
