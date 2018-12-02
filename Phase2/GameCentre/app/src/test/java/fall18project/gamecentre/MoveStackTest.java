package fall18project.gamecentre;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MoveStackTest {

    /**
     * The move stack to perform tests on
     */
    MoveStack<TestMove> moveStack;

    /**
     * A test move
     */
    class TestMove implements Move {

        int val;

        public TestMove(int val) {
            this.val = val;
        }

        public boolean isValid() {return true;}
        public Move invert() {return new TestMove(-val);}

        public boolean isEqual(TestMove m) {
            return m.val == val;
        }
        public boolean isInverse(TestMove m) {
            return m.val == -val;
        }

    }

    @Before
    public void setUp() {
        moveStack = new MoveStack<>();
    }

    @Test
    public void emptyStacksAreEmpty() {
        // A newly created stack has no moves or undos
        assertEquals(moveStack.getMoves(), 0);
        assertEquals(moveStack.getUndid(), 0);
        assertFalse(moveStack.hasMoves());
        assertFalse(moveStack.hasUndos());

        // Once we perform a move, we have a possible undo but not a possible redo
        moveStack.move(new TestMove(1));
        assertTrue(moveStack.top().isEqual(new TestMove(1)));
        assertFalse(moveStack.hasUndos());
        assertTrue(moveStack.hasMoves());
        assertEquals(moveStack.getMoves(), 1);
        assertEquals(moveStack.getUndid(), 0);
        assertFalse(moveStack.redo()); // Can't redo

        // Once we undo a move, we have a possible redo but no more possible undos
        assertTrue(moveStack.undo());
        assertTrue(moveStack.hasUndos());
        assertFalse(moveStack.hasMoves());
        assertEquals(moveStack.getMoves(), 0);
        assertEquals(moveStack.getUndid(), 1);
        assertFalse(moveStack.undo());

        // Once we redo this move, we again have a possible undo but not a possible redo
        assertTrue(moveStack.redo());
        assertFalse(moveStack.hasUndos());
        assertTrue(moveStack.hasMoves());
        assertEquals(moveStack.getMoves(), 1);
        assertEquals(moveStack.getUndid(), 0);
    }


    @Test
    public void doingANewMoveTruncatesTheStack() {
        // Do 3 moves and check moves and undos after each
        assertEquals(moveStack.getMoves(), 0);
        assertEquals(moveStack.getUndid(), 0);
        moveStack.move(new TestMove(5));
        assertEquals(moveStack.getMoves(), 1);
        assertEquals(moveStack.getUndid(), 0);
        moveStack.move(new TestMove(3));
        assertEquals(moveStack.getMoves(), 2);
        assertEquals(moveStack.getUndid(), 0);
        moveStack.move(new TestMove(7));
        assertEquals(moveStack.getMoves(), 3);
        assertEquals(moveStack.getUndid(), 0);

        // Now undo two of them and check moves and undos after each undo
        assertTrue(moveStack.undo());
        assertEquals(moveStack.getMoves(), 2);
        assertEquals(moveStack.getUndid(), 1);
        assertTrue(moveStack.undo());
        assertEquals(moveStack.getMoves(), 1);
        assertEquals(moveStack.getUndid(), 2);

        // Now do a new move, check move and undo counts and try to redo
        moveStack.move(new TestMove(2));
        assertEquals(moveStack.getMoves(), 2);
        assertEquals(moveStack.getUndid(), 0);
        assertFalse(moveStack.redo());
    }

}
