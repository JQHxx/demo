package wenran.com.module.home.contract;

import java.util.List;

import wenran.com.baselibrary.base.basemvp.IBasePresenter;
import wenran.com.baselibrary.base.basemvp.IBaseView;
import wenran.com.baselibrary.bean.StandardResultBean;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.module.home.bean.CollectParamBean;
import wenran.com.module.home.bean.CommentParamBean;
import wenran.com.module.home.bean.CourseDetailShowParamBean;
import wenran.com.module.home.bean.CourseDetailShowResultBean;
import wenran.com.module.home.bean.LikeParamBean;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */

public final class CourseDetailShowContract {

    public interface ICourseDetailShowModel {
        /**
         * 获取课程详情展示数据
         *
         * @param id
         * @param courseDetailShowParamBean 需要的参数
         * @param normalCallback
         */
        void getCourseDetailShow(String id,
                                 CourseDetailShowParamBean courseDetailShowParamBean,
                                 INormalCallback<CourseDetailShowResultBean> normalCallback);

        /**
         * 点赞
         *
         * @param likeParamBean  需要的参数
         * @param normalCallback
         */
        void doLike(LikeParamBean likeParamBean,
                    INormalCallback<StandardResultBean> normalCallback);

        /**
         * 收藏
         *
         * @param collectParamBean 需要的参数
         * @param normalCallback
         */
        void doCollect(CollectParamBean collectParamBean,
                       INormalCallback<StandardResultBean> normalCallback);

        /**
         * 评论
         *
         * @param commentParamBean 需要的参数
         * @param normalCallback
         */
        void doComment(CommentParamBean commentParamBean,
                       INormalCallback<StandardResultBean> normalCallback);
    }

    public interface ICourseDetailShowView extends IBaseView {

        /**
         * 获取课程详情展示数据成功返回操作
         *
         * @param classInfo 数据
         */
        void dealInitCourseDetailSuccess(CourseDetailShowResultBean.DataBean.ClassInfoBean classInfo);


        /**
         * 获取课程详情展示数据失败返回操作
         *
         * @param code
         * @param hint
         */
        void dealInitCourseDetailFailure(int code, String hint);

        /**
         * 显示课程大纲
         *
         * @param isSuccess
         * @param classComments 课程集合
         * @param isBought
         * @param coverUrl      封面地址，用于播放器展示
         */
        void showCourseOutlineRecyclerView(boolean isSuccess, boolean isBought, String coverUrl,
                                           List<CourseDetailShowResultBean.DataBean.ClassItemsBean> classComments);

        /**
         * 显示留言
         *
         * @param isSuccess
         * @param classCommentsBeans 留言集合
         */
        void showLeaveWordRecyclerView(boolean isSuccess,
                                       List<CourseDetailShowResultBean.DataBean.ClassCommentsBean> classCommentsBeans);

        /**
         * 收藏成功
         */
        void dealCollectSuccess();

        /**
         * 评论成功
         */
        void dealCommentSuccess();

    }

    public interface ICourseDetailShowPresenter extends IBasePresenter {
        /**
         * 获取课程详情展示数据
         *
         * @param id
         * @param courseDetailShowParamBean
         */
        void getCourseDetailShowData(String id,
                                     CourseDetailShowParamBean courseDetailShowParamBean);

        /**
         * 点赞
         *
         * @param likeParamBean  需要的参数
         * @param normalCallback
         */
        void doLike(LikeParamBean likeParamBean,
                    INormalCallback<StandardResultBean> normalCallback);

        /**
         * 收藏
         *
         * @param collectParamBean 需要的参数
         */
        void doCollect(CollectParamBean collectParamBean);

        /**
         * 评论
         *
         * @param commentParamBean 需要的参数
         */
        void doComment(CommentParamBean commentParamBean);

    }
}
