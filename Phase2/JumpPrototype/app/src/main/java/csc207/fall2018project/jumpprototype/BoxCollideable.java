package csc207.fall2018project.jumpprototype;

/**
 * A geometric object which can collide with a box
 */
public interface BoxCollideable {

    /**
     * Check whether this geometric object collides with the Box b
     * @param b the box to check for collisions with
     * @return whether this object collides with b
     */
    public boolean collidesWith(Box b);

}
