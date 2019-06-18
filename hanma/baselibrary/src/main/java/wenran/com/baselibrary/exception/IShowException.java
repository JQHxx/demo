package wenran.com.baselibrary.exception;

/**
 * Created by crowhine on 2018/7/27.
 *
 * @author crowhine
 *         用于在activity显示异常，或集中处理
 */

public interface IShowException {
    /**
     * 处理异常
     *
     * @param exceptionTag  异常标签
     * @param exceptionInfo 异常信息
     */
    void dealException(String exceptionTag, String exceptionInfo);
}
