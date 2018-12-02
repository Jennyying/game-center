package fall18project.gamecentre.reactorcontrol.physics;

import java.io.Serializable;

/**
 * The state of a JumpingRabbit game, including:
 * - Whether the game is paused or running
 * - The position and velocity of the rabbit and of any obstacles
 */
public class GameState implements Serializable {

    /**
     * A constant indicating zero mass, just for clarity
     */
    public static final double MASSLESS_OBJECT = 0.0;

    /**
     * The maximum reward from touching a CoinBox
     */
    private static final long MAX_REWARD = 3000;

    /**
     * The minimum reward from touching a CoinBox
     */
    private static final long MIN_REWARD = 1000;
    /**
     * Laser spawn timeout
     */
    private static int LASER_SPAWN_TIMEOUT = 100;
    /**
     * Reward spawn timeout
     */
    private static int REWARD_SPAWN_TIMEOUT = 100;
    /**
     * The screen, which can be moved by in-game events
     */
    private Screen screen;
    /**
     * The player character
     */
    private PlayerCharacter player;
    /**
     * The a laser, modelled as a DamagingBox with mass 0 doing 1 damage and having x and y
     * radii of 1
     */
    private DamagingBox laser;
    /**
     * The coin reward box, modelled as a CoinBox with mass 1 giving a random number of coins between
     * MIN_REWARD and MAX_REWARD
     */
    private CoinBox reward;
    /**
     * The force of gravity
     */
    private double gravityLevel = -1;
    /**
     * Laser spawn timer
     */
    private int laserSpawnTimer = 0;
    /**
     * Reward spawn timer
     */
    private int rewardSpawnTimer = 0;

    /**
     * Create a new GameState for a given screen width and height
     *
     * @param player the player character
     * @param laser  the laser shooting at the character
     * @param reward a CoinBox launched at the player potentially giving a reward
     * @param width  the screen's width
     * @param height the screen's height
     */
    public GameState(
            PlayerCharacter player,
            DamagingBox laser,
            CoinBox reward,
            int width, int height) {
        screen = new Screen(new MassivePoint(Screen.DEFAULT_SCREEN_MASS, 0, 0), width, height);
        this.player = player;
        this.laser = laser;
        this.reward = reward;
    }

    /**
     * Apply gravity on a Pushable game object
     *
     * @param p object to apply gravity to
     */
    private void applyGravity(Pushable p) {
        if (p.getMass() == MASSLESS_OBJECT) return;
        p.accY(gravityLevel);
    }

    /**
     * Get the player character
     *
     * @return the player chracter
     */
    public PlayerCharacter getPlayer() {
        return player;
    }

    /**
     * Return whether the game is over, i.e. the player has 0 health remaining
     *
     * @return whether the game is over
     */
    public boolean isOver() {
        return !player.hasHealth();
    }

    /**
     * Return the score of this game
     *
     * @return the score
     */
    public long getScore() {
        return getPlayer().getScore();
    }


    /**
     * Set the current gravity level
     *
     * @param gravityLevel new gravity level
     */
    public void setGravityLevel(double gravityLevel) {
        this.gravityLevel = gravityLevel;
    }

    /**
     * Update the laser's position, respawning it if necessary
     */
    private void updateLaserPosition() {
        laser.moveTimeStep();

        if (laser.keepAlive()) laserSpawnTimer = 0;
        else laserSpawnTimer++;

        // Respawn the laser if it exits the screen, or has been destroyed for LASER_SPAWN_TIMEOUT
        // ticks
        if (laserSpawnTimer > LASER_SPAWN_TIMEOUT || !laser.collidesWith(screen)) {
            double newLaserX = screen.getCentreX() + screen.getXRadius() - 1;
            double newLaserY = (2 * Math.random() - 1) * 0.7 * screen.getYRadius() + screen.getCentreY();
            double newLaserVx = -28;
            double newLaserVy = 0;
            laser.respawn(newLaserX, newLaserY, newLaserVx, newLaserVy);
        }
    }

