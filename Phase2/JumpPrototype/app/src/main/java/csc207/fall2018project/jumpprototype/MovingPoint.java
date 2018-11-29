package csc207.fall2018project.jumpprototype;

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
     * @param x x coordinate of the new point
     * @param y y coordinate of the new point
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
     * @return this point's velocity in the X direction
     */
    public double getXVelocity() {
        return vx;
    }

    /**
     * Get this point's velocity in the Y direction
     * @return this point's velocity in the Y direction
     */
    public double getYVelocity() {
        return vy;
    }

    /**
     * Add to this point's velocity in the X direction
     * @param dx amount to add to the velocity
     */
    public void addXVelocity(double dx) {
        vx += dx;
    }

    /**
     * Add to this point's velocity in the Y direction
     * @param dy amount to add to the velocity
     */
    public void addYVelocity(double dy) {
        vy += dy;
    }

    /**
     * Add to this point's velocity in the X and Y directions
     * @param dx amount to add to the x velocity
     * @param dy amount to add to the y velocity
     */
    public void addVelocity(double dx, double dy) {
        vx += dx; vy += dy;
    }

    /**
     * Move this point inertially for one time step
     */
    public void moveTimeStep() {
        shift(vx, vy);
    }

}
