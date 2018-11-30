package fall18project.gamecentre.minesweeper;

import android.app.Application;

import com.squareup.otto.Bus;

public class MainApp extends Application {

    /**
     * A bus to share data between components.
     */
    private static Bus gameBus;

    /**
     * An instance of the application.
     */
    private static MainApp appInstance;

    /**
     * A getter for the game bus.
     * @return gameBus
     */
    public static Bus getGameBus(){
        if(gameBus == null){
            gameBus = new Bus();
        }
        return gameBus;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        appInstance = this;
    }
}
