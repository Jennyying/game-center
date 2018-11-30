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
    public Context getContext();

    /**
     * Set this object's associated context
     *
     * @param context context to set
     */
    public void setContext(Context context);

    /**
     * Get this object's associated file
     *
     * @return the associated file's name
     */
    public String getFileName();

    /**
     * Set this object's associated file
     *
     * @param fileName file name to set
     */
    public void setFileName(String fileName);

    /**
     * Load this object's data from its associated file
     */
    public void loadFromFile();

    /**
     * Save this object's data to its associated file
     */
    public void storeToFile();

}
