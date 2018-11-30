package fall18project.gamecentre.reactorcontrol.physics;

import java.io.Serializable;

/**
 * A box which does damage to the player's health
 */
public class DamagingBox extends DestructibleBox implements PlayerInteractable, Serializable {

    private double damage;

    /**
     * Create a new damaging box with given center, x radius, y radius and damage
     *
     * @param damage Damage contact with the box causes
     * @param center Position and mass of the center of mass
     * @param rx     x radius of the box
     * @param ry     y radius of the box
     */
    public DamagingBox(double damage, MassivePoint center, double rx, double ry) {
        super(false, center, rx, ry);
        this.damage = damage;
    }

    /**
     * Create a new spent damaging box with only a given damage, mass, x radius and y radius
     *
     * @param damage Damage contact with the box causes
     * @param m      mass of the box
     * @param rx     x radius of the box
     * @param ry     y radius of the box
     */
    public DamagingBox(double damage, double m, double rx, double ry) {
        super(true, new MassivePoint(m, 0, 0), rx, ry);
        this.damage = damage;
    }

    /**
     * Interact with the player by doing damage if the player overlaps with the box, then destroying
     * the box
     *
     * @param p player to interact with
     */
    public void interactWith(PlayerCharacter p) {
        if (keepAlive() && this.collidesWith(p)) {
            p.doDamage(damage);
            makeSpent();
        }
    }

}
