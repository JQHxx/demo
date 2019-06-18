package wenran.com.module_player.service;

import android.content.Context;

import wenran.com.baselibrary.dao.CourseInfo;
import wenran.com.baselibrary.dao.PlayerStatusEnum;
import wenran.com.baselibrary.interfaces.IObtainCourseCallback;
import wenran.com.baselibrary.utils.EventBusUtil;
import wenran.com.baselibrary.utils.SpUtil;
import wenran.com.module_player.dao.PlayerDaoControl;

/**
 * Created by Crowhine on 2019/4/18
 *
 * @author Crowhine
 */
public class PlayerModel {
    private Context context;
    private final String playerStatusTag = "playerStatusTag";
    private final String playerShowStatusTag = "playerShowStatusTag";
    private final String finalCourseIdTag = "finalCourseIdTag";
    private final int defaultCourseId = -1;

    public PlayerModel(Context context) {
        this.context = context;
    }

    /**
     * 播放一个新音频或服务销毁，存储上一个课程信息
     *
     * @param needSaveCourse
     */
    public void savePreviousCourse(CourseInfo needSaveCourse) {
        PlayerDaoControl.addHistorySearchData(context, needSaveCourse,null);
    }

    /**
     * 播放一个新音频或服务销毁，存储上一个课程信息
     *
     * @param needSaveCourse
     */
    public void savePreviousCourse(CourseInfo needSaveCourse,IObtainCourseCallback iObtainCourseCallback) {
        PlayerDaoControl.addHistorySearchData(context, needSaveCourse,iObtainCourseCallback);
    }

    /**
     * 关于状态改变的操作
     */
    public void doSthAboutStatusChange(PlayerStatusEnum playerStatus, CourseInfo currentPlayingCourse) {
        if (playerStatus == PlayerStatusEnum.PLAYING_STATUS) {
            if (currentPlayingCourse != null) {
                int courseId = currentPlayingCourse.getCourseId();
                EventBusUtil.doSerializableEvent(playerStatus.name(), courseId, false);
            }
        } else {
            EventBusUtil.doSerializableEvent(playerStatus.name(), null, false);
        }
    }

    /**
     * 关于状态改变的操作
     */
    public void doSthAboutShowStatusChange(PlayerStatusEnum playerStatus) {
        EventBusUtil.doSerializableEvent(playerStatus.name(), null, false);
    }

    /**
     * 获取存储的课程
     */
    public void getSavedCourse(IObtainCourseCallback iObtainCourseCallback) {
        int finalCourseId = getFinalCourseId();
        if (finalCourseId == defaultCourseId) {
            iObtainCourseCallback.callback(null);
        } else {
            PlayerDaoControl.queryCourseInfo(context, finalCourseId, iObtainCourseCallback);
        }
    }

    /**
     * 获取当前课程总进度
     */
    public void getDuration(final IObtainCourseCallback iObtainCourseCallback) {
        int finalCourseId = getFinalCourseId();
        if (finalCourseId == defaultCourseId) {
            //没有存储
            iObtainCourseCallback.callback(-1);
        } else {
            PlayerDaoControl.queryCourseInfo(context, finalCourseId, new IObtainCourseCallback<CourseInfo>() {
                @Override
                public void callback(CourseInfo courseInfo) {
                    if (courseInfo == null||courseInfo.getAudioLength()==0) {
                        iObtainCourseCallback.callback(-1);
                    } else {
                        iObtainCourseCallback.callback(courseInfo.getAudioLength());
                    }
                }
            });
        }
    }

    /**
     * 获取当前课程播放进度
     */
    public void getCurrentPlayProgress(final IObtainCourseCallback iObtainCourseCallback) {
        int finalCourseId = getFinalCourseId();
        if (finalCourseId == defaultCourseId) {
            //没有存储
            iObtainCourseCallback.callback(-1);
        } else {
            PlayerDaoControl.queryCourseInfo(context, finalCourseId, new IObtainCourseCallback<CourseInfo>() {
                @Override
                public void callback(CourseInfo courseInfo) {
                    if (courseInfo == null) {
                        iObtainCourseCallback.callback(-1);
                    } else {
                        iObtainCourseCallback.callback(courseInfo.getCurrentPlayLength());
                    }
                }
            });
        }
    }

