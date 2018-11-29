package csc207.fall2018project.jumpprototype;

/**
 * A box (rectangle) with a velocity and mass which can be affected by forces
 */
public class MassiveBox extends Box implements Pushable {

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
     * @param centre the centre of this box
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
     * @param fx the amount of force to apply in the X direction
     */
    public void pushX(double fx) {
        centre.pushX(fx);
    }

    /**
     * Push the box in the Y direction with force fy
     * @param fy the amount of force to apply in the Y direction
     */
    public void pushY(double fy) {
        centre.pushY(fy);
    }

    /**
     * Push the box with a given force in the x and y directions
     * @param fx the amount of force to apply in the X direction
     * @param fy the amount of force to apply in the Y direction
     */
    public void push(double fx, double fy) {
        centre.push(fx, fy);
    }

    /**
     * Get the centre's X coordinate
     * @return the centre's X coordinate
     */
    public double getCentreX() {
        return centre.getX();
    }

    /**
     * Get the centre's Y coordinate
     * @return the centre's Y coordinate
     */
    public double getCentreY() {
        return centre.getY();
    }

    /**
     * Get the centre
     * @return the centre as a Point
     */
    public Point getCentre() {
        return centre;
    }

    /**
     * Get the X radius
     * @return the box's X radius
     */
    public double getXRadius() {
        return xRadius;
    }

    /**
     * Get the Y radius
     * @return the box's Y radius
     */
    public double getYRadius() {
        return yRadius;
    }


}
