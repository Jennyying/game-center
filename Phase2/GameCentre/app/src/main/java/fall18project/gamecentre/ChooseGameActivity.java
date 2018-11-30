package fall18project.gamecentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        setUpReactorModel();
        setUpSlidingTiles();
    }

    /**
     * Set up the button to open the ReactorControl minigame
     */
    private void setUpReactorControl() {
        Button reactorControlButton = findViewById(R.id.goToReactorControl);
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
    private void setUpReactorModel() {
        Button minesweeperButton = findViewById(R.id.goToReactorModel);
        minesweeperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToReactorModel();
            }
        });
    }

    /**
     *
     */
    private void setUpSlidingTiles() {
        Button slidingTilesButton = findViewById(R.id.goToSlidingTiles);
        slidingTilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSlidingTiles();
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
     * Go to the SlidingTiles minigame
     */
    private void goToSlidingTiles() {
        Intent slidingTiles = new Intent(
                this, fall18project.gamecentre.sliding_tiles.GameSetupActivity.class);
        startActivity(slidingTiles);
    }

    /**
     * Go to the minesweeper minigame
     */
    private void goToReactorModel() {
        Intent minesweeper = new Intent(
                this, fall18project.gamecentre.minesweeper.GameActivity.class);
        startActivity(minesweeper);
    }
}