    /**
     * 获取需要播放的课程进度
     */
    public void queryDataBaseForPreviousProgress(int courseId, IObtainCourseCallback iObtainCourseCallback) {
        PlayerDaoControl.queryCoursePlayProgress(context, courseId, iObtainCourseCallback);
    }

    /**
     * 获取快进的数值
     *
     * @param duration
     * @param currentPosition
     * @param defaultValue
     */
    public int getSpeedValue(int duration, int currentPosition, int defaultValue) {
        defaultValue = defaultValue * 1000;
        if (duration == -1 || currentPosition == -1) {
            //没有初始化播放
            return -1;
        }
        if (currentPosition + defaultValue <= duration) {
            return currentPosition + defaultValue;
        } else {
            //快进的默认值加当前位置大于总长度,返回总长度
            return duration;
        }
    }

    /**
     * 获取快退的数值
     *
     * @param currentPosition
     * @param defaultValue
     */
    public int getBackValue(int currentPosition, int defaultValue) {
        defaultValue = defaultValue * 1000;
        if (currentPosition == -1) {
            //没有初始化播放
            return -1;
        }
        if (currentPosition - defaultValue >= 0) {
            //正常值
            return currentPosition - defaultValue;
        } else {
            //回到原点
            return 0;
        }
    }

    /**
     * 存储播放器状态
     */
    public void savePlayerStatus(PlayerStatusEnum playerStatusEnum) {
        if (playerStatusEnum.equals(PlayerStatusEnum.PLAYING_STATUS)) {
            //关闭app，如果正在播放，把状态存储成暂停
            playerStatusEnum = PlayerStatusEnum.PAUSE_STATUS;
        }
        int currentStatus = playerStatusEnum.getCurrentStatus();
        SpUtil.putInt(context, playerStatusTag, currentStatus);
    }


    /**
     * 获取播放器状态
     */
    public PlayerStatusEnum getPlayerStatus() {
        PlayerStatusEnum playerStatusEnum = PlayerStatusEnum.CANCEL_STATUS;
        int anInt = SpUtil.getInt(context, playerStatusTag, PlayerStatusEnum.PAUSE_STATUS.getCurrentStatus());
        if (anInt == PlayerStatusEnum.PLAYING_STATUS.getCurrentStatus()) {
            playerStatusEnum = PlayerStatusEnum.PLAYING_STATUS;
        } else if (anInt == PlayerStatusEnum.PAUSE_STATUS.getCurrentStatus()) {
            playerStatusEnum = PlayerStatusEnum.PAUSE_STATUS;
        } else if (anInt == PlayerStatusEnum.COMPLETION_STATUS.getCurrentStatus()) {
            playerStatusEnum = PlayerStatusEnum.COMPLETION_STATUS;
        }
        return playerStatusEnum;
    }


    /**
     * 存储播放器Show状态
     */
    public void savePlayerShowStatus(PlayerStatusEnum playerShowStatusEnum) {
        int currentStatus = playerShowStatusEnum.getCurrentStatus();
        SpUtil.putInt(context, playerShowStatusTag, currentStatus);
    }


    /**
     * 获取播放器Show状态
     */
    public PlayerStatusEnum getPlayerShowStatus() {
        PlayerStatusEnum playerStatusEnum = PlayerStatusEnum.HIDE_PLAYER;
        int anInt = SpUtil.getInt(context, playerShowStatusTag, PlayerStatusEnum.HIDE_PLAYER.getCurrentStatus());
        if (anInt == PlayerStatusEnum.HIDE_PLAYER.getCurrentStatus()) {
            playerStatusEnum = PlayerStatusEnum.HIDE_PLAYER;
        } else if (anInt == PlayerStatusEnum.SHOW_PLAYER.getCurrentStatus()) {
            playerStatusEnum = PlayerStatusEnum.SHOW_PLAYER;
        }
        return playerStatusEnum;
    }

    /**
     * 存储最后播放的课程id
     */
    public void saveFinalCourseId(int courseId) {
        SpUtil.putInt(context, finalCourseIdTag, courseId);
    }

    /**
     * 获取最后播放的课程id
     */
    public int getFinalCourseId() {
        return SpUtil.getInt(context, finalCourseIdTag, defaultCourseId);
    }

    public void doSthForCompletion() {
        //
    }
}
