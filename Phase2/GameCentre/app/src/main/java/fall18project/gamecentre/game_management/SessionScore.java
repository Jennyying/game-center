package fall18project.gamecentre.game_management;

import java.io.Serializable;

/**
 * The score information for a given session of a game
 * <p>
 * TODO: add date/time information
 */
public class SessionScore implements Serializable, Comparable<SessionScore> {

    /**
     * The name of the user to whom this score belongs
     */
    private String userName;

    /**
     * The name of the game for which this score is for
     */
    private String gameName;

    /**
     * The score the user has achieved
     */
    private long score;

    /**
     * Create a new SessionScore object for a session of a game
     *
     * @param userName player's username
     * @param gameName game's name
     * @param score player's score
     */
    public SessionScore(String userName, String gameName, long score) {
        this.userName = userName;
        this.gameName = gameName;
        this.score = score;
    }

    /**
     * Get the username associated with a score
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Get the game name associated with a score
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Compare two scores. The scores are ordered by score in reverse (to make implementation of
     * scoreboard operations with NavigableSet easier), then by username (alphabetically) and then
     * by game name (alphabetically)
     * Compatible with equals
     */
    public int compareTo(SessionScore s) {
        if (score > s.score) return -1;
        else if (score < s.score) return 1;
        else {
            int cmp = s.userName.compareTo(userName);
            if(cmp != 0) return cmp;
            return s.gameName.compareTo(gameName);
        }
    }

    /**
     * Convert a session score into a string
     *
     * @returns the session score represented as username : score
     */
    public String toString() {
        return userName + " got " + score + " in " + gameName;
    }

}
