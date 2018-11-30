package fall18project.gamecentre;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import fall18project.gamecentre.user_management.LoginManager;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Login manager unit tests
 */
public class LoginManagerTest {
    /**
     * The login manager to test, which will be existing only in RAM (and hence have null context)
     */
    LoginManager loginManager = new LoginManager((Context) null);

    @Before
    public void setUp() {
        loginManager.registerUser("Aragorn", "Narsil123");
        loginManager.registerUser("Gandalf", "Mithrandir");
        loginManager.registerUser("Frodo", "Precious");
        loginManager.registerUser("The Globgogabgelab", "Yeast");
        loginManager.registerUser("Meme Man", "Never Trust Orang");
    }

    @Test
    public void login() {
        assertEquals(
                loginManager.login("Aragorn", "Narsil123"),
                LoginManager.LoginStatus.LOGIN_GOOD);
        assertEquals(
                loginManager.login("Aragorn", "Narsil321"),
                LoginManager.LoginStatus.LOGIN_BAD_PASSWORD);
        assertEquals(
                loginManager.login("Araborn", "Narsil123"),
                LoginManager.LoginStatus.LOGIN_BAD_USERNAME);
    }

    @Test
    public void userExists() {
        assertTrue(loginManager.userExists("Aragorn"));
        assertTrue(loginManager.userExists("Gandalf"));
        assertFalse(loginManager.userExists("Smeagol"));
    }

    @Test
    public void getUserNames() {
        String[] userNameArray = {
                "Aragorn", "Gandalf", "Frodo", "The Globgogabgelab", "Meme Man"
        };

        /**
         * Array to set conversion with help from
         * https://stackoverflow.com/questions/3064423/how-to-convert-an-array-to-a-set-in-java
         */
        assertEquals(
                loginManager.getUserNames(),
                new HashSet<String>(Arrays.asList(userNameArray))
        );
    }

}
