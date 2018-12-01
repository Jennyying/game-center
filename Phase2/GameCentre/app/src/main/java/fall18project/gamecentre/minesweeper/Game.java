package fall18project.gamecentre.minesweeper;

import com.squareup.otto.Subscribe;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Code adapted from https://github.com/kgleong/minesweeper.
 */


public class Game {

    public static int DEFAULT_MAX_UNDOS = 3;

    private GameManager gameManager;
    /**
     * The board being played.
     */
    private Board board;

    /**
     * The current queue of uncovered tiles
     */
    private TileView[] queue = new TileView[DEFAULT_MAX_UNDOS];

    /**
     * The position in the queue
     */
    private int queuePos = 0;

    /**
     * The amount of undos remaining
     */
    private int undos = DEFAULT_MAX_UNDOS;

    /**
     * A representation of the board that contains the tiles.
     */
    private Tile[][] boardGrid;

    /**
     * The tile view version of boardGrid.
     */
    private TileView[][] tileViewsGrid;

    /**
     * The game state.
     */
    private boolean gameFinished = false;
    private long startTime;
    private long elapsedTime = 0;

    /**
     * The number of unused remaining flags.
     */

    private int numFlagsRemaining;

    public Game(GameManager gameManager, Board board) {
        if (gameManager != null && board != null) {
            this.board = board;
            this.boardGrid = board.getBoardGrid();
            this.gameManager = gameManager;

            init();
        }

    }

    /**
     * Undo a move consisting of uncovering the tile passed in
     *
     * @param uncovered tile which was uncovered
     */
    private void undoMove(TileView uncovered) {
        coverAdjacentBlankTiles(uncovered);
    }

    /**
     * Undo a move
     */
    public void undoMove() {
        if (undos > 0 && queuePos > 0) {
            undoMove(queue[--queuePos % DEFAULT_MAX_UNDOS]);
            undos--;
            gameFinished = false;
        }
    }

    /**
     * Get how many undos are remaining
     *
     * @return how many undos are remaining
     */
    public int getUndos() {
        return undos;
    }

    public void setUndos(int undos) {
        this.undos = undos;
    }

    /**
     * Initialize the board, register this Game to the event bus and publish the initial game
     * statistics (flags remaining and elapsed time) to the bus
     */
    private void init() {
        int dimension = board.getDimension();

        numFlagsRemaining = board.getNumMines();
        tileViewsGrid = new TileView[dimension][dimension];

        // Register to receive game state change events
        GameActivity.getGameBus().register(this);

        // Publish initial stats
        gameManager.publishFlagsRemainingCount(numFlagsRemaining);
        gameManager.publishElapsedTime(elapsedTime);
    }

    /**
     * Unregister this Game from the event bus
     */
    public void unregisterFromEventBus() {
        GameActivity.getGameBus().unregister(this);
    }

    /**
     * Set the start time to current time.
     */
    public void startTimer() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Calculate the elapsed time.
     */
    public void stopTimer() {
        if (startTime > 0) {
            elapsedTime += System.currentTimeMillis() - startTime;

            //Reset timer
            startTime = 0;
        }
    }

