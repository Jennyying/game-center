package fall18project.gamecentre;

/**
 * An interface for a move in a game
 */
public interface Move {
    /**
     * Is the move valid?
     *
     * @return true if a move is valid, false otherwise
     */
    public boolean isValid();

    /**
     * Invert the move for un-doing
     *
     * @return the inverse of the move, or null if the move cannot be undone
     */
    public Move invert();
}
