package fall18project.gamecentre.reactorcontrol.physics;

public interface Respawnable extends DestructibleGameObject, Shiftable {

    /**
     * Respawn this object at a given (x, y) position, making it alive again
     *
     * @param x new x position
     * @param y new y position
     */
    public void respawn(double x, double y);

}
