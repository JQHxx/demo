package wenran.com.module_player.service;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import wenran.com.baselibrary.dao.CourseInfo;
import wenran.com.baselibrary.dao.PlayerStatusEnum;
import wenran.com.baselibrary.help.SomeData;
import wenran.com.baselibrary.interfaces.IObtainCourseCallback;
import wenran.com.baselibrary.interfaces.IPlayer;
import wenran.com.baselibrary.utils.GlideUtil;
import wenran.com.baselibrary.utils.PhoneUtil;
import wenran.com.baselibrary.utils.RouterUtil;
import wenran.com.baselibrary.utils.StringUtil;
import wenran.com.baselibrary.utils.TimeUtils;
import wenran.com.module_player.R;
import wenran.com.module_player.R2;

/**
 * Created by Crowhine on 2019/4/22
 *
 * @author Crowhine
 * <p>
 * 音频播放器
 */
public class AudioPlayerBinder implements SeekBar.OnSeekBarChangeListener {
    private View mRootView;
    private Unbinder unbinder;
    @BindView(R2.id.player_ll_all)
    LinearLayout playerLlAll;
    @BindView(R2.id.player_pb_progress)
    SeekBar playerSbProgress;
    @BindView(R2.id.player_iv_cancel)
    ImageView playerIvCancel;
    @BindView(R2.id.player_ll_content)
    LinearLayout getPlayerLlContent;
    @BindView(R2.id.player_iv_img)
    ImageView playerIvImg;
    @BindView(R2.id.player_tv_title)
    TextView playerTvTitle;
    @BindView(R2.id.player_tv_current_time)
    TextView playerTvCurrentTime;
    @BindView(R2.id.player_tv_total_time)
    TextView playerTvTotalTime;


    @BindView(R2.id.player_iv_fast_backward)
    ImageView playerIvFastBackward;
    @BindView(R2.id.player_iv_play_or_pause)
    ImageView playerIvPlayOrPause;
    @BindView(R2.id.player_iv_speed)
    ImageView playerIvSpeed;


    private CourseInfo currentPlayingCourse;
    private PlayerStatusEnum playerStatus = PlayerStatusEnum.PAUSE_STATUS;
    private PlayerStatusEnum isShowPlayer = PlayerStatusEnum.HIDE_PLAYER;
    private static final String TAG = AudioPlayerBinder.class.getSimpleName();
    private final Context context;
    private ObtainProgress obtainProgress;
    private ObjectAnimator mRootHideAnimator;
    private ObjectAnimator mRootShowAnimator;
    /**
     * 当前滑动的进度
     */
    private int currentProgress;

    public AudioPlayerBinder(View mRootView) {
        this.mRootView = mRootView;
        context = mRootView.getContext();
        init();
    }

    private void init() {
        unbinder = ButterKnife.bind(this, mRootView);
        createAnim();
        obtainProgress = new ObtainProgress(new IObtainCourseCallback<Integer>() {
            @Override
            public void callback(Integer courseInfo) {
                if (courseInfo != -1) {
                    playerSbProgress.setProgress(courseInfo);
                    playerTvCurrentTime.setText(TimeUtils.getDetailTime2(courseInfo / 1000));
                }
            }
        });
        playerSbProgress.setOnSeekBarChangeListener(this);
        getCurrentPlayShowStatus();
        getNewData(true);
    }


    /**
     * 初始化title和图片等
     */
    private void initContent() {
        if (currentPlayingCourse != null) {
            playerTvTitle.setText(currentPlayingCourse.getTitle());
            playerTvTotalTime.setText("/" + TimeUtils.getDetailTime2(currentPlayingCourse.getAudioLength() / 1000));
            if (StringUtil.isEmptyStr(currentPlayingCourse.getImgUrl())) {
                GlideUtil.setRoundedCornersImageByRes(context, R.mipmap.app_icon, playerIvImg,
                        PhoneUtil.Dp2px(context, 10), 0, RoundedCornersTransformation.CornerType.ALL, null, null);
            } else {
                GlideUtil.setRoundedCornersImageByUrl(context, currentPlayingCourse.getImgUrl(), playerIvImg,
                        PhoneUtil.Dp2px(context, 10), 0, RoundedCornersTransformation.CornerType.ALL, null, null);
            }
        }
    }


