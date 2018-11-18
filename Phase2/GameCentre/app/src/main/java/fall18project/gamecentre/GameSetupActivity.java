package fall18project.gamecentre;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GameSetupActivity extends AppCompatActivity {

    /*
     Photo choosing code adapted from https://gist.github.com/bunjix/7bcf36633e11f787215e by bunjix
     */

    private static final int PICK_PHOTO_FOR_PUZZLE = 0;

    private BoardManager boardManager;

    private SeekBar complexityBar;
    private boolean imageSelected = false;

    private int getCurrentComplexity() {
        return complexityBar.getProgress() + 2;
    }

    private void selectImage() {
        Intent choosePhoto = new Intent(Intent.ACTION_GET_CONTENT);
        choosePhoto.setType("image/*");
        startActivityForResult(choosePhoto, PICK_PHOTO_FOR_PUZZLE);
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        boardManager = new BoardManager(getCurrentComplexity(),
                getIntent().getExtras().getString("username"));
        saveGameToFile(StartingActivity.TEMP_SAVE_FILENAME);

        Intent tmp = new Intent(this, GameActivity.class);
        startActivity(tmp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);

        complexityBar = (SeekBar)findViewById(R.id.seekBar);

        addPlayButtonListener();
    }

    /**
     * Activate the play button.
     */
    private void addPlayButtonListener() {
        Button startButton = findViewById(R.id.playButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_PHOTO_FOR_PUZZLE && resultCode == Activity.RESULT_OK) {
            if(data == null || data.getData() == null) return;
            try {
                InputStream is = getApplicationContext().getContentResolver().
                        openInputStream(data.getData());
            } catch(FileNotFoundException f) {
                f.printStackTrace();
            }
        }
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
}
