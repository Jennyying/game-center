package fall18project.gamecentre.reactorcontrol.physics;

import java.io.Serializable;

public class Screen extends MassiveBox implements Serializable {

    public static double DEFAULT_SCREEN_MASS = 1000.0;

    /**
     * Create a new, move-able screen with a given centre, mass, width and height
     *
     * @param centre
     * @param width  the screen width
     * @param height the screen height
     */
    public Screen(MassivePoint centre, int width, int height) {
        super(centre, ((double) width) / 2, ((double) height) / 2);
    }

    /**
     * Get the width of the screen as an integer
     *
     * @return the width of the screen
     */
    public int getWidth() {
        return (int) (getXRadius() * 2);
    }

    /**
     * Get the height of the screen as an integer
     *
     * @return the height of the screen
     */
    public int getHeight() {
        return (int) (getYRadius() * 2);
    }

    /**
     * Get the X position on the screen of a given X coordinate in space
     *
     * @param x x position in space
     */
    public int getDrawXPosition(double x) {
        return (int) (x + getXRadius() - getCentreX());
    }

    /**
     * Get the Y position on the screen of a given Y coordinate in space
     *
     * @param y y position in space
     */
    public int getDrawYPosition(double y) {
        return (int) ((-y) + getYRadius() - getCentreY());
    }

    /**
     * Set the screen size
     *
     * @width width to set it to
     * @height height to set it to
     */
    public void setScreenSize(int width, int height) {
        setXRadius(width / 2);
        setYRadius(height / 2);
    }
}
