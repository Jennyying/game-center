package csc207.fall2018project.jumpprototype;

public class Screen extends MassiveBox {

    public static double DEFAULT_SCREEN_MASS = 1000.0;

    /**
     * Create a new, move-able screen with a given centre, mass, width and height
     * @param centre
     * @param width the screen width
     * @param height the screen height
     */
    public Screen(MassivePoint centre, int width, int height) {
        super(centre, ((double)width)/2, ((double)height)/2);
    }

    /**
     * Get the width of the screen as an integer
     * @return the width of the screen
     */
    public int getWidth() {
        return (int)(getXRadius() * 2);
    }

    /**
     * Get the height of the screen as an integer
     * @return the height of the screen
     */
    public int getHeight() {
        return (int)(getYRadius() * 2);
    }
}
