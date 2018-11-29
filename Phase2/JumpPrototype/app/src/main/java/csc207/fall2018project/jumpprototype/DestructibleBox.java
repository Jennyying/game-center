package csc207.fall2018project.jumpprototype;

import java.io.Serializable;

public class DestructibleBox extends MassiveBox implements Serializable, DestructibleGameObject {
    private boolean spent;

    /**
     * Create a new destructible box with given center, x radius, y radius and damage
     * @param spent whether the box is alive on creation
     * @param center Position and mass of the center of mass
     * @param rx x radius of the box
     * @param ry y radius of the box
     */
    public DestructibleBox(boolean spent, MassivePoint center, double rx, double ry) {
        super(center, rx, ry);
        this.spent = spent;
    }

    /**
     * Whether this box should be kept around next tick
     * @return whether the box has already interacted with the player
     */
    public boolean keepAlive() {
        return !spent;
    }

    /**
     * Un-spend a destructible box, i.e. make it alive again
     */
    public void makeAlive() {spent = false;}

    /**
     * Spend a destructible box, i.e. make it stop being alive
     */
    public void makeSpent() {spent = true;}

}
