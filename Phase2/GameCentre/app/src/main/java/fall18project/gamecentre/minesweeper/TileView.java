package fall18project.gamecentre.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.view.View;

import com.squareup.otto.Bus;

import java.util.HashMap;
import java.util.Map;


public class TileView extends View {

    /**
     * The Tile states.
     */
    public static final int COVERED = 0;
    public static final int IS_MINE = 1;
    public static final int UNCOVERED = 2;

    /**
     * The user gestures.
     */
    public static final int CLICK = 0;
    public static final int LONG_CLICK = 1;

    /**
     * A Level List Drawable for the covered and uncovered states.
     */
    private LevelListDrawable drawableContainer;

    /**
     * The x coordinate of the tile.
     */
    private int xCoord;

    /**
     * The y coordinate of the tile.
     */
    private int yCoord;

    /**
     * A bus to share information between different activities.
     */
    private Bus gameBus;

    /**
     * A HashMap that maps the number of adjacent mines to colors.
     */

    static Map<Integer, Integer> adjacentMinesToColor = new HashMap<>();

    /**
     * The color mapping.
     */
    static {
        adjacentMinesToColor.put(1, Color.RED);
        adjacentMinesToColor.put(2, Color.BLUE);
        adjacentMinesToColor.put(3, Color.GREEN);
        adjacentMinesToColor.put(4, Color.GRAY);
        adjacentMinesToColor.put(5, Color.DKGRAY);
        adjacentMinesToColor.put(6, Color.CYAN);
        adjacentMinesToColor.put(7, Color.YELLOW);
        adjacentMinesToColor.put(8, Color.MAGENTA);
    }

    /**
     * Setting up the Tile view.
     *
     * @param context the context
     * @param xCoord  the x coordinate of the associated tile
     * @param yCoord  the y coordinate of the associated tile
     */
    public TileView(Context context, int xCoord, int yCoord) {
        super(context);

        this.xCoord = xCoord;
        this.yCoord = yCoord;

        init();
    }

    private void init(){
        gameBus = MainApp.getGameBus();
        
    }

}
