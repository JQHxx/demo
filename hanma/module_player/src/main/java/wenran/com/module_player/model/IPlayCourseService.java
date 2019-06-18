package wenran.com.module_player.model;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import wenran.com.baselibrary.bean.StandardResultBean;
import wenran.com.module_player.bean.CourseProgressParamBean;

/**
 * Created by Crowhine on 2019/5/5
 *
 * @author Crowhine
 */
public interface IPlayCourseService {
    /**
     * 更新课程播放进度
     *
     * @param classItemId 课程id
     * @return
     */
    @POST("details/play/{classItemId}")
    Observable<StandardResultBean> updateCourseProgress(
            @Path("classItemId") int classItemId, @Body  CourseProgressParamBean courseProgressParamBean);
}
