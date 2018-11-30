package fall18project.gamecentre.user_management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fall18project.gamecentre.ChooseGameActivity;
import fall18project.gamecentre.IntroductionActivity;
import fall18project.gamecentre.R;

/**
 * Activity to create a new user
 */
public class NewUserActivity extends AppCompatActivity {

    /**
     * A login manager to handle creating new users
     */
    private LoginManager loginManager;

    /**
     * A user manager to handle updating the current user
     */
    private UserManager userManager;

    /**
     * A handle to animate the clippy image used as mascot.
     */
    private ImageView clippy;

    /**
     * A handle for the yes button layout
     */
    private LinearLayout yesLayout;

    /**
     * A handle for the new user layout
     */
    private LinearLayout newUserLayout;

    /**
     * A handle for the confirm username button
     */
    private Button confirmUsername;

    /**
     * A handle for the username textbox
     */
    private EditText inputUsername;

    /**
     * A handle for the set password layout
     */
    private LinearLayout setPasswordLayout;

    /**
     * The password input box
     */
    private EditText inputPassword;

    /**
     * The current username
     */
    private String currentUsername = null;

    /**
     * The current username display box
     */
    private TextView currentUsernameDisplay;

    /**
     * The animation for clippy bouncing
     */
    private Animation bounce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        loginManager = new LoginManager(this);
        userManager = new UserManager(this);
        setUpInterface();
    }

    /**
     * Set up the interface by registering all listeners and handles.
     * <p>
     * Animation loading code taken from
     * https://evgenii.com/blog/spring-button-animation-on-android
     */
    private void setUpInterface() {
        clippy = findViewById(R.id.clippy);
        yesLayout = findViewById(R.id.yesButtonLayout);
        newUserLayout = findViewById(R.id.newUserLayout);
        confirmUsername = findViewById(R.id.confirmUsername);
        inputUsername = findViewById(R.id.inputUsername);
        setPasswordLayout = findViewById(R.id.setPasswordLayout);
        inputPassword = findViewById(R.id.inputPassword);
        currentUsernameDisplay = findViewById(R.id.current_username);
        bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);

        yesLayout.setVisibility(View.VISIBLE);
        newUserLayout.setVisibility(View.INVISIBLE);
        setPasswordLayout.setVisibility(View.INVISIBLE);

        setUpClippyTapAnimation();
        setUpYesButton();
        setUpConfirmUsernameButton();
        SetUpIntroductionButton();
        SetUpSkipButton();
    }

    /**
     * Go to the introduction
     */
    private void goToIntroduction() {
        Intent introduction = new Intent(this, IntroductionActivity.class);
        startActivity(introduction);
    }

    /**
     * Go straight to the game choice screen
     */
    private void goToChooseGame() {
        Intent chooseGame = new Intent(this, ChooseGameActivity.class);
        startActivity(chooseGame);
    }

    /**
     * Make tapping the introduction button register the new user and start an IntroductionActivity
     */
    private void SetUpIntroductionButton() {

        Button introductionButton = findViewById(R.id.introduction_button);
        introductionButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        registerCurrentUser();
                        goToIntroduction();
                    }
                }
        );

    }

    /**
     * Make tapping the skip button register the new user and start a ChooseGameActivity
     */
    private void SetUpSkipButton() {

        Button skipButton = findViewById(R.id.skip_button);
        skipButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        registerCurrentUser();
                        goToChooseGame();
                    }
                }
        );

    }

    /**
     * Make tapping the yes button make the containing layout invisible and open up the signup
     * layout
     */
    private void setUpYesButton() {
        Button yesButton = findViewById(R.id.yesButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesLayout.setVisibility(View.INVISIBLE);
                newUserLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * @return whether the username currently in the EditText box inputUsername is valid
     */
    private boolean isInputtedUsernameValid() {
        //TODO: implement
        return !loginManager.userExists(inputUsername.getText().toString());
    }


    /**
     * Register the current user
     */
    private void registerCurrentUser() {
        userManager.setCurrentUser(currentUsername);
        loginManager.registerUser(currentUsername, inputPassword.getText().toString());
    }

    /**
     * Set up the confirm username button
     */
    private void setUpConfirmUsernameButton() {
        setUpConfirmUsernameButtonText();
        setUpConfirmUsernameButtonClick();
    }

    /**
     * Set up a listener for changes in the username textbox to update the button text
     */
    private void setUpConfirmUsernameButtonText() {
        inputUsername.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                updateConfirmUsernameButtonText();
                return true;
            }
        });
    }

    /**
     * Set up a listener to show the password setting screen and set the current username if a valid
     * username is chosen
     */
    private void setUpConfirmUsernameButtonClick() {
        confirmUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInputtedUsernameValid()) {
                    currentUsername = inputUsername.getText().toString();
                    currentUsernameDisplay.setText(currentUsername);
                    setPasswordLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * Make the confirm username button display INVALID
     */
    private void makeConfirmUsernameButtonInvalid() {
        confirmUsername.setText(getString(R.string.text_invalid_username));
        confirmUsername.
                setTextColor(getResources().getColor(android.R.color.white, null));
    }

    /**
     * Make the confirm username button display TAKEN
     */
    private void makeConfirmUsernameButtonTaken() {
        confirmUsername.setText(getString(R.string.text_taken_username));
        confirmUsername.
                setTextColor(getResources().getColor(R.color.colorAccent, null));
    }

    /**
     * Make the confirm username button display CHOOSE
     */
    private void makeConfirmUsernameButtonChoose() {
        confirmUsername.setText(getString(R.string.text_choose_username));
        confirmUsername.setTextColor(getResources().getColor(R.color.colorPrimary, null));
    }

    /**
     * Make the confirm username button display INVALID when the username is empty/invalid,
     * TAKEN if the username is already taken, CHOOSE if the username is free and
     * CHANGE if a username has already been chosen and the username is not invalid or taken
     */
    private void updateConfirmUsernameButtonText() {
        if (inputUsername.getText().toString().isEmpty()) {
            makeConfirmUsernameButtonInvalid();
        } else if (isInputtedUsernameValid()) {
            makeConfirmUsernameButtonChoose();
        } else {
            makeConfirmUsernameButtonTaken();
        }
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
