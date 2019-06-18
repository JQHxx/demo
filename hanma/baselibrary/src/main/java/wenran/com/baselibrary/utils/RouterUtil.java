package wenran.com.baselibrary.utils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

import java.io.Serializable;

import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.dao.PlayerStatusEnum;
import wenran.com.baselibrary.interfaces.IPay;
import wenran.com.baselibrary.interfaces.IPlayer;

/**
 * Created by Crowhine on 2019/3/19
 * <p>
 * 获取fragment
 *
 * @author Crowhine
 */
public class RouterUtil {

    public static Fragment getFragment(String routerPath, String tag, Object v) {

        Postcard build = ARouter.getInstance()
                .build(routerPath);
        if (v != null && tag != null) {
            if (v instanceof String) {
                build.withString(tag, (String) v);
            } else if (v instanceof Integer) {
                build.withInt(tag, (Integer) v);
            } else if (v instanceof Boolean) {
                build.withBoolean(tag, (Boolean) v);
            } else if (v instanceof Long) {
                build.withLong(tag, (Long) v);
            } else if (v instanceof Serializable) {
                build.withSerializable(tag, (Serializable) v);
            } else if (v instanceof Parcelable) {
                build.withParcelable(tag, (Parcelable) v);
            } else if (v instanceof Bundle) {
                build.withBundle(tag, (Bundle) v);
            } else {
                build.withObject(tag, v);
            }
        }
        Fragment fragment = (Fragment) build
                .navigation();
        return fragment;
    }


    /**
     * 获取播放器操作接口
     *
     * @return
     */
    public static IPlayer getIPlayer() {
        IPlayer iPlayer = (IPlayer)
                ARouter.getInstance().build(RouterPath.PLAYER_AUDIO_PLAYER_SERVICE).navigation();

        return iPlayer;
    }

    /**
     * 获取播放器销毁接口
     *
     * @return
     */
    public static IPlayer.IDestroyService getIDestroyService() {
        IPlayer.IDestroyService iDestroyService = (IPlayer.IDestroyService) ARouter.getInstance().build(RouterPath.PLAYER_AUDIO_PLAYER_SERVICE).navigation();
        return iDestroyService;
    }

    /**
     * 获取播放状态
     *
     * @return
     */
    public static PlayerStatusEnum getPlayerStatus() {
        PlayerStatusEnum playerStatus = ((IPlayer) ARouter.getInstance().build
                (RouterPath.PLAYER_AUDIO_PLAYER_SERVICE).navigation()).getPlayerStatus();
        return playerStatus;
    }

    /**
     * set播放状态
     *
     * @param playerStatus
     */
    public static void setPlayerStatus(PlayerStatusEnum playerStatus) {
        getIPlayer().setPlayerStatus(playerStatus);
    }

    /**
     * 获取播放显示状态
     *
     * @return
     */
    public static PlayerStatusEnum getPlayerShowStatus() {
        PlayerStatusEnum playerShowStatus = ((IPlayer) ARouter.getInstance().build
                (RouterPath.PLAYER_AUDIO_PLAYER_SERVICE).navigation()).getPlayerShowStatus();
        return playerShowStatus;
    }

    /**
     * set播放显示状态
     *
     * @param isShow 是否显示
     */
    public static void setPlayerShowStatus(boolean isShow) {
        PlayerStatusEnum playerShowStatus = PlayerStatusEnum.HIDE_PLAYER;
        if (isShow) {
            playerShowStatus = PlayerStatusEnum.SHOW_PLAYER;
        }
        getIPlayer().setPlayerShowStatus(playerShowStatus);
    }


    /**
     * 获取支付操作接口
     *
     * @return
     */
    public static IPay getIPay() {
        IPay iPay = (IPay)
                ARouter.getInstance().build(RouterPath.APP_PAY).navigation();
        return iPay;
    }

    /**
     * 支付宝支付
     *
     * @return
     */
    public static<T> void aliPay(Activity activity, int classInfoId, INormalCallback<T> iNormalCallback) {
        getIPay().aliPay(activity,classInfoId,iNormalCallback);
    }
}
