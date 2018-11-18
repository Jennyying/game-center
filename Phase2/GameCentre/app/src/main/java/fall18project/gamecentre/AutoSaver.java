package fall18project.gamecentre;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class AutoSaver {
    /**
     *  The GameActivity object associated with this game
     */
    private GameActivity gameActivity;

    /**
     * The rate in milliseconds at which the game saves
     */
    private static int saveInterval = 500;

    /**
     * Initiates a AutoSaver object to save at regular intervals
     *
     * @param gameActivity - The GameActivity object assosiated with this game
     */
    public AutoSaver(GameActivity gameActivity) {this.gameActivity = gameActivity;
    start(saveInterval);
    }

    /**
     * Calls the save() method after each given interval has elapsed.
     *
     * @param interval - The interval at which to save in milliseconds
     */
    public void start(long interval)
    {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                AutoSaver.this.save();
            }
        }, interval, interval);
    }

    /**
     * Saves the game when called.
     */
    private void save(){
        gameActivity.saveGameToFile(StartingActivity.SAVE_FILENAME);
        gameActivity.saveGameToFile(StartingActivity.TEMP_SAVE_FILENAME);
    }
}
