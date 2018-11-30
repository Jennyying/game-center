package fall18project.gamecentre.reactorcontrol.physics;

/**
 * An object which moves with a certain velocity
 */
public interface MovingObject extends Shiftable {

    /**
     * Get this object's velocity in the X direction
     *
     * @return the object's velocity in the X direction
     */
    public double getXVelocity();

    /**
     * Get this object's velocity in the Y direction
     *
     * @return the object's velocity in the Y direction
     */
    public double getYVelocity();

    /**
     * Move inertially for one time step, as in numerical integration
     */
    public void moveTimeStep();

}
