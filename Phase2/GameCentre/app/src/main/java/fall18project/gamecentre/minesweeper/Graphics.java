package fall18project.gamecentre.minesweeper;

import android.content.Context;

/**
 * Code adapted from https://github.com/kgleong/minesweeper.
 */

public class Graphics {

    public static int getColor(Context context, int resId) {
        return context.getResources().getColor(resId);
    }

    public static float spToPx(float sp, Context context) {
        return sp * getScaledDensity(context);
    }

    public static float dpToPx(float dp, Context context) {
        return dp * getDensity(context);
    }

    private static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    private static float getScaledDensity(Context context) {
        return context.getResources().getDisplayMetrics().scaledDensity;
    }
}
