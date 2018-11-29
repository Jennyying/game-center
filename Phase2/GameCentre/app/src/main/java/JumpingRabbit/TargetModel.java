package JumpingRabbit;

import android.graphics.Bitmap;

/**The superclass of target the rabbit is aim at in the game.
 *
 */

public class TargetModel {

    //the field of TargetModel
    protected static Bitmap image;

    public static int getVelocity() {
        return velocity;
    }

    protected static int velocity;
    protected static int imageX;
    protected static int imageY;

    public static void setImage(Bitmap image) {
        TargetModel.image = image;
    }

}
