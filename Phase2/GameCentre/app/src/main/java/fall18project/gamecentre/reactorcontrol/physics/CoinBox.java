package fall18project.gamecentre.reactorcontrol.physics;

import java.io.Serializable;

/**
 * A box which increases the user's score on collision
 */
public class CoinBox extends DestructibleBox implements PlayerInteractable, Serializable {

    /**
     * The default mass for a CoinBox
     */
    public static final double DEFAULT_COINBOX_MASS = 1;
    /**
     * The amount that this CoinBox increments the score by
     */
    private long scoreIncrement;


    /**
     * Create a new coin box with given center, x radius, y radius and damage
     *
     * @param score  Score given for contact with the box
     * @param center Position and mass of the center of mass
     * @param rx     x radius of the box
     * @param ry     y radius of the box
     */
    public CoinBox(long score, MassivePoint center, double rx, double ry) {
        super(false, center, rx, ry);
        scoreIncrement = score;
    }

    /**
     * Create a new spent coin box with only a given damage, mass, x radius and y radius
     *
     * @param score score contact with the box awards
     * @param m     mass of the box
     * @param rx    x radius of the box
     * @param ry    y radius of the box
     */
    public CoinBox(long score, double m, double rx, double ry) {
        super(true, new MassivePoint(m, 0, 0), rx, ry);
        scoreIncrement = score;
    }

    /**
     * Create a new spent coin box which awards no score, having only a mass, x radius and y radius
     *
     * @param m  mass of the box
     * @param rx x radius of the box
     * @param ry y radius of the box
     */
    public CoinBox(double m, double rx, double ry) {
        this(0, m, rx, ry);
    }

    /**
     * Create a new spent coin box which awards no score and has the default mass with a given
     * x radius and y radius
     *
     * @param rx x radius of the box
     * @param ry y radius of the box
     */
    public CoinBox(double rx, double ry) {
        this(0, DEFAULT_COINBOX_MASS, rx, ry);
    }

    /**
     * Interact with the player by doing damage if the player overlaps with the box, then destroying
     * the box
     *
     * @param p player to interact with
     */
    public void interactWith(PlayerCharacter p) {
        if (keepAlive() && this.collidesWith(p)) {
            p.incrementScore(scoreIncrement);
            makeSpent();
        }
    }

    /**
     * Set the score increment obtained when touching this coin box
     *
     * @param increment the new increment to set
     */
    public void setScoreIncrement(long increment) {
        scoreIncrement = increment;
    }

}
