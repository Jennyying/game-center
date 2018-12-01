package fall18project.gamecentre.user_management;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import fall18project.gamecentre.R;
import fall18project.gamecentre.utilities.BackgroundManager;

/**
 * An activity for logging in to an existing account
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Login management system
     */
    private LoginManager loginManager;

    /**
     * User management system (to deal with current logged in user)
     */
    private UserManager userManager;

    /**
     * A list of all usernames in the login management system. We can compute this on creation, and
     * re-compute on resumption, since we know that while this activity is open the list of usernames
     * will not changed
     */
    private List<String> usernameList;
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
    private BackgroundManager background;

    /**
     * Compute the list of usernames in the login manager
     */
    private void computeUsernameList() {
        usernameList = loginManager.getUserNameList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginManager = new LoginManager(this);
        userManager = new UserManager(this);
        computeUsernameList();

        setUpInterface();
    }

    private void setUpInterface() {
        ConstraintLayout layout = findViewById(R.id.constraintLayout);
        background = new BackgroundManager(this, null, layout);

        loginButton = findViewById(R.id.login);
        userNameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);

        setUpUsernameFieldAutocomplete();
        setUpLoginButtion();
        setUpNewGameButton();
    }

    /**
     * Set up the new game button
     */
    private void setUpNewGameButton() {
        Button newGameButton = findViewById(R.id.new_game);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewUser();
            }
        });
    }

    /**
     * Go to the new user screen
     */
    private void goToNewUser() {
        Intent newUser = new Intent(this, NewUserActivity.class);
        newUser.setFlags(newUser.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(newUser);
    }

    /**
     * Set up autocompletion for the username field
     */
    private void setUpUsernameFieldAutocomplete() {
        userNameField.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                usernameList));
    }

    /**
     * Set up the login button to return from the activity if the login is valid with an intent
     * indicating the new logged in user, or make a Toast if the login is invalid
     */
    private void setUpLoginButtion() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    /**
     * Attempt to login
     */
    private void attemptLogin() {
        LoginManager.LoginStatus login = loginManager.login(
                userNameField.getText().toString(),
                passwordField.getText().toString());
        switch (login) {
            case LOGIN_BAD_PASSWORD:
                Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
                break;
            case LOGIN_BAD_USERNAME:
                Toast.makeText(this, "Invalid username", Toast.LENGTH_SHORT).show();
                break;
            case LOGIN_GOOD:
                //TODO: actually log the user in and return from the activity
                userManager.setCurrentUser(userNameField.getText().toString());
                finish();
                break;
        }
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
