package wenran.com.baselibrary.base.basemvp;

import com.kingja.loadsir.callback.Callback;

/**
 * Created by Crowhine on 2019/1/8
 *
 * @author crowhine
 */
public interface IBaseView extends IBaseOriginalView {

    /**
     * 释放资源
     */
    void release();

    /**
     * 显示异常
     *
     * @param exceptionInfo
     */
    void showException(String exceptionInfo);

    /**
     * 处理失败返回消息,如没有登录，或者身份过期，提示跳转登录；
     *
     * @param code
     * @param hint
     */
    void dealFailure(Integer code, String hint);

    /**
     * 处理成功请求回调
     *
     * @param hint
     */
    void dealSuccess(String hint);

    /**
     * 提示
     *
     * @param hint
     */
    void showHint(String hint);

    /**
     * 初始化
     */
    void baseInitView();

    /**
     * 展示加载进度条
     *
     * @param hint 提示
     */
    void showLoadingProgress(String hint);

    /**
     * 隐藏加载进度条
     */
    void hideLoadingProgress();

    /**
     * 获取占位图服务
     *
     * @param target           activity or fragment
     * @param onReloadListener 点击回调
     * @param defaultShow      默认显示的展示类
     */
    void getLoadService(Object target, Class defaultShow, Callback.OnReloadListener onReloadListener);
}
