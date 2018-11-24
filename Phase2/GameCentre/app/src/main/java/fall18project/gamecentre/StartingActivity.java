package fall18project.gamecentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {

    /**
     * The main save file.
     */
    public static final String SAVE_FILENAME = "save_file.ser";
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_tmp.ser";
    /**
     * The scoreboard save file
     */
    public static final String SCORE_SAVE_FILENAME = "score_file.ser";
    /**
     * The user database save file
     */
    public static final String USERS_SAVE_FILENAME = "users_file.ser";


    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The scoreboard manager.
     */
    private ScoreboardManager scoreboardManager;

    /**
     * The user database.
     */
    private UserDatabase userDatabase;

    /**
     * The current user
     */
    private User currentUser;

    /**
     * The username box
     */
    private EditText usernameBox;

    /**
     * The password box
     */
    private EditText passwordBox;

    /**
     * The current user view
     */
    private TextView currentUserView;

    //public static final String EXTRA_MESSAGE = "fall18project.gamecentre.MESSAGE"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadScoreFromFile(SCORE_SAVE_FILENAME);
        loadUsersFromFile(USERS_SAVE_FILENAME);
        saveScoresToFile(SCORE_SAVE_FILENAME);
        saveUsersToFile(USERS_SAVE_FILENAME);
        loadGameFromFile(SAVE_FILENAME);

        setContentView(R.layout.activity_starting_);

        usernameBox = (EditText)findViewById(R.id.usernameBox);
        passwordBox = (EditText)findViewById(R.id.passwordBox);
        currentUserView = (TextView)findViewById(R.id.currentUser);

        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
        addSignInButtonListener();
        addSignUpButtonListener();
        addScoreBoardButtonListener();
    }

    private void signUp() {
        String username = usernameBox.getText().toString();
        String password = passwordBox.getText().toString();

        if(username.isEmpty()) {
            Toast.makeText(this, "Please insert a username", Toast.LENGTH_SHORT).show();
            return;
        }

        if(userDatabase.register(username, password) == null) Toast.makeText(
                this,
                "An account with that username already exists",
                Toast.LENGTH_LONG).show();

        saveUsersToFile(USERS_SAVE_FILENAME);
    }

    private void addScoreBoardButtonListener() {
        Button ScoreBoardButton = findViewById(R.id.GameScoreBoardButton);
        ScoreBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScoreboard();
            }
        });
    }

    /**
     * Activate the SignIn button.
     */
    private void addSignInButtonListener() {
        Button SignInButton = findViewById(R.id.SignInButton);
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    /**
     * Activate the SignUp button.
     */
    private void addSignUpButtonListener() {
        Button SignUpButton = findViewById(R.id.SignUpButton);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSetup();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGameFromFile(SAVE_FILENAME);
                saveGameToFile(TEMP_SAVE_FILENAME);
                makeToastLoadedText();
                switchToGame();
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGameToFile(SAVE_FILENAME);
                saveGameToFile(TEMP_SAVE_FILENAME);
                makeToastSavedText();
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }
    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadGameFromFile(TEMP_SAVE_FILENAME);
        loadScoreFromFile(SCORE_SAVE_FILENAME);
        loadUsersFromFile(USERS_SAVE_FILENAME);
    }

    protected void onPause() {
        super.onPause();
        saveGameToFile(StartingActivity.TEMP_SAVE_FILENAME);
        saveScoresToFile(StartingActivity.SCORE_SAVE_FILENAME);
        saveUsersToFile(StartingActivity.USERS_SAVE_FILENAME);
    }

    private void login() {

        String username = usernameBox.getText().toString();
        String password = passwordBox.getText().toString();

        int userID;

        if(username.isEmpty())
            Toast.makeText(this, "Please input a username", Toast.LENGTH_SHORT).show();
        else switch(userID = userDatabase.login(username, password)) {
            case UserDatabase.LOGIN_BAD_PASSWORD:
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
                break;
            case UserDatabase.LOGIN_NO_SUCH_USER:
                Toast.makeText(this, "No such user", Toast.LENGTH_SHORT).show();
                break;
            default:
                currentUser = userDatabase.getUserWithID(userID);
                currentUserView.setText("Hey, " + currentUser.getUserName() + "!");
                break;
        }
    }
    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        saveGameToFile(StartingActivity.TEMP_SAVE_FILENAME);
        saveScoresToFile(StartingActivity.SCORE_SAVE_FILENAME);
        saveUsersToFile(StartingActivity.USERS_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Switch to the ScoreboardActivity view to view the scoreboard
     */
    private void switchToScoreboard() {
        Intent tmp = new Intent(this, ScoreboardActivity.class);
        saveGameToFile(StartingActivity.TEMP_SAVE_FILENAME);
        saveScoresToFile(StartingActivity.SCORE_SAVE_FILENAME);
        saveUsersToFile(StartingActivity.USERS_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Switch to the GameSetupActivity view to set up the game
     */
    private void switchToSetup() {
        Intent tmp = new Intent(this, GameSetupActivity.class);
        if(currentUser != null)
            tmp.putExtra("username", currentUser.getUserName());
        saveGameToFile(StartingActivity.TEMP_SAVE_FILENAME);
        saveScoresToFile(StartingActivity.SCORE_SAVE_FILENAME);
        saveUsersToFile(StartingActivity.USERS_SAVE_FILENAME);
        startActivity(tmp);
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
     * Load the scoreboard manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadScoreFromFile(String fileName) {
        scoreboardManager = new ScoreboardManager();

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                scoreboardManager = (ScoreboardManager) input.readObject();
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
     * Load the user database from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadUsersFromFile(String fileName) {
        userDatabase = new UserDatabase();

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                userDatabase = (UserDatabase) input.readObject();
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
     * Save the scoreboard to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveScoresToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(scoreboardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Save the user database to filename
     */
    public void saveUsersToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(userDatabase);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
