package fall18project.gamecentre.game_management;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import fall18project.gamecentre.R;
import fall18project.gamecentre.user_management.UserScoreboard;

public class ScoreboardActivity extends AppCompatActivity {

    //TODO: fix this initialization
    //private ScoreboardManager scoreboardManager = new ScoreboardManager(null, null, null);
    private AutoCompleteTextView textView;
    private ListView scoreboard;
    private String[] globalScoreTableCache;
    private ArrayAdapter<String> emptyView;
    /**
     * Update the global score table cache
     */
    private void updateGlobalScoreTableCache() {
        //globalScoreTableCache = scoreboardManager.getGlobalScores().getPrintedScores();
    }

    /**
     * Display an empty scoreboard
     */
    private void displayEmptyScoreboard() {
        scoreboard.setAdapter(emptyView);
    }
    /**
     * Display the scoreboard of the UserScoreboard object passed in
     * @param u scoreboard to display
     */
    private void displayUserScoreboard(UserScoreboard u) {
        scoreboard.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                u.getPrintedScores()
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
        //loadFromFile(StartingActivity.SCORE_SAVE_FILENAME);
        setContentView(R.layout.activity_scoreboard);

        /*
        scoreboardManager.add(new SessionScore("Aragorn", 50.0));
        scoreboardManager.add(new SessionScore("Sauron", 200.0));
        scoreboardManager.add(new SessionScore("Frodo", 400.0));
        */

        /*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                scoreboardManager.getUserNames()
        );

        textView = findViewById(R.id.user_search);
        textView.setAdapter(adapter);
        textView.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int a, KeyEvent e) {
                        updateScoreboard();
                        return true;
                    }
                }
        );
        */

        scoreboard = findViewById(R.id.scoreboard);

        emptyView = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                new String[0]
        );

        updateGlobalScoreTableCache();
        displayGlobalScoreboard();
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
        /*
        String s = textView.getText().toString();
        if(s.isEmpty()) {
            displayGlobalScoreboard();
        }
        UserScoreboard u = scoreboardManager.searchUserScoreboard(s);
        if(u == null) {
            displayEmptyScoreboard();
            return;
        }
        displayUserScoreboard(u);
        */
    }

}
