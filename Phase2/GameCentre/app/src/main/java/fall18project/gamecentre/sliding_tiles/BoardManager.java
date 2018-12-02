package fall18project.gamecentre.sliding_tiles;

import java.io.Serializable;
import java.util.ArrayList;

import fall18project.gamecentre.game_management.SessionScore;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class BoardManager implements Serializable {

    /**
     * The number of moves performed. Used to calculate the score
     */
    public int moves = 0;
    /**
     * Whether a score has already been obtained from this board. Reset every move performed
     */
    public boolean score_taken = false;
    /**
     * The board being managed.
     */
    private Board board;
    /**
     * The username associated with this board
     */
    private String username;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    public BoardManager(Board board) {
        this.board = board;
    }

    /**
     * Manage a new shuffled board.
     *
     * @param sideLength the side length desired
     * @param userName the username associated with this board
     */
    public BoardManager(int sideLength, String userName) {
        username = userName;

        ArrayList<Tile> tiles = new ArrayList<>();
        int numTiles = sideLength * sideLength;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum, tileNum == numTiles - 1));
        }
        board = new Board(tiles, sideLength);
        board.shuffleSolvableBoard();

        }

    /**
     * Get how many moves can be redone
     * @return how many moves can be redone
     */
    public int getUndid() {
        return board.getUndid();
    }

    /**
     * Get how many moves can be undone
     * @return how many moves can be undone
     */
    public int getMoves() {
        return board.getMoves();
    }

    /**
     * Get the username associated with this board
     *
     * @return the username associated with this board
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username associated with this board
     *
     * @param un new username
     */
    public void setUsername(String un) {
        username = un;
    }

    /**
     * Return the current board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        return board.solved();
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    public boolean isValidTap(int position) {

        int row = position / board.getSideLength();
        int col = position % board.getSideLength();

        // Are any of the 4 the blank tile?
        return board.isBlank(row + 1, col)
                || board.isBlank(row - 1, col)
                || board.isBlank(row, col + 1)
                || board.isBlank(row, col - 1);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    public void touchMove(int position) {

        int row = position / board.getSideLength();
        int col = position % board.getSideLength();

        if (board.isBlank(row + 1, col)) {
            board.swapTiles(row + 1, col, row, col);
        } else if (board.isBlank(row - 1, col)) {
            board.swapTiles(row - 1, col, row, col);
        } else if (board.isBlank(row, col + 1)) {
            board.swapTiles(row, col + 1, row, col);
        } else if (board.isBlank(row, col - 1)) {
            board.swapTiles(row, col - 1, row, col);
        }

        moves++;
        score_taken = false;
    }

    /**
     * Undo the previous move, if any.
     *
     * @return whether there was a move to undo
     */
    public boolean undo() {
        boolean u = board.undo();
        if (u) {
            moves--;
            score_taken = false;
        }
        return u;
    }

    /**
     * Redo the previous move, if any
     *
     * @return whether there was a move to redo
     */
    public boolean redo() {
        boolean r = board.redo();
        if (r) {
            moves++;
            score_taken = false;
        }
        return r;
    }

    /**
     * Get the score,
     *
     * @return (10 * numTiles * e ^ ( - 0.05 * moves / sideLength))
     */
    public long getScoreVal() {
        return Math.max(0, 10000 - moves);
    }

    /**
     * Get a session score
     *
     * @return a session score with the username associated with this board and the score value
     */
    public SessionScore getScore() {
        return new SessionScore(username, GameActivity.GAME_NAME, getScoreVal());
    }

    /**
     * Mark this board as having been scored
     */
    public void markAsScored() {
        score_taken = true;
    }

    /**
     * Whether this board has a valid score (a.k.a. is solved) which has not been marked as taken
     *
     * @return see above
     */
    public boolean hasScore() {
        return !score_taken && board.solved();
    }
}