    @OnClick({R2.id.player_ll_all, R2.id.player_iv_cancel
            , R2.id.player_iv_fast_backward, R2.id.player_iv_play_or_pause
            , R2.id.player_iv_speed})
    public void onViewClicked(View view) {
        IPlayer navigation =RouterUtil.getIPlayer();
        if (view.getId() == R.id.player_ll_all) {
            return;
        } else if (view.getId() == R.id.player_iv_cancel) {
            //取消，隐藏
            navigation.setPlayerShowStatus(PlayerStatusEnum.HIDE_PLAYER);
        } else if (view.getId() == R.id.player_iv_fast_backward) {
            //快退
            navigation.back(null);
        } else if (view.getId() == R.id.player_iv_play_or_pause) {
            if (playerStatus.equals(PlayerStatusEnum.PLAYING_STATUS)) {
                //暂停
                navigation.pause();
            } else {
                try {
                    //播放
                    navigation.play(currentPlayingCourse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (view.getId() == R.id.player_iv_speed) {
            //快进
            navigation.speed(null);
        }
    }

    /**
     * 获取最新的数据
     *
     * @param isNeedObtainData
     */
    private void getNewData(boolean isNeedObtainData) {
        if (isNeedObtainData) {
            getCurrentPlayStatus();
            getCurrentPlayingCourse();
        } else {
            updateUi();
        }
    }


    /**
     * 注册
     */
    public void doResume() {
        registerEventBus(true);
        getCurrentPlayShowStatus();
        getNewData(true);
    }

    /**
     * 销毁注册
     */
    public void doPause() {
        obtainProgress.setNotify(false);
        registerEventBus(false);
    }

    public void doDestroy() {
        if (null != unbinder) {
            unbinder.unbind();
        }
        if (obtainProgress != null) {
            obtainProgress.doDestroy();
            obtainProgress = null;
        }
    }

    /**
     * 初始化时，获取当前播放的课程id
     */
    protected void getCurrentPlayingCourse() {
        RouterUtil.getIPlayer().getCurrentCourse(new IObtainCourseCallback<CourseInfo>() {
            @Override
            public void callback(CourseInfo courseInfo) {
                currentPlayingCourse = courseInfo;
                //是否可以滑动进度
                if (currentPlayingCourse == null) {
                    setProgressEnable(false);
                } else {
                    setProgressEnable(true);
                    initContent();
                }
                updateUi();
            }
        });
    }

    /**
     * 初始化时，获取当前播放的状态
     */
    protected void getCurrentPlayStatus() {
        PlayerStatusEnum playerStatus = RouterUtil.getPlayerStatus();
        if (!this.playerStatus.equals(playerStatus)) {
            this.playerStatus = playerStatus;
        }
    }

    /**
     * 初始化时，获取当前播放的show状态
     */
    protected void getCurrentPlayShowStatus() {
        PlayerStatusEnum playerStatus = RouterUtil.getPlayerShowStatus();
        if (!isShowPlayer.equals(playerStatus)) {
            this.isShowPlayer = playerStatus;
            updateUiForIsShowPlayer();
        }
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
            String hidePlayer = PlayerStatusEnum.HIDE_PLAYER.name();
            String showPlayer = PlayerStatusEnum.SHOW_PLAYER.name();
            if (playStatus.equals(event.getTag())) {
                //播放
                this.playerStatus = PlayerStatusEnum.PLAYING_STATUS;
                getNewData(true);
            } else if (pauseStatus.equals(event.getTag())) {
                //暂停
                this.playerStatus = PlayerStatusEnum.PAUSE_STATUS;
                getNewData(false);
            } else if (completionStatus.equals(event.getTag())) {
                //完成
                this.playerStatus = PlayerStatusEnum.COMPLETION_STATUS;
                getNewData(false);
            } else if (hidePlayer.equals(event.getTag())) {
                //隐藏
                setPlayerShowStatus(false);
            } else if (showPlayer.equals(event.getTag())) {
                //显示
                setPlayerShowStatus(true);
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
        setPlayerShowStatus(true);
        obtainProgress.setNotify(true);
        playerIvPlayOrPause.setImageResource(R.mipmap.player_pause);
    }

    /**
     * 更新ui为暂停状态
     */
    private void updateUiForPauseStatus() {
        obtainProgress.setNotify(false);
        playerIvPlayOrPause.setImageResource(R.mipmap.player_play);
    }

    /**
     * 更新ui为完成状态
     */
    private void updateUiForCompletionStatus() {
        setPlayerShowStatus(true);
        obtainProgress.setNotify(false);
        playerIvPlayOrPause.setImageResource(R.mipmap.player_play);
    }

    /**
     * 设置播放器的是否显示的状态
     *
     * @param isShow
     */
    private void setPlayerShowStatus(boolean isShow) {
        if (isShow == true && isShowPlayer == PlayerStatusEnum.SHOW_PLAYER) {
            return;
        } else if (isShow == false && isShowPlayer == PlayerStatusEnum.HIDE_PLAYER) {
            return;
        }
        if (isShow) {
            isShowPlayer = PlayerStatusEnum.SHOW_PLAYER;
        } else {
            isShowPlayer = PlayerStatusEnum.HIDE_PLAYER;
        }
        updateUiForIsShowPlayer();
    }

    /**
     * 更新ui为是否显示播放器
     */
    private void updateUiForIsShowPlayer() {
        if (isShowPlayer == PlayerStatusEnum.HIDE_PLAYER) {
            hideRootViewAnimator();
//            playerLlAll.setVisibility(View.GONE);
        } else {
//            playerLlAll.setVisibility(View.VISIBLE);
            showRootViewAnimator();
        }
    }

    /**
     * 实例化动画
     */
    private void createAnim() {
        mRootShowAnimator = ObjectAnimator.ofFloat(mRootView, "translationY", 200, 0);
        mRootShowAnimator.setDuration(300);
        mRootHideAnimator = ObjectAnimator.ofFloat(mRootView, "translationY", 0, 200);
        mRootHideAnimator.setDuration(300);
    }

    /**
     * 显示
     */
    public void showRootViewAnimator() {
        mRootShowAnimator.start();
    }

    /**
     * 隐藏
     */
    public void hideRootViewAnimator() {
        mRootShowAnimator.end();
        mRootHideAnimator.start();
    }

    /**
     * 设置是否可以滑动
     *
     * @param isEnable 如果课程为null，不能滑动
     */
    private void setProgressEnable(boolean isEnable) {
        playerSbProgress.setEnabled(isEnable);
        if (isEnable && currentPlayingCourse != null) {
            playerSbProgress.setMax(currentPlayingCourse.getAudioLength());
            obtainProgress.getCurrentProgress(new IObtainCourseCallback<Integer>() {
                @Override
                public void callback(Integer courseInfo) {
                    playerSbProgress.setProgress(courseInfo);
                    playerTvCurrentTime.setText(TimeUtils.getDetailTime2(courseInfo / 1000));
                }
            });

        }
    }


    /**
     * SeekBar滑动时间处理
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            currentProgress = progress;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (obtainProgress != null) {
            obtainProgress.setNotify(false);
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        IPlayer navigation =RouterUtil.getIPlayer();
        navigation.seek(currentProgress);
        Log.d("debug", "onStopTrackingTouch");
    }


}
