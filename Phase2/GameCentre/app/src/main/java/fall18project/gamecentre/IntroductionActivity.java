package fall18project.gamecentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Give a user the introduction to In the Colonel, and then modify the user settings appropriately
 */
public class IntroductionActivity extends AppCompatActivity {

    /**
     * Username of current logged in user
     */
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        userName = getIntent().getStringExtra("userName");
        setUpInterface();
    }

    /**
     * Set up the interface for IntroductionActivity, which here is Clippy and the "Let's Go" button
     */
    private void setUpInterface() {
        registerLetsGoButton();
    }

    /**
     * Register an OnClickHandler for the Let's Go button to go to the game selection screen
     */
    private void registerLetsGoButton() {
        Button letsGoButton = findViewById(R.id.lets_go_button);
        letsGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChooseGame();
            }
        });
    }

    /**
     * Go to the game selection screen
     */
    private void goToChooseGame() {
        Intent chooseGame = new Intent(this, ChooseGameActivity.class);
        chooseGame.putExtra("userName", userName);
        startActivity(chooseGame);
    }
}
