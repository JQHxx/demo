package wenran.com.baselibrary.help;

import com.kingja.loadsir.core.LoadSir;

import wenran.com.baselibrary.callbackrepalce.EmptyCallback;
import wenran.com.baselibrary.callbackrepalce.ErrorCallback;
import wenran.com.baselibrary.callbackrepalce.LoadingCallback;
import wenran.com.baselibrary.callbackrepalce.NothingCallback;
import wenran.com.baselibrary.callbackrepalce.PlaceholderCallback;
import wenran.com.baselibrary.callbackrepalce.TimeoutCallback;


/**
 * Created by Crowhine on 2019/1/22
 *
 * @author Crowhine
 * function:占位图类
 */
public class MyLoad {
    /**
     * 初始化展位图类
     *
     * @param defaultShow
     */
    public LoadSir initLoadSir(Class defaultShow) {
        LoadSir loadSir = new LoadSir.Builder()
                .addCallback(new LoadingCallback())
                .addCallback(new PlaceholderCallback())
                .addCallback(new ErrorCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new NothingCallback())
                .setDefaultCallback(defaultShow)
                .build();
        return loadSir;
    }
}
