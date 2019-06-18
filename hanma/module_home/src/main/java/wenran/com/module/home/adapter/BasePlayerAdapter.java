package wenran.com.module.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import wenran.com.baselibrary.dao.PlayerStatusEnum;
import wenran.com.baselibrary.help.SomeData;
import wenran.com.baselibrary.utils.RouterUtil;

/**
 * Created by Crowhine on 2019/4/19
 *
 * @author Crowhine
 */
public abstract class BasePlayerAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T>{
    protected Context context;
    protected int currentPlayId = -1;
    protected PlayerStatusEnum playerStatus = PlayerStatusEnum.PAUSE_STATUS;

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
        PlayerStatusEnum playerStatus =RouterUtil.getIPlayer().getPlayerStatus();
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
            if (registered==false){
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
                this.playerStatus=PlayerStatusEnum.PLAYING_STATUS;
            } else if (pauseStatus.equals(event.getTag())) {
                //暂停
                currentPlayId = -1;
                this.playerStatus=PlayerStatusEnum.PAUSE_STATUS;
            } else if (completionStatus.equals(event.getTag())) {
                //完成
                this.playerStatus=PlayerStatusEnum.COMPLETION_STATUS;
            }
            notifyDataSetChanged();
        }
    }
}
