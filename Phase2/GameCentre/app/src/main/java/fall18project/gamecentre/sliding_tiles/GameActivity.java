package fall18project.gamecentre.sliding_tiles;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import fall18project.gamecentre.R;
import fall18project.gamecentre.game_management.GameScoreboardManager;
import fall18project.gamecentre.user_management.UserManager;
import fall18project.gamecentre.utilities.ImageSplit;

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
     * A handle for the undo button
     */
    @Bind(R.id.undo_button)
    protected Button undoButton;

    /**
     * A handle for the redo button
     */
    @Bind(R.id.redo_button)
    protected Button redoButton;

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

    /**
     * A list of BitmapDrawables to display on the buttons
     */
    private ArrayList<BitmapDrawable> bitmapDrawables;

    /**
     * Grid view
     */
    private GestureDetectGridView gridView;

    /**
     * Calculated column height and width
     */
    private static int columnWidth, columnHeight;

    /**
     * The image data to unscramble. If null, we use the default tile backgrounds
     */
    private Bitmap imageData = null;

    /**
     * Return the temporary filename to save games to
     *
     * @param cn the current user name
     * @return the temporary filename to save games to
     */
    public static String getTempSaveFilename(String cn) {
        return TEMP_SAVE_FILENAME + cn + ".ser";
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
     * Load the game from the temp file
     */
    private void loadGameFromTempFile() {
        loadGameFromFile(getTempSaveFilename());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri imageDataUri = getIntent().getData();
        if(imageDataUri != null) {
            /*
                Based off code by zzmilanzz zzmadubashazz found at
                https://stackoverflow.com/questions/3879992/how-to-get-bitmap-from-an-uri
             */
            try {
                imageData = MediaStore.Images.Media.getBitmap(getContentResolver(), imageDataUri);
            } catch(Exception e) {
                Log.e("loading image for board", e.toString());
                imageData = null;
            }
        } else {
            Log.v("loading image for board","loading default tile icons");
        }

        scoreboardManager = new GameScoreboardManager(this);
        userManager = new UserManager(this);

        loadGameFromTempFile();

        createTileButtons();
        setContentView(R.layout.activity_main);

        setUpGridView();

        ButterKnife.bind(this);

        setUpRedoButton();
        setUpUndoButton();
    }

    /**
     * Set up gridView and the associated board observers
     */
    private void setUpGridView() {
        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardManager.getBoard().getSideLength());
        gridView.setBoardManager(boardManager);
        gridView.setOnUpdate(new Runnable() {
            public void run() {
                updateText();
            }
        });
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
     * Update the text on the undo button to show the number of undos currently remaining
     */
    private void updateUndoButtonText() {
        String undoButtonText = getString(R.string.text_undo_button, boardManager.getMoves());
        undoButton.setText(undoButtonText);
    }

    /**
     * Update the text on the redo button to show the number of redos currently remaining
     */
    private void updateRedoButtonText() {
        String redoButtonText = getString(R.string.text_redo_button, boardManager.getUndid());
        redoButton.setText(redoButtonText);
    }

    /**
     * Update the text on the undo and redo buttons to show the current number of undos and redos.
     */
    private void updateText() {
        updateUndoButtonText();
        updateRedoButtonText();
    }

    /**
     * Set up the undo button to perform an undo
     */
    private void setUpUndoButton() {
        updateUndoButtonText();
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager.undo();
                updateText();
            }
        });
    }

    /**
     * Set up the redo button to perform a redo
     */
    private void setUpRedoButton() {
        updateRedoButtonText();
        redoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager.redo();
                updateText();
            }
        });
    }

    /**
     * Create the buttons to use to display the tiles
     */
    private void createTileButtons() {
        Board board = boardManager.getBoard();

        tileButtons = new ArrayList<>(board.numTiles());
        for(int i = 0; i < board.numTiles(); i++) tileButtons.add(new Button(this));

        if(imageData != null) {
            bitmapDrawables = ImageSplit.split(this, imageData, board.getSideLength());
        }

        updateTileButtons();
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();

        // Sanity checks for array sizes, to aid debugging
        if(bitmapDrawables != null && bitmapDrawables.size() != board.numTiles()) {
            throw new RuntimeException(
                    "Expected " + board.numTiles()
                            + " tile drawables, but got "
                            + bitmapDrawables.size());
        }


        for(int i = 0; i < tileButtons.size(); i++) {
            Button tmp = tileButtons.get(i);
            if(board.isBlank(i)) {
                // Remove background to indicate this tile is blank
                tmp.setBackground(null);
            } else if(bitmapDrawables == null) {
                tmp.setBackgroundResource(board.getTile(i).getBackground());
            } else {
                tmp.setBackground(bitmapDrawables.get(board.getTile(i).getId()));
            }
            tileButtons.set(i, tmp);
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
     * Save the board manager to the temp file
     */
    public void saveGameToTempFile() {
        saveGameToFile(getTempSaveFilename());
    }


    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}
