package fall18project.gamecentre;

/**
 * An abstract class representing a move in a game
 */
public abstract class Move {
    /**
     * Is the move valid?
     * @return true if a move is valid, false otherwise
     */
    abstract public boolean is_valid();

    /**
     * Invert the move for un-doing
     * @return the inverse of the move, or null if the move cannot be undone
     */
    abstract public Move invert();
}