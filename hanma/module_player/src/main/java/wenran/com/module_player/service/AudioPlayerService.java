package wenran.com.module_player.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

import wenran.com.baselibrary.dao.CourseInfo;
import wenran.com.baselibrary.dao.PlayerStatusEnum;
import wenran.com.baselibrary.interfaces.IObtainCourseCallback;
import wenran.com.baselibrary.interfaces.IPlayer;

/**
 * Created by Crowhine on 2019/4/11
 *
 * @author Crowhine
 */

public class AudioPlayerService extends Service implements IPlayer, IPlayer.IRouterPlayerService {
    private AudioPlayer audioPlayer;


    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        getAudioPlayer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new PlayerBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        doSthForDestroy();
        super.onDestroy();
    }

    private void doSthForDestroy(){
        if (audioPlayer!=null){
            audioPlayer.onDestroy();
            audioPlayer=null;
        }
    }

    @Override
    public void play(CourseInfo courseInfo) throws IOException {
        getAudioPlayer().play(courseInfo);
    }

    @Override
    public void pause() {
        getAudioPlayer().pause();
    }

    @Override
    public void speed(Integer second) {
        getAudioPlayer().speed(second);
    }

    @Override
    public void back(Integer second) {
        getAudioPlayer().back(second);
    }

    @Override
    public void seek(int progress) {
        getAudioPlayer().seek(progress);
    }

    @Override
    public CourseInfo getCurrentCourse(IObtainCourseCallback iObtainCourseCallback) {
        return getAudioPlayer().getCurrentCourse(iObtainCourseCallback);
    }

    @Override
    public Integer getCurrentPlayId() {
        return getAudioPlayer().getCurrentPlayId();
    }

    @Override
    public void getDuration(IObtainCourseCallback iObtainCourseCallback) {
         getAudioPlayer().getDuration(iObtainCourseCallback);
    }

    @Override
    public void getCurrentProgress(IObtainCourseCallback iObtainCourseCallback) {
         getAudioPlayer().getCurrentProgress(iObtainCourseCallback);
    }

    @Override
    public PlayerStatusEnum getPlayerStatus() {
        return getAudioPlayer().getPlayerStatus();
    }

    @Override
    public void setPlayerStatus(PlayerStatusEnum playerStatus) {
        getAudioPlayer().setPlayerStatus(playerStatus);
    }

    @Override
    public PlayerStatusEnum getPlayerShowStatus() {
        return getAudioPlayer().getPlayerShowStatus();
    }

    @Override
    public void setPlayerShowStatus(PlayerStatusEnum playerStatus) {
        getAudioPlayer().setPlayerShowStatus(playerStatus);
    }

    @Override
    public void playOrPause(CourseInfo courseInfo) throws IOException {
        if (courseInfo == null) {
            //暂停
            getAudioPlayer().pause();
        } else {
            getAudioPlayer().play(courseInfo);
        }
    }

    public class PlayerBinder extends Binder {
        /**
         * 获取Service的方法
         *
         * @return TimerService
         */
        public AudioPlayerService getService() {
            return AudioPlayerService.this;
        }
    }

    private AudioPlayer getAudioPlayer(){
        if (audioPlayer==null){
            audioPlayer = new AudioPlayer(getApplicationContext());
        }
        return audioPlayer;
    }

}
