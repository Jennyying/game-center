package fall18project.gamecentre.reactorcontrol.physics;

/**
 * A geometric object which can collide with a box
 */
public interface BoxCollideable {

    /**
     * Check whether this geometric object collides with the Box b
     *
     * @param b the box to check for collisions with
     * @return whether this object collides with b
     */
    public boolean collidesWith(Box b);

    /**
     * Check whether this geometric object lies completely within the Box b
     *
     * @param b the box to check whether this object is within
     * @return whether this object is within b
     */
    public boolean isWithin(Box b);

}
