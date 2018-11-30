package fall18project.gamecentre;

import org.junit.Before;
import org.junit.Test;

import fall18project.gamecentre.reactorcontrol.physics.DestructibleBox;
import fall18project.gamecentre.reactorcontrol.physics.MassivePoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("deprecation")
public class DestructibleBoxTest extends MassiveBoxTest {
    /**
     * The DestructibleBox for testing
     */
    private MassivePoint massivePoint;
    private DestructibleBox destructibleBoxAlive;
    private DestructibleBox destructibleBoxNotAlive;

    /**
     * Make a DestructibleBox.
     */
    @Before
    public void setUp() throws Exception {
        MassivePoint massivePoint = new MassivePoint(1, 2, 3, 4, 5.5);
        destructibleBoxAlive = new DestructibleBox(true, massivePoint,
                3.2, 1.7);
        destructibleBoxNotAlive = new DestructibleBox(false, massivePoint, 0, 0);

    }

    /**
     * Test whether the box has already interacted with the player
     */
    @Test
    public void keepAlive() {
        assertFalse(destructibleBoxAlive.keepAlive());
        assertTrue(destructibleBoxNotAlive.keepAlive());
    }

    /**
     * Test if a destructibleBox is make alive again
     */
    @Test
    public void makeAlive() {
        destructibleBoxAlive.makeAlive();
        assertTrue(destructibleBoxAlive.keepAlive());
        destructibleBoxNotAlive.makeAlive();
        assertTrue(destructibleBoxNotAlive.keepAlive());
    }

    /**
     * Test if a destructibleBox stop being alive
     */
    @Test
    public void makeSpent() {
        destructibleBoxNotAlive.makeSpent();
        assertFalse(destructibleBoxNotAlive.keepAlive());
        destructibleBoxAlive.makeSpent();
        assertFalse(destructibleBoxAlive.keepAlive());
    }

    /**
     * Test if Respawn this box at a given (x, y) position
     */
    @Test
    public void respawn() {
        destructibleBoxNotAlive.makeAlive();
        destructibleBoxAlive.makeAlive();
        destructibleBoxAlive.respawn(1.2, 1.2);
        assertEquals(destructibleBoxAlive.getCentreY(), 1.2, 0.0001);
        destructibleBoxNotAlive.respawn(7.2, 0);
        assertEquals(destructibleBoxNotAlive.getCentreX(), 7.2, 0.0001);
    }


    /**
     * Test if this object is respawn at a given (x, y) position with velocity (vx, vy), alive again
     */
    @Test
    public void respawn1() {
        destructibleBoxAlive.makeAlive();
        destructibleBoxNotAlive.makeAlive();
        destructibleBoxNotAlive.respawn(1, 2, 2, 3);
        assertEquals(destructibleBoxNotAlive.getCentreX(), 1, 0.0001);
        assertEquals(destructibleBoxNotAlive.getCentreY(), 2, 0.0001);
        assertEquals(destructibleBoxNotAlive.getXVelocity(), 2, 0.0001);
        assertEquals(destructibleBoxNotAlive.getYVelocity(), 3, 0.0001);

    }
}