package fall18project.gamecentre.minesweeper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.squareup.otto.Bus;

import fall18project.gamecentre.R;

/**
 * Code adapted from https://github.com/kgleong/minesweeper.
 */


public class BoardView extends ViewGroup {
    /**
     * The default line color.
     */
    static final int LINE_COLOR = Color.BLACK;

    /**
     * The border witdh of the board.
     */
    static final int BORDER_WIDTH = 2;

    /**
     * The gridline width.
     */
    static final int GRIDLINE_WIDTH = 1;

    /**
     * The paint for the grid lines
     */
    private Paint gridlinesPaint;
    /**
     * The gridline color.
     */
    private int gridlineColor;
    /**
     * The gridline stroke width.
     */
    private float gridlinesStrokeWidth;
    /**
     * The paint for the border.
     */
    private Paint borderPaint;

    /**
     * The border color.
     */
    private int borderColor;

    /**
     * The border stroke width.
     */
    private float borderStrokeWidth;

    /**
     * The associated board.
     */
    private Board board;

    /**
     * The bus for sharing data
     */
    private Bus gameBus;

    /**
     * The BoardView constructor
     *
     * @param context      the context
     * @param attributeSet an AttributeSet for the boardview xml files.
     */
    public BoardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        gameBus = GameActivity.getGameBus();
        setWillNotDraw(false);
        extractAttributes(attributeSet);
        setupDrawObjects();
    }

    /**
     * Extract attributes from attributeSet and set the boardview attributes to the associated
     * attributes.
     *
     * @param attributeSet the attribute set.
     */
    private void extractAttributes(AttributeSet attributeSet) {
        TypedArray attributesArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.BoardView);
        // Convert default values to pixels
        float defaultBorderStrokeWidthInPx = Graphics.dpToPx(BORDER_WIDTH, getContext());
        float defaultGridLineStrokeWidthInPx = Graphics.dpToPx(GRIDLINE_WIDTH, getContext());

        try {
            gridlineColor = attributesArray.getColor(
                    R.styleable.BoardView_gridLineColor, LINE_COLOR);

            gridlinesStrokeWidth = attributesArray.getDimension(
                    R.styleable.BoardView_gridLineWidth, defaultGridLineStrokeWidthInPx);

            borderColor = attributesArray.getColor(
                    R.styleable.BoardView_borderColor, LINE_COLOR);

            borderStrokeWidth = attributesArray.getDimension(
                    R.styleable.BoardView_borderWidth, defaultBorderStrokeWidthInPx);
        } finally {
            attributesArray.recycle();
        }

    }

    /**
     * Set up the style and color information for the grid lines and the borders in the board.
     */
    private void setupDrawObjects() {
        gridlinesPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gridlinesPaint.setColor(gridlineColor);
        gridlinesPaint.setStyle(Paint.Style.STROKE);
        gridlinesPaint.setStrokeWidth(gridlinesStrokeWidth);

        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderStrokeWidth);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();

        int dimension = board.getDimension();
        int interval = Math.min(getMeasuredWidth(), getMeasuredHeight()) / dimension;

        // Place all tiles
        for (int i = 0; i < childCount; i++) {
            TileView tileView = (TileView) getChildAt(i);

            int top = (i / dimension) * interval;
            int bottom = top + interval;
            int left = (i % dimension) * interval;
            int right = left + interval;

            tileView.layout(left, top, right, bottom);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        // Ensure the board is a square
        int dimension = Math.min(width, height);

        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        drawGridLines(width, height, canvas);
        drawBorder(width, height, canvas);
    }

    /**
     * Draw the border on the canvas's associated with the board
     *
     * @param width  the width
     * @param height the height
     * @param canvas the canvas
     */
    private void drawBorder(int width, int height, Canvas canvas) {
        canvas.drawRect(0, 0, width, height, borderPaint);
    }

    /**
     * Draw on the gridlines on the canvas's associated with the board
     *
     * @param width  the width
     * @param height the height
     * @param canvas the canvas
     */
    private void drawGridLines(int width, int height, Canvas canvas) {
        if (board != null) {
            int dimension = board.getDimension();
            int interval = height / dimension;

            float startX = 0;
            float startY;
            float endX = width;
            float endY;

            // Horizontal lines
            for (int i = 1; i < dimension; i++) {
                startY = endY = interval * i;
                canvas.drawLine(startX, startY, endX, endY, gridlinesPaint);
            }

            startY = 0;
            endY = height;

            // Vertical lines
            for (int i = 1; i < dimension; i++) {
                startX = endX = interval * i;
                canvas.drawLine(startX, startY, endX, endY, gridlinesPaint);
            }
        }
    }

    /**
     * Setup the tile views.
     *
     * @param board the board associated with the boardview.
     */

    public void setupBoard(Board board) {
        this.board = board;

        // Clear old tiles
        this.removeAllViews();

        // Create new set of tiles
        createTileViews();
    }

    /**
     * Create the tile views.
     */
    private void createTileViews() {
        int dimension = board.getDimension();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                TileView tileView = new TileView(getContext(), j, i);

                addView(tileView);

                // Notify the game that a new tile has been created.
                gameBus.post(new Game.TileViewCreatedEvent(tileView));
            }
        }

    }

}
