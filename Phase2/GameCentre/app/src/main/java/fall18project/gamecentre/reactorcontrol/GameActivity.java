package fall18project.gamecentre.reactorcontrol;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import fall18project.gamecentre.R;
import fall18project.gamecentre.user_management.GameOverActivity;

public class GameActivity extends AppCompatActivity {

    /**
     * The name of the game
     */
    public static final String GAME_NAME = "ReactorControl";

    /**
     * Time interval between frames
     */
    private final static long TIME_INTERVAL = 30;

    /**
     * The game view, controlling all graphics for the game
     */
    private GameView gameView;

    /**
     * A gradient background for the view. Inspired by the answer by slund to
     * https://stackoverflow.com/questions/6115715/how-do-i-programmatically-set-the-background-color-gradient-on-a-custom-title-ba
     */
    private GradientDrawable gradientBackground = new GradientDrawable(
        GradientDrawable.Orientation.TOP_BOTTOM, new int[]{R.color.amber_900, R.color.colorTerminalGlow});

    /**
     * The player's username
     */
    private String userName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);
        gradientBackground.setUseLevel(true);
        gameView.setGradientBackground(gradientBackground);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameView.invalidate();
            }
        }, 0, TIME_INTERVAL);
    }

    /**
     * Finish the game, passing the score and game name to GameOverActivity
     */
    private void gameOver() {
        Intent over = new Intent(this, GameOverActivity.class);
        /*over.putExtra("score", gameView.getScore());
        over.putExtra("gameName", "reactorControl");
        over.putExtra("userName", userName);*/
        startActivity(over);
    }


}
