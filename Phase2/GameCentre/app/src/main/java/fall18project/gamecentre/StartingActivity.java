package fall18project.gamecentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.matteobattilana.weather.WeatherView;

import java.util.Random;

import fall18project.gamecentre.game_management.ScoreboardActivity;
import fall18project.gamecentre.user_management.LoginActivity;
import fall18project.gamecentre.user_management.NewUserActivity;
import fall18project.gamecentre.user_management.User;
import fall18project.gamecentre.user_management.UserManager;
import fall18project.gamecentre.utilities.BackgroundManager;

/**
 * The initial activity for the sliding puzzle tile game.
 * <p>
 * Weather display based off that found at
 * https://github.com/matteobattilana/weatherview
 */
public class StartingActivity extends AppCompatActivity {

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
            "Too inefficient for even Gosling",
            "Should've been written in... eh anything is better than Java"
    };
    /**
     * A request for a logged in user, or null
     */
    public static int LOGGED_IN_REQUEST_CODE = 2;
    /**
     * Manager for the background aesthetics of the starting activity
     */
    private BackgroundManager background;
    /**
     * Manager and interface for user database
     */
    private UserManager userManager;
    /**
     * The text view for the current logged in user
     */
    private TextView currentUserView;
    /**
     * The text view for the log in status
     */
    private TextView loginStatusView;
    /**
     * The login button
     */
    private Button loginButton;
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
        userManager = new UserManager(this);

        // Set up the interface
        setUpInterface();
    }

    @Override
    protected void onResume() {
        super.onResume();
        background.onResume();
        setRandomQuote();
        updateLoginStatusDisplay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        background.onPause();
    }

    /**
     * Update login status display
     */
    private void updateLoginStatusDisplay() {
        updateCurrentUserName();
        updateLoginText();
    }

    /**
     * Update the current user name display
     */
    private void updateCurrentUserName() {
        String currentUserName = userManager.loadCurrentUserName();
        if (currentUserName != null) {
            currentUserView.setText(currentUserName);
            loginStatusView.setText(R.string.text_signed_in_as);
        } else {
            currentUserView.setText("");
            loginStatusView.setText(R.string.text_not_logged_in);
        }
    }

    /**
     * Update the text of the login button
     */
    private void updateLoginText() {
        String currentUserName = userManager.loadCurrentUserName();
        if (currentUserName == null) {
            loginButton.setText(R.string.text_sign_in);
        } else {
            loginButton.setText(R.string.text_sign_out);
        }
    }

    /**
     * Set up the interface by registering all listeners, setting a random quote and managing
     * the background
     */
    private void setUpInterface() {
        // Set up the background manager
        WeatherView weatherView = findViewById(R.id.weather_view);
        ConstraintLayout layout = findViewById(R.id.constraintLayout);
        background = new BackgroundManager(this, weatherView, layout);

        // Start animations
        background.start();

        loginButton = findViewById(R.id.sign);
        currentUserView = findViewById(R.id.currentUsername);
        loginStatusView = findViewById(R.id.currentlyLoggedIn);

        registerSignInOutButton();
        registerPlayButton();
        registerNewPlayerButton();
        registerScoresButton();

        updateLoginStatusDisplay();
        setRandomQuote();
    }

    /**
     * Register an on-click listener for the sign in/out button to go to the login
     * activity if no user is currently signed in or sign out if a user is currently
     * signed in
     */
    private void registerSignInOutButton() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User currentUser = userManager.loadCurrentUser();
                if (currentUser != null) {
                    userManager.resetCurrentUser();
                    updateLoginStatusDisplay();
                } else goToLogin();
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
                User currentUser = userManager.loadCurrentUser();
                if (currentUser == null) goToNewUser();
                else if (currentUser.getSettings().shouldShowIntroduction()) goToIntroduction();
                else goToChooseGame();
            }
        });
    }

    /**
     * Register the new player button to create a new user (logging out the current user)
     */
    private void registerNewPlayerButton() {
        Button newPlayerButton = findViewById(R.id.player);
        newPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewUser();
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
        startActivity(introduction);
    }

    /**
     * Go to ChooseGameActivity. Must have a non-null current user, i.e. a current user logged in
     */
    private void goToChooseGame() {
        Intent chooseGame = new Intent(this, ChooseGameActivity.class);
        startActivity(chooseGame);
    }

    /**
     * Sign out the current user (by saving it and setting it to null if it isn't already)
     */
    private void signOutCurrentUser() {
        userManager.resetCurrentUser();
    }

    private void setRandomQuote() {
        if (subtitleBox == null) {
            subtitleBox = findViewById(R.id.funnyline);
        }

        String randomQuote = funnySubtitles[randomNumberGenerator.nextInt(funnySubtitles.length)];
        subtitleBox.setText(randomQuote);
    }

}
