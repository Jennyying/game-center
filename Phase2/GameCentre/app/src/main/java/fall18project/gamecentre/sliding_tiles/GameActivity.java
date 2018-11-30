package fall18project.gamecentre.sliding_tiles;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall18project.gamecentre.R;
import fall18project.gamecentre.game_management.GameScoreboardManager;
import fall18project.gamecentre.user_management.UserManager;

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements Observer {

    /**
     * The prefix for user's temporary saved games
     */
    public static final String TEMP_SAVE_FILENAME = "sliding_tiles_temp_";

    /**
     * The prefix for user's saved games
     */
    public static final String SAVE_FILENAME = "sliding_tiles_";

    /**
     * The name of this game
     */
    public static final String GAME_NAME = "SlidingTiles";

    /**
     * Constants for swiping directions. Should be an enum, probably.
     */
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;
    private static int columnWidth, columnHeight;
    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The scoreboard manager
     */
    private GameScoreboardManager scoreboardManager;

    /**
     * The user manager
     */
    private UserManager userManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;
    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;

    /**
     * Return the temporary filename to save games to
     *
     * @param cn the current user name
     * @return the temporary filename to save games to
     */
    public static String getTempSaveFilename(String cn) {
        return SAVE_FILENAME + cn + ".ser";
    }

    /**
     * Return the filename to save games to
     *
     * @param cn the current user name
     * @return the filename to save games to
     */
    public static String getSaveFilename(String cn) {
        return SAVE_FILENAME + cn + ".ser";
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    /**
     * Return the temporary save filename to save to
     *
     * @return the temporary save filename for the current user
     */
    public String getTempSaveFilename() {
        return getTempSaveFilename(userManager.loadCurrentUserName());
    }

    /**
     * Return the filename to save games to
     *
     * @return the filename to save games to
     */
    public String getSaveFilename() {
        return getSaveFilename(userManager.loadCurrentUserName());
    }

    /**
     * Load the game from the temp file
     */
    private void loadGameFromTempFile() {
        loadGameFromFile(getTempSaveFilename());
    }

    /**
     * Load the game from the save file
     */
    private void loadGameFromSaveFile() {
        loadGameFromFile(getSaveFilename());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scoreboardManager = new GameScoreboardManager(this);
        userManager = new UserManager(this);

        loadGameFromTempFile();

        createTileButtons(this);
        setContentView(R.layout.activity_main);

        setUpGridView();
    }

    /**
     * Set up gridView and the associated board observers
     */
    protected void setUpGridView() {
        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardManager.getBoard().getSideLength());
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardManager.getBoard().getSideLength();
                        columnHeight = displayHeight / boardManager.getBoard().getSideLength();

                        display();
                    }
                });
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != board.getSideLength(); row++) {
            for (int col = 0; col != board.getSideLength(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / board.getSideLength();
            int col = nextPos % board.getSideLength();
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        // If the board has been solved, record the score
        if (boardManager.hasScore()) {
            scoreboardManager.addScoreForGame(boardManager.getScore());
        }
        saveGameToTempFile();
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadGameFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (BoardManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveGameToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Save the board manager to the save file
     */
    public void saveGameToTempFile() {
        saveGameToFile(getTempSaveFilename());
    }

    /**
     * Save the board manager to the temp file
     */
    public void saveGameToSaveFile() {
        saveGameToFile(getSaveFilename());
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}
