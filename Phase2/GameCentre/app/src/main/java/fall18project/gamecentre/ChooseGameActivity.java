package fall18project.gamecentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * An activity for choosing from a list of games in the game centre
 */
public class ChooseGameActivity extends AppCompatActivity {

    public static final String[] GAME_NAMES = {
        "SlidingTiles",
        "ReactorControl",
        "Minesweeper"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);

        setUpInterface();
    }

    /**
     * Set up the interface for this activity
     */
    private void setUpInterface() {
        setUpReactorControl();
        setUpMinesweeper();
    }

    /**
     * Set up the button to open the ReactorControl minigame
     */
    private void setUpReactorControl() {
        Button reactorControlButton = findViewById(R.id.goToHyperslide);
        reactorControlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToReactorControl();
            }
        });
    }

    /**
     * Set up the button to open the Minesweeper minigame
     */
    private void setUpMinesweeper() {
        Button minesweeperButton = findViewById(R.id.goToMinesweeper);
        minesweeperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMinesweeper();
            }
        });
    }

    /**
     * Go to the ReactorControl minigame
     */
    private void goToReactorControl() {
        Intent reactorControl = new Intent(
                this, fall18project.gamecentre.reactorcontrol.GameActivity.class);
        startActivity(reactorControl);
    }

    /**
     * Go to the minesweeper minigame
     */
    private void goToMinesweeper() {
        Intent minesweeper = new Intent(
                this, fall18project.gamecentre.minesweeper.GameActivity.class);
        startActivity(minesweeper);
    }
}

