package fall18project.gamecentre.minesweeper;

import android.content.Context;

/**
 * Code adapted from https://github.com/kgleong/minesweeper.
 */

public class Graphics {
    /**
     *
     * @param context
     * @param resId
     * @return
     */
    public static int getColor(Context context, int resId) {
        return context.getResources().getColor(resId);
    }

    /**
     *
     * @param sp
     * @param context
     * @return
     */
    public static float spToPx(float sp, Context context) {
        return sp * getScaledDensity(context);
    }

    /**
     *
     * @param dp
     * @param context
     * @return
     */
    public static float dpToPx(float dp, Context context) {
        return dp * getDensity(context);
    }

    /**
     *
     * @param context
     * @return
     */
    private static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     *
     * @param context
     * @return
     */
    private static float getScaledDensity(Context context) {
        return context.getResources().getDisplayMetrics().scaledDensity;
    }
}
