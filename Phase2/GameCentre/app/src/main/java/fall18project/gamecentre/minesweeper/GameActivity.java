package fall18project.gamecentre.minesweeper;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Bus;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import fall18project.gamecentre.R;
import fall18project.gamecentre.game_management.GameScoreboardManager;
import fall18project.gamecentre.game_management.SessionScore;
import fall18project.gamecentre.user_management.UserManager;

/**
 * The game activity for Minesweeper
 *
 * Code adapted from https://github.com/kgleong/minesweeper.
 */
public class GameActivity extends AppCompatActivity implements GameManager.Listener {

    public static Bus gameBus = new Bus();
    @Bind(R.id.board_layout_view)
    BoardView boardView;
    @Bind(R.id.remaining_flags_text_view)
    TextView mRemainingFlagsTextView;
    @Bind(R.id.elapsed_time_text_view)
    TextView mElapsedTimeTextView;
    @Bind(R.id.finish_button)
    Button finishButton;
    @Bind(R.id.reset_button)
    Button resetButton;
    @Bind(R.id.undo_button)
    Button undoButton;

    /**
     * The manager for the Minesweeper board
     */
    private GameManager gameManager;

    /**
     * The manager for the currently logged in user
     */
    private UserManager userManager = new UserManager(this);

    /**
     * The manager for the game scoreboards
     */
    private GameScoreboardManager gameScoreboardManager = new GameScoreboardManager(this);

    /**
     * The dimension of the board
     */
    private int dimension = Board.DEFAULT_DIMENSION;

    /**
     * The number of mines on the board
     */
    private int numMines = Board.DEFAULT_NUM_MINES;

    /**
     * The tiles of the board
     */

    private Timer timer;

    /**
     * @return the game bus for carrying game events
     */
    public static Bus getGameBus() {
        if (gameBus == null) {
            gameBus = new Bus();
        }
        return gameBus;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_minesweeper);
        ButterKnife.bind(this);

        setupGame();
        setupViews();
    }

    /**
     * Set up the Minesweeper game
     */
    private void setupGame() {
        try {
            gameManager = new GameManager(dimension, numMines, boardView, this);
        } catch (Exception e) {
            String errorMessage = getResources().getString(R.string.board_initialization_error);
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Set up the game's views
     */
    private void setupViews() {
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.finishGame();
                onGameFinished();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    gameManager.initGame(dimension, numMines);

                    stopTimer();
                    startTimer();
                } catch (Exception e) {
                    Context context = GameActivity.this;
                    String message = context.getResources().getString(R.string.game_reset_error);
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        setupUndoView();
    }

    /**
     * Update the text of the undo button
     */
    private void updateUndoButtonText()
    {
        String undoButtonText = getString(R.string.text_undo_button, gameManager.getUndos());
        undoButton.setText(undoButtonText);
    }

    /**
     * Sets up the ability of tiles to change from covered to uncovered
     */
    private void setupUndoView() {
        updateUndoButtonText();
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.undo();
                updateUndoButtonText();
            }
        });
        /*
        float fillPercent = 0.8f;
        int inPlayOuter = getResources().getColor(R.color.blue_grey_300);
        int inPlayInner = getResources().getColor(R.color.blue_grey_600);

        statusImageDrawable = new LevelListDrawable();
        statusImageDrawable.addLevel(0, IN_PLAY_LEVEL, new ConcentricCirclesDrawable(new int[]{inPlayOuter, inPlayInner}, fillPercent));
        statusImageDrawable.addLevel(0, WON_LEVEL, new ConcentricCirclesDrawable(new int[]{Color.GREEN, Color.YELLOW}, fillPercent));
        statusImageDrawable.addLevel(0, LOST_LEVEL, new ConcentricCirclesDrawable(new int[]{Color.RED, Color.BLACK}, fillPercent));

        statusImageView.setBackground(statusImageDrawable);
        */
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (gameManager != null) {
            updateMineFlagsRemainingCount(gameManager.getMineFlagsRemainingCount());
            startTimer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    /**
     * Starts the game timer
     */
    void startTimer() {
        if (gameManager != null && !gameManager.isGameFinished()) {
            gameManager.startTimer();

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateTimeElapsed(gameManager.getElapsedTime());
                        }
                    });
                }
            };

            timer = new Timer();

            // Delay: 0, Interval: 1000ms
            timer.schedule(timerTask, 0, 1000);
        }
    }


    /**
     * Stops the game timer
     */
    void stopTimer() {
        if (gameManager != null && timer != null) {
            gameManager.stopTimer();

            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void updateTimeElapsed(long elapsedTime) {
        int elapsedTimeInSeconds = (int) elapsedTime / 1000;

        mElapsedTimeTextView.setText(String.valueOf(elapsedTimeInSeconds));
    }

    @Override
    public void updateMineFlagsRemainingCount(int flagsRemaining) {
        mRemainingFlagsTextView.setText(String.valueOf(flagsRemaining));
    }

    @Override
    public void onLoss() {
        String message = getResources().getString(R.string.loss_message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        long oldScore = gameManager.getElapsedTime();
        long score = (long) (oldScore - Math.exp(1 / oldScore)) * 1000;
        SessionScore sessionScore = new SessionScore(userManager.loadCurrentUserName(), "minesweeper", score);
        gameScoreboardManager.addScoreForGame(userManager, sessionScore);
    }

    @Override
    public void onWin() {
        String message = getResources().getString(R.string.win_message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        long oldScore = gameManager.getElapsedTime();
        long score = (long) (oldScore + Math.exp(1 / oldScore)) * 1000;
        SessionScore sessionScore = new SessionScore(userManager.loadCurrentUserName(), "minesweeper", score);
        gameScoreboardManager.addScoreForGame(userManager, sessionScore);
    }

    @Override
    public void onGameFinished() {
        stopTimer();
    }
}
