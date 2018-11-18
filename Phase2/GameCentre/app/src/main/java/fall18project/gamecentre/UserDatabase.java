package fall18project.gamecentre;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The database of user accounts
 */
public class UserDatabase implements Serializable {
    private Map<String, String> users = new HashMap<String, String>();
    private String currentUser = null;

    public enum LoginStatus {
        LOGIN_GOOD,
        LOGIN_BAD_PASSWORD,
        LOGIN_NO_SUCH_USER
    }

    /**
     * Register a new user, and return whether this was successful.
     * Always fails if a user with the same username already exists
     * @param username the username of the new user
     * @param password the password of the new user
     * @return true if the user was successfully registered, false otherwise
     */
    public boolean register(String username, String password){
        if(users.containsKey(username)) return false;
        users.put(username, password);
        return true;
    }

    /**
     * Attempt to login with a given username and password.
     * Returns LOGIN_BAD_PASSWORD if the password used was incorrect and LOGIN_NO_SUCH_USER
     * if no user exists with the given username.
     * @param username the username used
     * @param password the password used
     * @return LOGIN_GOOD if the username and password match, an error code otherwise
     */
    public LoginStatus login(String username, String password) {
        String p = users.get(username);
        if(p == null) return LoginStatus.LOGIN_NO_SUCH_USER;
        else if(p.equals(password)) {
            currentUser = username;
            return LoginStatus.LOGIN_GOOD;
        }
        else return LoginStatus.LOGIN_NO_SUCH_USER;
    }

    public String getCurrentUser() {
        return currentUser;
    }
}
