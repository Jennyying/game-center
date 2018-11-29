package csc207.fall2018project.jumpprototype;

import java.io.Serializable;

/**
 * The state of a JumpingRabbit game, including:
 * - Whether the game is paused or running
 * - The position and velocity of the rabbit and of any obstacles
 */
public class GameState implements Serializable {

    /**
     * The screen, which can be moved by in-game events
     */
    private Screen screen;

    /**
     * The player character
     */
    private PlayerCharacter player;

    /**
     * The poison bottle, modelled as a DamagingBox with mass 5 doing 1 damage and having x and y
     * radii of 1
     */
    private DamagingBox laser;

    /**
     * The force of gravity
     */
    private double gravityLevel = -2;

    /**
     * Laser spawn timer
     */
    private int spawnTimer = 0;

    /**
     * Laser spawn timeout
     */
    private static int LASER_SPAWN_TIMEOUT = 100;

    /**
     * Create a new GameState for a given screen width and height
     * @param player the player character
     * @param laser the laser shooting at the character
     * @param width the screen's width
     * @param height the screen's height
     */
    public GameState(PlayerCharacter player, DamagingBox laser, int width, int height) {
        screen = new Screen(new MassivePoint(Screen.DEFAULT_SCREEN_MASS, 0, 0), width, height);
        this.player = player;
        this.laser = laser;
    }

    /**
     * Get the player character
     * @return the player chracter
     */
    public PlayerCharacter getPlayer() {return player;}


    /**
     * Set the current gravity level
     * @param gravityLevel new gravity level
     */
    public void setGravityLevel(double gravityLevel) {
        this.gravityLevel = gravityLevel;
    }

    /**
     * Run a single game tick
     */
    public void runTick() {
        player.accY(gravityLevel);
        if(player.getCentreX() > -0.7 * screen.getXRadius()) {
            player.setCentreX(-0.7 * screen.getXRadius());
        }

        laser.moveTimeStep();
        if(laser.keepAlive()) spawnTimer = 0;
        else spawnTimer++;
        if(spawnTimer > LASER_SPAWN_TIMEOUT || !laser.collidesWith(screen)) {
            laser.setCentreX(screen.getCentreX() + screen.getXRadius() - 1);
            laser.setCentreY((2* Math.random() - 1) * 0.7 * screen.getYRadius() + screen.getCentreY());
            laser.setVx(-28);
            laser.setVy(0);
            laser.makeAlive();
        }
        player.moveTimeStep();

        player.boundWithinBox(screen);

        laser.interactWith(player);
    }

    /**
     * Get the X coordinate on the screen to draw the player at
     * @return x coordinate of where to draw the player on the screen
     */
    public int getPlayerDrawX() {
        return screen.getDrawXPosition(player.getCentreX());
    }

    /**
     * Get the Y coordinate on the screen to draw the player at
     * @return y coordinate of where to draw the player on the screen
     */
    public int getPlayerDrawY() {
        return screen.getDrawYPosition(player.getCentreY());
    }

    /**
     * Get the X coordinate of the poison on the screen
     * @return x coordinate to draw the poison at
     */
    public int getLaserDrawX() {
        return screen.getDrawXPosition(laser.getCentreX());
    }

    /**
     * Get the Y coordinate of the poison on the screen
     * @return y coordinate to draw the poison at
     */
    public int getLaserDrawY() {
        return screen.getDrawYPosition(laser.getCentreY());
    }

    /**
     * Whether to draw the laser or not
     * @return whether the laser is alive
     */
    public boolean shouldDrawLaser() {return laser.keepAlive();}

    /**
     * Set the screen size
     * @param width new screen width
     * @param height new screen height
     */
    public void setScreenSize(int width, int height) {
        screen.setScreenSize(width, height);
    }

}
