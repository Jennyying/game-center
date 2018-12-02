package fall18project.gamecentre.reactorcontrol.physics;

import java.io.Serializable;

/**
 * The current state of the player's character, i.e. position, velocity and health remaining
 */
public class PlayerCharacter extends DestructibleBox implements Serializable {

    /**
     * The default X radius of the bounding box surrounding the center of the player to check for collisions.
     */
    public static final double DEFAULT_BOUNDING_BOX_X_RADIUS = 150;
    /**
     * The default Y radius of the bounding box surrounding the center of the player to check for collisions.
     */
    public static final double DEFAULT_BOUNDING_BOX_Y_RADIUS = 150;
    /**
     * The default starting mass for player characters
     */
    public static final double DEFAULT_PLAYER_MASS = 80;
    /**
     * The player's current shield
     */
    private double shield;
    /**
     * The player's maximum shield
     */
    private double maxShield;
    /**
     * The player's current health
     */
    private int health;
    /**
     * The player's current score
     */
    private long score = 0;
    /**
     * The players jumping strength
     */
    private double jumpStrength = 1000;

    /**
     * Construct a new player at a given point, with a given size, mass and starting health and shield
     *
     * @param centre the given player's starting position and velocity
     * @param rx     the given player's starting x radius
     * @param ry     the given player's starting y radius
     * @param shield the given player's starting (max) shield
     * @param health the given player's starting health
     */
    public PlayerCharacter(MassivePoint centre, double rx, double ry, double shield, int health) {
        super(true, centre, rx, ry);
        this.maxShield = shield;
        this.shield = shield;
        this.health = health;
    }

    /**
     * Construct a new player with a given size, mass and starting health and shield
     *
     * @param rx     the given player's starting x radius
     * @param ry     the given player's starting y radius
     * @param shield the given player's starting shield
     * @param health the given player's starting health
     */
    public PlayerCharacter(double m, double rx, double ry, double shield, int health) {
        super(false, new MassivePoint(m, 0, 0), rx, ry);
        this.shield = shield;
        this.health = health;
    }

    /**
     * Get the player's current health
     *
     * @return the player's health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Check whether the player has health remaining
     *
     * @return whether the player has any health remaining
     */
    public boolean hasHealth() {
        return health > 0;
    }

    /**
     * Decrement the player's current health
     */
    public void decrementHealth() {
        health--;
    }

    /**
     * Decrement the player's current health by a given amount
     *
     * @param amount amount to decrement health by
     */
    public void decrementHealth(int amount) {
        health -= amount;
    }

    /**
     * Do damage to the player's shields
     *
     * @param damage damage to do
     */
    public void damageShields(double damage) {
        shield -= damage;
        if (shield < 0) shield = 0;
    }

    /**
     * Do damage to the player
     *
     * @param damage damage to do
     */
    public void doDamage(double damage) {
        if (damage < shield) {
            shield -= damage;
        } else {
            damage -= shield;
            shield = 0;
        }
        decrementHealth((int) Math.ceil(damage));
    }

    /**
     * Get the player's current shield as a proportion of maximum shield
     *
     * @return the player's shield
     */
    public double getShield() {
        return shield/maxShield;
    }

    /**
     * Recharge the player's current shield for 1 tick
     */
    public void rechargeShield() {
        shield += 0.0005;
        if(shield > maxShield) shield = maxShield;
    }

    /**
     * Get the player's current score
     *
     * @return the player's score
     */
    public long getScore() {
        return score;
    }

    /**
     * Increment the player's score
     */
    public void incrementScore() {
        score++;
    }

    /**
     * Increment the player's score by a specified amount
     *
     * @param amount amount to increment the score by
     */
    public void incrementScore(long amount) {
        score += amount;
    }

    /**
     * Make the player jump
     */
    public void jump() {
        pushY(jumpStrength);
    }

    /**
     * Set the player's jump strength
     *
     * @param strength the player's new jump strength
     */
    public void setJumpStrength(double strength) {
        jumpStrength = strength;
    }

}
