package JumpingRabbit;

import android.graphics.Bitmap;

/**The superclass of target the rabbit is aim at in the game.
 *
 */

public class TargetModel {
    //the field of TargetModel
    protected static Bitmap image;
    protected static int velocity;
    protected static int imageX;
    protected static int imageY;

    //the constructor of TargetModel
    public TargetModel(Bitmap image, int velocity) {
        this.image = image;
        this.velocity = velocity;
    }

}
