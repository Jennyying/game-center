package fall18project.gamecentre.reactorcontrol.physics;

/**
 * An object in the game such as a DamagingBox that can be destroyed
 */
public interface DestructibleGameObject {

    /**
     * Whether this object should be kept around next tick
     *
     * @return whether this object has not been destroyed
     */
    public boolean keepAlive();

    /**
     * Make a destructible object alive
     */
    public void makeAlive();

    /**
     * Make a destructible object spent
     */
    public void makeSpent();

}
