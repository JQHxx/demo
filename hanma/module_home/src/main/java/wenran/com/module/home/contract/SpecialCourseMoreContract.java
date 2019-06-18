package wenran.com.module.home.contract;

import java.util.List;

import wenran.com.baselibrary.base.basemvp.IBasePresenter;
import wenran.com.baselibrary.base.basemvp.IBaseView;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.module.home.bean.CourseMoreParamBean;
import wenran.com.module.home.bean.SpecialMoreResultBean;
import wenran.com.module.home.constant.HomeConstantTag;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */

public final class SpecialCourseMoreContract {

    public interface ISpecialMoreModel {
        /**
         * 获取课程更多数据
         *
         * @param classMoreParamBean 需要的参数
         * @param normalCallback
         */
        void getSpecialMore(CourseMoreParamBean classMoreParamBean,
                            INormalCallback<SpecialMoreResultBean> normalCallback);
    }

    public interface ISpecialMoreView extends IBaseView {

        /**
         * 获取课程更多数据返回操作
         *
         * @param data      携带数据
         * @param isSuccess
         */
        void dealInitSpecialMoreData(boolean isSuccess, List<SpecialMoreResultBean.DataBean> data);


        /**
         * 获取课程更多数据返回操作
         *
         * @param isSuccess
         * @param data      携带数据
         */
        void dealRefresh(boolean isSuccess, List<SpecialMoreResultBean.DataBean> data);

        /**
         * 刷新更多
         *
         * @param isSuccess
         * @param data      携带数据
         */
        void dealMore(boolean isSuccess, List<SpecialMoreResultBean.DataBean> data);


        /**
         * 没有更多数据
         *
         * @param type
         */
        void dealNoMoreData(HomeConstantTag type);
    }

    public interface ISpecialMorePresenter extends IBasePresenter {
        /**
         * 获取课程更多数据
         *
         * @param isSpecialOrSingle  true:专题课，false单项课
         * @param type               刷新还是加载跟多或者初始化
         * @param classMoreParamBean
         */
        void getSpecialMoreData(boolean isSpecialOrSingle, HomeConstantTag type,
                                CourseMoreParamBean classMoreParamBean);

        /**
         * 获取当前页码
         *
         * @return
         */
        int getCurrentPage();
    }
}
