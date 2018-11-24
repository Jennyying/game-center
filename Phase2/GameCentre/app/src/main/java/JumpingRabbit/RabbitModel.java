package JumpingRabbit;

import android.graphics.Bitmap;

public class RabbitModel {
    private static Bitmap rabbit;
    private static int rabbitVelocity;

    public static int getRabbitX() {
        return rabbitX;
    }

    private static int rabbitX = 10;
    private static int rabbitY;
    static int minRabbitY = 150;
    static int maxRabbitY;
    //        = canvasHeight - 100;
    public RabbitModel(int maxRabbitY){
        this.maxRabbitY = maxRabbitY;
    }

    public static Bitmap getRabbit() {
        return rabbit;
    }
    public void setRabbit(Bitmap rabbit){
        RabbitModel.rabbit = rabbit;
    }

    public void setRabbitY(int rabbitY){
        RabbitModel.rabbitY = rabbitY;
    }

    public static int getRabbitY(){
        return rabbitY;
    }

    public static int getMinRabbitY() {
        return minRabbitY;
    }

    public static void addRabbitVelocity(int num) {
        rabbitVelocity += num;
    }

    public static int getMaxRabbitY() {

        return maxRabbitY;
    }

    public int getRabbitVelocity() {
        return rabbitVelocity;
    }

}
