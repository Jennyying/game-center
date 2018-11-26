package fall18project.gamecentre.user_management;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * A class to manage the database of users and their passwords on disk or in memory
 */
public class UserManager {

    /**
     * A manager for user logins and their associated passwords, either loaded from a file or memory
     */
    private LoginManager loginManager;

    /**
     * The prefix to use for User object serialization files. Might be null
     */
    private String userPrefix;

    /**
     * The context to use for loading files
     */
    private Context context;

    /**
     * Construct a new user manager with a given login manager and user file prefix
     * @param context context to use to load files
     * @param loginManager login manager to use
     * @param userPrefix prefix before usernames for files to serializ User objects
     */
    public UserManager(Context context, LoginManager loginManager, String userPrefix) {
        this.context = context;
        this.loginManager = loginManager;
        this.userPrefix = userPrefix;
    }

    /**
     * Get the login manager in use
     * @return the current login manager
     */
    public LoginManager getLoginManager() {
        return loginManager;
    }

    /**
     * A static method to load a User object with a given prefix.
     * Returns if loading encounters an error. Throws an exception if the wrong user is loaded
     * @param context context to load file from
     * @param userName username to attempt to load. Will return null if another user is loaded
     * @param userPrefix prefix for serialization file, if any. If null, will ignore.
     * @return User object for userName, or null if none can be found
     */
    public static User loadUser(
            Context context, String userName, String userPrefix) throws
    RuntimeException {
        User loaded = null;
        String toLoadFrom = userPrefix + userName + ".ser";

        try {
            InputStream inputStream = context.openFileInput(toLoadFrom);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                loaded = (User)input.readObject();
                inputStream.close();
            }
            if(loaded == null) return null;
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            return null;
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
            return null;
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
            return null;
        }

        if(loaded.getUserName().compareTo(userName) != 0) {
            throw new RuntimeException(
                    "Tried to load username " + userName + " but got " + loaded.getUserName());
        }
        return loaded;
    }

    /**
     * Attempt to load a user with the given username, returning null if this fails
     * @param userName username to attempt to load
     * @return User object loaded from disk, or null on failure
     */
    public User loadUser(String userName) {
        return loadUser(context, userName, userPrefix);
    }

    /**
     * Attempt to load a user with the given username, and if there is none, create one without
     * storing to disk. Throws an exception if the file for userName contains the information of
     * a different user
     * @param userName username to attempt to load
     * @return User object loaded from disk, or new user if failed to load
     */
    public User getUser(String userName) {
        User result = loadUser(userName);
        //TODO: return a new user after removing user IDs from the User class
        return result;
    }

    /**
     * Attempt to store a user given by a User object to disk with a prefix
     * @param context context to use to open a file
     * @param user User object to serialize
     * @param userPrefix prefix for the file to store
     */
    public static void storeUser(
            Context context, User user, String userPrefix) {
        String toStoreTo = userPrefix + user.getUserName() + ".ser";

        //TODO: think about whether exceptions here should be caught or not
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(toStoreTo, Context.MODE_PRIVATE));
            outputStream.writeObject(user);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Attempt to store a user given by a User object to disk with the current prefix
     * @param user User object to serialize
     */
    public void storeUser(User user) {
        storeUser(context, user, userPrefix);
    }

}
