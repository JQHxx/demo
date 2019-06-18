package wenran.com.baselibrary.callback;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */
public interface IStandardCallBack<T> {
    /**
     * 成功的回调
     *
     * @param msg 回调信息
     */
    void success(T msg);

    /**
     * 失败回调
     *
     * @param code
     * @param failureInfo 失败信息
     */
    void failure(String code, String failureInfo);
}
