package wenran.com.baselibrary.interfaces;

import java.io.IOException;

import wenran.com.baselibrary.dao.CourseInfo;
import wenran.com.baselibrary.dao.PlayerStatusEnum;

/**
 * Created by Crowhine on 2019/4/10
 *
 * @author Crowhine
 */
public interface IPlayer {

    /**
     * 播放
     *
     * @param courseInfo * @throws IOException
     */
    void play(CourseInfo courseInfo) throws IOException;

    /**
     * 暂停
     */
    void pause();


    /**
     * 快进
     *
     * @param second
     */
    void speed(Integer second);

    /**
     * 快退
     *
     * @param second
     */
    void back(Integer second);

    /**
     * 拖着到
     *
     * @param progress
     */
    void seek(int progress);

    /**
     * 获取播放状态
     *
     * @return
     */
    PlayerStatusEnum getPlayerStatus();

    /**
     * 设置播放状态
     *
     * @param playerStatus
     * @return
     */
    void setPlayerStatus(PlayerStatusEnum playerStatus);


    /**
     * 获取播放是否隐藏状态
     *
     * @return
     */
    PlayerStatusEnum getPlayerShowStatus();

    /**
     * 设置播放否隐藏状态
     *
     * @param playerStatus
     * @return
     */
    void setPlayerShowStatus(PlayerStatusEnum playerStatus);

    /**
     * 获取当前播放的课程信息
     *
     * @param iObtainCourseCallback
     * @return
     */
    CourseInfo getCurrentCourse(IObtainCourseCallback<CourseInfo> iObtainCourseCallback);

    /**
     * 获取课程id
     *
     * @return
     */
    Integer getCurrentPlayId();

    /**
     * 获取总的时长
     *
     * @param iObtainCourseCallback
     * @return
     */
    void getDuration(IObtainCourseCallback iObtainCourseCallback);

    /**
     * 获取当前播放进度
     *
     * @param iObtainCourseCallback
     * @return
     */
    void getCurrentProgress(IObtainCourseCallback iObtainCourseCallback);

    interface IRouterPlayerService {
        /**
         * 点击了播放或暂停
         *
         * @param courseInfo
         * @throws IOException
         */
        void playOrPause(CourseInfo courseInfo) throws IOException;


    }

    interface IDestroyService {
        /**
         * 销毁服务
         */
        void destroyPlayerService();
    }

}
