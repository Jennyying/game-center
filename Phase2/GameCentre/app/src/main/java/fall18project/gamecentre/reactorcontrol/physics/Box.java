package fall18project.gamecentre.reactorcontrol.physics;

/**
 * A box, something with a centre (having x and y coordinates), an x radius and a y radius
 */
abstract public class Box implements BoxCollideable {

    /**
     * Get the centre's X coordinate
     *
     * @return the centre's X coordinate
     */
    abstract public double getCentreX();

    /**
     * Get the centre's Y coordinate
     *
     * @return the centre's Y coordinate
     */
    abstract public double getCentreY();

    /**
     * Get the centre of the box as a point
     *
     * @return the centre of the box
     */
    abstract public Point getCentre();

    /**
     * Get the X radius
     *
     * @return the box's X radius
     */
    abstract public double getXRadius();

    /**
     * Get the Y radius
     *
     * @return the box's Y radius
     */
    abstract public double getYRadius();

    /**
     * Get the top Y coordinate
     *
     * @return the Y coordinate of the top of the box
     */
    public double getTopY() {
        return getCentreY() + getYRadius();
    }

    /**
     * Get the bottom Y coordinate
     *
     * @return the Y coordinate of the bottom of the box
     */
    public double getBottomY() {
        return getCentreY() - getYRadius();
    }

    /**
     * Get the right X coordinate
     *
     * @return the X coordinate of the right of the box
     */
    public double getRightX() {
        return getCentreX() + getXRadius();
    }

    /**
     * Get the left X coordinate
     *
     * @return the X coordinate of the left of the box
     */
    public double getLeftX() {
        return getCentreX() - getXRadius();
    }

    /**
     * Check whether this box collides with the box b
     *
     * @param b the box to check for collisions with
     * @return whether this object collides with b
     */
    public boolean collidesWith(Box b) {
        double dx = Math.abs(getCentreX() - b.getCentreX());
        double dy = Math.abs(getCentreY() - b.getCentreY());
        return dx < getXRadius() + b.getXRadius() && dy < getYRadius() + b.getYRadius();
    }

    /**
     * Check whether this box lies completely within the Box b
     *
     * @param b the box to check whether this object is within
     * @return whether this object is within b
     */
    public boolean isWithin(Box b) {
        double dx = Math.abs(getCentreX() - b.getCentreX());
        double dy = Math.abs(getCentreY() - b.getCentreY());
        return dx + getXRadius() < b.getXRadius() && dy + getYRadius() < b.getYRadius();
    }

}
