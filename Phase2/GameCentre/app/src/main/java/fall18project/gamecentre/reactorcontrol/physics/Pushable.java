package fall18project.gamecentre.reactorcontrol.physics;

/**
 * An object which can be pushed with a force in the X and Y directions, impacting its velocity.
 */
public interface Pushable extends MovingObject {

    /**
     * Push the object in the X direction with force fx
     *
     * @param fx the amount of force to apply in the X direction
     */
    public void pushX(double fx);

    /**
     * Push the object in the Y direction with force fy
     *
     * @param fy the amount of force to apply in the Y direction
     */
    public void pushY(double fy);

    /**
     * Push the object with a given force in the x and y directions
     *
     * @param fx the amount of force to apply in the X direction
     * @param fy the amount of force to apply in the Y direction
     */
    public void push(double fx, double fy);

    /**
     * Accelerate the object in the X direction by ax
     *
     * @param ax the amount of acceleration to apply in the X direction
     */
    public void accX(double ax);

    /**
     * Push the object in the Y direction with acceleration ay
     *
     * @param ay the amount of acceleration to apply in the Y direction
     */
    public void accY(double ay);

    /**
     * Push the object with a given force in the x and y directions
     *
     * @param ax the amount of force to apply in the X direction
     * @param ay the amount of force to apply in the Y direction
     */
    public void acc(double ax, double ay);

    /**
     * Get the object's mass. Returns 0 for objects not affected by forces (like lasers)
     *
     * @return the object's mass
     */
    public double getMass();

}
