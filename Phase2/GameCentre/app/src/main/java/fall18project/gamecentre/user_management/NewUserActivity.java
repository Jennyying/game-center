package fall18project.gamecentre.user_management;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import fall18project.gamecentre.R;

/**
 * Activity to create a new user
 */
public class NewUserActivity extends AppCompatActivity {

    /**
     * A handle to animate the clippy image used as mascot.
     */
    private ImageView clippy;

    /**
     * The animation for clippy bouncing
     */
    private Animation bounce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        setUpInterface();
    }

    /**
     * Set up the interface by registering all listeners and handles.
     *
     * Animation loading code taken from
     * https://evgenii.com/blog/spring-button-animation-on-android
     */
    private void setUpInterface() {
        clippy = findViewById(R.id.clippy);
        bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);

        setUpClippyTapAnimation();
    }

    /**
     * Make clippy icon bounce slightly and glow on tap
     */
    private void setUpClippyTapAnimation() {
        clippy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clippyBounce();
            }
        });
    }

    /**
     * Make clippy icon bounce slightly and glow
     * Animation code taken from
     * https://evgenii.com/blog/spring-button-animation-on-android
     */
    private void clippyBounce() {
        clippy.startAnimation(bounce);
    }
}
