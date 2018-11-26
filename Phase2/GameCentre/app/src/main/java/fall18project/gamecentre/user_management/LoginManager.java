package fall18project.gamecentre.user_management;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fall18project.gamecentre.utilities.Saveable;

/**
 * A class to manage usernames and passwords and validate attempts to login
 */
public class LoginManager implements Saveable {

    class SaltAndDigest {

        private static final int DEFAULT_SALT_BYTES = 8;

        private byte[] salt;
        private byte[] digest;

        /**
         * Create a salt and digest pair with the given salt and digest
         * @param salt the salt to use, which can be of any length, though 8 bytes is usually used here
         * @param digest the resulting message digest to be stored with the salt
         */
        public SaltAndDigest(byte[] salt, byte[] digest) {
            this.salt = salt;
            this.digest = digest;
        }

        /**
         * Digest a message with the given salt using the SHA256 algorithm
         * @param salt salt to use (as prefix)
         * @param message message to digest
         * @return the SHA256 digest of the given message prefixed by the given salt
         */
        public byte[] getDigest(byte[] salt, String message) {
            try {
                MessageDigest digester = MessageDigest.getInstance("SHA-256");
                digester.update(salt);
                return digester.digest(message.getBytes());
            } catch(NoSuchAlgorithmException e) {
                throw new RuntimeException("Error initializing message digester " + e.toString());
            }
        }

        /**
         * Digest a message with the salt contained in this object using the SHA256 algorithm
         * @param message message to digest
         * @return SHA256 hash of message with the salt in this object
         */
        public byte[] getDigest(String message) {
            return getDigest(this.salt, message);
        }

        /**
         * Digest a message with the given salt using the SHA256 algorithm and set this object to
         * the given salt, digest pair
         * @param salt salt to use (as prefix)
         * @param message message to digest
         */
        private void setDigest(byte[] salt, String message) {
            this.salt = salt;
            this.digest = getDigest(salt, message);
        }

        /**
         * Store the digest of the given message with the given salt in this object
         * @param salt salt to use (as prefix)
         * @param message message to digest
         */
        public SaltAndDigest(byte[] salt, String message) {
            setDigest(salt, message);
        }

        /**
         * Digest a message with a random hash using the SHA256 algorithm and store it in this
         * SaltAndHash object. The salt will be DEFAULT_SALT_BYTES long
         * @param message message to digest
         */
        public SaltAndDigest(String message) {
            SecureRandom rng = new SecureRandom();
            byte[] salt = new byte[DEFAULT_SALT_BYTES];
            rng.nextBytes(salt);
            setDigest(salt, message);
        }

        /**
         * See whether another digest is equivalent to this one
         * @param digest message digest to compare
         * @return whether it is equivalent to the stored digest
         */
        public boolean equalDigest(byte[] digest) {
            return Arrays.equals(this.digest, digest);
        }

        /**
         * See whether a message salted with this object's salt gives the same digest as stored in
         * this object
         * @param message message to check
         * @return whether the digest of this message with the salt in this object is the same as
         * this object's digest
         */
        public boolean equalMessage(String message) {
            return equalDigest(getDigest(message));
        }
    }

    /**
     * A database mapping strings to byte arrays representing the salted SHA256 message
     * digests of user passwords along with a salt
     */
    private Map<String, SaltAndDigest> passwordDatabase = new HashMap<>();

    /**
     * The context to use to open files. May be null, in which case file operations will throw exceptions
     */
    private Context context = null;

    /**
     * The file name to store to/load from. May be null, in which case file operations will throw exception
     */
    private String fileName = null;

    /**
     * Initialize a login manager with a given context and set a file to store to and attempt
     * to load from. If nothing can be loaded, generate a new map for the password database
     * @param context context to use
     * @param fileName file to store to/load from
     */
    public LoginManager(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
        Map<String, SaltAndDigest> lm = loadMapFromFile();
    }

    /**
     * Initialize a login manager with a given context and a given password database, and
     * set a file to store to
     * @param context context to use
     * @param fileName file to store to
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
     * @param passwordDatabase map to use as password database
     */
    public LoginManager(Map<String, SaltAndDigest> passwordDatabase) {
        this.passwordDatabase = passwordDatabase;
    }

    /**
     * @return the associated context
     */
    public Context getContext() {return context;}

    /**
     * @param context context to set
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * @return the associated file name
     */
    public String getFileName() {return fileName;}

    /**
     * @param fileName file name to set
     */
    public void setFileName(String fileName) {
       this.fileName = fileName;
    }

    /**
     * Load a password database map stored in a file
     * @param context context to load the file using
     * @param fileName file to attempt to load password database from
     * @return a Map if the file contains a valid password database, null otherwise
     */
    private static Map<String, SaltAndDigest> loadMapFromFile(Context context, String fileName) {
        try {
            InputStream inputStream = context.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                Map<String, SaltAndDigest> lm = (Map<String, SaltAndDigest>)input.readObject();
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
     * Load a map from the context and filename stored in this object
     * @return a map if context and file are not null and a valid password database is in the file,
     * or null otherwise
     */
    private Map<String, SaltAndDigest> loadMapFromFile() {
        if(this.context == null || this.fileName == null) return null;
        return loadMapFromFile(this.context, this.fileName);
    }

    /**
     * Load a map from memory and store it in this object. If no map can be loaded, leave the
     * map currently in the object alone
     */
    public void loadFromFile() {
        Map<String, SaltAndDigest> lm = loadMapFromFile();
        if(lm != null) this.passwordDatabase = lm;
    }

    /**
     * Store this login manager's password database to a file without setting the file as the default
     * storage location
     * @param context context to store the file using
     * @param fileName file to attempt to store the password database to
     */
    private void storeToFile(Context context, String fileName) {
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
     * Register a user without updating the file on disk
     * @param userName username to register
     * @param password password to register
     */
    private void registerUserInMemory(String userName, String password) {
        passwordDatabase.put(userName, new SaltAndDigest(password));
    }

    /**
     * Register a user after re-loading the file from disk, and then store back to disk
     * @param userName username to register
     * @param password password to register
     */
    private void registerUser(String userName, String password) {
       loadFromFile();
       registerUserInMemory(userName, password);
       storeToFile();
    }

    public enum LoginStatus {
        LOGIN_GOOD,
        LOGIN_BAD_USERNAME,
        LOGIN_BAD_PASSWORD
    }

    /**
     * Attempt to log in with a given username and password
     * @param userName username to attempt to login with
     * @param password password to attempt to login with
     * @return an enum indicating whether the login was successful, failed because there was no
     * user with the given username or failed because the password was incorrect
     */
    public LoginStatus login(String userName, String password) {
        SaltAndDigest sd = passwordDatabase.get(userName);
        if(sd == null) return LoginStatus.LOGIN_BAD_USERNAME;
        if(sd.equalMessage(password)) return LoginStatus.LOGIN_GOOD;
        return LoginStatus.LOGIN_BAD_PASSWORD;
    }

    /**
     * Get the usernames in the login database
     * @return a list of all usernames in the login database
     */
    public Set<String> getUserNames() {
        return passwordDatabase.keySet();
    }

}
