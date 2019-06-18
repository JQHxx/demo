package wenran.com.baselibrary.interfaces;

/**
 * Created by Crowhine on 2019/4/18
 *
 * @author Crowhine
 */
public interface IObtainCourseCallback<T> {
    /**
     * 获取课程信息
     *
     * @param courseInfo
     */
    void callback(T courseInfo);
}
