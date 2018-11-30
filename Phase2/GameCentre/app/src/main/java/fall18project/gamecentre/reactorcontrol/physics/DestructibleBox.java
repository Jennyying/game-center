package fall18project.gamecentre.reactorcontrol.physics;

import java.io.Serializable;

public class DestructibleBox extends MassiveBox implements Serializable, VelocityRespawnable {
    private boolean spent;

    /**
     * Create a new destructible box with given center, x radius, y radius and damage
     *
     * @param spent  whether the box is alive on creation
     * @param center Position and mass of the center of mass
     * @param rx     x radius of the box
     * @param ry     y radius of the box
     */
    public DestructibleBox(boolean spent, MassivePoint center, double rx, double ry) {
        super(center, rx, ry);
        this.spent = spent;
    }

    /**
     * Whether this box should be kept around next tick
     *
     * @return whether the box has already interacted with the player
     */
    public boolean keepAlive() {
        return !spent;
    }

    /**
     * Un-spend a destructible box, i.e. make it alive again
     */
    public void makeAlive() {
        spent = false;
    }

    /**
     * Spend a destructible box, i.e. make it stop being alive
     */
    public void makeSpent() {
        spent = true;
    }

    /**
     * Respawn this box at a given (x, y) position
     *
     * @param x new x position
     * @param y new y position
     */
    public void respawn(double x, double y) {
        makeAlive();
        setCentreX(x);
        setCentreY(y);
    }

    /**
     * Respawn this object at a given (x, y) position with velocity (vx, vy), making it alive again
     *
     * @param x  new x position
     * @param y  new y position
     * @param vx new x velocity
     * @param vy new y velocity
     */
    public void respawn(double x, double y, double vx, double vy) {
        makeAlive();
        setCentreX(x);
        setCentreY(y);
        setVx(vx);
        setVy(vy);
    }

}
