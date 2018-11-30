package fall18project.gamecentre.user_management;

import java.io.Serializable;

/**
 * A class for encapsulating a user's individual game settings (for things like whether to
 * display hints or play the intro)
 */
public class UserSettings implements Serializable {

    /**
     * Whether to show the user the introduction/tutorial screen on opening the game. See doc
     * for the enum ShowIntroductionStatus
     */
    private ShowIntroductionStatus showIntroduction = ShowIntroductionStatus.ONCE;

    /**
     * Check whether the introduction should be shown on opening the game
     *
     * @return whether the introduction should be shown
     */
    public boolean shouldShowIntroduction() {
        return showIntroduction == ShowIntroductionStatus.YES
                || showIntroduction == ShowIntroductionStatus.ONCE;
    }

    /**
     * To be called once the introduction is shown. Sets ONCE values for showIntroduction to NO,
     * since the introduction has now been shown once.
     */
    public void onShownIntroduction() {
        showIntroduction = ShowIntroductionStatus.NO;
    }

    /**
     * Set whether to show the introduction, not show the introduction, or just show it one more time
     *
     * @param status the value of ShowIntroductionStatus indicating your desired behaviour
     */
    public void setShowIntroduction(ShowIntroductionStatus status) {
        showIntroduction = status;
    }

    /**
     * Whether to show the introduction every time (yes), show the introduction once and not again
     * (once) or not to show the introduction (no)
     */
    public enum ShowIntroductionStatus implements Serializable {
        YES,
        NO,
        ONCE
    }

}
