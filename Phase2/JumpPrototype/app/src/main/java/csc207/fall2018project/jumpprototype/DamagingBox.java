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
     * Reset the box to have a random y position within a box with a given padding,
     * centre x position at the edge of a given box and
     * random x, y velocity in (-vx, 0) and (0, vy) respectively
     * @param b box to use
     * @param p padding to use. If greater than y radius of the box, the new y position snaps to the
     *          center of b's y coordinate
     * @param vx maximum x velocity
     * @param vy maximum y velocity
     */
    public void resetBox(Box b, double p, double vx, double vy) {
        setCentreX(b.getRightX());
        if(p > b.getYRadius()) p = b.getYRadius();
        setCentreY(((Math.random() * 2) - 1) * (b.getYRadius() - p) - b.getCentreY());
        setVx(Math.random() * -1 * vx);
        setVy(Math.random() * vy);
    }

}
