package csc207.fall2018project.jumpprototype;

/**
 * A box, something with a centre (having x and y coordinates), an x radius and a y radius
 */
abstract public class Box implements BoxCollideable {

    /**
     * Get the centre's X coordinate
     * @return the centre's X coordinate
     */
    abstract public double getCentreX();

    /**
     * Get the centre's Y coordinate
     * @return the centre's Y coordinate
     */
    abstract public double getCentreY();

    /**
     * Get the centre of the box as a point
     * @return the centre of the box
     */
    abstract public Point getCentre();

    /**
     * Get the X radius
     * @return the box's X radius
     */
    abstract public double getXRadius();

    /**
     * Get the Y radius
     * @return the box's Y radius
     */
    abstract public double getYRadius();

    /**
     * Check whether this box collides with the box b
     * @param b the box to check for collisions with
     * @return whether this object collides with b
     */
    public boolean collidesWith(Box b) {
        double dx = Math.abs(getCentreX() - b.getCentreX());
        double dy = Math.abs(getCentreY() - b.getCentreY());
        return dx < getXRadius() + b.getXRadius() && dy < getYRadius() + b.getYRadius();
    }

}
