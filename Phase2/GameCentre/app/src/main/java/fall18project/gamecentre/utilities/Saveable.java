package fall18project.gamecentre.utilities;

import android.content.Context;

/**
 * An object which can have its state saved given a context and file name
 */
public interface Saveable {

    /**
     * Get this object's associated context (for saving)
     *
     * @return the context
     */
    Context getContext();

    /**
     * Set this object's associated context
     *
     * @param context context to set
     */
    void setContext(Context context);

    /**
     * Get this object's associated file
     *
     * @return the associated file's name
     */
    String getFileName();

    /**
     * Set this object's associated file
     *
     * @param fileName file name to set
     */
    void setFileName(String fileName);

    /**
     * Load this object's data from its associated file
     */
    void loadFromFile();

    /**
     * Save this object's data to its associated file
     */
    void storeToFile();

}
