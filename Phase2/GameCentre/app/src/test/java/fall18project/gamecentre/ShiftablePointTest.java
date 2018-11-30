package fall18project.gamecentre;

import org.junit.Before;
import org.junit.Test;

import fall18project.gamecentre.reactorcontrol.physics.Box;
import fall18project.gamecentre.reactorcontrol.physics.Point;
import fall18project.gamecentre.reactorcontrol.physics.ShiftablePoint;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for class ShiftablePointTest.
 */

@SuppressWarnings("ALL")
public class ShiftablePointTest {

    /**
     * The shiftable point for testing.
     */
    private ShiftablePoint shiftablepoint;
    private Box box = new Box() {
        @Override
        public double getCentreX() {
            return 0;
        }

        @Override
        public double getCentreY() {
            return 0;
        }

        @Override
        public Point getCentre() {
            return null;
        }

        @Override
        public double getXRadius() {
            return 0;
        }

        @Override
        public double getYRadius() {
            return 0;
        }
    };

    /**
     * Make a valid Shiftable point.
     */
    @Before
    public void setUp() throws Exception {
        double x = 0.0;
        double y = 0.0;
        shiftablepoint = new ShiftablePoint(x, y);
    }

    /**
     * Test whether the method shiftXByZero() will not change coord X .
     */
    @Test
    public void shiftXByZero() {
        shiftablepoint.shiftX(0.0);
        assertEquals(0.0, shiftablepoint.getX(), 0.0001);
    }

    /**
     * Test whether the method shiftXByNonZero() will change coord X correctly.
     */
    @Test
    public void shiftXByNonZero() {
        shiftablepoint.shiftX(5.0);
        assertEquals(5.0, shiftablepoint.getX(), 0.0001);
    }

    /**
     * Test whether the method shiftYByZero() will not change coord Y .
     */
    @Test
    public void shiftYByZero() {
        shiftablepoint.shiftY(0.0);
        assertEquals(0.0, shiftablepoint.getY(), 0.0001);
    }

    /**
     * Test whether the method shiftYByNonZero() will change coord Y correctly.
     */
    @Test
    public void shiftYByNonZero() {
        shiftablepoint.shiftY(5.0);
        assertEquals(5.0, shiftablepoint.getY(), 0.0001);
    }

    /**
     * Test whether shift() shifts the points x and y coordinates correctly.
     */
    @Test
    public void shift() {
        shiftablepoint.shift(0.0, 0.0);
        assertEquals(0.0, shiftablepoint.getX(), 0.0001);
        assertEquals(0.0, shiftablepoint.getY(), 0.0001);
    }


    /**
     * Test whether setPosition() sets the point correctly.
     */
    @Test
    public void setPosition() {
        shiftablepoint.setPosition(2.0, 3.0);
        assertEquals(2.0, shiftablepoint.getX(), 0.0001);
        assertEquals(3.0, shiftablepoint.getY(), 0.0001);
    }

    /**
     * Test whether boudWithin can bound a point to within a box b.
     */
    @Test
    public void boundWithin() {

    }

    @Test
    public void boundWithin1() {
    }

}