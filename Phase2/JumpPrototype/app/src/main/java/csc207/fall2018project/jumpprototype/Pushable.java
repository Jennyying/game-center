package csc207.fall2018project.jumpprototype;

/**
 * An object which can be pushed with a force in the X and Y directions, impacting its velocity.
 */
public interface Pushable {

    /**
     * Push the object in the X direction with force fx
     * @param fx the amount of force to apply in the X direction
     */
    public void pushX(double fx);

    /**
     * Push the object in the Y direction with force fy
     * @param fy the amount of force to apply in the Y direction
     */
    public void pushY(double fy);

    /**
     * Push the object with a given force in the x and y directions
     * @param fx the amount of force to apply in the X direction
     * @param fy the amount of force to apply in the Y direction
     */
    public void push(double fx, double fy);

}
