package fall18project.gamecentre.user_management;

import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fall18project.gamecentre.R;
import fall18project.gamecentre.user_management.User;
import fall18project.gamecentre.utilities.WeatherBackground;

/**
 * An activity for logging in to an existing account
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * User management system
     */
    private UserManager userManager;

    /**
     * Login management system
     */
    private LoginManager loginManager;

    /**
     * Background graphics manager (for rain effect and gradient animation)
     */
    private WeatherBackground background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userManager = new UserManager(this, UserManager.DEFAULT_USER_PREFIX);
        loginManager = new LoginManager(this, LoginManager.DEFAULT_FILE_NAME);

        ConstraintLayout layout = findViewById(R.id.constraintLayout);
        background = new WeatherBackground(this, null, layout);
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
