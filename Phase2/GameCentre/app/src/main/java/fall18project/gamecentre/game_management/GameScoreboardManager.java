package fall18project.gamecentre.game_management;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fall18project.gamecentre.user_management.User;
import fall18project.gamecentre.user_management.UserManager;

/**
 * A manager for the individual scoreboards for a set of games
 */
public class GameScoreboardManager {

    /**
     * The default scoreboard prefix
     */
    public static final String DEFAULT_SCORE_PREFIX = "scores_";

    /**
     * The default location for the global scoreboard
     */
    public static final String DEFAULT_GLOBAL_SCOREBOARD_LOCATION = "global_scoreboard";
    /**
     * The global scoreboard
     */
    GameScoreboard globalScoreboard = null;
    /**
     * The prefix to use for Scoreboard object serialization files. Might be null
     */
    private String scorePrefix;
    /**
     * The location to store the global scoreboard
     */
    private String globalLocation;
    /**
     * The context to use for loading files. If null, then the userManager operates entirely in
     * RAM, and never saves anything to disk, which is only useful in testing
     */
    private Context context;

    /**
     * Construct a new scoreboard manager with a given context and score file prefix
     *
     * @param context        context to use to load and store files
     * @param scorePrefix    prefix before game names for files to serialize Scoreboard objects
     * @param globalLocation location to store the global scoreboard at
     */
    public GameScoreboardManager(Context context, String scorePrefix, String globalLocation) {
        this.context = context;
        this.scorePrefix = scorePrefix;
        this.globalLocation = globalLocation;
        reloadGlobalScoreboard();
    }

    /**
     * Construct a new scoreboard manager with a given context and score file prefix
     *
     * @param context     context to use to load and store files
     * @param scorePrefix prefix before game names for files to serialize Scoreboard objects
     */
    public GameScoreboardManager(Context context, String scorePrefix) {
        this(context, scorePrefix, DEFAULT_GLOBAL_SCOREBOARD_LOCATION);
    }

    /**
     * Construct a scoreboard manager with a given context
     */
    public GameScoreboardManager(Context context) {
        this(context, DEFAULT_SCORE_PREFIX);
    }

    /**
     * A static method to load a Scoreboard object with a given prefix.
     * Returns null if loading encounters an error.
     *
     * @param context     context to load file from. If null, will return null
     * @param gameName    game name to attempt to load.
     * @param scorePrefix prefix for scoreboard serialization files.
     * @return scoreBoard object for gameName, or null if none can be found
     */
    public static GameScoreboard loadScoreboard(
            Context context, String gameName, String scorePrefix) {
        if (context == null) return null;

        GameScoreboard loaded = null;
        String toLoadFrom = scorePrefix + gameName + ".ser";

        Log.v("scoreboard manager", "attempting to load " + toLoadFrom);

        try {
            InputStream inputStream = context.openFileInput(toLoadFrom);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                loaded = (GameScoreboard) input.readObject();
                inputStream.close();
            }
            if (loaded == null) return null;
        } catch (FileNotFoundException e) {
            Log.e("scoreboard manager", "File not found: " + e.toString());
            return null;
        } catch (IOException e) {
            Log.e("scoreboard manager", "Can not read file: " + e.toString());
            return null;
        } catch (ClassNotFoundException e) {
            Log.e("scoreboard manager", "File contained unexpected data type: " + e.toString());
            return null;
        } catch (NullPointerException e) {
            Log.e("scoreboard manager", "NullPointerException");
        }

        return loaded;
    }

    /**
     * Attempt to store a scoreboard into a context
     *
     * @param context     context to use to open a file
     * @param gameName    name of game associated with scoreboard
     * @param board       scoreboard to serialize
     * @param scorePrefix prefix for the file to store
     */
    public static void storeScoreboard(
            Context context, String gameName, GameScoreboard board, String scorePrefix) {
        if (context == null) return;
        String toStoreTo = scorePrefix + gameName + ".ser";

        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(toStoreTo, Context.MODE_PRIVATE));
            outputStream.writeObject(board);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Reload the global scoreboard from disk, creating a new one if none is saved yet
     *
     * @return the global scoreboard
     */
    public GameScoreboard reloadGlobalScoreboard() {
        globalScoreboard = loadScoreboard(context, globalLocation, "");
        if (globalScoreboard == null) globalScoreboard = new GameScoreboard();
        return globalScoreboard;
    }

    /**
     * Get the current global scoreboard
     *
     * @return the global scoreboard
     */
    public GameScoreboard getGlobalScoreboard() {
        return globalScoreboard;
    }

    /**
     * Write the current global scoreboard to disk
     */
    public void writeGlobalScoreboard() {
        storeScoreboard(context, globalLocation, globalScoreboard, "");
    }

    /**
     * Attempt to load a scoreboard with the given game name, returning null if this fails
     *
     * @param gameName game name to attempt to load
     * @return GameScoreboard object loaded from disk, or null on failure
     */
    public GameScoreboard loadScoreboard(String gameName) {
        return loadScoreboard(context, gameName, scorePrefix);
    }

    /**
     * Attempt to load a user with the given username, and if there is none, create one without
     * storing to disk. Throws an exception if the file for userName contains the information of
     * a different user
     *
     * @param gameName game name to attempt to load
     * @return GameScoreboard object loaded from disk, or new GameScoreboard if failed to load
     */
    public GameScoreboard getScoreboard(String gameName) {
        GameScoreboard result = loadScoreboard(gameName);
        if (result == null)
            result = new GameScoreboard();
        return result;
    }

    /**
     * Attempt to store a scoreboard into the context
     *
     * @param gameName name of game associated with scoreboard
     * @param board    scoreboard to serialize
     */
    public void storeScoreboard(String gameName, GameScoreboard board) {
        storeScoreboard(context, gameName, board, scorePrefix);
    }

    /**
     * Add a score for a game, syncing all changes to disk
     *
     * @param score score to add
     */
    public void addScoreForGame(SessionScore score) {
        GameScoreboard board = getScoreboard(score.getGameName());
        board.add(score);
        reloadGlobalScoreboard();
        globalScoreboard.add(score);
        storeScoreboard(score.getGameName(), board);
        writeGlobalScoreboard();
    }

    /**
     * Add a score for the current user, syncing all changes to disk
     *
     * @param userManager user manager
     * @param score       score to add
     */
    public void addScoreForGame(UserManager userManager, SessionScore score) {
        addScoreForGame(score);
        User cu = userManager.loadCurrentUser();
        cu.getScoreboard().add(score);
        userManager.storeUser(cu);
    }

}
