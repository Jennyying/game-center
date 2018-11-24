package JumpingRabbit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import fall18project.gamecentre.R;


public class GameOver extends AppCompatActivity {
    private int score;
    TextView textScore;
    Button mainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        //display score
        score = GameActivity.getGameView().getScore();
        textScore = (TextView) findViewById(score);
        String message = "Your score is" + Integer.toString(score);
        textScore.setText(message);

        addMainButtonListener();

    }

    private void addMainButtonListener() {
        Button mainMenu = findViewById(R.id.menu);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMainMenu();
            }
        });
    }

    private void switchToMainMenu() {
        Intent tmp = new Intent(this, MainActivity.class);
        startActivity(tmp);
    }
}