    /**
     * Update the reward's position
     */
    private void updateRewardPosition() {
        applyGravity(reward);
        reward.moveTimeStep();

        if (!reward.collidesWith(screen)) reward.makeSpent();

        if (reward.keepAlive()) rewardSpawnTimer = 0;
        else rewardSpawnTimer++;

        // Respawn the laser if it exits the screen, or has been destroyed for LASER_SPAWN_TIMEOUT
        // ticks
        if (rewardSpawnTimer > REWARD_SPAWN_TIMEOUT) {
            double newRewardX = screen.getCentreX() + screen.getXRadius() - 1;
            double newRewardY = (1.5 * Math.random() - 1) * 0.7 * screen.getYRadius() + screen.getCentreY();
            //TODO: set speeds properly
            double newRewardVx = -30;
            double newRewardVy = 30;
            reward.respawn(newRewardX, newRewardY, newRewardVx, newRewardVy);
            reward.setScoreIncrement((int) ((MAX_REWARD - MIN_REWARD) * Math.random() + MIN_REWARD));
        }
    }

    /**
     * Update the player's position
     */
    private void updatePlayerPosition() {
        applyGravity(player);
        player.moveTimeStep();
        player.boundWithinBox(screen);
    }

    /**
     * Interact between objects and the player
     */
    private void interactWithPlayer() {
        laser.interactWith(player);
        reward.interactWith(player);
    }

    /**
     * Run a single game tick
     */
    public void runTick() {
        updateLaserPosition();
        updateRewardPosition();
        updatePlayerPosition();

        if (!isOver()) {
            player.incrementScore();
            player.rechargeShield();
            interactWithPlayer();
        }
    }

    /**
     * Get the X coordinate on the screen to draw the player at
     *
     * @return x coordinate of where to draw the player on the screen
     */
    public int getPlayerDrawX() {
        return screen.getDrawXPosition(player.getCentreX());
    }

    /**
     * Get the Y coordinate on the screen to draw the player at
     *
     * @return y coordinate of where to draw the player on the screen
     */
    public int getPlayerDrawY() {
        return screen.getDrawYPosition(player.getCentreY());
    }

    /**
     * Get the X coordinate of the laser on the screen
     *
     * @return x coordinate to draw the laser at
     */
    public int getLaserDrawX() {
        return screen.getDrawXPosition(laser.getCentreX());
    }

    /**
     * Get the Y coordinate of the laser on the screen
     *
     * @return y coordinate to draw the laser at
     */
    public int getLaserDrawY() {
        return screen.getDrawYPosition(laser.getCentreY());
    }

    /**
     * Get the X coordinate of the coins on the screen
     *
     * @return x coordinate to draw the coins at
     */
    public int getCoinDrawX() {
        return screen.getDrawXPosition(reward.getCentreX());
    }

    /**
     * Get the Y coordinate of the coins on the screen
     *
     * @return y coordinate to draw the coins at
     */
    public int getCoinDrawY() {
        return screen.getDrawYPosition(reward.getCentreY());
    }


    /**
     * Whether to draw the laser or not
     *
     * @return whether the laser is alive
     */
    public boolean shouldDrawLaser() {
        return laser.keepAlive();
    }

    /**
     * Whether to draw the coins or not
     *
     * @return whether the coins are alive
     */
    public boolean shouldDrawCoins() {
        return reward.keepAlive();
    }

    /**
     * Whether to draw the coin box or not
     *
     * @return whether the coin box is alive
     */
    public boolean shouldDrawCoinBox() {
        return reward.keepAlive();
    }

    /**
     * Set the screen size
     *
     * @param width  new screen width
     * @param height new screen height
     */
    public void setScreenSize(int width, int height) {
        screen.setScreenSize(width, height);
    }

}
