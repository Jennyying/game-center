package fall18project.gamecentre.game_management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fall18project.gamecentre.user_management.UserScoreboard;
import fall18project.gamecentre.utilities.Saveable;

/**
 * A manager for the scoreboards associated with each game in the game centre
 */
public class ScoreboardManager implements Serializable {

    //TODO: change from serializable to

    /**
     * The global scoreboard, containing all games.
     */
    private GameScoreboard scores = new GameScoreboard();

    /**
     * Individual game scoreboards.
     */
    private Map<String, GameScoreboard> gameScores = new HashMap<>();

    /**
     * Individual user scoreboards associated with a username
     * Note that the same scoreboard may be associated to more than one username, and a score
     * may be posted in multiple user scoreboards (e.g. a team scoreboard and individual scoreboards)
     */
    private Map<String, UserScoreboard> userScores = new HashMap<>();

    /**
     * List of usernames associated with a scoreboard. Kept separately outside the HashMap since
     * the auto-complete feature needs a List, not a Set
     */
    private List<String> userNames = new ArrayList<>();

    /**
     * List of game names associated with a scoreboard. Kept separately outside the HashMap since
     * the auto-complete feature needs a List, not a Set
     */
    private List<String> gameNames = new ArrayList<>();

    /*
     * Return the global scoreboard
     * @return scores
     */
    public GameScoreboard getGlobalScores() {
        return scores;
    }

    /**
     * Return the list of user-names
     */
    public List<String> getUserNames() {
        return userNames;
    }

    /**
     * Return the list of game-names
     */
    public List<String> getGameNames() {return gameNames;}

    /**
     * Return the number of user scoreboards
     */
    public int numUserScoreboards() {
        return userScores.size();
    }

    /**
     * Return the number of game scoreboards
     */
    public int numGameScoreboards() {return gameScores.size();}

    /**
     * Create an empty scoreboard associated with a game if one does not already exist
     * @param gameName game name to associate scoreboard with
     * @return the scoreboard created or the previously existing one
     */
    public GameScoreboard getGameScoreboard(String gameName) {
        GameScoreboard g = gameScores.get(gameName);
        if(g != null) return g;

        g = new GameScoreboard();
        gameScores.put(gameName, g);
        gameNames.add(gameName);

        return g;
    }

    /**
     * Create an empty scoreboard associated with a username if one does not already exist
     * @param userName username to associate scoreboard with
     * @return the scoreboard created or the previously existing one
     */
    public UserScoreboard getUserScoreboard(String userName) {
        UserScoreboard u = userScores.get(userName);
        if(u != null) return u;

        u = new UserScoreboard();
        userScores.put(userName, u);
        userNames.add(userName);

        return u;
    }

    /**
     * Search for a scoreboard associated with a given username
     * @param userName username to search for
     * @return the scoreboard associated with the name, or null if there is none
     */
    public UserScoreboard searchUserScoreboard(String userName) {
        return userScores.get(userName);
    }

    /**
     * Add a score to the global scoreboard table and to the scoreboard associated with their
     * username. Return the latter.
     *
     * @return scoreboard associated with the username to which the score is credited/ null if no name
     */
    public UserScoreboard add(SessionScore score) {
        if(score.getUserName().isEmpty()) return null;
        scores.add(score);

        UserScoreboard u = getUserScoreboard(score.getUserName());
        u.add(score);
        return u;
    }
}
