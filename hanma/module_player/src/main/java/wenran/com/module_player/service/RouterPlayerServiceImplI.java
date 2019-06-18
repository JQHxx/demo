package wenran.com.module_player.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;

import java.io.IOException;

import wenran.com.baselibrary.dao.CourseInfo;
import wenran.com.baselibrary.dao.PlayerStatusEnum;
import wenran.com.baselibrary.interfaces.IObtainCourseCallback;
import wenran.com.baselibrary.interfaces.IPlayer;
import wenran.com.baselibrary.utils.RouterPath;

/**
 * Created by Crowhine on 2019/4/10
 *
 * @author Crowhine
 * <p>
 * function:播放器点击事件处理
 */
@Route(path = RouterPath.PLAYER_AUDIO_PLAYER_SERVICE)
public class RouterPlayerServiceImplI implements IProvider,
        IPlayer, IPlayer.IRouterPlayerService, IPlayer.IDestroyService {
    private Context context;
    private PlayerServiceConnection playerServiceConnection;
    private Intent playerIntent;

    @Override
    public void init(Context context) {
        this.context = context;
        getPlayerService();
    }


    @Override
    public void play(CourseInfo courseInfo) throws IOException {
        getPlayerService().play(courseInfo);
    }

    @Override
    public void pause() {
        getPlayerService().pause();
    }


    @Override
    public void speed(Integer second) {
        getPlayerService().speed(second);
    }

    @Override
    public void back(Integer second) {
        getPlayerService().back(second);
    }

    @Override
    public void seek(int progress) {
        getPlayerService().seek(progress);
    }

    @Override
    public CourseInfo getCurrentCourse(IObtainCourseCallback iObtainCourseCallback) {
        return getPlayerService().getCurrentCourse(iObtainCourseCallback);
    }

    @Override
    public Integer getCurrentPlayId() {
        if (getPlayerService() == null) {
            return -1;
        }
        return getPlayerService().getCurrentPlayId();
    }

    @Override
    public void getDuration(IObtainCourseCallback iObtainCourseCallback) {
        if (getPlayerService() == null) {
            iObtainCourseCallback.callback(-1);
        } else {
            getPlayerService().getDuration(iObtainCourseCallback);
        }

    }

    @Override
    public void getCurrentProgress(IObtainCourseCallback iObtainCourseCallback) {
        if (getPlayerService() == null) {
            iObtainCourseCallback.callback(-1);
        } else {
            getPlayerService().getCurrentProgress(iObtainCourseCallback);
        }
    }

    @Override
    public PlayerStatusEnum getPlayerStatus() {
        return getPlayerService().getPlayerStatus();
    }

    @Override
    public void setPlayerStatus(PlayerStatusEnum playerStatus) {
        getPlayerService().setPlayerStatus(playerStatus);
    }

    @Override
    public PlayerStatusEnum getPlayerShowStatus() {
        if (getPlayerService() == null) {
            Log.d("debug", "服务没生成");
            return PlayerStatusEnum.HIDE_PLAYER;
        }
        return getPlayerService().getPlayerShowStatus();
    }

    @Override
    public void setPlayerShowStatus(PlayerStatusEnum playerStatus) {
        getPlayerService().setPlayerShowStatus(playerStatus);
    }

    /**
     * 获取服务
     */
    private AudioPlayerService getPlayerService() {
//        if (playerIntent == null) {
//            playerIntent = new Intent(context, AudioPlayerService.class);
//            context.startService(playerIntent);
//        }
        if (playerServiceConnection == null) {
            playerServiceConnection = new PlayerServiceConnection();
            context.bindService(new Intent(context, AudioPlayerService.class), playerServiceConnection, Context.BIND_AUTO_CREATE);
        }
        return playerServiceConnection.getPlayerService();
    }

    /**
     * 销毁服务
     */
    @Override
    public void destroyPlayerService() {
        if (playerServiceConnection != null) {
            context.unbindService(playerServiceConnection);
        }
//        if (playerIntent != null) {
//            context.stopService(playerIntent);
//        }
    }

    @Override
    public void playOrPause(CourseInfo courseInfo) throws IOException {
        getPlayerService().playOrPause(courseInfo);
    }
}
