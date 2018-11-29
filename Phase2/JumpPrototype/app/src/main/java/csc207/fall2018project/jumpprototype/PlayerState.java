package csc207.fall2018project.jumpprototype;

import java.io.Serializable;

/**
 * The current state of the player, i.e. position, velocity and health remaining
 */
public class PlayerState implements Serializable {

    /**
     * The player's current X coordinate *on the screen*, i.e. stored as an offset of the screen's X
     * coordinate
     */
    private double x;

    /**
     * The screen's current X coordinate
     */
    private double sx = 0;

    /**
     * The player's current Y coordinate *on the screen*, i.e. stored as an offset  of the screen's Y
     * coordinate
     */
    private double y;

    /**
     * The screen's current Y coordinate
     */
    private double sy = 0;

    /**
     * The player's current velocity in the X direction
     */
    private double vx;

    /**
     * The screen's current velocity in the X direction
     */
    private double svx = 0;

    /**
     * The player's current velocity in the Y direction
     */
    private double vy;

    /**
     * The screen's current velocity in the Y direction
     */
    private double svy = 0;

    /**
     * The player's current health
     */
    private double health;

    /**
     * The radius of the bounding box surrounding the center of the player to check for collisions.
     */
    private static final double BOUNDING_BOX_RADIUS = 50;

    /**
     * Construct a new PlayerState with the given starting parameters
     * @param x starting x coordinate
     * @param y starting y coordinate
     * @param sx starting screen x coordinate
     * @param sy starting screen y coordinate
     * @param vx starting x velocity
     * @param vy starting y velocity
     * @param svx starting screen x velocity
     * @param svy starting screen y velocity
     * @param health starting player health
     */
    public PlayerState(
            double x, double y, double sx, double sy, double vx, double vy, double svx, double svy,
            double health) {
        this.x = x;
        this.y = y;
        this.sx = sx;
        this.sy = sy;
        this.vx = vx;
        this.vy = vy;
        this.svx = svx;
        this.svy = svy;
        this.health = health;
    }

    /**
     * Check whether the player's position is within the box made by the (minX, minY) and (maxX, maxY)
     * @param minX minimum X coordinate
     * @param minY minimum Y coordinate
     * @param maxX maximum X coordinate
     * @param maxY maximum Y coordinate
     * @return whether (x, y) is in bounds
     */
    public boolean isInBounds(double minX, double minY, double maxX, double maxY) {
        return x >= minX && y >= minY && x <= maxX && y <= maxY;
    }

    /**
     * Check whether the rectangle centered at (centerX, centerY) with X-radius radiusX
     * (i.e. width 2*radiusX) and Y-radius radiusY (i.e. height 2*radiusY) overlaps the square
     * of radius BOUNDING_BOX_RADIUS centered at (x, y)
     * @param centerX the x coordinate of the center of the rectangle to check for collisions with
     * @param centerY the y coordinate of the center of the rectangle to check for collisions with
     * @param radiusX the x radius of the rectangle to check for collisions with
     * @param radiusY the y radius of the rectangle to check for collisions with
     * @return Whether there is a collision with the given rectangle
     */
    public boolean checkCollision(
            double centerX, double centerY, double radiusX, double radiusY) {
        // Unsigned x and y differences between the two centeres
        double dx = Math.abs(x - centerX);
        double dy = Math.abs(y - centerY);

        return dx <= radiusX + BOUNDING_BOX_RADIUS && dy <= radiusY + BOUNDING_BOX_RADIUS;
    }

    /**
     * Get the distance between the player's position and a point (px, py)
     * @param px the x coordinate of the point
     * @param py the y coordinate of the point
     */
    public double getDistance(double px, double py) {
        return Math.sqrt((x - px) * (x - px) + (y - py) * (y - py));
    }


}
