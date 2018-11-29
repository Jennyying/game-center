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
     * The force of gravity
     */
    private double gravityLevel = 2;

    /**
     * Create a new GameState for a given screen width and height
     * @param player the player character
     * @param width the screen's width
     * @param height the screen's height
     */
    public GameState(PlayerCharacter player, int width, int height) {
        screen = new Screen(new MassivePoint(Screen.DEFAULT_SCREEN_MASS, 0, 0), width, height);
        this.player = player;
    }

    /**
     * Get the player character
     * @return the player chracter
     */
    public PlayerCharacter getPlayer() {return player;}


    /**
     * Run a single game tick
     */
    public void runTick() {
    }

}
