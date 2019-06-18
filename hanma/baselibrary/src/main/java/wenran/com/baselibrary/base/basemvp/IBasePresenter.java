package wenran.com.baselibrary.base.basemvp;

/**
 * Created by Crowhine on 2019/1/8
 *
 * @author crowhine
 */
public interface IBasePresenter extends IBaseOriginalPresenter {
    /**
     * 异常集中处理
     *
     * @param failureInfo
     * @param isShow
     * @param c
     */
    void dealException(String failureInfo, boolean isShow, Class c);



    /**
     * 取消网络请求
     *
     * @param tag 网络请求标记
     */
    void cancelNet(Object tag);

    /**
     * 取消所有的网络请求
     */
    void cancelAllNet();
}
