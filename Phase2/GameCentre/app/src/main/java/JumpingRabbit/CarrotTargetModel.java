package JumpingRabbit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import fall18project.gamecentre.R;

import static android.support.v4.graphics.drawable.IconCompat.getResources;

public class CarrotTargetModel extends TargetModel {
    public static int score = 0;

    public CarrotTargetModel(Bitmap image, int velocity) {
        super(image, velocity);
    }

    public static void generate(Canvas canvas) {
        int randomGeneration = (int) Math.floor(Math.random() * (RabbitModel.getMaxRabbitY() -
                RabbitModel.getMinRabbitY())) + RabbitModel.getMinRabbitY();
        imageX -= velocity;
        if (RabbitModel.checkCollision(imageX, imageY)) {
            score += 1;
            imageX = -20;
        }
        if (imageX < 0) {
            imageX = canvas.getWidth() + 20;

            imageY = randomGeneration;
        }

        canvas.drawBitmap(image, imageX, imageY, null);
    }

}
