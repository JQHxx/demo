package com.wenran.wenran.main.login.base;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.wenran.wenran.BuildConfig;

import wenran.com.baselibrary.base.BaseApplication;
import wenran.com.baselibrary.interfaces.IPlayer;
import wenran.com.baselibrary.utils.MyLog;
import wenran.com.baselibrary.utils.RouterPath;

/**
 * Created by crowh on 2018/7/2.
 *
 * @author crowhine
 */
public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initUm();
        init();
    }

    private void init() {
        MyLog.e(this,"----------");
        ARouter.getInstance().build(RouterPath.PLAYER_AUDIO_PLAYER_SERVICE).navigation();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
    }

    private void initUm() {
        UMShareAPI.get(this);
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this,"5c64cd59f1f556404c001a49","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        //58edcfeb310c93091c000be2 5965ee00734be40b580001a0

        PlatformConfig.setWeixin("wx44065acae2873d7a","b28f774505e0c31e9fa008a5c83d119e");
////        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
//        PlatformConfig.setQQZone("1107718887", "fjAElnQth87EhOZd");

        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);
    }

    @Override
    public void onTerminate() {
        IPlayer.IDestroyService navigation = (IPlayer.IDestroyService) ARouter.getInstance().build(RouterPath.PLAYER_AUDIO_PLAYER_SERVICE).navigation();
        navigation.destroyPlayerService();
        super.onTerminate();
    }
}