    /**
     * Reveals the mines on the board and computes the result of the game
     */
    public void finishGame() {
        boolean won = true;
        int dimension = boardGrid.length;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                TileView tileView = tileViewsGrid[i][j];
                Tile tile = boardGrid[i][j];

                int state = tileView.getState();
                boolean containsMine = tile != null && tile.containsMine();

                if (state == TileView.FlAGGED_AS_MINE) {
                    if (!containsMine) {
                        /* Uncover the flags that don't contain mines. */
                        tileView.setState(TileView.UNCOVERED);
                    }
                } else if (state == TileView.COVERED) {
                    if (containsMine) {
                        won = false;
                    }
                    /* Uncover all tiles without flags. */
                    tileView.setState(TileView.UNCOVERED);
                }
            }
        }
        publishResult(won);
    }

    /**
     * Display the result of the game on the screen.
     */
    private void publishResult(boolean won) {
        if (!gameFinished) {
            gameFinished = true;
            gameManager.publishGameFinished();

            if (won) {
                gameManager.publishWin();
            } else {
                gameManager.publishLoss();
            }
        }

    }

    /**
     * Search for and uncover the blank tiles adjacent to the tile
     * represeted by tileView.
     *
     * @param tileView the given tileView.
     */
    public void uncoverAdjacentBlankTiles(TileView tileView) {
        int x = tileView.getXCoord();
        int y = tileView.getYCoord();

        Tile tile = boardGrid[y][x];

        // A null tile represents a tile with no adjacent mines
        if (tile == null) {
            /* BFS search */
            Set<TileView> visited = new HashSet<>();
            Stack<TileView> queue = new Stack<>();

            visited.add(tileView);
            queue.add(tileView);

            int dimension = boardGrid.length;

            while (!queue.empty()) {
                TileView currentTile = queue.pop();

                x = currentTile.getXCoord();
                y = currentTile.getYCoord();

                int xStart = Math.max(0, x - 1);
                int yStart = Math.max(0, y - 1);

                for (int i = xStart; i < dimension && i <= x + 1; i++) {
                    for (int j = yStart; j < dimension && j <= y + 1; j++) {
                        TileView adjacentTileView = tileViewsGrid[j][i];
                        Tile adjacentTile = boardGrid[j][i];

                        boolean added = visited.add(adjacentTileView);

                        if (added && adjacentTile == null) {
                            adjacentTileView.setState(TileView.UNCOVERED);
                            queue.add(adjacentTileView);
                        }
                    }
                }
            }
        }

    }

    /**
     * Search for and cover the blank tiles adjacent to the (uncovered) tile
     * represeted by tileView.
     *
     * @param tileView the given tileView.
     */
    public void coverAdjacentBlankTiles(TileView tileView) {

        int x = tileView.getXCoord();
        int y = tileView.getYCoord();

        Tile tile = boardGrid[y][x];

        // A null tile represents a tile with no adjacent mines
        if (tile == null) {
            /* BFS search */
            Set<TileView> visited = new HashSet<>();
            Stack<TileView> queue = new Stack<>();

            visited.add(tileView);
            queue.add(tileView);

            int dimension = boardGrid.length;

            while (!queue.empty()) {
                TileView currentTile = queue.pop();

                x = currentTile.getXCoord();
                y = currentTile.getYCoord();

                int xStart = Math.max(0, x - 1);
                int yStart = Math.max(0, y - 1);

                for (int i = xStart; i < dimension && i <= x + 1; i++) {
                    for (int j = yStart; j < dimension && j <= y + 1; j++) {
                        TileView adjacentTileView = tileViewsGrid[j][i];
                        Tile adjacentTile = boardGrid[j][i];

                        boolean added = visited.add(adjacentTileView);

                        if (added && adjacentTile == null) {
                            adjacentTileView.setState(TileView.COVERED);
                            queue.add(adjacentTileView);
                        }
                    }
                }
            }
        }

        tileView.setState(TileView.COVERED);

    }

    public long getElapsedTime() {
        // If the timer has been started, then add the time since it was started
        // to the saved elapsed time.
        long additionalRealTime = 0;

        if (startTime > 0) {
            additionalRealTime = System.currentTimeMillis() - startTime;
        }
        return elapsedTime + additionalRealTime;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public int getMineFlagsRemainingCount() {
        return numFlagsRemaining;
    }

    @Subscribe
    public void onTileCreated(TileViewCreatedEvent event) {
        TileView tileView = event.mTileView;

        int x = tileView.getXCoord();
        int y = tileView.getYCoord();

        tileViewsGrid[y][x] = tileView;

        // Set the uncovered graphic for the TileView.
        tileView.setupUncoveredTileDrawable(boardGrid[y][x]);
    }

    @Subscribe
    public void onTileViewAction(TileViewActionEvent event) {
        if (!gameFinished) {
            boolean isAllowed = false;

            int action = event.mAction;
            TileView tileView = event.mTileView;

            int state = -1;

            switch (action) {
                // Toggling mine flag on a tile
                case TileView.CLICK:
                    switch (tileView.getState()) {
                        case TileView.COVERED:
                            // Add a flag
                            if (numFlagsRemaining > 0) {
                                state = TileView.FlAGGED_AS_MINE;
                                isAllowed = true;

                                gameManager.publishFlagsRemainingCount(--numFlagsRemaining);
                            }
                            break;
                        case TileView.FlAGGED_AS_MINE:
                            // Remove a flag
                            state = TileView.COVERED;
                            isAllowed = true;

                            gameManager.publishFlagsRemainingCount(++numFlagsRemaining);
                            break;
                    }
                    break;

                // Uncovering a tile
                case TileView.LONG_CLICK:
                    if (tileView.getState() == TileView.COVERED) {

                        // Append this to the queue of uncovered tiles
                        queue[queuePos++ % DEFAULT_MAX_UNDOS] = tileView;

                        // Even if a player loses, uncover the tile.
                        state = TileView.UNCOVERED;
                        isAllowed = true;

                        int x = tileView.getXCoord();
                        int y = tileView.getYCoord();
                        Tile tile = boardGrid[y][x];

                        if (tile == null) {
                            uncoverAdjacentBlankTiles(tileView);
                        } else {
                            // If tile is over a square that contains a mine, player loses.
                            if (tile.containsMine()) {
                                publishResult(false);
                            }
                        }
                    }
                    break;
            }

            if (isAllowed) {
                tileView.setState(state);
            }
        }
    }

    public static class TileViewCreatedEvent {
        TileView mTileView;

        public TileViewCreatedEvent(TileView tileView) {
            mTileView = tileView;
        }
    }

    public static class TileViewActionEvent {
        TileView mTileView;
        int mAction;

        public TileViewActionEvent(TileView tileView, int action) {
            mTileView = tileView;
            mAction = action;
        }
    }
}
