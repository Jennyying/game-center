package fall18project.gamecentre.reactorcontrol.physics;

import java.io.Serializable;

/**
 * A point moving inertially with a given velocity
 */
public class MovingPoint extends ShiftablePoint implements MovingObject, Serializable {

    /**
     * The x velocity of the point
     */
    private double vx;

    /**
     * The y velocity of the point
     */
    private double vy;

    /**
     * Create a new moving point with a given position, velocity and mass
     *
     * @param x  x coordinate of the new point
     * @param y  y coordinate of the new point
     * @param vx x velocity of the new point
     * @param vy y velocity of the new point
     */
    public MovingPoint(double x, double y, double vx, double vy) {
        super(x, y);
        this.vx = vx;
        this.vy = vy;
    }

    /**
     * Create a stationary point at a given position
     *
     * @param x x coordinate of the new point
     * @param y y coordinate of the new point
     */
    public MovingPoint(double x, double y) {
        super(x, y);
        this.vx = 0;
        this.vy = 0;
    }

    /**
     * Get this point's velocity in the X direction
     *
     * @return this point's velocity in the X direction
     */
    public double getXVelocity() {
        return vx;
    }

    /**
     * Get this point's velocity in the Y direction
     *
     * @return this point's velocity in the Y direction
     */
    public double getYVelocity() {
        return vy;
    }

    /**
     * Accelerate the object in the X direction by ax
     *
     * @param ax the amount of acceleration to apply in the X direction
     */
    public void accX(double ax) {
        vx += ax;
    }

    /**
     * Accelerate the object in the Y direction with acceleration ay
     *
     * @param ay the amount of acceleration to apply in the Y direction
     */
    public void accY(double ay) {
        vy += ay;
    }

    /**
     * Accelerate the object with a given force in the x and y directions
     *
     * @param ax the amount of force to apply in the X direction
     * @param ay the amount of force to apply in the Y direction
     */
    public void acc(double ax, double ay) {
        vx += ax;
        vy += ay;
    }

    /**
     * Set the object's velocity in the x direction to be vx
     *
     * @param vx the object's new velocity in the x direction
     */
    public void setVx(double vx) {
        this.vx = vx;
    }

    /**
     * Set the object's velocity in the y direction to be vy
     *
     * @param vy the object's new velocity in the y direction
     */
    public void setVy(double vy) {
        this.vy = vy;
    }

    /**
     * Move this point inertially for one time step
     */
    public void moveTimeStep() {
        shift(vx, vy);
    }

    /**
     * Bound a point to within a box b. Zero velocity in the direction that bounding occurs, if
     * any (i.e. if the right or left x edge of b is hit, set this point's x velocity to zero)
     *
     * @param b box to bound the point within
     */
    public void boundWithin(Box b) {
        if (getX() < b.getLeftX() || getX() > b.getRightX()) vx = 0;
        if (getY() < b.getBottomY() || getY() > b.getTopY()) vy = 0;
        super.boundWithin(b);
    }

    /**
     * Bound a point to within a box b with padding. If the padding for any axis is so great that no point in
     * the box is at least padding away from one of the edges, snap the point to the center of the box
     *
     * @param b        box to bound the point within
     * @param paddingX padding to use for X
     * @param paddingY padding to use for Y
     */
    public void boundWithin(Box b, double paddingX, double paddingY) {
        if (getX() < b.getLeftX() + paddingX || getX() > b.getRightX() - paddingX) vx = 0;
        if (getY() < b.getBottomY() + paddingY || getY() > b.getTopY() - paddingY) vy = 0;
        super.boundWithin(b, paddingX, paddingY);
    }

}
