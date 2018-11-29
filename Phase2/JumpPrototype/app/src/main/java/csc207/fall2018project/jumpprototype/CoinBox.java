package csc207.fall2018project.jumpprototype;

import java.io.Serializable;

/**
 * A box which increases the user's score on collision
 */
public class CoinBox extends DestructibleBox implements PlayerInteractable, Serializable {

    private long scoreIncrement;


    /**
     * Create a new coin box with given center, x radius, y radius and damage
     * @param score Score given for contact with the box
     * @param center Position and mass of the center of mass
     * @param rx x radius of the box
     * @param ry y radius of the box
     */
    public CoinBox(long score, MassivePoint center, double rx, double ry) {
        super(false, center, rx, ry);
        scoreIncrement = score;
    }

    /**
     * Create a new spent coin box with only a given damage, mass, x radius and y radius
     * @param score score contact with the box awards
     * @param m mass of the box
     * @param rx x radius of the box
     * @param ry y radius of the box
     */
    public CoinBox(long score, double m, double rx, double ry) {

        super(true, new MassivePoint(m, 0, 0), rx, ry);
        scoreIncrement = score;
    }

    /**
     * Interact with the player by doing damage if the player overlaps with the box, then destroying
     * the box
     * @param p player to interact with
     */
    public void interactWith(PlayerCharacter p) {
        if(this.collidesWith(p)) {p.incrementScore(scoreIncrement); makeSpent();}
    }

}
