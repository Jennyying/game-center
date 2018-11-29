package csc207.fall2018project.jumpprototype;

/**
 * A point in 2-dimensional Euclidean space
 */
public class Point implements Shiftable {

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
    public Point(double x, double y) {
        this.x = x; this.y = y;
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

}
