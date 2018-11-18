package fall18project.gamecentre;

import android.se.omapi.Session;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private Board board;

    /**
     * The username associated with this board
     */
    private String username;

    /**
     * The number of moves performed. Used to calculate the score
     */
    public int moves = 0;

    /**
     * Whether a score has already been obtained from this board. Reset every move performed
     */
    public boolean score_taken = false;

    /**
     * Manage a board that has been pre-populated.
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
    }

    /**
     * Set the username associated with this board
     * @param un new username
     */
    public void setUsername(String un) {username = un;}

    /**
     * Get the username associated with this board
     * @return the username associated with this board
     */
    public String getUsername() {return username;}


    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Manage a new shuffled board.
     * @param sl the side length desired
     * @param un the username associated with this board
     */
    BoardManager(int sl, String un) {
        username = un;

        List<Tile> tiles = new ArrayList<>();
        int numTiles = sl * sl;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum, tileNum == numTiles - 1));
        }
        Collections.shuffle(tiles);
        this.board = new Board(tiles, sl);
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        return board.solved();
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {

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
    void touchMove(int position) {

        int row = position / board.getSideLength();
        int col = position % board.getSideLength();

        if(board.isBlank(row + 1, col)) {
            board.swapTiles(row + 1, col, row, col);
        }
        else if(board.isBlank(row - 1, col)) {
            board.swapTiles(row - 1, col, row, col);
        }
        else if(board.isBlank(row, col + 1)) {
            board.swapTiles(row, col + 1, row, col);
        }
        else if(board.isBlank(row, col - 1)) {
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
        if(u) {moves--; score_taken = false;}
        return u;
    }

    /**
     * Redo the previous move, if any
     *
     * @return whether there was a move to redo
     */
    public boolean redo() {
        boolean r = board.redo();
        if(r) {moves++; score_taken = false;}
        return r;
    }

    /**
     * Get the score,
     * @return (10*numTiles*e^(-0.05 * moves/sideLength))
     */
    public double getScoreVal() {
        return 10 * board.numTiles() * Math.exp(-0.05 * moves / board.getSideLength());
    }

    /**
     * Get a session score
     * @return a session score with the username associated with this board and the score value
     */
    public SessionScore getScore() {
        return new SessionScore(username, getScoreVal());
    }

    /**
     * Mark this board as having been scored
     */
    public void markAsScored() {
        score_taken = true;
    }

    /**
     * Whether this board has a valid score (a.k.a. is solved) which has not been marked as taken
     * @return see above
     */
    public boolean hasScore() {
        return !score_taken && board.solved();
    }
}