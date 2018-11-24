package fall18project.gamecentre;

import android.util.SparseArray;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The database of user accounts.
 */
public class UserDatabase implements Serializable {

    /**
     * Value to return on a bad password attempt
     */
    public static final int LOGIN_BAD_PASSWORD = -1;

    /**
     * Value to return on an unknown username
     */
    public static final int LOGIN_NO_SUCH_USER = -2;

    /**
     * The map of usernames to user IDs
     */
    private Map<String, Integer> userIDTable = new HashMap<String, Integer>();

    /**
     * The map of user IDs to passwords (for local account authentication)
     */
    private Map<Integer, String> userPasswords = new HashMap<Integer, String>();

    /**
     * The map of user IDs to User objects (for locally stored users)
     */
    private Map<Integer, User> userArray = new HashMap<Integer, User>();

    /**
     * The current maximum user ID. User ID generation may change quite a bit later...
     */
    private int maxUserID = 0;

    /**
     * Insert a new user into the user ID table, password array and user array
     * @param ID the user ID to use for registration. Will override any user previously having this ID
     * @param username the username to use
     * @param password the password to use
     * @return the User object created
     */
    private User insertUser(int ID, String username, String password) {
        userIDTable.put(username, ID);
        userPasswords.put(ID, password);
        User newUser = new User(ID, username);
        userArray.put(ID, newUser);
        return newUser;
    }

    /**
     * Generate a fresh new local user ID
     * @return the new user ID, or -1 on failure
     */
    private int getNewUserID() {
        return maxUserID++;
    }

    /**
     * Register a new user with a given name, and return the User object created if this was
     * successful. If unsuccessful, return null
     * Always fails if a user with the same username already exists
     * @param username the username of the new user
     * @param password the password of the new user
     * @return true if the user was successfully registered, false otherwise
     */
    public User register(String username, String password){
        if(userIDTable.containsKey(username)) return null;
        int newID = getNewUserID();
        if(newID < 0) return null;
        return insertUser(newID, username, password);
    }

    /**
     * Attempt to login with a given username and password.
     * Returns LOGIN_BAD_PASSWORD if the password used was incorrect and LOGIN_NO_SUCH_USER
     * if no user exists with the given username. Otherwise, return the user ID
     *
     * @param username the username used
     * @param password the password used
     * @return the user ID if the username and password are valid, an error code otherwise
     */
    public int login(String username, String password) {
        Integer userID = userIDTable.get(username);
        if (userID == null) return LOGIN_NO_SUCH_USER;

        String passwordToCheck = userPasswords.get(userID);

        if (passwordToCheck == null || passwordToCheck.equals(password))
            return userID;
        return LOGIN_NO_SUCH_USER;
    }

    /**
     * Gets the user associated with a given user ID, or null if there is none
     * @param id the ID to search for
     * @return the user with the ID
     */
    public User getUserWithID(int id) {
        return userArray.get(id);
    }
}
