package csc207.fall2018project.jumpprototype;

import java.io.Serializable;

/**
 * A point in 2-dimensional Euclidean space which can be moved around
 */
public class ShiftablePoint extends Point implements Shiftable, Serializable {

    /**
     * The point's x coordinate
     */
    private double x;

    /**
     * The point's y coordinate
     */
    private double y;

    /**
     * Initialize a point at (x, y)
     * @param x the x coordinate to use to initialize the point
     * @param y the y coordinate to use to initialize the point
     */
    public ShiftablePoint(double x, double y) {
        this.x = x; this.y = y;
    }

    /**
     * Initialize a shiftable point from any Point
     * @param p the Point to transform into a ShiftablePoint
     */
    public ShiftablePoint(Point p) {
        this(p.getX(), p.getY());
    }

    /**
     * Shift the point's x coordinate by dx
     * @param dx how much to shift by
     */
    public void shiftX(double dx) {
        x += dx;
    }

    /**'
     * Shift the point's y coordinate by dx
     * @param dy how much to shift by
     */
    public void shiftY(double dy) {
        y += dy;
    }

    /**
     * Shift the points x and y coordinates by dx, dy respectively
     * @param dx how much to shift x by
     * @param dy how much to shift y by
     */
    public void shift(double dx, double dy) {
        x += dx; y += dy;
    }

    /**
     * Get the point's x coordinate
     */
    public double getX() {return x;}

    /**
     * Get the point's y coordinate
     */
    public double getY() {return y;}

    /**
     * Set the point's x position
     * @param x the point's new x position
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Set the point's y position
     * @param y the point's new y position
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Set the point's position
     * @param x the point's new x position
     * @param y the point's new y position
     */
    public void setPosition(double x, double y) {
        this.x = x; this.y = y;
    }

}
