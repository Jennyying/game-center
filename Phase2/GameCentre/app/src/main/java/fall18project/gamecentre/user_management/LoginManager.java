package fall18project.gamecentre.user_management;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fall18project.gamecentre.utilities.Saveable;

/**
 * A class to manage usernames and passwords and validate attempts to login
 */
public class LoginManager implements Saveable {

    /**
     * The default file name for the login manager
     */
    public static final String DEFAULT_FILE_NAME = "password_database.ser";

    /**
     * A database mapping strings to byte arrays representing the salted SHA256 message
     * digests of user passwords along with a salt
     */
    private Map<String, SaltAndDigest> passwordDatabase;

    /**
     * The context to use to open files. May be null, in which case file operations will throw exceptions
     */
    private Context context = null;

    /**
     * The file name to store to/load from. May be null, in which case file operations will throw exception
     */
    private String fileName = null;

    /**
     * Initialize a login manager with a given context
     *
     * @param context context to use
     */
    public LoginManager(Context context) {
        this(context, DEFAULT_FILE_NAME);
    }

    /**
     * Initialize a login manager with a given context and set a file to store to and attempt
     * to load from. If nothing can be loaded, generate a new map for the password database
     *
     * @param context  context to use
     * @param fileName file to store to/load from
     */
    public LoginManager(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
        passwordDatabase = loadMapFromFile();
        if (passwordDatabase == null) passwordDatabase = new HashMap<>();
    }

    /**
     * Initialize a login manager with a given context and a given password database, and
     * set a file to store to
     *
     * @param context          context to use
     * @param fileName         file to store to
     * @param passwordDatabase map to use as password database
     */
    public LoginManager(
            Context context, String fileName, Map<String, SaltAndDigest> passwordDatabase) {
        this.context = context;
        this.fileName = fileName;
        this.passwordDatabase = passwordDatabase;
        storeToFile();
    }

    /**
     * Initialize a login manager with the given map as password database
     *
     * @param passwordDatabase map to use as password database
     */
    public LoginManager(Map<String, SaltAndDigest> passwordDatabase) {
        this.passwordDatabase = passwordDatabase;
    }

    /**
     * Load a password database map stored in a file
     *
     * @param context  context to load the file using
     * @param fileName file to attempt to load password database from
     * @return a Map if the file contains a valid password database, null otherwise
     */
    private static Map<String, SaltAndDigest> loadMapFromFile(Context context, String fileName) {
        try {
            InputStream inputStream = context.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                Map<String, SaltAndDigest> lm = (Map<String, SaltAndDigest>) input.readObject();
                inputStream.close();
                return lm;
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
        return null;
    }

    /**
     * @return the associated context
     */
    public Context getContext() {
        return context;
    }

    /**
     * @param context context to set
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * @return the associated file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName file name to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Load a map from the context and filename stored in this object
     *
     * @return a map if context and file are not null and a valid password database is in the file,
     * or null otherwise
     */
    private Map<String, SaltAndDigest> loadMapFromFile() {
        if (this.context == null || this.fileName == null) return null;
        return loadMapFromFile(this.context, this.fileName);
    }

    /**
     * Load a map from memory and store it in this object. If no map can be loaded, leave the
     * map currently in the object alone
     */
    public void loadFromFile() {
        Map<String, SaltAndDigest> lm = loadMapFromFile();
        if (lm != null) this.passwordDatabase = lm;
    }

    /**
     * Store this login manager's password database to a file without setting the file as the default
     * storage location. Does nothing if context is null
     *
     * @param context  context to store the file using
     * @param fileName file to attempt to store the password database to
     */
    private void storeToFile(Context context, String fileName) {
        if (context == null) return;
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStream.writeObject(passwordDatabase);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Store this login manager's password database to this login manager's file and context
     */
    public void storeToFile() {
        storeToFile(this.context, this.fileName);
    }

    /**
     * Check whether a certain username exists in the password database
     *
     * @param userName username to check for
     * @return whether a user with name userName exists in the database
     */
    public boolean userExists(String userName) {
        return passwordDatabase.containsKey(userName);
    }

    /**
     * Register a user without updating the file on disk. Returns whether this was successful
     *
     * @param userName username to register
     * @param password password to register
     * @return true if user successfully inserted, false if failed due to existing user with same
     * username
     */
    private boolean registerUserInMemory(String userName, String password) {
        if (userExists(userName)) return false;
        passwordDatabase.put(userName, new SaltAndDigest(password));
        return true;
    }

    /**
     * Register a user after re-loading the file from disk, and then store back to disk
     *
     * @param userName username to register
     * @param password password to register
     */
    public boolean registerUser(String userName, String password) {
        loadFromFile();
        boolean result = registerUserInMemory(userName, password);
        storeToFile();
        return result;
    }

    /**
     * Attempt to log in with a given username and password
     *
     * @param userName username to attempt to login with
     * @param password password to attempt to login with
     * @return an enum indicating whether the login was successful, failed because there was no
     * user with the given username or failed because the password was incorrect
     */
    public LoginStatus login(String userName, String password) {
        SaltAndDigest sd = passwordDatabase.get(userName);
        if (sd == null) return LoginStatus.LOGIN_BAD_USERNAME;
        if (sd.equalMessage(password)) return LoginStatus.LOGIN_GOOD;
        return LoginStatus.LOGIN_BAD_PASSWORD;
    }

    /**
     * Get the usernames in the login database
     *
     * @return a set of all usernames in the login database
     */
    public Set<String> getUserNames() {
        return passwordDatabase.keySet();
    }

    /**
     * Get a list of all usernames in the login database
     *
     * @return a list of all usernames in the login database
     */
    public List<String> getUserNameList() {
        return new ArrayList<String>(getUserNames());
    }

    public enum LoginStatus {
        LOGIN_GOOD,
        LOGIN_BAD_USERNAME,
        LOGIN_BAD_PASSWORD
    }

}
