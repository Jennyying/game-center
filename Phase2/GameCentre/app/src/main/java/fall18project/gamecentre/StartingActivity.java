package fall18project.gamecentre;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.*;

import com.github.matteobattilana.weather.WeatherView;

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
    }

    @Override
    protected void onPause() {
        super.onPause();
        background.onPause();
    }

    private void setUpInterface() {
        registerLoginButton();
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

}
