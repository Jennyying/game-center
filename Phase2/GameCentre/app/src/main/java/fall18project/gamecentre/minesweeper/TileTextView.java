package fall18project.gamecentre.minesweeper;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;


public class TileTextView extends AppCompatTextView {
    public static String TAG = TileTextView.class.getName();

    public TileTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TileDrawable.TileAttributeSet attributes =
                TileDrawable.extractAttributes(context, attrs);

        try {
            TileDrawable drawable =
                    new TileDrawable(attributes.getColorArray(), attributes.getFillPercent());

            setBackground(drawable);
        } catch (Exception e) {
            Log.e(TAG, e.getClass().getName());
        }
    }
}
