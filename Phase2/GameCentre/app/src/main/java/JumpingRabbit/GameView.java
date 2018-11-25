package JumpingRabbit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import fall18project.gamecentre.R;


public class GameView extends View {
    //canvas
    private Canvas canvas;
    private int canvasHeight;
    private int canvasWidth;
    //rabbit
    private RabbitModel rabbitModel;
    //poison(velocity = 25)
    private PoisonTargetModel poisonTargetModel;
    //carrot(velocity = 17)
    private CarrotTargetModel carrotTargetModel;

    private StateModel stateModel;


    public GameView(Context context) {
        super(context);
        rabbitModel.setRabbit(BitmapFactory.decodeResource(getResources(), R.drawable.rabbit));
        stateModel.setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        carrotTargetModel.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.carrot));
        poisonTargetModel.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.poison));
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
        PoisonTargetModel.generate(canvas);


        //carrot
       CarrotTargetModel.generate(canvas);
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
