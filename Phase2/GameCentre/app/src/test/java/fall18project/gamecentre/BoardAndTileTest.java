package fall18project.gamecentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall18project.gamecentre.sliding_tiles.Board;
import fall18project.gamecentre.sliding_tiles.BoardManager;
import fall18project.gamecentre.sliding_tiles.Tile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BoardAndTileTest {

    /**
     * The board manager for testing.
     */
    BoardManager boardManager;

    /**
     * Make a set of tiles that are in order.
     *
     * @param sideLength the side length to target
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles(int sideLength) {

        int numTiles = sideLength * sideLength;

        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, tileNum));
        }

        return tiles;
    }

    /**
     * Make a solved Board.
     */
    private void setUpCorrect() {
        List<Tile> tiles = makeTiles(4);
        Board board = new Board(tiles, 4);
        boardManager = new BoardManager(board);
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();
        assertTrue(boardManager.puzzleSolved());
        swapFirstTwoTiles();
        assertFalse(boardManager.puzzleSolved());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();
        assertEquals(1, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(2, boardManager.getBoard().getTile(0, 1).getId());
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, boardManager.getBoard().getTile(0, 1).getId());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();
        assertEquals(15, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(16, boardManager.getBoard().getTile(3, 3).getId());
        boardManager.getBoard().swapTiles(3, 3, 3, 2);
        assertEquals(16, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(15, boardManager.getBoard().getTile(3, 3).getId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertEquals(true, boardManager.isValidTap(11));
        assertEquals(true, boardManager.isValidTap(14));
        assertEquals(false, boardManager.isValidTap(10));
    }


    /**
     * Test whether undo and redo work correctly
     */
    @Test
    public void testUndo() {
        setUpCorrect();
        assertEquals(false, boardManager.undo());
        assertEquals(12, boardManager.getBoard().getTile(2, 3).getId());
        assertEquals(16, boardManager.getBoard().getTile(3, 3).getId());
        boardManager.touchMove(11);
        assertEquals(16, boardManager.getBoard().getTile(2, 3).getId());
        assertEquals(12, boardManager.getBoard().getTile(3, 3).getId());
        assertEquals(true, boardManager.undo());
        assertEquals(12, boardManager.getBoard().getTile(2, 3).getId());
        assertEquals(16, boardManager.getBoard().getTile(3, 3).getId());
        assertEquals(true, boardManager.redo());
        assertEquals(16, boardManager.getBoard().getTile(2, 3).getId());
        assertEquals(12, boardManager.getBoard().getTile(3, 3).getId());
        assertEquals(false, boardManager.redo());
        assertEquals(true, boardManager.undo());
        assertEquals(12, boardManager.getBoard().getTile(2, 3).getId());
        assertEquals(16, boardManager.getBoard().getTile(3, 3).getId());
        assertEquals(false, boardManager.undo());
    }

}

