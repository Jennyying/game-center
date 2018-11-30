package fall18project.gamecentre.minesweeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fall18project.gamecentre.R;

/**
 * the game activity
 */
public class GameActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_minesweeper);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
