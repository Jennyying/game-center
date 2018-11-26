package fall18project.gamecentre.utilities;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.Layout;
import android.widget.RelativeLayout;

import com.github.matteobattilana.weather.PrecipType;
import com.github.matteobattilana.weather.WeatherView;
import com.github.matteobattilana.weather.WeatherViewSensorEventListener;

/**
 * A class to encapsulate the creation of a responsive, rainy background from a relative layout,
 * weather view variable and context, the former of which may be null
 */
public class WeatherBackground {

    private WeatherViewSensorEventListener weatherSensor = null;
    private WeatherView weatherView = null;
    private AnimationDrawable anim = null;

    /**
     * Initialize a weather background
     * @param context Context to initialize it to. Should be the activity we're initializing it for
     * @param view Weather view to configure. Null if none
     * @param layout Layout to configure the background of. Null if none
     */
    public WeatherBackground(Context context, WeatherView view, RelativeLayout layout) {
        if(view != null) {
            weatherView = view;
            weatherSensor = new WeatherViewSensorEventListener(context, weatherView);
        }
        if(layout != null) {
            anim = (AnimationDrawable) layout.getBackground();
            configureBackgroundAnimation();
        }
    }

    /**
     * Configure the background animation, assuming anim is not null
     */
    private void configureBackgroundAnimation() {
        // Setting enter fade animation duration to 5 seconds
        anim.setEnterFadeDuration(5000);

        // Setting exit fade animation duration to 2 seconds
        anim.setExitFadeDuration(2000);
    }

    /**
     * Configure the rain animation, assuming the sensor and view are not null
     */
    private void configureWeatherAnimation() {
        weatherView.setWeatherData(PrecipType.RAIN);
    }

    /**
     * Start the weather and background animations if they exist
     */
    public void start() {
        if(anim != null && !anim.isRunning()) anim.start();
    }

    /**
     * Pause the weather and background animations if they exist, and pause the sensor
     */
    public void onPause() {
        if(anim != null && anim.isRunning()) anim.stop();
        if(weatherSensor != null) weatherSensor.onPause();
    }

    /**
     * Resume the weather and background animations if they exist, and resume the sensor
     */
    public void onResume() {
        if(anim != null && !anim.isRunning()) anim.start();
        if(weatherSensor != null) weatherSensor.onResume();
    }
}
