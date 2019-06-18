package wenran.com.module.home.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import wenran.com.baselibrary.dao.CourseInfo;
import wenran.com.baselibrary.dao.PlayerStatusEnum;
import wenran.com.baselibrary.help.SomeData;
import wenran.com.baselibrary.interfaces.IPlayer;
import wenran.com.baselibrary.utils.MyToast;
import wenran.com.baselibrary.utils.RouterUtil;
import wenran.com.module.home.R;

/**
 * 课程详情介绍中的播放按钮控制
 * <p>
 * Created by Crowhine on 2019/4/22
 *
 * @author Crowhine
 */
public class CourseExplainPlayControl implements View.OnClickListener {
    protected Context context;
    CourseInfo courseInfo;
    /**
     * 播放按钮
     */
    ImageView imageViewPlayer;

    /**
     * 当前播放id
     */
    protected int currentPlayId = -1;
    /**
     * 课程id
     */
    private int courseId;
    protected PlayerStatusEnum playerStatus = PlayerStatusEnum.PAUSE_STATUS;

    public CourseExplainPlayControl(Context context, CourseInfo courseInfo, ImageView imageViewPlayer) {
        this.context = context;
        this.courseInfo = courseInfo;
        if (courseInfo != null) {
            courseId = courseInfo.getCourseId();
        }
        this.imageViewPlayer = imageViewPlayer;
        imageViewPlayer.setOnClickListener(this);
        init();
    }

    private void init() {
        getCurrentPlayIdForInit();
        getCurrentPlayStatus();
        updateUi();
    }

    /**
     * 初始化时，获取当前播放的课程id
     */
    protected void getCurrentPlayIdForInit() {
        Integer currentPlayId = RouterUtil.getIPlayer().getCurrentPlayId();
        if (currentPlayId != null && currentPlayId != -1) {
            this.currentPlayId = currentPlayId;
        }
    }

    /**
     * 初始化时，获取当前播放的状态
     */
    protected void getCurrentPlayStatus() {
        PlayerStatusEnum playerStatus = RouterUtil.getIPlayer().getPlayerStatus();
        this.playerStatus = playerStatus;
    }

    /**
     * 是否注册eventBus
     *
     * @param isRegister
     */
    public void registerEventBus(boolean isRegister) {
        if (isRegister) {
            boolean registered = EventBus.getDefault().isRegistered(this);
            if (registered == false) {
                EventBus.getDefault().register(this);
            }
        } else {
            EventBus.getDefault().unregister(this);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SomeData event) {
        if (event != null) {
            String playStatus = PlayerStatusEnum.PLAYING_STATUS.name();
            String pauseStatus = PlayerStatusEnum.PAUSE_STATUS.name();
            String completionStatus = PlayerStatusEnum.COMPLETION_STATUS.name();
            if (playStatus.equals(event.getTag())) {
                //播放
                Integer t = (Integer) event.getT();
                if (t != null) {
                    currentPlayId = t;
                }
                this.playerStatus = PlayerStatusEnum.PLAYING_STATUS;
                updateUi();
            } else if (pauseStatus.equals(event.getTag())) {
                //暂停
                currentPlayId = -1;
                this.playerStatus = PlayerStatusEnum.PAUSE_STATUS;
                updateUi();
            } else if (completionStatus.equals(event.getTag())) {
                //完成
                this.playerStatus = PlayerStatusEnum.COMPLETION_STATUS;
                updateUi();
            }
        }
    }

    /**
     * 获取当前的播放器和课程信息后，更新ui
     */
    private void updateUi() {
        switch (playerStatus) {

            case PLAYING_STATUS:
                //播放
                updateUiForPlayStatus();
                break;
            case PAUSE_STATUS:
                //暂停
                updateUiForPauseStatus();
                break;
            case COMPLETION_STATUS:
                //完成
                updateUiForCompletionStatus();
                break;
            default:
                break;
        }
    }


    /**
     * 更新ui为播放状态
     */
    private void updateUiForPlayStatus() {
        if (isPlayCurrentCourse()){
            imageViewPlayer.setImageResource(R.mipmap.course_explain_pasue);
        }
    }

    /**
     * 更新ui为暂停状态
     */
    private void updateUiForPauseStatus() {
        imageViewPlayer.setImageResource(R.mipmap.course_explain_play);
    }

    /**
     * 更新ui为完成状态
     */
    private void updateUiForCompletionStatus() {
        imageViewPlayer.setImageResource(R.mipmap.course_explain_play);
    }

    /**
     * 是否操作的课程和正在播放的课程id相同
     */
    private boolean isPlayCurrentCourse() {
        if (courseId == currentPlayId) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (currentPlayId == courseId&&this.playerStatus==PlayerStatusEnum.PLAYING_STATUS) {
            //做暂停操作
            MyToast.s(context, "暂停", 0);
            IPlayer navigation =RouterUtil.getIPlayer();
            navigation.pause();
        } else {
            //做播放操作
            MyToast.s(context, "播放", 0);
            IPlayer navigation = RouterUtil.getIPlayer();
            try {
                navigation.play(courseInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
