package wenran.com.baselibrary.callback;


/**
 * Created by crowhine on 2018/7/26.
 *
 * @author crowhine
 * description:回调
 */

public interface INormalCallback<T>{
    /**
     * 成功的回调
     *
     * @param msg 回调信息
     */
    void success(T msg);

    /**
     * 失败回调
     *
     * @param failureInfo 失败信息
     */
    void failure(String failureInfo);
}
