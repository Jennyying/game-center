package fall18project.gamecentre.utilities;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;

import com.github.matteobattilana.weather.PrecipType;
import com.github.matteobattilana.weather.WeatherView;
import com.github.matteobattilana.weather.WeatherViewSensorEventListener;

/**
 * A class to encapsulate the creation of a responsive, rainy background from a relative layout,
 * weather view variable and context, the former of which may be null
 */
public class BackgroundManager {

    /**
     * Default enter fade duration in milliseconds
     */
    private static final int DEFAULT_ENTER_FADE_DURATION = 10000;
    /**
     * Default exit fade duration in milliseconds
     */
    private static final int DEFAULT_EXIT_FADE_DURATION = 10000;
    /**
     * Class for connecting rain direction to sensors. Might be null
     */
    private WeatherViewSensorEventListener weatherSensor = null;
    /**
     * Handle on rain renderer. Might be null, if no rain is being rendered
     */
    private WeatherView weatherView = null;
    /**
     * Handle on background animation. Might be null, if we don't have one.
     */
    private AnimationDrawable anim = null;

    /**
     * Initialize a weather background
     *
     * @param context Context to initialize it to. Should be the activity we're initializing it for
     * @param view    Weather view to configure. Null if none
     * @param layout  Layout to configure the background of. Null if none
     */
    public BackgroundManager(Context context, WeatherView view, ConstraintLayout layout) {
        if (view != null) {
            weatherView = view;
            weatherSensor = new WeatherViewSensorEventListener(context, weatherView);
        }
        if (layout != null) {
            anim = (AnimationDrawable) layout.getBackground();
            configureBackgroundAnimation();
        }
    }

    /**
     * Configure the background animation, assuming anim is not null
     */
    private void configureBackgroundAnimation() {
        anim.setEnterFadeDuration(DEFAULT_ENTER_FADE_DURATION);
        anim.setExitFadeDuration(DEFAULT_EXIT_FADE_DURATION);
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
        if (anim != null && !anim.isRunning()) anim.start();
    }

    /**
     * Pause the weather and background animations if they exist, and pause the sensor
     */
    public void onPause() {
        if (anim != null && anim.isRunning()) anim.stop();
        if (weatherSensor != null) weatherSensor.onPause();
    }

    /**
     * Resume the weather and background animations if they exist, and resume the sensor
     */
    public void onResume() {
        if (anim != null && !anim.isRunning()) anim.start();
        if (weatherSensor != null) weatherSensor.onResume();
    }
}
