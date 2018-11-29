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
    private double health;

    /**
     * The player's current score
     */
    private long score = 0;

    /**
     * The default X radius of the bounding box surrounding the center of the player to check for collisions.
     */
    public static final double DEFAULT_BOUNDING_BOX_X_RADIUS = 50;
    /**
     * The default Y radius of the bounding box surrounding the center of the player to check for collisions.
     */
    public static final double DEFAULT_BOUNDING_BOX_Y_RADIUS = 50;
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
    public PlayerCharacter(MassivePoint centre, double rx, double ry, double shield, double health) {
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
    public PlayerCharacter(MassivePoint centre, double shield, double health) {
        this(centre, DEFAULT_BOUNDING_BOX_X_RADIUS, DEFAULT_BOUNDING_BOX_Y_RADIUS, shield, health);
    }

}
