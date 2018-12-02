package fall18project.gamecentre.sliding_tiles;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;

import fall18project.gamecentre.R;
import fall18project.gamecentre.user_management.UserManager;

public class GameSetupActivity extends AppCompatActivity {

    /*
     Photo choosing code adapted from https://gist.github.com/bunjix/7bcf36633e11f787215e by bunjix
     */
    private static final int PICK_PHOTO_FOR_PUZZLE = 0;

    private BoardManager boardManager;

    private UserManager userManager;

    private SeekBar complexityBar;
    private Uri imageSelected = null;

    private ImageView imageSelector;

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
        boardManager = new BoardManager(getCurrentComplexity(), userManager.loadCurrentUserName());
        saveGameToFile(GameActivity.getTempSaveFilename(userManager.loadCurrentUserName()));

        Intent tmp = new Intent(this, GameActivity.class);
        if(imageSelected != null) {
            tmp.setData(imageSelected);
        }
        startActivity(tmp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);

        userManager = new UserManager(this);
        complexityBar = (SeekBar) findViewById(R.id.seekBar);
        addPlayButtonListener();
        addImageSelector();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_PHOTO_FOR_PUZZLE) {
            if(resultCode == RESULT_OK) {
                imageSelected = data.getData();
                imageSelector.setImageURI(imageSelected);
            }
        }
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

    /**
     * Activate the image selector
     */
    private void addImageSelector() {
        imageSelector = findViewById(R.id.imageSelected);
        imageSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
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
