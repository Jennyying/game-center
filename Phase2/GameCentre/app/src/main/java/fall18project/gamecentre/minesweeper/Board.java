package fall18project.gamecentre.minesweeper;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Code adapted from https://github.com/kgleong/minesweeper.
 */

/**
 * The minesweeper board.
 */
public class Board {

    /**
     * The default dimensions of the board.
     */
    final static int DEFAULT_DIMENSION = 8;

    /**
     * The default number of mines on the board.
     */
    final static int DEFAULT_NUM_MINES = 10;

    /**
     * The board creation states.
     */
    final static int BOARD_CREATED = 0;
    final static int GRID_CREATED = 1;
    final static int MINES_PLACED = 2;
    final static int GRID_POPULATED = 3;

    /**
     * The current state of the board.
     */
    private int currentState = BOARD_CREATED;

    /**
     * The actual number of mines on the board.
     */
    private int numMines;

    /**
     * The actual dimensions of the board.
     */
    private int dimension;

    /**
     * A representation of the board.
     */
    private Tile[][] boardGrid = null;

    /**
     * The set of tiles containing mines.
     */
    private Set<Tile> mineTiles;

    /**
     * A new board with the given dimensions and number of mines.
     *
     * @param dimension the dimensions of the board
     * @param numMines  the number of mines on the board
     */
    private Board(int dimension, int numMines) {
        this.dimension = dimension;
        this.numMines = numMines;
    }

    /**
     * Initialize the board.
     */
    private void init() {
        initBoardGrid();
        initAndPlaceMines();
        calculateNumberedtiles();
    }

    /**
     * Initialize the grid.
     */
    private void initBoardGrid() {
        if (currentState == BOARD_CREATED) {
            boardGrid = new Tile[dimension][dimension];
            currentState = GRID_CREATED;
        }

    }

    /**
     * Initialize and place the mines.
     */
    private void initAndPlaceMines() {
        if (currentState == GRID_CREATED) {
            mineTiles = new HashSet<>(numMines);

            // Create mines
            for (int i = 0; i < numMines; i++) {
                Tile tile;

                do {
                    // Randomly assign mines
                    int x = new Random().nextInt(dimension);
                    int y = new Random().nextInt(dimension);

                    tile = new Tile(x, y);
                    tile.setContainsMine(true);
                } while (!mineTiles.add(tile));
            }

            // Place mines
            for (Tile tile : mineTiles) {
                boardGrid[tile.getYGridCoordinate()][tile.getXGridCoordinate()] = tile;
            }

            if (numMines == mineTiles.size()) {
                currentState = MINES_PLACED;
            }
        }
    }

    /**
     * Computes the tiles that are adjacent to mine tiles.
     */
    private void calculateNumberedtiles() {
        if (currentState == MINES_PLACED) {
            for (Tile mine : mineTiles) {
                int x = mine.getXGridCoordinate();
                int y = mine.getYGridCoordinate();

                int startingX = Math.max(0, x - 1);
                int startingY = Math.max(0, y - 1);

                for (int i = startingX; i < dimension && i <= x + 1; i++) {
                    for (int j = startingY; j < dimension && j <= y + 1; j++) {

                        Tile tile = boardGrid[j][i];

                        if (tile == null) {
                            boardGrid[j][i] = tile = new Tile(i, j);
                        } else if (tile.containsMine()) {
                            // Don't need to calculate adjacent mines count for
                            // tiles containing mines.
                            continue;
                        }
                        int newAdjacentMinesCount = tile.getAdjacentMines() + 1;
                        tile.setAdjacentMines(newAdjacentMinesCount);
                    }
                }
            }
            currentState = GRID_POPULATED;
        }

    }

    /**
     * @return the boardGrid
     */
    public Tile[][] getBoardGrid() {
        return boardGrid;
    }

    /**
     * @return the current dimensions of the board.
     */

    public int getDimension() {
        return dimension;
    }

    /**
     * @return the number of mines on the board.
     */
    public int getNumMines() {
        return numMines;
    }

    /**
     * A builder for the board.
     */
    public static class Builder {

        int dimension = DEFAULT_DIMENSION;
        int numMines = DEFAULT_NUM_MINES;

        public Builder dimension(int dimension) {
            if (dimension > 0) {
                this.dimension = dimension;
            }

            return this;
        }

        public Builder numMines(int numMines) {
            if (numMines > 0) {
                this.numMines = numMines;
            }

            return this;
        }

        public Board build() {
            Board board = new Board(dimension, numMines);
            board.init();

            return board;
        }
    }
}
