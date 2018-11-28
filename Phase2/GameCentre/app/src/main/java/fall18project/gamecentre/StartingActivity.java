package fall18project.gamecentre;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.*;

import com.github.matteobattilana.weather.WeatherView;

import java.util.Random;

import fall18project.gamecentre.user_management.LoginActivity;
import fall18project.gamecentre.user_management.LoginManager;
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

        WeatherView weatherView = findViewById(R.id.weather_view);
        ConstraintLayout layout = findViewById(R.id.constraintLayout);
        background = new WeatherBackground(this, weatherView, layout);

        userManager = new UserManager(this, UserManager.DEFAULT_USER_PREFIX);

        // Start animations
        background.start();

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

    private void setUpInterface() {
        registerLoginButton();
        setRandomQuote();
    }

    private void registerLoginButton() {
        Button loginButton = findViewById(R.id.sign);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });
    }

    private void goToLogin() {
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }

    private void setRandomQuote() {
        if(subtitleBox == null) {
            subtitleBox = findViewById(R.id.funnyline);
        }

        String randomQuote = funnySubtitles[randomNumberGenerator.nextInt(funnySubtitles.length)];
        subtitleBox.setText(randomQuote);
    }

}
