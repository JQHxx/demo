package wenran.com.baselibrary.base;

import android.app.Application;

import com.kingja.loadsir.core.LoadSir;

import wenran.com.baselibrary.callbackrepalce.CustomCallback;
import wenran.com.baselibrary.callbackrepalce.EmptyCallback;
import wenran.com.baselibrary.callbackrepalce.ErrorCallback;
import wenran.com.baselibrary.callbackrepalce.LoadingCallback;
import wenran.com.baselibrary.callbackrepalce.NothingCallback;
import wenran.com.baselibrary.callbackrepalce.PlaceholderCallback;
import wenran.com.baselibrary.callbackrepalce.TimeoutCallback;

/**
 * Created by Crowhine on 2019/3/8
 *
 * @author Crowhine
 */
public class BaseApplication extends Application {
    public static BaseApplication app;

    @Override
    public void onCreate() {
        app = this;
        super.onCreate();
        initLoadView();
    }

    /**
     * 初始化占位图
     */
    private void initLoadView() {
        LoadSir.beginBuilder()
                .addCallback(new LoadingCallback())
                .addCallback(new PlaceholderCallback())
                .addCallback(new ErrorCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new NothingCallback())
                .setDefaultCallback(NothingCallback.class)
                .commit();
    }
}
