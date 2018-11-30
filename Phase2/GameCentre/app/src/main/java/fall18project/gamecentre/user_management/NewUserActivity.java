package fall18project.gamecentre.user_management;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
     * The current username
     */
    private String currentUsername = null;

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
        yesLayout = findViewById(R.id.yesButtonLayout);
        newUserLayout = findViewById(R.id.newUserLayout);
        confirmUsername = findViewById(R.id.confirmUsername);
        inputUsername = findViewById(R.id.inputUsername);
        bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);

        yesLayout.setVisibility(View.VISIBLE);
        newUserLayout.setVisibility(View.INVISIBLE);

        setUpClippyTapAnimation();
        setUpYesButton();
        setUpConfirmUsernameButtonText();
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
        if(inputUsername.getText().toString().isEmpty()) {
            makeConfirmUsernameButtonInvalid();
        } else if(false) { //TODO: replace this for a check of whether the username is already taken
            makeConfirmUsernameButtonTaken();
        } else {
            makeConfirmUsernameButtonChoose();
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
