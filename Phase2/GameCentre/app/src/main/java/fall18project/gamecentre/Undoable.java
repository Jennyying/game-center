package fall18project.gamecentre;

/**
 * Something which supports the undo and redo operations
 */
public interface Undoable {

    /**
     * Whether the object has any operations on it which can be undone
     *
     * @return true if so, false otherwise
     */
    public boolean hasMoves();

    /**
     * Whether the object has had moves undone which can be redone
     *
     * @return true if so, false otherwise
     */
    public boolean hasUndos();

    /**
     * Undo a move
     *
     * @return whether a move has been successfully undone
     */
    public boolean undo();

    /**
     * Redo a move
     *
     * @return whether a move has been successfully redone
     */
    public boolean redo();

    /**
     * Delete redo history past the given point (since a move has been performed which changes history)
     *
     * @return whether any history has been deleted
     */
    public boolean truncate();

}
