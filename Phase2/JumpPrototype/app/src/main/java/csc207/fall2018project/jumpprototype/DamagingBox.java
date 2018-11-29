package csc207.fall2018project.jumpprototype;

import java.io.Serializable;

/**
 * A box which does damage to the player's health
 */
public class DamagingBox extends MassiveBox
        implements PlayerInteractable, Serializable, DestructibleGameObject {

    private double damage;
    private boolean spent;

    /**
     * Create a new damaging box with given center, x radius, y radius and damage
     * @param damage Damage contact with the box causes
     * @param center Position and mass of the center of mass
     * @param rx x radius of the box
     * @param ry y radius of the box
     */
    public DamagingBox(double damage, MassivePoint center, double rx, double ry) {
        super(center, rx, ry);
        this.damage = damage;
        spent = false;
    }

    /**
     * Create a new spent damaging box with only a given damage, mass, x radius and y radius
     * @param damage Damage contact with the box causes
     * @param m mass of the box
     * @param rx x radius of the box
     * @param ry y radius of the box
     */
    public DamagingBox(double damage, double m, double rx, double ry) {
        this(damage, new MassivePoint(m, 0, 0), rx, ry);
        spent = true;
    }

    /**
     * Interact with the player by doing damage if the player overlaps with the box, then destroying
     * the box
     * @param p player to interact with
     */
    public void interactWith(PlayerCharacter p) {
        if(this.collidesWith(p)) {p.doDamage(damage); spent = true;}
    }

    /**
     * Whether this box should be kept around next tick
     * @return whether the box has already interacted with the player
     */
    public boolean keepAlive() {
        return !spent;
    }

    /**
     * Un-spend a damaging box, i.e. make it alive again
     */
    public void makeAlive() {spent = false;}

}
