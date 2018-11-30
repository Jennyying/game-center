package fall18project.gamecentre.reactorcontrol.physics;

/**
 * An abstract point in 2D, Euclidean space
 */
abstract public class Point implements BoxCollideable {
    /**
     * Get the point's x coordinate
     */
    abstract public double getX();

    /**
     * Get the point's y coordinate
     */
    abstract public double getY();

    /**
     * Check whether this box collides with the box b
     *
     * @param b the box to check for collisions with
     * @return whether this object collides with b
     */
    public boolean collidesWith(Box b) {
        return isWithin(b);
    }

    /**
     * Check whether this box lies completely within the Box b
     *
     * @param b the box to check whether this object is within
     * @return whether this object is within b
     */
    public boolean isWithin(Box b) {
        double dx = Math.abs(getX() - b.getCentreX());
        double dy = Math.abs(getY() - b.getCentreY());
        return dx < b.getXRadius() && dy < b.getYRadius();
    }
}
