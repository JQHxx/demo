package wenran.com.baselibrary.base;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.server.ObtainNetData;

/**
 * Created by Crowhine on 2019/2/21
 *
 * @author Crowhine
 */
public class BaseModel {

    /**
     * 发送网络请求
     *
     * @param observable
     * @param callback
     * @param <T>
     */
    public  <T> void sendRequest(@NonNull Observable<T> observable, INormalCallback<T> callback) {
        new ObtainNetData(observable,callback);
    }

    /**
     * 发送网络请求
     *
     * @param tag
     * @param observable
     * @param callback
     * @param <T>
     */
    public <T> void sendRequest(@NonNull Object tag, @NonNull Observable<T> observable, INormalCallback callback) {
    }

    /**
     * 发送网络请求
     *
     * @param observable 被观察者
     * @param observer   观察者
     * @param <T>
     */
    public <T> void sendRequest(@NonNull Observable<T> observable) {
    }

    /**
     * 发送网络请求
     *
     * @param tag        请求标记
     * @param observable 被观察者
     * @param observer   观察者
     * @param <T>
     */
    public <T> void sendRequest(@NonNull Object tag, @NonNull Observable<T> observable) {
    }
}
