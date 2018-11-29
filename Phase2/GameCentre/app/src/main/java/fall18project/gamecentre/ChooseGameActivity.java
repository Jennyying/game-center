package fall18project.gamecentre;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * An activity for choosing from a list of games in the game centre
 */
public class ChooseGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);
    }
}
