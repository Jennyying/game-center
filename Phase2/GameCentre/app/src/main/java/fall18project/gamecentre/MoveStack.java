package fall18project.gamecentre;

import java.io.Serializable;
import java.util.ArrayList;


public class MoveStack<T> implements Serializable, Undoable {

    /**
     * A list of all moves performed up till now
     */
    private ArrayList<T> moves = new ArrayList<T>();

    /**
     * The amount of moves already undid. Capped at the size of moves, with reset every operation
     */
    private int undid = 0;

    /**
     * Check if moves are remaining in the move stack
     *
     * @return whether moves are remaining in the move stack
     */
    public boolean hasMoves() {
        return undid < moves.size();
    }

    /**
     * Check whether any moves can be undone
     *
     * @return whether any moves can be undone
     */
    public boolean hasUndos() {
        return undid > 0 && moves.size() != 0;
    }

    /**
     * Return how many moves can be undone
     */
    public int getMoves() {
        return moves.size() - undid;
    }

    /**
     * Return how many moves have been undone
     */
    public int getUndid() {
        return undid;
    }

    /**
     * Increments undid and returns whether there are still moves to do
     *
     * @return boolean - returns whether there are still moves to do
     */
    public boolean undo() {
        if (!hasMoves()) return false;
        undid++;
        return true;
    }

    /**
     * Decrements undid counter and returns whether there are undos moves to do
     *
     * @return boolean - returns whether there are still undos to do
     */
    public boolean redo() {
        if (!hasUndos()) return false;
        undid--;
        return true;
    }

    /**
     * Clears part of moves sublist and returns whether there are undos moves to do
     *
     * @return boolean - returns whether there are still undos to do
     */
    public boolean truncate() {
        if (!hasUndos()) return false;
        moves.subList(moves.size() - undid, moves.size()).clear();
        undid = 0; // There are now no undos remaining
        return true;
    }

    /**
     * Add a move to the move stack and truncate any moves after it
     *
     * @param mv the move to be added
     */
    public void move(T mv) {
        truncate();
        moves.add(mv);
    }

    /**
     * @return the move at the top of the move stack, if any. Throws an exception if the move stack is empty
     */
    public T top() {
        return moves.get(moves.size() - undid - 1);
    }


}
