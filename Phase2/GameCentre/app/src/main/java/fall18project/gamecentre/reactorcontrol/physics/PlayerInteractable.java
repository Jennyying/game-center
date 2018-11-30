package fall18project.gamecentre.reactorcontrol.physics;

/**
 * An object which interacts with the player in some way, such as for example by doing damage or
 * awarding points
 */
public interface PlayerInteractable {

    /**
     * Interact with a given player during a single game tick. For example, this might do damage
     * and destroy a projectile if the player is in the same region as the projectile, or do nothing
     * otherwise.
     *
     * @param p the player to interact with.
     */
    public void interactWith(PlayerCharacter p);

}
