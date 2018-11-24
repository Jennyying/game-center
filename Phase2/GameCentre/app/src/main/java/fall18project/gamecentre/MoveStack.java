package fall18project.gamecentre;

import java.util.ArrayList;
import java.io.Serializable;


public class MoveStack<T> implements Serializable, Undoable {

    /**
     * A list of all moves performed up till now
     */
    private ArrayList<T> moves = new ArrayList<T>();

    /**
     * The amount of moves already undid. Capped at the size of moves, with reset every operation
     */
    private int undid = 0;

    public boolean has_moves() {
        return undid < moves.size();
    }
    public boolean has_undos() {
        return undid > 0 && moves.size() != 0;
    }

    /** Increments undid and returns whether there are still moves to do
     *
     * @return boolean - returns whether there are still moves to do
     */
    public boolean undo() {
        if(!has_moves()) return false;
        undid++;
        return true;
    }

    /** Decrements undid counter and returns whether there are undos moves to do
     *
     * @return boolean - returns whether there are still undos to do
     */
    public boolean redo() {
        if(!has_undos()) return false;
        undid--;
        return true;
    }

    /** Clears part of moves sublist and returns whether there are undos moves to do
     *
     * @return boolean - returns whether there are still undos to do
     */
    public boolean truncate() {
        if(!has_undos()) return false;
        moves.subList(moves.size() - undid, moves.size()).clear();
        return true;
    }

    /**
     * Add a move to the move stack and truncate any moves after it
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
