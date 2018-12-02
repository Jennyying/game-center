package fall18project.gamecentre.minesweeper;


import android.util.Log;

/**
 * Code adapted from https://github.com/kgleong/minesweeper.
 */

public class GameManager {
    /**
     *
     */
    private Listener listener;
    /**
     *
     */
    private BoardView boardView;
    /**
     *
     */
    private Game game;

    /**
     * Initializes a GameManager for a game of minesweeper
     *
     * @param dimension the side length of the Minesweeper board
     * @param numMines  the number of mines to place on the board
     * @param boardView the board view to display the board on
     * @param listener  a listener for updates to the board's state
     */
    public GameManager(int dimension, int numMines, BoardView boardView, Listener listener) {

        this.boardView = boardView;
        this.listener = listener;

        initGame(dimension, numMines);
    }

    /**
     * Initializes the game with a given dimension and number of mines
     *
     * @param dimension the side length of the Minesweeper board
     * @param numMines  the number of mines to place on the board
     */
    public void initGame(int dimension, int numMines) {
        // Ensure that old games don't receive game events.
        if (game != null) {
            game.unregisterFromEventBus();
        }

        // Pass a new Board with new mines placement to a new Game.
        Board board = new Board.Builder().dimension(dimension).numMines(numMines).build();
        game = new Game(this, board);
        // The BoardLayoutView posts events to the Game during setup, so there must
        // be an initialized Game before setup occurs.
        boardView.setupBoard(board);
    }

    /**
     * Undo the latest move
     */
    public void undo() {
        game.undoMove();
    }

    /**
     * Get how many undos are remaining
     * @return how many undos are remaining
     */
    public int getUndos() {
        return game.getUndos();
    }

    /**
     * Alerts the listener a victory has occured
     */
    public void publishWin() {
        listener.onWin();
    }

    /**
     * Alerts the listener a loss has occured
     */
    public void publishLoss() {
        listener.onLoss();
    }

    /**
     * Alerts the listener that the game is finished
     */
    public void publishGameFinished() {
        listener.onGameFinished();
    }

    /**
     * Tells the listener how many flags are remaining
     *
     * @param flagsRemaining the amount of flags to tell the listener are remaining
     */
    public void publishFlagsRemainingCount(int flagsRemaining) {
        listener.updateMineFlagsRemainingCount(flagsRemaining);
    }

    /**
     * Tells the listener how much time has elapsed
     *
     * @param elapsedTime the amount of time to tell the listener has elapsed
     */
    public void publishElapsedTime(long elapsedTime) {
        listener.updateTimeElapsed(elapsedTime);
    }

    // Delegate methods to Game object

    /**
     * Ends the game
     */
    public void finishGame() {
        game.finishGame();
    }

    /**
     * Starts the game timer
     */
    public void startTimer() {
        game.startTimer();
    }

    /**
     * Ends the game timer
     */
    public void stopTimer() {
        game.stopTimer();
    }

    /**
     * @return the amount of in-game time elapsed
     */
    public long getElapsedTime() {
        return game.getElapsedTime();
    }

    /**
     * @return the number of mine flags remaining
     */
    public int getMineFlagsRemainingCount() {
        return game.getMineFlagsRemainingCount();
    }

    /**
     * @return whether the game is finished yet
     */
    public boolean isGameFinished() {
        return game.isGameFinished();
    }
    // End delegated methods


    public interface Listener {
        /**
         * Update the amount of time elapsed
         *
         * @param elapsedTime the desired new value for time elapsed
         */
        void updateTimeElapsed(long elapsedTime);

        /**
         * Update the number of flags remaining
         *
         * @param flagsRemaining the new number of flags remaining
         */
        void updateMineFlagsRemainingCount(int flagsRemaining);

        /**
         * To be called when the game is lost
         */
        void onLoss();

        /**
         * To be called when the game is won
         */
        void onWin();

        /**
         * To be called when the game is finished
         */
        void onGameFinished();
    }

    /**
     * A class for building Minesweeper boards
     */
    public static class Builder {
        public static final String TAG = Builder.class.getName();

        int mDimension = Board.DEFAULT_DIMENSION;
        int mNumMines = Board.DEFAULT_NUM_MINES;
        Listener mListener;
        BoardView mBoardLayoutView;

        public Builder dimension(int dimension) {
            if (dimension > 0) {
                mDimension = dimension;
            }


            return this;
        }

        public Builder numMines(int numMines) {
            if (numMines > 0) {
                mNumMines = numMines;
            }

            return this;
        }

        public Builder listener(Listener listener) {
            mListener = listener;

            return this;
        }

        public Builder boardView(BoardView boardView) {
            mBoardLayoutView = boardView;

            return this;
        }

        public GameManager build() throws Exception {
            if (mListener == null || mBoardLayoutView == null) {
                Log.e(this.getClass().getName(), "Game manager listener and board layout view required");
                throw new Exception();
            } else {
                return new GameManager(mDimension, mNumMines, mBoardLayoutView, mListener);
            }
        }
    }
}
