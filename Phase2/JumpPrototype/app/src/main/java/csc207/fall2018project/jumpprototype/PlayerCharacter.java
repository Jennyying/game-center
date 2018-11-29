package csc207.fall2018project.jumpprototype;

import java.io.Serializable;

/**
 * The current state of the player's character, i.e. position, velocity and health remaining
 */
public class PlayerCharacter extends MassiveBox implements Serializable {

    /**
     * The player's current shield
     */
    private double shield;

    /**
     * The player's current health
     */
    private int health;

    /**
     * The player's current score
     */
    private long score = 0;

    /**
     * The default X radius of the bounding box surrounding the center of the player to check for collisions.
     */
    public static final double DEFAULT_BOUNDING_BOX_X_RADIUS = 0;
    /**
     * The default Y radius of the bounding box surrounding the center of the player to check for collisions.
     */
    public static final double DEFAULT_BOUNDING_BOX_Y_RADIUS = 0;
    /**
     * The default starting mass for player characters
     */
    public static final double DEFAULT_PLAYER_MASS = 80;

    /**
     * Construct a new player at a given point, with a given size, mass and starting health and shield
     * @param centre the given player's starting position and velocity
     * @param rx the given player's starting x radius
     * @param ry the given player's starting y radius
     * @param shield the given player's starting shield
     * @param health the given player's starting health
     */
    public PlayerCharacter(MassivePoint centre, double rx, double ry, double shield, int health) {
        super(centre, rx, ry);
        this.shield = shield;
        this.health = health;
    }

    /**
     * Construct a new player at a given point with a given mass, starting health and shield using the
     * default starting size
     * @param centre the given player's starting position and velocity
     * @param shield the given player's starting shield
     * @param health the given player's starting health
     */
    public PlayerCharacter(MassivePoint centre, double shield, int health) {
        this(centre, DEFAULT_BOUNDING_BOX_X_RADIUS, DEFAULT_BOUNDING_BOX_Y_RADIUS, shield, health);
    }

    /**
     * Get the player's current health
     * @return the player's health
     */
    public int getHealth() {return health;}

    /**
     * Check whether the player has health remaining
     * @return whether the player has any health remaining
     */
    public boolean hasHealth() {return health == 0;}

    /**
     * Decrement the player's current health
     */
    public void decrementHealth() {
        health--;
    }

    /**
     * Decrement the player's current health by a given amount
     * @param amount amount to decrement health by
     */
    public void decrementHealth(int amount) {
        health -= amount;
    }

    /**
     * Do damage to the player's shields
     * @param damage damage to do
     */
    public void damageShields(double damage) {
        shield -= damage;
        if(shield < 0) shield = 0;
    }

    /**
     * Do damage to the player
     * @param damage damage to do
     */
    public void doDamage(double damage) {
        if(shield < damage) {
            damage -= shield;
            shield = 0;
        }
        decrementHealth((int)damage);
    }

    /**
     * Get the player's current shield
     * @return the player's shield
     */
    public double getShield() {return shield;}

    /**
     * Get the player's current score
     * @return the player's score
     */
    public long getScore() {return score;}

    /**
     * Increment the player's score
     */
    public void incrementScore() {score++;}

    /**
     * Increment the player's score by a specified amount
     * @param amount amount to increment the score by
     */
    public void incrementScore(long amount) {score += amount;}

}
