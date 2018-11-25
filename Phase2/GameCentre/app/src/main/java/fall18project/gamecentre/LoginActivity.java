package fall18project.gamecentre;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * An activity for logging in to an existing account
 */
public class LoginActivity extends AppCompatActivity {

    private UserDatabase userDatabase;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
