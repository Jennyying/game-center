package fall18project.gamecentre;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.*;

import com.github.matteobattilana.weather.PrecipType;
import com.github.matteobattilana.weather.WeatherData;
import com.github.matteobattilana.weather.WeatherViewSensorEventListener;
import com.github.matteobattilana.weather.WeatherView;

import fall18project.gamecentre.WeatherBackground;

import org.jetbrains.annotations.NotNull;

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
