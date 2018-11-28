package fall18project.gamecentre;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;

import com.github.matteobattilana.weather.WeatherView;

import fall18project.gamecentre.user_management.LoginManager;
import fall18project.gamecentre.user_management.UserManager;
import fall18project.gamecentre.utilities.WeatherBackground;

/**
 * The initial activity for the sliding puzzle tile game.
 * <p>
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_);

        WeatherView weatherView = findViewById(R.id.weather_view);
        ConstraintLayout layout = findViewById(R.id.constraintLayout);
        background = new WeatherBackground(this, weatherView, layout);

        userManager = new UserManager(
                this, LoginManager.DEFAULT_FILE_NAME, UserManager.DEFAULT_USER_PREFIX);

        // Start animations
        background.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        background.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        background.onPause();
    }

}
