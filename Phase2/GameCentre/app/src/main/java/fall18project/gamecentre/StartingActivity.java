package fall18project.gamecentre;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.*;

import com.github.matteobattilana.weather.WeatherView;

import fall18project.gamecentre.utilities.WeatherBackground;

/**
 * The initial activity for the sliding puzzle tile game.
 *
 * Weather display based off that found at
 * https://github.com/matteobattilana/weatherview
 */
public class StartingActivity extends AppCompatActivity {

    private WeatherBackground background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_);

        WeatherView weatherView = findViewById(R.id.weather_view);
        RelativeLayout layout = findViewById(R.id.relativeLayout);
        background = new WeatherBackground(this, weatherView, layout);

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
