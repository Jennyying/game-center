package fall18project.gamecentre.minesweeper;

import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StateSet;

/**
 * Code adapted from https://github.com/kgleong/minesweeper.
 */

public class TileButton extends AppCompatButton {
    /**
     *
     */
    static final int COLOR_OFFSET = 40;
    /**
     *
     */
    public static String TAG = TileButton.class.getName();

    /**
     *
     * @param context
     * @param attrs
     */
    public TileButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TileDrawable.TileAttributeSet beveledAttributes =
                TileDrawable.extractAttributes(context, attrs);

        try {
            setBackground(createBackground(beveledAttributes));

        } catch (Exception e) {
            Log.e(TAG, e.getClass().getName());
        }
    }

    /**
     *
     * @param attributeSet
     * @return
     */
    private StateListDrawable createBackground(TileDrawable.TileAttributeSet attributeSet) {
        StateListDrawable drawable = new StateListDrawable();

        TileDrawable normalDrawable = new TileDrawable(attributeSet.getColorArray(), attributeSet.getFillPercent());

        TileDrawable.TileAttributeSet pressedAttributeSet = createPressedAttributes(attributeSet);
        TileDrawable pressedDrawable = new TileDrawable(pressedAttributeSet.getColorArray(), pressedAttributeSet.getFillPercent());

        drawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        drawable.addState(new int[]{android.R.attr.state_hovered}, pressedDrawable);
        drawable.addState(StateSet.WILD_CARD, normalDrawable);
        return drawable;
    }

    /**
     *
     * @param attributeSet
     * @return
     */
    private TileDrawable.TileAttributeSet createPressedAttributes(TileDrawable.TileAttributeSet attributeSet) {

        int[] colorList = attributeSet.getColorArray();
        int[] pressedColorList = new int[TileDrawable.REQUIRED_COLOR_COUNT];

        for (int i = 0; i < colorList.length; i++) {
            pressedColorList[i] = colorList[i] + COLOR_OFFSET;
        }

        return new TileDrawable.TileAttributeSet(pressedColorList, attributeSet.getFillPercent());
    }
}