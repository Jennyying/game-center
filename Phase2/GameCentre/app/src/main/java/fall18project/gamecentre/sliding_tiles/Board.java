package fall18project.gamecentre.sliding_tiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import fall18project.gamecentre.MoveStack;
import fall18project.gamecentre.Undoable;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Serializable, Iterable<Tile>, Undoable {

    /**
     * The number of rows.
     */
    private int sideLength;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[] tiles;

    /**
     * Shuffle the tiles in the board
     */
    public void shuffleTiles() {
        Collections.shuffle(Arrays.asList(tiles));
    }

    /*
     * A record of the moves made on the board
     */
    private MoveStack<TileSwap> moves = new MoveStack<TileSwap>();

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == sideLength * sideLength
     *
     * @param tiles      the tiles for the board
     * @param sideLength the side length of the board
     */
    public Board(List<Tile> tiles, int sideLength) {
        this.sideLength = sideLength;
        this.tiles = new Tile[numTiles()];

        assert (tiles.size() == sideLength * sideLength);
        int i = 0;
        for (Tile t : tiles) this.tiles[i++] = t;
    }

    /**
     * Get the side length of the board
     */
    public int getSideLength() {
        return sideLength;
    }

    /**
     * Set the side length of the board
     *
     * @param n target side length
     */
    public void setSideLength(int n) {
        sideLength = n;
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    public int numTiles() {
        return sideLength * sideLength;
    }

    /**
     * Return the position corresponding to (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the position in the tile array corresponding to (row, col)
     */
    private int getPos(int row, int col) {
        return row * sideLength + col;
    }

    /**
     * Return the tile at pos
     *
     * @param pos the position of the tile
     * @return the tile at pos
     */
    public Tile getTile(int pos) {
        return tiles[pos];
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public Tile getTile(int row, int col) {
        return getTile(getPos(row, col));
    }


    /**
     * Is a position in bounds?
     *
     * @param row the tile row
     * @param col the tile column
     * @return whether the position (row, col) is in bounds
     */
    public boolean inBounds(int row, int col) {
        return row >= 0 && col >= 0 && row < sideLength && col < sideLength;
    }

    /**
     * Is a tile the blank tile (false if not in bounds)
     *
     * @param row the tile row
     * @param col the tile column
     * @return false if (row, col) is out of bounds or the tile at (row, col) is blank, else true
     */
    public boolean isBlank(int row, int col) {
        return inBounds(row, col) && getTile(row, col).getId() == numTiles();
    }

    /**
     * Is a tile the blank tile (false if not in bounds)
     *
     * @param id the id of the tile
     * @return false if id is out of bounds or the tile at id is blank, else true
     */
    public boolean isBlank(int id) {
        return (id < numTiles() && id >= 0) && getTile(id).getId() == numTiles();
    }

    /**
     * Return an iterator over tiles in the board
     *
     * @return an iterator over tiles
     */
    public Iterator<Tile> iterator() {
        return Arrays.asList(tiles).iterator();
    }

    /**
     * Is the board solved?
     *
     * @return true if the board is solved (i.e. in sorted order), false otherwise
     */
    public boolean solved() {
        for (int i = 1; i < tiles.length; i++) {
            if (tiles[i - 1].getId() >= tiles[i].getId()) return false;
        }
        return true;
    }

    /**
     * Do a tile swap. Does not affect move stack
     *
     * @param t1 first tile to swap
     * @param t2 second tile to swap
     */
    public void doSwapTiles(int t1, int t2) {
        Tile tmp = getTile(t1);
        tiles[t1] = getTile(t2);
        tiles[t2] = tmp;

        setChanged();
        notifyObservers();
    }

    /**
     * Perform the TileSwap t while registering it in the move stack
     *
     * @param t TileSwap to perform
     */
    public void swapTiles(TileSwap t) {
        doSwapTiles(t.getT1(), t.getT2());
        moves.move(t);
    }

    /**
     * Swap the tiles at pos1 and pos2
     *
     * @param pos1 the first tile position
     * @param pos2 the second tile position
     */
    public void swapTiles(int pos1, int pos2) {
        swapTiles(new TileSwap(pos1, pos2));
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    public void swapTiles(int row1, int col1, int row2, int col2) {
        swapTiles(getPos(row1, col1), getPos(row2, col2));
    }

    public boolean hasMoves() {
        return moves.hasMoves();
    }

    public boolean hasUndos() {
        return moves.hasUndos();
    }

    public boolean truncate() {
        return moves.truncate();
    }

    /**
     * Undo a move.
     *
     * @return false if no moves to undo, true otherwise
     */
    public boolean undo() {
        if (!hasMoves()) return false;

        doSwapTiles(moves.top().getT1(), moves.top().getT2());
        moves.undo();

        return true;
    }

    /*
     * Redo a move
     * @return false if no moves to undo, true otherwise
     */
    public boolean redo() {
        if (!hasUndos()) return false;

        moves.redo();
        doSwapTiles(moves.top().getT1(), moves.top().getT2());

        return true;
    }

    /**
     * Get how many moves can be redone
     *
     * @return how many moves can be redone
     */
    public int getUndid() {
        return moves.getUndid();
    }

    /**
     * Get how many moves can be undone
     *
     * @return how many moves can be undone
     */
    public int getMoves() {
        return moves.getMoves();
    }

    @Override
    public String toString() {
        Tile[][] tiles = new Tile[sideLength][sideLength];
        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                tiles[i][j] = getTile(i, j);
            }
        }
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    /**
     * Count the number of inversions for the board.
     */
    public int inversions() {
        int inversions = 0;
        for (int i = 1; i <= sideLength * sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                for (int k = 0; k < sideLength; k++) {
                    int tileVal = getTile(j, k).getId();
                    if (tileVal < i) {
                        inversions++;
                    }
                }
            }
        }
        return inversions;
    }


    /**
     * Return a list containing the positions of tiles neighboring the tile at the given position
     * @param pos the position of the tile in the array
     * @return A list of the neighboring tiles
     */

    public List<Integer> getNeighbourTiles(int pos) {
        List<Integer> neighborPositions = new ArrayList<Integer>();

        if (pos + sideLength < tiles.length) {
            neighborPositions.add(pos + sideLength);
        }
        if (pos - sideLength >= 0) {
            neighborPositions.add(pos - sideLength);
        }
        if (pos + 1 < tiles.length && !((pos + 1) % sideLength == 0)) {
            neighborPositions.add(pos + 1);
        }
        if (pos - 1 >= 0 && !(pos % sideLength == 0)) {
            neighborPositions.add(pos - 1);
        }
        return neighborPositions;
    }

    /**
     * Get the position of the blank tile
     */
    public int getBlankTilePos() {
        int i = 0;
        while (i < tiles.length && !isBlank(i)) {
            i++;
        }
        return i;
    }

    /**
     * Take a solvable board and randomly swap the blank tile with one of its neighbors a
     * certain amount of times.
     */

    public void shuffleSolvableBoard() {
        for (int i = 0; i < tiles.length * tiles.length; i++) {
            int pos = getBlankTilePos();
            List<Integer> neighbourTiles = getNeighbourTiles(pos);
            Random rand = new Random();
            int randomPos = neighbourTiles.get(rand.nextInt(neighbourTiles.size()));
            swapTiles(pos, randomPos);
        }
    }
}

