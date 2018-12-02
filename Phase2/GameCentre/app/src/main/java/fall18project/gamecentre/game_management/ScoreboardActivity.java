package fall18project.gamecentre.game_management;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import fall18project.gamecentre.ChooseGameActivity;
import fall18project.gamecentre.R;
import fall18project.gamecentre.user_management.LoginManager;
import fall18project.gamecentre.user_management.User;
import fall18project.gamecentre.user_management.UserManager;

public class ScoreboardActivity extends AppCompatActivity {

    private GameScoreboardManager scoreboardManager;
    private LoginManager loginManager;
    private UserManager userManager;
    private AutoCompleteTextView userNameView;
    private AutoCompleteTextView gameNameView;
    private ListView scoreboard;
    private String[] globalScoreTableCache;
    private ArrayAdapter<String> emptyView;

    /**
     * Update the global score table cache
     */
    private void updateGlobalScoreTableCache() {
        globalScoreTableCache = scoreboardManager.getGlobalScoreboard().getPrintedScores();
    }

    /**
     * Display an empty scoreboard
     */
    private void displayEmptyScoreboard() {
        scoreboard.setAdapter(emptyView);
    }

    /**
     * Display the Scoreboard object passed in. Display nothing if null is passed in
     *
     * @param scoreboard scoreboard to display
     */
    private void displayScoreboard(Scoreboard scoreboard) {
        if (scoreboard == null) {
            this.scoreboard.setAdapter(emptyView);
            return;
        }
        this.scoreboard.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                scoreboard.getPrintedScores()
        ));
    }

    /**
     * Display a game filtered scoreboard
     *
     * @param gameFile the filter to apply to the game
     * @param scoreboard  the scoreboard to filter
     */
    private void displayFilteredScoreboard(String gameFile, Scoreboard scoreboard) {
        ArrayList<String> filteredScores = new ArrayList<>(scoreboard.size());
        Iterator<SessionScore> stringIterator = scoreboard.iterator();
        while (stringIterator.hasNext()) {
            SessionScore score = stringIterator.next();
            if (score.getGameName().equals(gameFile)) filteredScores.add(score.toString());
        }

        this.scoreboard.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                filteredScores
        ));
    }

    /**
     * Display the global score table
     */
    private void displayGlobalScoreboard() {
        scoreboard.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                globalScoreTableCache));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        scoreboardManager = new GameScoreboardManager(this);
        loginManager = new LoginManager(this);
        userManager = new UserManager(this);

        setUpInterface();
    }

    /**
     * Set up the scoreboard interface
     */
    private void setUpInterface() {
        scoreboard = findViewById(R.id.scoreboard);

        emptyView = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                new String[0]
        );

        setUpAutoUsernameInput();
        setUpAutoGameInput();

        updateGlobalScoreTableCache();
        displayGlobalScoreboard();
    }

    /**
     * Set up the username input box
     */
    private void setUpAutoUsernameInput() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                loginManager.getUserNameList()
        );

        userNameView = findViewById(R.id.user_search);
        userNameView.setAdapter(adapter);
        userNameView.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int a, KeyEvent e) {
                        updateScoreboard();
                        return true;
                    }
                }
        );
    }

    /**
     * Set up the game name input box
     */
    private void setUpAutoGameInput() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                ChooseGameActivity.GAME_NAMES
        );

        gameNameView = findViewById(R.id.game_name);
        gameNameView.setAdapter(adapter);
        gameNameView.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int a, KeyEvent e) {
                        updateScoreboard();
                        return true;
                    }
                }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateGlobalScoreTableCache();
    }

    /**
     * Update the scoreboard displayed based off the contents of the username field
     */
    public void updateScoreboard() {
        String userName = userNameView.getText().toString();
        String gameName = gameNameView.getText().toString();
        if (userName.isEmpty() && gameName.isEmpty()) {
            displayGlobalScoreboard();
        } else if (userName.isEmpty()) {
            displayScoreboard(scoreboardManager.getScoreboard(gameName));
        } else {
            User u = userManager.loadUser(userName);
            if (u == null) displayEmptyScoreboard();
            else if (gameName.isEmpty()) displayScoreboard(u.getScoreboard());
            else displayFilteredScoreboard(gameName, u.getScoreboard());
        }
    }

}
