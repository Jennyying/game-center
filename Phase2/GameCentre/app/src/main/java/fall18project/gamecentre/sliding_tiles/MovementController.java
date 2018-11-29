package fall18project.gamecentre.sliding_tiles;

import android.content.Context;
import android.widget.Toast;


public class MovementController {

    /**
     * Contains the boardmanager passed on by an instance of the game
     */
    private BoardManager boardManager = null;

    public MovementController() {
    }

    /** Initialises class and sets local boardmanage variable
     *
     * @param boardManager the boardmanage for this game
     */
    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /** Processes whether a tap is valid or is a winning tap and displays the relavent information
     * to the user
     *
     * @param context context of the action
     * @param position position of the tap
     * @param display display condition
     */
    public void processTapMovement(Context context, int position, boolean display) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
