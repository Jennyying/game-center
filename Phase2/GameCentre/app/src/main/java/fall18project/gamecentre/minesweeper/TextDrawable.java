package fall18project.gamecentre.minesweeper;


import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

/**
 * Code adapted from https://github.com/kgleong/minesweeper.
 */

public class TextDrawable extends Drawable {
    /**
     *
     */
    static final float DEFAULT_FILL_PERCENT = 0.75f;
    /**
     *
     */
    private String text;
    /**
     *
     */
    private int textColor;
    /**
     *
     */
    private Paint paint;
    /**
     *
     */
    private float fillPercent = DEFAULT_FILL_PERCENT;

    /**
     *
     * @param text
     * @param textColor
     */
    public TextDrawable(String text, int textColor) {
        this(text, textColor, null);
    }

    /**
     *
     * @param text
     * @param textColor
     * @param fillPercent
     */
    public TextDrawable(String text, int textColor, Float fillPercent) {
        this.text = text;
        this.textColor = textColor;

        if (fillPercent != null) {
            this.fillPercent = fillPercent;
        }

        setupDrawObjects();
    }

    /**
     *
     */
    private void setupDrawObjects() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(textColor);
        paint.setFakeBoldText(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    public void draw(Canvas canvas) {
        if (text.length() > 0) {
            Rect bounds = getBounds();

            float textSize = Math.max(bounds.height(), getBounds().width()) * fillPercent;
            paint.setTextSize(textSize);

            // Ascent - The recommended distance above baseline for single spaced text.
            // Descent - The recommended distance below baseline for single spaced text.
            // Top - The maximum distance above the baseline for the tallest glyph in the font at a given text size.
            // Bottom - The maximum distance below the baseline for the lowest glyph in the font at a given text size.
            // Baseline - The horizontal line the text 'sits' on.

            // Calculate recommended height
            float textHeight = Math.abs(paint.ascent()) + Math.abs(paint.descent());

            // Calculate baseline to vertically center text.
            float centeredBaseline = bounds.centerY() + (textHeight / 2) - paint.descent();

            // 3rd argument is the baseline for text.
            canvas.drawText(text, bounds.centerX(), centeredBaseline, paint);
        }
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
}
