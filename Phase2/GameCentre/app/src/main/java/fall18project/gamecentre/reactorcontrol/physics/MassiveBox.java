package fall18project.gamecentre.reactorcontrol.physics;

import java.io.Serializable;

/**
 * A box (rectangle) with a velocity and mass which can be affected by forces
 */
public class MassiveBox extends Box implements Pushable, Serializable {

    /**
     * Center of mass of the box
     */
    private MassivePoint centre;

    /**
     * X radius of the box, i.e. width/2
     */
    private double xRadius;

    /**
     * Y radius of the box, i.e. length/2
     */
    private double yRadius;

    /**
     * Create a new box with the given centre, x radius and y radius
     *
     * @param centre  the centre of this box
     * @param xRadius the x radius of this box
     * @param yRadius the y radius of this box
     */
    public MassiveBox(MassivePoint centre, double xRadius, double yRadius) {
        this.centre = centre;
        this.xRadius = xRadius;
        this.yRadius = yRadius;
    }

    /**
     * Push the box in the X direction with force fx
     *
     * @param fx the amount of force to apply in the X direction
     */
    public void pushX(double fx) {
        centre.pushX(fx);
    }

    /**
     * Push the box in the Y direction with force fy
     *
     * @param fy the amount of force to apply in the Y direction
     */
    public void pushY(double fy) {
        centre.pushY(fy);
    }

    /**
     * Push the box with a given force in the x and y directions
     *
     * @param fx the amount of force to apply in the X direction
     * @param fy the amount of force to apply in the Y direction
     */
    public void push(double fx, double fy) {
        centre.push(fx, fy);
    }

    /**
     * Accelerate the object in the X direction by ax
     *
     * @param ax the amount of acceleration to apply in the X direction
     */
    public void accX(double ax) {
        centre.accX(ax);
    }

    /**
     * Push the object in the Y direction with acceleration ay
     *
     * @param ay the amount of acceleration to apply in the Y direction
     */
    public void accY(double ay) {
        centre.accY(ay);
    }

    /**
     * Push the object with a given force in the x and y directions
     *
     * @param ax the amount of force to apply in the X direction
     * @param ay the amount of force to apply in the Y direction
     */
    public void acc(double ax, double ay) {
        centre.acc(ax, ay);
    }

    /**
     * Get this box's mass
     *
     * @return mass of this box
     */
    public double getMass() {
        return centre.getMass();
    }


    /**
     * Get the centre's X coordinate
     *
     * @return the centre's X coordinate
     */
    public double getCentreX() {
        return centre.getX();
    }

    /**
     * Set the centre's X coordinate
     *
     * @param x the centre's new X coordinate
     */
    public void setCentreX(double x) {
        centre.setX(x);
    }

    /**
     * Get the centre's Y coordinate
     *
     * @return the centre's Y coordinate
     */
    public double getCentreY() {
        return centre.getY();
    }

    /**
     * Set the centre's Y coordinate
     *
     * @param y the centre's new Y coordinate
     */
    public void setCentreY(double y) {
        centre.setY(y);
    }

    /**
     * Shift the object dx in the x direction
     *
     * @param dx how much to shift by
     */
    public void shiftX(double dx) {
        centre.shiftX(dx);
    }

    /**
     * Shift the object dy in the y direction
     *
     * @param dy how much to shift by
     */
    public void shiftY(double dy) {
        centre.shiftY(dy);
    }

    /**
     * Shift the object's position
     *
     * @param dx how much to shift the x position by
     * @param dy how much to shift the y position by
     */
    public void shift(double dx, double dy) {
        centre.shift(dx, dy);
    }


    /**
     * Get this box's velocity in the X direction
     *
     * @return this box's velocity in the X direction
     */
    public double getXVelocity() {
        return centre.getXVelocity();
    }

    /**
     * Get this box's velocity in the Y direction
     *
     * @return this box's velocity in the Y direction
     */
    public double getYVelocity() {
        return centre.getYVelocity();
    }


    /**
     * Set the object's velocity in the x direction to be vx
     *
     * @param vx the object's new velocity in the x direction
     */
    public void setVx(double vx) {
        centre.setVx(vx);
    }

    /**
     * Set the object's velocity in the y direction to be vy
     *
     * @param vy the object's new velocity in the y direction
     */
    public void setVy(double vy) {
        centre.setVy(vy);
    }

    /**
     * Get the centre
     *
     * @return the centre as a Point
     */
    public Point getCentre() {
        return centre;
    }

    /**
     * Get the X radius
     *
     * @return the box's X radius
     */
    public double getXRadius() {
        return xRadius;
    }

    /**
     * Set the X radius
     *
     * @param x the box's new X radius
     */
    public void setXRadius(double x) {
        xRadius = x;
    }

    /**
     * Get the Y radius
     *
     * @return the box's Y radius
     */
    public double getYRadius() {
        return yRadius;
    }

    /**
     * Set the Y radius
     *
     * @param y the box's new Y radius
     */
    public void setYRadius(double y) {
        yRadius = y;
    }

    /**
     * Bind the centre of this box to the inside of a larger box. If it can't fit, snap to the centre
     * of the other box
     *
     * @param b the box to bind to
     */
    public void boundWithinBox(Box b) {
        centre.boundWithin(b, xRadius, yRadius);
    }

    /**
     * Bind the centre of this box to the inside of a larger box with padding.
     * If it can't fit, snap to the centre of the other box
     *
     * @param b        the box to bind to
     * @param paddingX the amount of padding to use in the X direction
     * @param paddingY the amount of padding to use in the Y direction
     */
    public void boundWithinBox(Box b, double paddingX, double paddingY) {
        centre.boundWithin(b, xRadius + paddingX, yRadius + paddingY);
    }

    /**
     * Move this point inertially for one time step
     */
    public void moveTimeStep() {
        centre.moveTimeStep();
    }


}
