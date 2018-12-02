package fall18project.gamecentre;

import android.se.omapi.Session;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import fall18project.gamecentre.game_management.Scoreboard;
import fall18project.gamecentre.game_management.SessionScore;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Scoreboard unit tests
 */
public class ScoreboardTest {

    /**
     * The scoreboard to test
     */
    Scoreboard scoreboard;

    @Before
    public void setUp() {
        scoreboard = new Scoreboard();
    }

    @Test
    public void newScoreboardIsEmpty() {
        assertEquals(scoreboard.size(), 0);
        assertFalse(scoreboard.iterator().hasNext());
        assertEquals(scoreboard.getPrintedScores().length, 0);
    }

    @Test
    public void scoresAreAdded() {
        SessionScore score1 =
                new SessionScore("Aragorn", "The War of the Ring", 3019);
        SessionScore score2 =
                new SessionScore("Sauron", "The War of the Ring", 3019);

        scoreboard.add(score1);
        assertEquals(scoreboard.size(), 1);

        Iterator<SessionScore> iter = scoreboard.iterator();

        assertTrue(iter.hasNext());

        SessionScore tmp = iter.next();

        assertEquals(tmp.getUserName(), "Aragorn");
        assertEquals(tmp.getGameName(), "The War of the Ring");
        assertEquals(tmp.compareTo(score1), 0);
        assertFalse(iter.hasNext());

        scoreboard.add(score2);
        iter = scoreboard.iterator();

        assertEquals(scoreboard.size(), 2);
        assertTrue(iter.hasNext());
        iter.next();
        assertTrue(iter.hasNext());
        iter.next();
        assertFalse(iter.hasNext());
    }

    @Test
    public void scoresCompareProperly() {

        SessionScore bigScore = new SessionScore("Drogo", "The War of the Ring", 99999);
        SessionScore smallScore =
                new SessionScore("Drogo", "The War of the Ring", 5);
        SessionScore smallName =
                new SessionScore("Frodo", "The War of the Ring", 5);
        SessionScore smallGame =
                new SessionScore("Drogo", "Shire Lyfe", 5);

        // Order by score is first, and gives exact results of -1, 0, 1
        assertEquals(bigScore.compareTo(smallScore), -1);
        assertEquals(smallScore.compareTo(bigScore), 1);
        assertEquals(bigScore.compareTo(smallName), -1);
        assertEquals(smallName.compareTo(bigScore), 1);
        assertEquals(bigScore.compareTo(smallGame), -1);
        assertEquals(smallGame.compareTo(bigScore), 1);

        // Order by name is second
        assertTrue(smallGame.compareTo(smallName) >= 1);
        assertTrue(smallGame.compareTo(smallScore) >= 1);
        assertTrue(smallName.compareTo(smallGame) <= -1);
        assertTrue(smallName.compareTo(smallScore) <= -1);
        assertTrue(smallScore.compareTo(smallName) >= 1);
        assertTrue(smallScore.compareTo(smallGame) <= -1);

        // Order is compatible with equality
        assertEquals(bigScore.compareTo(bigScore), 0);
        assertEquals(smallScore.compareTo(smallScore), 0);
        assertEquals(smallGame.compareTo(smallGame), 0);
        assertEquals(smallName.compareTo(smallName), 0);
    }

    @Test
    public void scoresConvertToStrings() {
        SessionScore bigScore = new SessionScore("Drogo", "The War of the Ring", 99999);

        assertEquals(bigScore.toString(), "Drogo got 99999 in The War of the Ring");
    }

}
