package fall18project.gamecentre.user_management;

import java.io.Serializable;

/**
 * A user account with a name, balance of coins, total amount of coins earned and set of scores
 * The password is not stored with the account, since it should be handled by the password manager
 * (and potentially, other forms of authentication could be used, like Google logins, etc.)
 * <p>
 * User accounts are optionally associated with an email (i.e. the email string could be null)
 */
public class User implements Serializable {

    /**
     * Username, which may be changed later by the user
     */
    private String name;

    /**
     * Email, which may be changed later by the user, and can alternatively be used to login
     */
    private String email = null;

    /**
     * Coin balance of the users.
     */
    private long coinBalance = 0;

    /**
     * Total coins ever earned by the user
     */
    private long coinsEarned = 0;

    /**
     * Personal scoreboard of the user
     */
    private UserScoreboard scoreboard = new UserScoreboard();

    /**
     * This user's personal game settings
     */
    private UserSettings settings = new UserSettings();

    /**
     * Construct a user with the given user ID and username
     *
     * @param name the username to set
     */
    public User(String name) {
        this.name = name;
    }

    /**
     * Get this user's username
     *
     * @return the username
     */
    public String getUserName() {
        return name;
    }

    /**
     * Get coin balance
     *
     * @return the current coin balance
     */
    public long getCoinBalance() {
        return coinBalance;
    }

    /**
     * Get coins earned
     *
     * @return the total number of coins ever earned
     */
    public long getCoinsEarned() {
        return coinsEarned;
    }

    /**
     * Give the user a certain number of coins
     *
     * @param amount how many coins to give
     */
    public void giveCoins(long amount) {
        coinBalance += amount;
        coinsEarned += amount;
    }

    /**
     * Take a certain number of coins from the user. If the user doesn't have enough coins, return
     * false and do not update the user's balance, otherwise return true
     *
     * @param amount how many coins to spend
     * @return whether the user has enough coins
     */
    public boolean spendCoins(long amount) {
        if (amount > coinBalance) return false;
        coinBalance -= amount;
        return true;
    }

    /**
     * Get the user's personal scoreboard
     *
     * @return the user's scoreboard
     */
    public UserScoreboard getScoreboard() {
        return scoreboard;
    }

    /**
     * Get the user's personal settings
     *
     * @return the user's personal settings
     */
    public UserSettings getSettings() {
        return settings;
    }
}
