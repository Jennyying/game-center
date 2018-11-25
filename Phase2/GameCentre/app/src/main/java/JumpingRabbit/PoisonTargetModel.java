package JumpingRabbit;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class PoisonTargetModel extends TargetModel {
    private static int numLife;

    public PoisonTargetModel(Bitmap image, int velocity){
        super(image, velocity);
        int numLife = 3;
    }

    public static void generate(Canvas canvas) {
        int randomGeneration = (int) Math.floor(Math.random() * (RabbitModel.getMaxRabbitY() - RabbitModel.minRabbitY)) + RabbitModel.minRabbitY;
        imageX -= velocity;
        if (RabbitModel.checkCollision(imageX, imageY, rabbitModel)){
            imageX = -20;
            numLife --;
            if(numLife == 0){
                //TODO: make a game over layout

            }
        }
        if (imageX < 0){
            imageX = canvas.getWidth() + 100;
            imageY = randomGeneration;
        }
        canvas.drawBitmap(image, imageX, imageY, null);
    }
}
