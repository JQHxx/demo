package wenran.com.baselibrary.dao;

/**
 * Created by Crowhine on 2019/4/10
 *
 * @author Crowhine
 */
public enum PlayerStatusEnum {
    /**
     * 播放器状态，和点击操作
     */
    CANCEL_STATUS(0), PLAYING_STATUS(1), PAUSE_STATUS(2),COMPLETION_STATUS(3),
    CURRENT_PROGREE(007),
    SHOW_PLAYER(01),HIDE_PLAYER(02),
    ;

    private int currentStatus;

    PlayerStatusEnum(int currentStatus) {
        this.currentStatus = currentStatus;
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
    }

}
