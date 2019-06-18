package wenran.com.baselibrary.exception;


import wenran.com.baselibrary.base.BaseApplication;
import wenran.com.baselibrary.base.basemvp.IBaseView;
import wenran.com.baselibrary.utils.MyLog;

/**
 * 自定义异常
 * Created by crowhine on 2018/7/26.
 *
 * @author crowhine
 */
public class MyException extends Exception {
    private final String whichClass;
    private final String errorInfo;

    /**
     * @param whichClass   tag
     * @param errorInfo 错误信息
     */
    public MyException(String whichClass, String errorInfo) {
        super(errorInfo);
        this.errorInfo = errorInfo;
        this.whichClass = whichClass;
        dealError();
    }

    private void dealError() {
        //处理异常
        MyLog.e(BaseApplication.app, "异常" + whichClass + "===" + errorInfo);
    }

    /**
     * 在activity中显示异常
     *
     * @param iShowException 需要显示的异常
     */
    public void showException(IBaseView iShowException) {
        iShowException.showException(whichClass+" : "+errorInfo);
    }
}
