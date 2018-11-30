package fall18project.gamecentre;

import org.junit.Test;

import java.util.Iterator;

import fall18project.gamecentre.game_management.ScoreboardManager;
import fall18project.gamecentre.game_management.SessionScore;
import fall18project.gamecentre.user_management.LoginManager;
import fall18project.gamecentre.user_management.UserManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ScoreboardTest {

    ScoreboardManager scoreboardManager;

    /**
     * Initialize the scoreboard manager to empty
     */
    public void setUpEmpty() {
        scoreboardManager = new ScoreboardManager(
                null,
                new UserManager(null, UserManager.DEFAULT_USER_PREFIX),
                new LoginManager(null, LoginManager.DEFAULT_FILE_NAME));
    }


    /**
     * Test adding scores to the scoreboard works and the scores are properly sorted
     */
    @Test
    public void testAddingScores() {

        setUpEmpty();
        assertEquals(0, scoreboardManager.getGlobalScores().size());
        assertEquals(0, scoreboardManager.numUserScoreboards());

        scoreboardManager.add(new SessionScore("Sauron", 100));
        assertEquals(1, scoreboardManager.getGlobalScores().size());
        assertEquals(1, scoreboardManager.numUserScoreboards());

        scoreboardManager.add(new SessionScore("Aragorn", 50));
        assertEquals(2, scoreboardManager.getGlobalScores().size());
        assertEquals(2, scoreboardManager.numUserScoreboards());

        scoreboardManager.add(new SessionScore("Sauron", 300));
        assertEquals(3, scoreboardManager.getGlobalScores().size());
        assertEquals(2, scoreboardManager.numUserScoreboards());

        scoreboardManager.add(new SessionScore("Frodo", 400));
        assertEquals(4, scoreboardManager.getGlobalScores().size());
        assertEquals(3, scoreboardManager.numUserScoreboards());

        Iterator<SessionScore> allScores = scoreboardManager.getGlobalScores().iterator();
        assertTrue(allScores.hasNext());
        assertEquals(0, allScores.next().compareTo(new SessionScore("Frodo", 400)));
        assertTrue(allScores.hasNext());
        assertEquals(0, allScores.next().compareTo(new SessionScore("Sauron", 300)));
        assertTrue(allScores.hasNext());
        assertEquals(0, allScores.next().compareTo(new SessionScore("Sauron", 100)));
        assertTrue(allScores.hasNext());
        assertEquals(0, allScores.next().compareTo(new SessionScore("Aragorn", 50)));
        assertFalse(allScores.hasNext());

        Iterator<SessionScore> sauronScores =
                scoreboardManager.getUserScoreboard("Sauron").iterator();

        assertTrue(sauronScores.hasNext());
        assertEquals(0, sauronScores.next().compareTo(new SessionScore("Sauron", 300)));
        assertTrue(sauronScores.hasNext());
        assertEquals(0, sauronScores.next().compareTo(new SessionScore("Sauron", 100)));
        assertFalse(sauronScores.hasNext());

        Iterator<SessionScore> frodoScores =
                scoreboardManager.getUserScoreboard("Frodo").iterator();
        assertTrue(frodoScores.hasNext());
        assertEquals(0, frodoScores.next().compareTo(new SessionScore("Frodo", 400)));
        assertFalse(frodoScores.hasNext());

        Iterator<SessionScore> bombadilScores =
                scoreboardManager.getUserScoreboard("Tom Bombadil").iterator();
        assertFalse(bombadilScores.hasNext());
    }
}
