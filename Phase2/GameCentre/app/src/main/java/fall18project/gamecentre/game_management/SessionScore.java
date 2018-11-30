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
     * The score the user has achieved
     */
    private long score;

    /**
     * Create a new SessionScore object for a session of a game
     *
     * @param un player's username
     * @param sc player's score
     */
    public SessionScore(String un, long sc) {
        userName = un;
        score = sc;
    }

    /**
     * Get the username associated with a score
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Compare two scores. The scores are ordered by score, and then by alphabetical username order,
     * but in *descending* order (i.e. the highest score is ranked smallest) to make implementation
     * of scoreboard operations using NavigableSet easier.
     * <p>
     * Compatible with equals
     */
    public int compareTo(SessionScore s) {
        if (score > s.score) return -1;
        else if (score < s.score) return 1;
        else return s.userName.compareTo(userName);
    }

    /**
     * Convert a session score into a string
     *
     * @returns the session score represented as username : score
     */
    public String toString() {
        return userName + " : " + score;
    }

}
