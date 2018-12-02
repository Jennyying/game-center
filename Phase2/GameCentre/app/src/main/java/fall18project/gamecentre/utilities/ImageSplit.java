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
     * @param sideLength the side length of the board
     * @return an ArrayList of Bitmaps
     */
    public static ArrayList<BitmapDrawable> split(Context context, Bitmap image, int sideLength) {

        int height, width;

        ArrayList<BitmapDrawable> images =
                new ArrayList<>(sideLength * sideLength);

        height = image.getHeight() / sideLength;
        width = image.getWidth() / sideLength;

        int yCoord = 0;
        for (int x = 0; x < sideLength; x++) {
            int xCoord = 0;
            for (int y = 0; y < sideLength; y++) {
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
