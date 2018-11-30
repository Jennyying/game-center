package fall18project.gamecentre.user_management;

import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import fall18project.gamecentre.R;
import fall18project.gamecentre.user_management.User;
import fall18project.gamecentre.utilities.WeatherBackground;

/**
 * An activity for logging in to an existing account
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Login management system
     */
    private LoginManager loginManager;

    /**
     * A list of all usernames in the login management system. We can compute this on creation, and
     * re-compute on resumption, since we know that while this activity is open the list of usernames
     * will not changed
     */
    private List<String> usernameList;

    /**
     * Compute the list of usernames in the login manager
     */
    private void computeUsernameList() {
        usernameList = loginManager.getUserNameList();
    }

    /**
     * Username field
     */
    private AutoCompleteTextView userNameField;

    /**
     * Password field
     */
    private EditText passwordField;

    /**
     * Login button
     */
    private Button loginButton;

    /**
     * Background graphics manager (for rain effect and gradient animation)
     */
    private WeatherBackground background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginManager = new LoginManager(this, LoginManager.DEFAULT_FILE_NAME);
        computeUsernameList();

        setUpInterface();
    }

    private void setUpInterface() {
        ConstraintLayout layout = findViewById(R.id.constraintLayout);
        background = new WeatherBackground(this, null, layout);

        loginButton = findViewById(R.id.login);
        userNameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);

        setUpUsernameFieldAutocomplete();
    }

    /**
     * Set up autocompletion for the username field
     */
    private void setUpUsernameFieldAutocomplete() {
        userNameField.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                usernameList));
    }

    @Override
    protected void onResume() {
        super.onResume();
        background.onResume();
        computeUsernameList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        background.onPause();
    }
}
