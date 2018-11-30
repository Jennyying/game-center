package fall18project.gamecentre;

import org.junit.Before;
import org.junit.Test;

import fall18project.gamecentre.reactorcontrol.physics.MassiveBox;
import fall18project.gamecentre.reactorcontrol.physics.MassivePoint;

public class MassiveBoxTest {
    /**
     * The Massive Box for testing
     */
    private MassiveBox massiveBox;

    /**
     * Make a MassiveBox.
     */
    @Before
    public void setUp() throws Exception {
        MassivePoint massivePoint = new MassivePoint(1.1, 2.2, 3.5, 2.5, 7.4);
        massiveBox = new MassiveBox(massivePoint, 4.1, 5.6);
    }


    @Test
    public void pushX() {
    }

    @Test
    public void pushY() {
    }

    @Test
    public void push() {
    }

    @Test
    public void accX() {
    }

    @Test
    public void accY() {
    }

    @Test
    public void acc() {
    }


    @Test
    public void shiftX() {
    }

    @Test
    public void shiftY() {
    }

    @Test
    public void shift() {
    }

    @Test
    public void boundWithinBox() {
    }

    @Test
    public void boundWithinBox1() {
    }

    @Test
    public void moveTimeStep() {
    }
}