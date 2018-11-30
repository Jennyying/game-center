package fall18project.gamecentre.game_management;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * A scoreboard for a game in the game centre
 */
public class Scoreboard implements Serializable {
    /**
     * The scores in the scoreboard, stored in a navigable set
     */
    private NavigableSet<SessionScore> scores = new TreeSet<SessionScore>();

    /**
     * Add a new score to this user's scoreboard. Note that this class does *not* check for username
     * consistency.
     *
     * @param score the session score to input
     */
    public void add(SessionScore score) {
        scores.add(score);
    }

    /**
     * Return the size of this scoreboard
     */
    public int size() {
        return scores.size();
    }

    /**
     * Iterate over user scores
     *
     * @return an iterator over the scores in descending order
     */
    public Iterator<SessionScore> iterator() {
        return scores.iterator();
    }

    /**
     * Get an array of strings representing all the user scores in sorted order
     *
     * @return an array of all user scores in sorted order
     */
    public String[] getPrintedScores() {
        String[] result = new String[size()];
        int i = 0;
        Iterator<SessionScore> it = iterator();
        while (it.hasNext()) {
            result[i++] = it.next().toString();
        }
        return result;
    }
}
