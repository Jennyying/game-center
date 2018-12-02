package fall18project.gamecentre.user_management;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class SaltAndDigest implements Serializable {

    private static final int DEFAULT_SALT_BYTES = 8;

    private byte[] salt;
    private byte[] digest;

    /**
     * Create a salt and digest pair with the given salt and digest
     *
     * @param salt   the salt to use, which can be of any length, though 8 bytes is usually used here
     * @param digest the resulting message digest to be stored with the salt
     */
    private SaltAndDigest(byte[] salt, byte[] digest) {
        this.salt = salt;
        this.digest = digest;
    }

    /**
     * Store the digest of the given message with the given salt in this object
     *
     * @param salt    salt to use (as prefix)
     * @param message message to digest
     */
    public SaltAndDigest(byte[] salt, String message) {
        setDigest(salt, message);
    }

    /**
     * Digest a message with a random hash using the SHA256 algorithm and store it in this
     * SaltAndHash object. The salt will be DEFAULT_SALT_BYTES long
     *
     * @param message message to digest
     */
    public SaltAndDigest(String message) {
        SecureRandom rng = new SecureRandom();
        byte[] salt = new byte[DEFAULT_SALT_BYTES];
        rng.nextBytes(salt);
        setDigest(salt, message);
    }

    /**
     * Digest a message with the given salt using the SHA256 algorithm
     *
     * @param salt    salt to use (as prefix)
     * @param message message to digest
     * @return the SHA256 digest of the given message prefixed by the given salt
     */
    public byte[] getDigest(byte[] salt, String message) {
        try {
            MessageDigest digester = MessageDigest.getInstance("SHA-256");
            digester.update(salt);
            return digester.digest(message.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error initializing message digester " + e.toString());
        }
    }

    /**
     * Digest a message with the salt contained in this object using the SHA256 algorithm
     *
     * @param message message to digest
     * @return SHA256 hash of message with the salt in this object
     */
    public byte[] getDigest(String message) {
        return getDigest(this.salt, message);
    }

    /**
     * Digest a message with the given salt using the SHA256 algorithm and set this object to
     * the given salt, digest pair
     *
     * @param salt    salt to use (as prefix)
     * @param message message to digest
     */
    private void setDigest(byte[] salt, String message) {
        this.salt = salt;
        this.digest = getDigest(salt, message);
    }

    /**
     * See whether another digest is equivalent to this one
     *
     * @param digest message digest to compare
     * @return whether it is equivalent to the stored digest
     */
    public boolean equalDigest(byte[] digest) {
        return Arrays.equals(this.digest, digest);
    }

    /**
     * See whether a message salted with this object's salt gives the same digest as stored in
     * this object
     *
     * @param message message to check
     * @return whether the digest of this message with the salt in this object is the same as
     * this object's digest
     */
    public boolean equalMessage(String message) {
        return equalDigest(getDigest(message));
    }
}
