package fall18project.gamecentre;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.*;

import com.github.matteobattilana.weather.WeatherView;

import java.util.Random;

import fall18project.gamecentre.game_management.ScoreboardActivity;
import fall18project.gamecentre.user_management.LoginActivity;
import fall18project.gamecentre.user_management.LoginManager;
import fall18project.gamecentre.user_management.NewUserActivity;
import fall18project.gamecentre.user_management.User;
import fall18project.gamecentre.user_management.UserManager;
import fall18project.gamecentre.utilities.WeatherBackground;

/**
 * The initial activity for the sliding puzzle tile game.
 *
 * Weather display based off that found at
 * https://github.com/matteobattilana/weatherview
 */
public class StartingActivity extends AppCompatActivity {

    /**
     * Manager for the background aesthetics of the starting activity
     */
    private WeatherBackground background;

    /**
     * Manager and interface for user database
     */
    private UserManager userManager;

    /**
     * The current logged in user. Is null if and only if no one is logged in
     */
    private User currentUser = null;

    /**
     * A collection of funny quotes to put in the subtitle
     */
    private static final String[] funnySubtitles = {
            "Should've been written in Haskell",
            "Should've been written in Rust",
            "Should've been written in C++",
            "Where you go after a system call",
            "On Android but without androids",
            "Would be revolutionary in the 70's",
            "Cyberpunk but without the punk",
            "SIGSEGV... sorry NullPointerException",
            "Better than Clippy!",
            "Default Text Joke",
            "Cyberpunk Microsoft Solitaire",
            "Can't come up with anything...",
            "A metagame for metagamers",
            "I'm so meta, even this acronym",
            "Too inefficient for even Gosling"
    };

    /**
     * Random number generator to pick quotes
     */
    private Random randomNumberGenerator = new Random();

    /**
     * The subtitle box for the funny quotes.
     */
    private TextView subtitleBox = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_);
        userManager = new UserManager(this, UserManager.DEFAULT_USER_PREFIX);

        // Set up the interface
        setUpInterface();
    }

    @Override
    protected void onResume() {
        super.onResume();
        background.onResume();
        setRandomQuote();
    }

    @Override
    protected void onPause() {
        super.onPause();
        background.onPause();
    }

    /**
     * Set up the interface by registering all listeners, setting a random quote and managing
     * the background
     */
    private void setUpInterface() {
        // Set up the background manager
        WeatherView weatherView = findViewById(R.id.weather_view);
        ConstraintLayout layout = findViewById(R.id.constraintLayout);
        background = new WeatherBackground(this, weatherView, layout);

        // Start animations
        background.start();

        registerSignInOutButton();
        registerPlayButton();
        registerScoresButton();
        setRandomQuote();
    }

    /**
     * Register an on-click listener for the sign in/out button to go to the login
     * activity if no user is currently signed in or sign out if a user is currently
     * signed in
     */
    private void registerSignInOutButton() {
        Button loginButton = findViewById(R.id.sign);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser != null) signOutCurrentUser();
                else goToLogin();
            }
        });
    }

    /**
     * Register an on-click listener to go to the scoreboard activity
     */
    private void registerScoresButton() {
        Button scoresButton = findViewById(R.id.scores);
        scoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToScoreboard();
            }
        });
    }

    /**
     * Go to the login interface
     */
    private void goToLogin() {
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }

    /**
     * Go to the scoreboard
     */
    private void goToScoreboard() {
        Intent scoreboard = new Intent(this, ScoreboardActivity.class);
        startActivity(scoreboard);
    }

    /**
     * Register the play button to create a new user if none is logged in, otherwise to check
     * whether the introduction should be shown and, if so, show it, or go straight to the
     * game choosing screen
     */
    private void registerPlayButton() {
        Button playButton = findViewById(R.id.play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser == null) goToNewUser();
                else if(currentUser.getSettings().shouldShowIntroduction()) goToIntroduction();
                else goToChooseGame();
            }
        });
    }

    /**
     * Go to NewUserActivity
     */
    private void goToNewUser() {
        Intent newUser = new Intent(this, NewUserActivity.class);
        startActivity(newUser);
    }

    /**
     * Go to IntroductionActivity. Must have a non-null current user, i.e. a current user logged in
     */
    private void goToIntroduction() {
        Intent introduction = new Intent(this, IntroductionActivity.class);
        introduction.putExtra("name", currentUser.getUserName());
        startActivity(introduction);
    }

    /**
     * Go to ChooseGameActivity. Must have a non-null current user, i.e. a current user logged in
     */
    private void goToChooseGame() {
        Intent chooseGame = new Intent(this, ChooseGameActivity.class);
        chooseGame.putExtra("name", currentUser.getUserName());
        startActivity(chooseGame);
    }

    /**
     * Sign out the current user (by saving it and setting it to null if it isn't already)
     */
    private void signOutCurrentUser() {
        if(currentUser != null) userManager.storeUser(currentUser);
        currentUser = null;
    }

    private void setRandomQuote() {
        if(subtitleBox == null) {
            subtitleBox = findViewById(R.id.funnyline);
        }

        String randomQuote = funnySubtitles[randomNumberGenerator.nextInt(funnySubtitles.length)];
        subtitleBox.setText(randomQuote);
    }

}
