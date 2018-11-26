package fall18project.gamecentre.utilities;
import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

public class ImageSplit {

    /**
     * Split a given image into chunks for the board
     * Adapted from code by Alexios Karapetsas at
     * https://stackoverflow.com/questions/29783308/androidhow-to-divide-an-image-without-using-canvas
     * @param image the image
     * @param sidelength the side length of the board
     * @return an ArrayList of Bitmaps
     */
    public static ArrayList split(ImageView image, int sidelength) {

        int height, width;

        ArrayList<Bitmap> images = new ArrayList<Bitmap>(sidelength * sidelength);

        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        height = bitmap.getHeight() / sidelength;
        width = bitmap.getWidth() / sidelength;

        int yCoord = 0;
        for (int x = 0; x < sidelength; x++) {
            int xCoord = 0;
            for (int y = 0; y < sidelength; y++) {
                images.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, width, height));
                xCoord += width;
            }
            yCoord += height;
        }
        return images;
    }
}
