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
     * The default user prefix
     */
    public static final String DEFAULT_USER_PREFIX = "user_";

    /**
     * The default file to store the current user in
     */
    public static final String DEFAULT_CURRENT_USER_STORAGE = "current_user.ser";

    /**
     * The prefix to use for User object serialization files. Might be null
     */
    private String userPrefix;

    /**
     * The file to store the current user in
     */
    private String userStorage;

    /**
     * The context to use for loading files. If null, then the userManager operates entirely in
     * RAM, and never saves anything to disk, which is only useful in testing
     */
    private Context context;

    /**
     * Construct a new user manager with a given context and user file prefix
     *
     * @param context     context to use to load and store files
     * @param userPrefix  prefix before usernames for files to serialize User objects
     * @param userStorage storage directory for the current user
     */
    public UserManager(Context context, String userPrefix, String userStorage) {
        this.context = context;
        this.userPrefix = userPrefix;
        this.userStorage = userStorage;
    }

    /**
     * Construct a new user manager with a given context user file prefix
     *
     * @param context    context to use to load and store files
     * @param userPrefix prefix before usernames for files to serialize User objects
     */
    public UserManager(Context context, String userPrefix) {
        this(context, userPrefix, DEFAULT_CURRENT_USER_STORAGE);
    }

    /**
     * Construct a user manager with a given context
     */
    public UserManager(Context context) {
        this(context, DEFAULT_USER_PREFIX, DEFAULT_CURRENT_USER_STORAGE);
    }

    /**
     * A static method to load a User object with a given prefix.
     * Returns if loading encounters an error. Throws an exception if the wrong user is loaded
     *
     * @param context    context to load file from. If null, will return nill
     * @param userName   username to attempt to load.
     * @param userPrefix prefix for serialization file.
     * @return User object for userName, or null if none can be found
     */
    public static User loadUser(
            Context context, String userName, String userPrefix) throws
            RuntimeException {
        if (context == null) return null;

        User loaded = null;
        String toLoadFrom = userPrefix + userName + ".ser";

        try {
            InputStream inputStream = context.openFileInput(toLoadFrom);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                loaded = (User) input.readObject();
                inputStream.close();
            }
            if (loaded == null) return null;
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

        if (loaded.getUserName().compareTo(userName) != 0) {
            throw new RuntimeException(
                    "Tried to load username " + userName + " but got " + loaded.getUserName());
        }
        return loaded;
    }

    /**
     * Attempt to store a user given by a User object to disk with a prefix
     *
     * @param context    context to use to open a file
     * @param user       User object to serialize
     * @param userPrefix prefix for the file to store
     */
    public static void storeUser(
            Context context, User user, String userPrefix) {
        if (context == null) return;
        String toStoreTo = userPrefix + user.getUserName() + ".ser";

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
     * Attempt to load a user with the given username, returning null if this fails
     *
     * @param userName username to attempt to load
     * @return User object loaded from disk, or null on failure
     */
    public User loadUser(String userName) {
        return loadUser(context, userName, userPrefix);
    }

    /**
     * Load the current user name from disk
     */
    public String loadCurrentUserName() {
        String loaded = null;

        try {
            InputStream inputStream = context.openFileInput(userStorage);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                loaded = (String) input.readObject();
                inputStream.close();
            }
            if (loaded == null) return null;
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
        return loaded;
    }

    /**
     * Load the current user from disk, and if a corresponding file does not exist on disk, create it
     */
    public User loadCurrentUser() {
        String currentUserName = loadCurrentUserName();
        if (currentUserName == null) return null;
        User result = getUser(currentUserName);
        storeUser(result);
        return result;
    }

    /**
     * Attempt to load a user with the given username, and if there is none, create one without
     * storing to disk. Throws an exception if the file for userName contains the information of
     * a different user
     *
     * @param userName username to attempt to load
     * @return User object loaded from disk, or new user if failed to load
     */
    public User getUser(String userName) {
        User result = loadUser(userName);
        if (result == null)
            result = new User(userName);
        return result;
    }

    /**
     * Attempt to store a user given by a User object to disk with the current prefix
     *
     * @param user User object to serialize
     */
    public void storeUser(User user) {
        storeUser(context, user, userPrefix);
    }

    /**
     * Set the current user on disk
     */
    public void setCurrentUser(String newCurrentUser) {
        if (context == null) return;

        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(userStorage, Context.MODE_PRIVATE));
            outputStream.writeObject(newCurrentUser);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Set the current user on disk to null
     */
    public void resetCurrentUser() {
        setCurrentUser(null);
    }

}
