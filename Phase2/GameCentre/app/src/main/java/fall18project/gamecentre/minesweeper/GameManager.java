package fall18project.gamecentre.minesweeper;


import android.util.Log;

public class GameManager{
    private Listener listener;
    private BoardView boardView;
    private Game game;

    public GameManager(int dimension, int numMines, BoardView boardView, Listener listener) {

        this.boardView = boardView;
        this.listener = listener;

        initGame(dimension, numMines);
    }

    public void initGame(int dimension, int numMines) {
        // Ensure that old games don't receive game events.
        if(game != null) {
            game.unregisterFromEventBus();
        }

        // Pass a new Board with new mines placement to a new Game.
        Board board = new Board.Builder().dimension(dimension).numMines(numMines).build();
        game = new Game(this, board);

        // The BoardLayoutView posts events to the Game during setup, so there must
        // be an initialized Game before setup occurs.
        boardView.setupBoard(board);
    }

    public void publishWin() {
        listener.onWin();
    }

    public void publishLoss() {
        listener.onLoss();
    }

    public void publishGameFinished() {
        listener.onGameFinished();
    }

    public void publishFlagsRemainingCount(int flagsRemaining) {
        listener.updateMineFlagsRemainingCount(flagsRemaining);
    }

    public void publishElapsedTime(long elapsedTime) {
        listener.updateTimeElapsed(elapsedTime);
    }

    // Delegate methods to Game object
    public void finishGame() {
        game.finishGame();
    }

    public void startTimer() {
        game.startTimer();
    }

    public void stopTimer() {
        game.stopTimer();
    }

    public long getElapsedTime() {
        return game.getElapsedTime();
    }

    public int getMineFlagsRemainingCount() {
        return game.getMineFlagsRemainingCount();
    }

    public boolean isGameFinished() {
        return game.isGameFinished();
    }
    // End delegated methods

    public interface Listener {
        void updateTimeElapsed(long elapsedTime);
        void updateMineFlagsRemainingCount(int flagsRemaining);
        void onLoss();
        void onWin();
        void onGameFinished();
    }

    public static class Builder {
        public static final String TAG = Builder.class.getName();

        int mDimension = Board.DEFAULT_DIMENSION;
        int mNumMines = Board.DEFAULT_NUM_MINES;
        Listener mListener;
        BoardView mBoardLayoutView;

        public Builder dimension(int dimension)  {
            if(dimension > 0) {
                mDimension = dimension;
            }


            return this;
        }

        public Builder numMines(int numMines)  {
            if(numMines > 0) {
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
            if(mListener == null || mBoardLayoutView == null) {
                Log.e(this.getClass().getName(), "Game manager listener and board layout view required");
                throw new Exception();
            }
            else {
                return new GameManager(mDimension, mNumMines, mBoardLayoutView, mListener);
            }
        }
    }
}
