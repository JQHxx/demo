package wenran.com.module_player.service;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.Objects;

import wenran.com.baselibrary.dao.CourseInfo;
import wenran.com.baselibrary.dao.PlayerStatusEnum;
import wenran.com.baselibrary.interfaces.IObtainCourseCallback;
import wenran.com.baselibrary.interfaces.IPlayer;
import wenran.com.baselibrary.utils.MyToast;
import wenran.com.module_player.R;

/**
 * Created by Crowhine on 2019/4/11
 *
 * @author Crowhine
 */
public class AudioPlayer implements IPlayer,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnErrorListener {
    private int defaultBack = 10, defaultSpeed = 10;
    private MediaPlayer mediaPlayer;
    private String currentPlayingUrl;
    private PlayerStatusEnum playerStatusEnum = PlayerStatusEnum.CANCEL_STATUS;
    PlayerStatusEnum playerShowStatus;
    private CourseInfo currentPlayingCourse;
    private Context context;
    private PlayerModel playerModel;

    public AudioPlayer(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnPreparedListener(this);
        playerModel = new PlayerModel(context);

        playerStatusEnum = getPlayerStatus();
    }

    @Override
    public void play(CourseInfo courseInfo) throws IOException {
        if (courseInfo == null) {
            //课程为null，播放之前存储的，如果没有不做事情
            getCurrentCourse(new IObtainCourseCallback<CourseInfo>() {
                @Override
                public void callback(CourseInfo courseInfo) {
                    if (courseInfo != null) {
                        try {
                            play(courseInfo);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        MyToast.s(context, context.getString(R.string.play_error), 0);
                    }
                }
            });
        } else {
            String audioUrl = courseInfo.getAudioUrl();
            if (!TextUtils.isEmpty(audioUrl)) {
                if (Objects.equals(currentPlayingUrl, audioUrl)) {
                    if (mediaPlayer.isPlaying()) {
                        //已经在播放
                        return;
                    } else {
                        //暂停状态，继续播放
                        startPlay(false);
                    }
                } else {
                    //播放一个新的课程，或者播放完成后从头播放
                    CourseInfo needSaveCourse = courseInfo;
                    if (!playerStatusEnum.equals(PlayerStatusEnum.CANCEL_STATUS)) {
                        //不是第一次播放
                        savePreviousCourse(needSaveCourse, mediaPlayer.getCurrentPosition());
                    }
                    try {
                        currentPlayingUrl = audioUrl;
                        currentPlayingCourse = courseInfo;
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(audioUrl);
                        mediaPlayer.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    /**
     * 播放
     *
     * @param isQueryCourse 为true的话存储，并且查询数据库，获取需要播放的课程的进度
     */
    private void startPlay(boolean isQueryCourse) {
        if (isQueryCourse) {
            playerModel.queryDataBaseForPreviousProgress(currentPlayingCourse.getCourseId(), new IObtainCourseCallback<Integer>() {
                @Override
                public void callback(Integer needPlayCourseProgress) {
                    if (needPlayCourseProgress != null && needPlayCourseProgress != 0) {
                        seek(needPlayCourseProgress);
                    } else {
                        startPlay(false);
                    }
                }
            });
        } else {
            mediaPlayer.start();
            setPlayerStatus(PlayerStatusEnum.PLAYING_STATUS);
            setPlayerShowStatus(PlayerStatusEnum.SHOW_PLAYER);
        }
    }


    @Override
    public void pause() {
        mediaPlayer.pause();
        setPlayerStatus(PlayerStatusEnum.PAUSE_STATUS);
    }


    @Override
    public void speed(Integer second) {
        if (second == null) {
            second = defaultSpeed;
        }
        if (playerStatusEnum.equals(PlayerStatusEnum.CANCEL_STATUS)) {
            final Integer finalSecond = second;
            getCurrentCourse(new IObtainCourseCallback<CourseInfo>() {
                @Override
                public void callback(CourseInfo courseInfo) {
                    if (courseInfo == null) {
                        //app首次快进
                        MyToast.s(context,context.getString(R.string.play_error),0);
                    } else {
                        int speedValue = playerModel.getSpeedValue
                                (courseInfo.getAudioLength(), courseInfo.getCurrentPlayLength(), finalSecond);
                        if (speedValue == -1) {
                            try {
                                play(courseInfo);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            seek(speedValue);
                        }
                    }
                }
            });

        } else {
            int speedValue = playerModel.getSpeedValue(mediaPlayer.getDuration(), mediaPlayer.getCurrentPosition(), second);
            if (speedValue == -1) {
                try {
                    play(currentPlayingCourse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                seek(speedValue);
            }
        }
    }

    @Override
    public void back(Integer second) {
        if (second == null) {
            second = defaultSpeed;
        }
        if (playerStatusEnum.equals(PlayerStatusEnum.CANCEL_STATUS)) {
            final Integer finalSecond = second;
            getCurrentCourse(new IObtainCourseCallback<CourseInfo>() {
                @Override
                public void callback(CourseInfo courseInfo) {
                    if (courseInfo == null) {
                        //app首次快进
                        MyToast.s(context,context.getString(R.string.play_error),0);
                    } else {
                        int speedValue = playerModel.getBackValue
                                (courseInfo.getCurrentPlayLength(), finalSecond);
                        if (speedValue == -1) {
                            try {
                                play(courseInfo);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            seek(speedValue);
                        }
                    }
                }
            });

        } else {
            int speedValue = playerModel.getBackValue(mediaPlayer.getCurrentPosition(), second);
            if (speedValue == -1) {
                try {
                    play(currentPlayingCourse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                seek(speedValue);
            }
        }
    }

    /**
     * seek后需要更新的课程信息
     */
    CourseInfo needSaveCourseInfo;

    @Override
    public void seek(final int progress) {
        if (mediaPlayer == null) {
            return;
        }

        if (currentPlayingCourse == null) {
            //初始化，还没有播放歌曲
            getCurrentCourse(new IObtainCourseCallback<CourseInfo>() {
                @Override
                public void callback(final CourseInfo courseInfo) {
                    if (courseInfo != null) {
                        needSaveCourseInfo = courseInfo;
                        needSaveCourseInfo.setCurrentPlayLength(progress);
                        //更新课程进度
                        playerModel.savePreviousCourse(needSaveCourseInfo, new IObtainCourseCallback() {
                            @Override
                            public void callback(Object courseInfo2) {
                                try {
                                    play(courseInfo);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        MyToast.s(context, context.getString(R.string.play_error), 0);
                    }
                }
            });
            Log.d("debug", "seek");
        } else {
            mediaPlayer.seekTo(progress);
        }
    }

    @Override
    public PlayerStatusEnum getPlayerStatus() {
        return playerStatusEnum;
    }

    @Override
    public void setPlayerStatus(PlayerStatusEnum playerStatus) {
        playerStatusEnum = playerStatus;
        playerModel.doSthAboutStatusChange(playerStatus, currentPlayingCourse);
    }

    @Override
    public PlayerStatusEnum getPlayerShowStatus() {
        return playerShowStatus == null ? playerModel.getPlayerShowStatus() : playerShowStatus;
    }

    @Override
    public void setPlayerShowStatus(PlayerStatusEnum playerStatus) {
        playerShowStatus = playerStatus;
        playerModel.doSthAboutShowStatusChange(playerStatus);
    }

    @Override
    public CourseInfo getCurrentCourse(IObtainCourseCallback iObtainCourseCallback) {
        if (currentPlayingCourse != null) {
            iObtainCourseCallback.callback(currentPlayingCourse);
        } else {
            playerModel.getSavedCourse(iObtainCourseCallback);
        }
        return null;
    }

    @Override
    public Integer getCurrentPlayId() {
        return currentPlayingCourse == null ? playerModel.getFinalCourseId() : currentPlayingCourse.getCourseId();
    }

    @Override
    public void getDuration(IObtainCourseCallback iObtainCourseCallback) {
        if (playerStatusEnum.equals(PlayerStatusEnum.CANCEL_STATUS) ||
                mediaPlayer == null || mediaPlayer.getDuration() == -1) {
            playerModel.getDuration(iObtainCourseCallback);
        } else {
            iObtainCourseCallback.callback(mediaPlayer.getDuration());
        }
    }

    @Override
    public void getCurrentProgress(IObtainCourseCallback iObtainCourseCallback) {
        if (playerStatusEnum.equals(PlayerStatusEnum.CANCEL_STATUS) ||
                mediaPlayer == null || mediaPlayer.getDuration() == -1) {
            playerModel.getCurrentPlayProgress(iObtainCourseCallback);
        } else {
            iObtainCourseCallback.callback(mediaPlayer.getCurrentPosition());
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        currentPlayingCourse.setAudioLength(mediaPlayer.getDuration());
        startPlay(true);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mediaPlayer.getCurrentPosition()==0){
            return;
        }
        //播放完成
        currentPlayingUrl = "";
        setPlayerStatus(PlayerStatusEnum.COMPLETION_STATUS);
        savePreviousCourse(currentPlayingCourse, mediaPlayer.getCurrentPosition());
        //播放完后需要处理的事情
        playerModel.doSthForCompletion();
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        //拖拽进度条
        startPlay(false);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    /**
     * 播放另一个课程时，或者服务销毁时，存储课程信息
     *
     * @param needSaveCourse
     * @param currentPlayProgress
     */
    private void savePreviousCourse(CourseInfo needSaveCourse, Integer currentPlayProgress) {
        if (needSaveCourse != null) {
            needSaveCourse.setCurrentPlayLength(currentPlayProgress);
            playerModel.savePreviousCourse(needSaveCourse);
        }

    }


    /**
     * 做销毁操作
     */
    public void onDestroy() {
        savePreviousCourse(currentPlayingCourse, mediaPlayer.getCurrentPosition());
        playerModel.savePlayerShowStatus(playerShowStatus == null ? getPlayerShowStatus() : playerShowStatus);
        if (currentPlayingCourse != null) {
            playerModel.saveFinalCourseId(currentPlayingCourse.getCourseId());
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (playerModel != null) {
            playerModel = null;
        }
    }
}
