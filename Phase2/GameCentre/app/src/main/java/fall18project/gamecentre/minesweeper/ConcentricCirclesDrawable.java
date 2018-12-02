package fall18project.gamecentre.minesweeper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Code adapted from https://github.com/kgleong/minesweeper.
 */


public class ConcentricCirclesDrawable extends Drawable {

    /**
     * The default ratio between the size of the inner and outer circle
     */
    private static final float DEFAULT_FILL_PERCENT = 0.55f;

    /**
     * The default color of the outer circle
     */
    private static final int DEFAULT_OUTER_RING_COLOR = Color.GREEN;

    /**
     * The default color of the inner circle
     */
    private static final int DEFAULT_INNER_RING_COLOR = Color.YELLOW;

    /**
     * The ratio between the size of the inner and outer circle
     */
    private float fillPercent = DEFAULT_FILL_PERCENT;

    /**
     * A list containing the colors of concentric rings
     */
    private int[] ringColorList;

    /**
     * A Paint object to draw the concentric circles
     */
    private Paint paint;

    private ConcentricCirclesDrawableState drawableState;

    public ConcentricCirclesDrawable() {
        this(null, null);
    }

    /**
     * Creates concentric circles using the supplied color list.
     *
     * @param ringColorList list of colors assigned from the outside
     *                      ring (index 0) to the center ring (index n)
     * @param fillPercent   percent of space this drawable should take up within its bounds.
     */
    public ConcentricCirclesDrawable(int[] ringColorList, Float fillPercent) {
        if (ringColorList == null) {
            this.ringColorList = new int[]{DEFAULT_OUTER_RING_COLOR, DEFAULT_INNER_RING_COLOR};
        } else {
            this.ringColorList = ringColorList;
        }

        if (fillPercent != null) {
            this.fillPercent = fillPercent;
        }

        setupDrawObjects();
        saveConstantState();
    }

    /**
     *
     */
    private void saveConstantState() {
        if (drawableState == null) {
            drawableState = new ConcentricCirclesDrawableState();
            drawableState.mfillPercent = fillPercent;
            drawableState.mRingColorList = ringColorList;
        }
    }

    /**
     *
     */
    private void setupDrawObjects() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();

        float interval = fillPercent / ringColorList.length;

        for (int i = 0; i < ringColorList.length; i++) {
            paint.setColor(ringColorList[i]);
            drawCenteredCircle(bounds, fillPercent - (i * interval), canvas, paint);
        }
    }

    /**
     *
     * @param bounds
     * @param radiusPercentage
     * @param canvas
     * @param paint
     */
    private void drawCenteredCircle(Rect bounds, float radiusPercentage, Canvas canvas, Paint paint) {
        float radius = Math.min(bounds.height(), bounds.width()) * radiusPercentage / 2;
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), radius, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    @Override
    public ConstantState getConstantState() {
        return drawableState;
    }

    private class ConcentricCirclesDrawableState extends ConstantState {
        private float mfillPercent;
        private int[] mRingColorList;

        @Override
        public Drawable newDrawable() {
            return new ConcentricCirclesDrawable(mRingColorList, mfillPercent);
        }

        @Override
        public int getChangingConfigurations() {
            return 0;
        }
    }
}
