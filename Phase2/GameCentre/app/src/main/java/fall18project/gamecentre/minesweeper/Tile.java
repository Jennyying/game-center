package fall18project.gamecentre.minesweeper;

/**
 * Code adapted from https://github.com/kgleong/minesweeper.
 */

public class Tile {

    /**
     * A boolean indicating if the tile contains a mine.
     */
    private boolean containsMine = false;

    /**
     * The number of tiles adjacent to the tile that cointain mines.
     */
    private int adjacentMines = 0;

    /**
     * The x coordinate of the tile on the board.
     */
    private int xCoordinate;

    /**
     * The y coordinate of the tile on the board.
     */
    private int yCoordinate;

    /**
     * A tile with the given coordinates.
     *
     * @param xGridCoordinate the x coordinate of the tile
     * @param yGridCoordinate the y coordinate of the tile
     */
    public Tile(int xGridCoordinate, int yGridCoordinate) {
        xCoordinate = xGridCoordinate;
        yCoordinate = yGridCoordinate;
    }

    @Override
    public boolean equals(Object object) {
        boolean result = false;

        if (object instanceof Tile) {
            Tile otherSquare = (Tile) object;

            if (this.xCoordinate == otherSquare.xCoordinate &&
                    this.yCoordinate == otherSquare.yCoordinate) {

                result = true;
            }
        }

        return result;
    }

    @Override
    public int hashCode() {
        StringBuilder builder = new StringBuilder();
        builder.append(xCoordinate).append(',').append('y');

        return builder.toString().hashCode();
    }

    /**
     * @return if the tile contains a mine.
     */
    public boolean containsMine() {
        return containsMine;
    }

    /**
     * Set the tile containsMine attribute.
     *
     * @param containsMine
     */

    public void setContainsMine(boolean containsMine) {
        this.containsMine = containsMine;
    }

    /**
     * @return the number of adjacent mines.
     */

    public int getAdjacentMines() {
        return adjacentMines;
    }

    /**
     * Set the number of adjacent mines.
     *
     * @param adjacentMines the number of mines adjacent to the tile.
     */

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }

    /**
     * @return the x coordinate of the tile.
     */

    public int getXGridCoordinate() {
        return xCoordinate;
    }

    /**
     * @return the y coordinate of the tile.
     */

    public int getYGridCoordinate() {
        return yCoordinate;
    }
}
