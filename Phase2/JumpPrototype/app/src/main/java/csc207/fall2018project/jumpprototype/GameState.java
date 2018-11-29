package csc207.fall2018project.jumpprototype;

/**
 * The state of a JumpingRabbit game, including:
 * - Whether the game is paused or running
 * - The position and velocity of the rabbit and of any obstacles
 */
public class GameState {

    /**
     * The screen, which can be moved by in-game events
     */
    private Screen screen;

    /**
     * The player character
     */
    private PlayerCharacter player;

    /**
     * Create a new GameState for a given screen width and height
     * @param width the screen's width
     * @param height the screen's height
     */
    public GameState(int width, int height) {
        screen = new Screen(new MassivePoint(Screen.DEFAULT_SCREEN_MASS, 0, 0), width, height);
        player = new PlayerCharacter(
                new MassivePoint(PlayerCharacter.DEFAULT_PLAYER_MASS, 0, 0), width, height);
    }

}
