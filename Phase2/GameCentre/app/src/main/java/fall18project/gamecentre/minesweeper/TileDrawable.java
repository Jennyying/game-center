package fall18project.gamecentre.minesweeper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import java.util.Arrays;
import java.util.List;

import fall18project.gamecentre.R;

/**
 * Code adapted from https://github.com/kgleong/minesweeper.
 */

public class TileDrawable extends Drawable {
    /**
     *
     */
    public static final int REQUIRED_COLOR_COUNT = 5;
    private static final float DEFAULT_FILL_PERCENT = 0.75f;
    private static final int INNER_RECT_COLOR_INDEX = 0;
    private static final int LEFT_BEVEL_COLOR_INDEX = 1;
    private static final int TOP_BEVEL_COLOR_INDEX = 2;
    private static final int RIGHT_BEVEL_COLOR_INDEX = 3;
    private static final int BOTTOM_BEVEL_COLOR_INDEX = 4;
    private float fillPercent = DEFAULT_FILL_PERCENT;
    /**
     *
     */
    private int[] colorList;
    private Paint paint;
    /**
     *
     */
    private Path topBevelPath;
    private Path leftBevelPath;
    private Path bottomBevelPath;
    private Path righBevelPath;
    /**
     *
     */
    private TileDrawableState drawableState;

    /**
     *
     * @param colorList
     * @param fillPercent
     */
    public TileDrawable(int[] colorList, Float fillPercent) {
        if (fillPercent != null) {
            this.fillPercent = fillPercent;
        }

        this.colorList = colorList;
        setupDrawObjects();
        saveConstantState();
    }

    /**
     *
     * @param context
     * @param attrs
     * @return
     */
    public static TileAttributeSet extractAttributes(Context context, AttributeSet attrs) {
        TileAttributeSet tileAttributeSet = null;

        TypedArray attributesArray = context.obtainStyledAttributes(attrs, R.styleable.TileView);

        try {
            int[] colorList = new int[TileDrawable.REQUIRED_COLOR_COUNT];

            colorList[INNER_RECT_COLOR_INDEX] = attributesArray.getColor(R.styleable.TileView_innerRectColor, -1);
            colorList[LEFT_BEVEL_COLOR_INDEX] = attributesArray.getColor(R.styleable.TileView_leftBevelColor, -1);
            colorList[TOP_BEVEL_COLOR_INDEX] = attributesArray.getColor(R.styleable.TileView_topBevelColor, -1);
            colorList[RIGHT_BEVEL_COLOR_INDEX] = attributesArray.getColor(R.styleable.TileView_rightBevelColor, -1);
            colorList[BOTTOM_BEVEL_COLOR_INDEX] = attributesArray.getColor(R.styleable.TileView_bottomBevelColor, -1);
            float fillPercent = attributesArray.getFloat(R.styleable.TileView_fillPercentage, -1);

            tileAttributeSet = new TileAttributeSet(colorList, fillPercent);
        } finally {
            attributesArray.recycle();
            return tileAttributeSet;
        }
    }

    /**
     *
     */
    private void saveConstantState() {
        if (drawableState == null) {
            drawableState = new TileDrawableState();
            drawableState.mColorList = colorList;
            drawableState.mFillPercent = fillPercent;
        }
    }

    /**
     *
     */
    private void setupDrawObjects() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        topBevelPath = new Path();
        leftBevelPath = new Path();
        bottomBevelPath = new Path();
        righBevelPath = new Path();
    }

    @Override
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();

        Rect innerRect = createInnerRect(bounds);

        // Draw inner rectangle
        paint.setColor(colorList[INNER_RECT_COLOR_INDEX]);
        canvas.drawRect(innerRect, paint);

        // Bounds rect
        float[] topLeft = new float[]{bounds.left, bounds.top};
        float[] topRight = new float[]{bounds.right, bounds.top};
        float[] bottomRight = new float[]{bounds.right, bounds.bottom};
        float[] bottomLeft = new float[]{bounds.left, bounds.bottom};

        // Inner rect
        float[] innerTopLeft = new float[]{innerRect.left, innerRect.top};
        float[] innerTopRight = new float[]{innerRect.right, innerRect.top};
        float[] innerBottomRight = new float[]{innerRect.right, innerRect.bottom};
        float[] innerBottomLeft = new float[]{innerRect.left, innerRect.bottom};

        // Draw left bevel
        setBevelPath(leftBevelPath, topLeft, innerTopLeft, innerBottomLeft, bottomLeft);
        paint.setColor(colorList[LEFT_BEVEL_COLOR_INDEX]);
        canvas.drawPath(leftBevelPath, paint);

        // Draw top bevel
        setBevelPath(topBevelPath, topLeft, topRight, innerTopRight, innerTopLeft);
        paint.setColor(colorList[TOP_BEVEL_COLOR_INDEX]);
        canvas.drawPath(topBevelPath, paint);

        // Draw right bevel
        setBevelPath(righBevelPath, innerTopRight, topRight, bottomRight, innerBottomRight);
        paint.setColor(colorList[RIGHT_BEVEL_COLOR_INDEX]);
        canvas.drawPath(righBevelPath, paint);

        // Draw bottom bevel
        setBevelPath(bottomBevelPath, innerBottomLeft, innerBottomRight, bottomRight, bottomLeft);
        paint.setColor(colorList[BOTTOM_BEVEL_COLOR_INDEX]);
        canvas.drawPath(bottomBevelPath, paint);
    }

    /**
     *
     * @param bounds
     * @return
     */
    private Rect createInnerRect(Rect bounds) {
        float height = bounds.height();
        float width = bounds.width();

        float innerHeight = height * fillPercent;
        float innerWidth = width * fillPercent;

        int left = (int) ((width - innerWidth) / 2);
        int top = (int) ((height - innerHeight) / 2);
        int bottom = (int) (top + innerHeight);
        int right = (int) (left + innerWidth);

        return new Rect(left, top, right, bottom);
    }

    /**
     *
     * @param path
     * @param topLeft
     * @param topRight
     * @param bottomRight
     * @param bottomLeft
     */
    private void setBevelPath(Path path,
                              float[] topLeft,
                              float[] topRight,
                              float[] bottomRight,
                              float[] bottomLeft) {

        path.moveTo(topLeft[0], topLeft[1]);
        List<float[]> vertexList = Arrays.asList(topRight, bottomRight, bottomLeft, topLeft);

        for (float[] vertex : vertexList) {
            path.lineTo(vertex[0], vertex[1]);
        }
        path.close();
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

    @Override
    public ConstantState getConstantState() {
        return drawableState;
    }

    /**
     *
     */
    public static class TileAttributeSet {
        int[] colorArray;
        Float fillPercent;

        public TileAttributeSet(int[] colorArray, float fillPercent) {
            boolean allColorsPresent = true;

            for (int color : colorArray) {
                if (color == -1) {
                    allColorsPresent = false;
                    break;
                }
            }

            if (allColorsPresent) {
                this.colorArray = colorArray;
            }

            if (fillPercent > 0) {
                this.fillPercent = fillPercent;
            }
        }

        /**
         *
         * @return
         */
        public int[] getColorArray() {
            return colorArray;
        }

        /**
         *
         * @return
         */
        public Float getFillPercent() {
            return fillPercent;
        }
    }

    /**
     *
     */
    private class TileDrawableState extends ConstantState {
        int[] mColorList;
        float mFillPercent;

        @Override
        public Drawable newDrawable() {
            try {
                return new TileDrawable(mColorList, mFillPercent);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        public int getChangingConfigurations() {
            return 0;
        }
    }
}
