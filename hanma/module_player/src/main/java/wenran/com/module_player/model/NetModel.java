package wenran.com.module_player.model;

import io.reactivex.Observable;
import wenran.com.baselibrary.base.BaseModel;
import wenran.com.baselibrary.bean.StandardResultBean;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.server.RetrofitManager;
import wenran.com.module_player.bean.CourseProgressParamBean;

/**
 * Created by Crowhine on 2019/5/5
 *
 * @author Crowhine
 */
public class NetModel extends BaseModel {


    /**
     * 更新课程进度
     *
     * @param classItemId
     * @param courseProgressParamBean
     */
    public void updateCourseProgress(int classItemId, CourseProgressParamBean courseProgressParamBean, INormalCallback<StandardResultBean> normalCallback) {
        Observable<StandardResultBean> updateCourseProgress = RetrofitManager.getInstance().
                getRetrofitService(IPlayCourseService.class).
                updateCourseProgress(classItemId, courseProgressParamBean);
        sendRequest(updateCourseProgress, normalCallback);
    }
}
