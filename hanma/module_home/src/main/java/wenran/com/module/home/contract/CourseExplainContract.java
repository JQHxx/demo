package wenran.com.module.home.contract;

import java.util.List;

import wenran.com.baselibrary.base.basemvp.IBasePresenter;
import wenran.com.baselibrary.base.basemvp.IBaseView;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.module.home.bean.CourseExplainParamBean;
import wenran.com.module.home.bean.CourseExplainResultBean;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */

public final class CourseExplainContract {

    public interface ICourseExplainModel {
        /**
         * 获取课程介绍数据
         *
         * @param dataParam      需要的参数
         * @param normalCallback
         */
        void getCourseExplain(CourseExplainParamBean dataParam, INormalCallback<CourseExplainResultBean> normalCallback);

    }

    public interface ICourseExplainView extends IBaseView {

        /**
         * 获取课程介绍数据返回操作
         *
         * @param imageIdList 携带数据
         * @param imageList 携带数据
         */
        void dealCourseExplainBannerSuccess(List<Integer> imageIdList, List<String> imageList);

        /**
         * 获取课程介绍数据失败返回操作
         *
         * @param code
         * @param msg  携带数据
         */
        void dealCourseExplainBannerFailure(int code, String msg);

        /**
         * 获取课程介绍数据返回操作
         *
         * @param data 携带数据
         */
        void dealCourseExplainSuccess(CourseExplainResultBean.DataBean data);

        /**
         * 获取课程介绍数据失败返回操作
         *
         * @param code
         * @param msg  携带数据
         */
        void dealCourseExplainFailure(int code, String msg);
    }

    public interface ICourseExplainPresenter extends IBasePresenter {
        /**
         * 获取课程介绍数据
         *
         * @param dataParam
         */
        void getCourseExplainData(CourseExplainParamBean dataParam);

    }
}
