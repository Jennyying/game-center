package fall18project.gamecentre.sliding_tiles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
        userManager = new UserManager(this);
        boardManager = new BoardManager(getCurrentComplexity(), userManager.loadCurrentUserName());
        saveGameToFile(GameActivity.getSaveFilename(userManager.loadCurrentUserName()));

        Intent tmp = new Intent(this, GameActivity.class);
        startActivity(tmp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);

        complexityBar = (SeekBar) findViewById(R.id.seekBar);
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
        if (requestCode == PICK_PHOTO_FOR_PUZZLE && resultCode == Activity.RESULT_OK) {
            if (data == null || data.getData() == null) return;
            try {
                InputStream is = getApplicationContext().getContentResolver().
                        openInputStream(data.getData());
            } catch (FileNotFoundException f) {
                f.printStackTrace();
            }
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
