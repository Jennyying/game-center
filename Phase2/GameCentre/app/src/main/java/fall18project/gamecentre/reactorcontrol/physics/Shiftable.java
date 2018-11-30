package fall18project.gamecentre.reactorcontrol.physics;

/**
 * A geometric object which can be shifted in the X and Y directions
 */
public interface Shiftable {

    /**
     * Shift the object dx in the x direction
     *
     * @param dx how much to shift by
     */
    public void shiftX(double dx);

    /**
     * Shift the object dy in the y direction
     *
     * @param dy how much to shift by
     */
    public void shiftY(double dy);

    /**
     * Shift the object's position
     *
     * @param dx how much to shift the x position by
     * @param dy how much to shift the y position by
     */
    public void shift(double dx, double dy);

}
