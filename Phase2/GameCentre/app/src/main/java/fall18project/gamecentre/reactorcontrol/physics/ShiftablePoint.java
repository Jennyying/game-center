package fall18project.gamecentre.reactorcontrol.physics;

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
     *
     * @param x the x coordinate to use to initialize the point
     * @param y the y coordinate to use to initialize the point
     */
    public ShiftablePoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Initialize a shiftable point from any Point
     *
     * @param p the Point to transform into a ShiftablePoint
     */
    public ShiftablePoint(Point p) {
        this(p.getX(), p.getY());
    }

    /**
     * Shift the point's x coordinate by dx
     *
     * @param dx how much to shift by
     */
    public void shiftX(double dx) {
        x += dx;
    }

    /**
     * '
     * Shift the point's y coordinate by dx
     *
     * @param dy how much to shift by
     */
    public void shiftY(double dy) {
        y += dy;
    }

    /**
     * Shift the points x and y coordinates by dx, dy respectively
     *
     * @param dx how much to shift x by
     * @param dy how much to shift y by
     */
    public void shift(double dx, double dy) {
        x += dx;
        y += dy;
    }

    /**
     * Get the point's x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Set the point's x position
     *
     * @param x the point's new x position
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Get the point's y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Set the point's y position
     *
     * @param y the point's new y position
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Set the point's position
     *
     * @param x the point's new x position
     * @param y the point's new y position
     */
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Bound a point to within a box b
     *
     * @param b box to bound the point within
     */
    public void boundWithin(Box b) {
        if (x < b.getLeftX()) x = b.getLeftX();
        if (x > b.getRightX()) x = b.getRightX();
        if (y < b.getBottomY()) y = b.getBottomY();
        if (y > b.getTopY()) y = b.getTopY();
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
        if (b.getXRadius() < paddingX) x = b.getCentreX();
        else {
            if (x < b.getLeftX() + paddingX) x = b.getLeftX() + paddingX;
            if (x > b.getRightX() - paddingX) x = b.getRightX() - paddingX;
        }
        if (b.getYRadius() < paddingY) y = b.getCentreY();
        else {
            if (y < b.getBottomY() + paddingY) y = b.getBottomY() + paddingY;
            if (y > b.getTopY() - paddingY) y = b.getTopY() - paddingY;
        }
    }

}
