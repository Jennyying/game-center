package fall18project.gamecentre.reactorcontrol.physics;

import java.io.Serializable;

/**
 * A point with a velocity and mass which can be affected by forces
 */
public class MassivePoint extends MovingPoint implements Pushable, Serializable {

    private double mass;

    /**
     * Create a new massive point with a given mass, position and velocity
     *
     * @param mass mass of the point
     * @param x    x position
     * @param y    y position
     * @param vx   x velocity
     * @param vy   y velocity
     */
    public MassivePoint(double mass, double x, double y, double vx, double vy) {
        super(x, y, vx, vy);
        this.mass = mass;
    }

    /**
     * Create a new massive point with no velocity at a given position
     *
     * @param mass mass of the new point
     * @param x    x position
     * @param y    y position
     */
    public MassivePoint(double mass, double x, double y) {
        this(mass, x, y, 0, 0);
    }

    /**
     * Get this point's mass
     *
     * @return mass of this point
     */
    public double getMass() {
        return mass;
    }


    /**
     * Push the point in the X direction with force fx
     *
     * @param fx the amount of force to apply in the X direction
     */
    public void pushX(double fx) {
        if (mass == 0) return;
        accX(fx / mass);
    }

    /**
     * Push the point in the Y direction with force fy
     *
     * @param fy the amount of force to apply in the Y direction
     */
    public void pushY(double fy) {
        if (mass == 0) return;
        accY(fy / mass);
    }

    /**
     * Push the point with a given force in the x and y directions
     *
     * @param fx the amount of force to apply in the X direction
     * @param fy the amount of force to apply in the Y direction
     */
    public void push(double fx, double fy) {
        pushX(fx);
        pushY(fy);
    }

}
