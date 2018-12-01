package fall18project.gamecentre.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageSplit {

    /**
     * Split a given image into chunks for the board
     * Adapted from code by Alexios Karapetsas at
     * https://stackoverflow.com/questions/29783308/androidhow-to-divide-an-image-without-using-canvas
     *
     * @param context the context to use for creating new Drawables
     * @param image the image
     * @param sidelength the side length of the board
     * @return an ArrayList of Bitmaps
     */
    public static ArrayList<BitmapDrawable> split(Context context, Bitmap image, int sidelength) {

        int height, width;

        ArrayList<BitmapDrawable> images =
                new ArrayList<>(sidelength * sidelength);

        height = image.getHeight() / sidelength;
        width = image.getWidth() / sidelength;

        int yCoord = 0;
        for (int x = 0; x < sidelength; x++) {
            int xCoord = 0;
            for (int y = 0; y < sidelength; y++) {
                images.add(new BitmapDrawable(
                        context.getResources(),
                        Bitmap.createBitmap(image, xCoord, yCoord, width, height))
                );
                xCoord += width;
            }
            yCoord += height;
        }
        return images;
    }
}
