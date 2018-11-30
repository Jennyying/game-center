package fall18project.gamecentre.reactorcontrol.physics;

public interface VelocityRespawnable extends Respawnable, Pushable {

    /**
     * Respawn this object at a given (x, y) position with velocity (vx, vy), making it alive again
     *
     * @param x  new x position
     * @param y  new y position
     * @param vx new x velocity
     * @param vy new y velocity
     */
    public void respawn(double x, double y, double vx, double vy);


}
