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

import fall18project.gamecentre.R;

/**
 * Code adapted from https://github.com/kgleong/minesweeper.
 */


public class TileView extends View {

    /**
     * The Tile states.
     */
    public static final int COVERED = 0;
    public static final int FlAGGED_AS_MINE = 1;
    public static final int UNCOVERED = 2;

    /**
     * The user gestures.
     */
    public static final int CLICK = 0;
    public static final int LONG_CLICK = 1;
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

        gameBus = GameActivity.getGameBus();
        setupDrawableBackgrounds();
        setupListeners();
    }

    /**
     *
     */
    private void setupListeners() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Notify the game that the user has performed an action on this tile.
                gameBus.post(new Game.TileViewActionEvent(TileView.this, CLICK));
            }
        });

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // Notify the game that the user has performed an action on this tile.
                gameBus.post(new Game.TileViewActionEvent(TileView.this, LONG_CLICK));

                // Return true to consume event.
                return true;
            }
        });
    }

    /**
     *
     */
    private void setupDrawableBackgrounds() {
        Drawable coveredTile = setupCoveredTile();
        LayerDrawable flaggedMineDrawable = new LayerDrawable(new Drawable[]{coveredTile, new ConcentricCirclesDrawable()});

        drawableContainer = new LevelListDrawable();
        drawableContainer.addLevel(0, COVERED, coveredTile);
        drawableContainer.addLevel(0, FlAGGED_AS_MINE, flaggedMineDrawable);

        setBackground(drawableContainer);
    }

    /**
     *
     * @return
     */
    private Drawable setupCoveredTile() {
        int colorInner = Graphics.getColor(getContext(), R.color.blue_grey_200);
        int colorTop = Graphics.getColor(getContext(), R.color.blue_grey_300);
        int colorLeft = Graphics.getColor(getContext(), R.color.blue_grey_400);
        int colorBottom = Graphics.getColor(getContext(), R.color.blue_grey_500);
        int colorRight = Graphics.getColor(getContext(), R.color.blue_grey_600);

        int[] tileColors = new int[]{colorInner, colorLeft, colorTop, colorRight, colorBottom};

        return new TileDrawable(tileColors, null);
    }

    /**
     *
     * @param tile
     */
    public void setupUncoveredTileDrawable(Tile tile) {
        Drawable uncoveredDrawable;

        if (tile != null && tile.containsMine()) {
            uncoveredDrawable = new ConcentricCirclesDrawable(new int[]{Color.RED, Color.BLACK}, 0.50f);
        } else {
            String adjacentMineCountText;
            int textColor = 0;

            if (tile == null) {
                adjacentMineCountText = "";
            } else {
                int adjacentMinesCount = tile.getAdjacentMines();

                textColor = adjacentMinesToColor.get(adjacentMinesCount);
                adjacentMineCountText = String.valueOf(adjacentMinesCount);
            }
            uncoveredDrawable = new TextDrawable(adjacentMineCountText, textColor);
        }
        drawableContainer.addLevel(0, UNCOVERED, uncoveredDrawable);
    }

    /**
     * The getter for xCoord.
     *
     * @return the x coordinate of the associated tile
     */
    public int getXCoord() {
        return xCoord;
    }

    /**
     * The getter for yCoord.
     *
     * @return the y coordinate of the associated tile
     */
    public int getYCoord() {
        return yCoord;
    }

    /**
     * The getter for the state of drawableContainer (indicating if the tile is covered or uncovered)
     *
     * @return the level
     */
    public int getState() {
        return drawableContainer.getLevel();
    }

    /**
     * The setter for the state of drawableContainer
     *
     * @param state the state.
     */
    public void setState(int state) {
        drawableContainer.setLevel(state);
    }


}


