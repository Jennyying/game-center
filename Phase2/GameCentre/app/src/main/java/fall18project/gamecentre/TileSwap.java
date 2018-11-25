package fall18project.gamecentre;

import java.io.Serializable;

/**
 * A move in which two tiles are swapped on a SlidingTiles board. Is it's own inverse.
 */
public class TileSwap implements Move, Serializable {

    /**
     * The first tile to swap
     */
    private int t1;

    /**
     * The second tile to swap
     */
    private int t2;

    /**
     * Construct an invalid tile swap
     */
    public TileSwap() {t1 = -1; t2 = -1;}

    public TileSwap(int a, int b) {t1 = a; t2 = b;}

    /**
     * @return whether this is a valid TileSwap
     */
    public boolean is_valid() {
        return t1 >= 0 && t2 >= 0;
    }

    /**
     * @return the inverse of this TileSwap, which is itself
     */
    public Move invert() {
        return this;
    }

    public int getT1() {
        return t1;
    }

    public int getT2() {
        return t2;
    }
}
