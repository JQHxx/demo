package wenran.com.module.home.presenter;

import java.util.List;

import wenran.com.baselibrary.base.basemvp.BasePresenterImpl;
import wenran.com.baselibrary.bean.StandardResultBean;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.utils.SpUtil;
import wenran.com.module.home.bean.CollectParamBean;
import wenran.com.module.home.bean.CommentParamBean;
import wenran.com.module.home.bean.CourseDetailShowParamBean;
import wenran.com.module.home.bean.CourseDetailShowResultBean;
import wenran.com.module.home.bean.LikeParamBean;
import wenran.com.module.home.contract.CourseDetailShowContract;
import wenran.com.module.home.model.NetModel;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */
public class CourseDetailShowPresenterImpl extends BasePresenterImpl<CourseDetailShowContract.ICourseDetailShowView, NetModel>
        implements CourseDetailShowContract.ICourseDetailShowPresenter {

    public CourseDetailShowPresenterImpl(CourseDetailShowContract.ICourseDetailShowView mBaseView) {
        super(mBaseView);
        initModel(NetModel.class);
    }


    @Override
    public void getCourseDetailShowData(String id, CourseDetailShowParamBean courseDetailShowParamBean) {
        if (courseDetailShowParamBean == null || !isViewAttach()) {
            dealException(ConstantTag.NEED_MORE_INFO.getTagValue(), true, getClass());
            return;
        } else {
            getView().showLoadingProgress(null);
            String appToken = SpUtil.getString(getView().getSelfActivity(), ConstantTag.APP_TOKEN.name());
            courseDetailShowParamBean.setApptoken(appToken);
        }
        getView().showLoadingProgress(null);
        netModel.getCourseDetailShow(id, courseDetailShowParamBean, new INormalCallback<CourseDetailShowResultBean>() {
            @Override
            public void success(CourseDetailShowResultBean msg) {
                dealDataForSuccess(msg);
            }

            @Override
            public void failure(String failureInfo) {
                dealException(failureInfo, true, getClass());
            }
        });
    }

    @Override
    public void doLike(LikeParamBean likeParamBean, final INormalCallback<StandardResultBean> normalCallback) {
        if (likeParamBean == null || !isViewAttach()) {
            dealException(ConstantTag.NEED_MORE_INFO.getTagValue(), true, getClass());
            return;
        } else {
            getView().showLoadingProgress(null);
            String appToken = SpUtil.getString(getView().getSelfActivity(), ConstantTag.APP_TOKEN.name());
            likeParamBean.setApptoken(appToken);
        }
        getView().showLoadingProgress(null);
        netModel.doLike(likeParamBean, new INormalCallback<StandardResultBean>() {
            @Override
            public void success(StandardResultBean msg) {
                if (isViewAttach() == false) {
                    return;
                }
                getView().hideLoadingProgress();
                if (msg == null) {
                    dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
                    return;
                }
                if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                    normalCallback.success(null);
                } else {
                    normalCallback.failure(null);
                    getView().dealFailure(msg.getStatusCode(), msg.getMessage());
                }
            }

            @Override
            public void failure(String failureInfo) {
                dealException(failureInfo, true, getClass());
            }
        });
    }

    @Override
    public void doCollect(CollectParamBean collectParamBean) {
        if (collectParamBean == null || !isViewAttach()) {
            dealException(ConstantTag.NEED_MORE_INFO.getTagValue(), true, getClass());
            return;
        } else {
            getView().showLoadingProgress(null);
            String appToken = SpUtil.getString(getView().getSelfActivity(), ConstantTag.APP_TOKEN.name());
            collectParamBean.setApptoken(appToken);
        }
        getView().showLoadingProgress(null);
        netModel.doCollect(collectParamBean, new INormalCallback<StandardResultBean>() {
            @Override
            public void success(StandardResultBean msg) {
                if (isViewAttach() == false) {
                    return;
                }
                getView().hideLoadingProgress();
                if (msg == null) {
                    dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
                    return;
                }
                if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                    getView().dealCollectSuccess();
                } else {
                    getView().dealFailure(msg.getStatusCode(), msg.getMessage());
                }
            }

            @Override
            public void failure(String failureInfo) {
                dealException(failureInfo, true, getClass());
            }
        });
    }

    @Override
    public void doComment(CommentParamBean commentParamBean) {
        if (commentParamBean == null || !isViewAttach()) {
            dealException(ConstantTag.NEED_MORE_INFO.getTagValue(), true, getClass());
            return;
        } else {
            getView().showLoadingProgress(null);
            String appToken = SpUtil.getString(getView().getSelfActivity(), ConstantTag.APP_TOKEN.name());
            commentParamBean.setApptoken(appToken);
        }
        getView().showLoadingProgress(null);
        netModel.doComment(commentParamBean, new INormalCallback<StandardResultBean>() {
            @Override
            public void success(StandardResultBean msg) {
                if (isViewAttach() == false) {
                    return;
                }
                getView().hideLoadingProgress();
                if (msg == null) {
                    dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
                    return;
                }
                if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                    getView().dealCommentSuccess();
                } else {
                    getView().dealFailure(msg.getStatusCode(), msg.getMessage());
                }
            }

            @Override
            public void failure(String failureInfo) {
                dealException(failureInfo, true, getClass());
            }
        });
    }

    private void dealDataForSuccess(CourseDetailShowResultBean msg) {
        if (isViewAttach() == false) {
            return;
        }
        getView().hideLoadingProgress();
        if (msg == null) {
            dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
            return;
        }
        if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
            CourseDetailShowResultBean.DataBean data = msg.getData();
            if (data != null) {
                dealCourseDetailData(msg);
                dealOutlineData(data);
                dealCommentsData(data.getClassComments());
            } else {
                getView().dealInitCourseDetailFailure(msg.getStatusCode(), msg.getMessage());
            }
        } else {
            getView().dealInitCourseDetailFailure(msg.getStatusCode(), msg.getMessage());
        }
    }

    /**
     * 课程详情信息
     *
     * @date
     */
    private void dealCourseDetailData(CourseDetailShowResultBean msg) {
        CourseDetailShowResultBean.DataBean.ClassInfoBean classInfo = msg.getData().getClassInfo();
        if (msg.getData().getClassInfo() != null) {
            getView().dealInitCourseDetailSuccess(classInfo);
        } else {
            getView().dealFailure(msg.getStatusCode(), msg.getMessage());
        }
    }

    /**
     * 课程详情大纲
     *
     * @date
     */
    private void dealOutlineData(CourseDetailShowResultBean.DataBean data) {
        boolean isBought = false;
        //图片地址
        String coverUrl = "";
        if (data.getClassInfo() != null) {
            isBought = data.getClassInfo().isIsbuy();
            coverUrl= data.getClassInfo().getCover();
        }
        List<CourseDetailShowResultBean.DataBean.ClassItemsBean> classItems = data.getClassItems();
        if (classItems != null && classItems.size() != 0) {
            getView().showCourseOutlineRecyclerView(true, isBought, coverUrl,classItems);
        } else {
            getView().showCourseOutlineRecyclerView(false, isBought,coverUrl, null);
        }
    }

    /**
     * 课程详情留言
     *
     * @date
     */
    private void dealCommentsData(List<CourseDetailShowResultBean.DataBean.ClassCommentsBean> classComments) {
        if (classComments != null && classComments.size() != 0) {
            getView().showLeaveWordRecyclerView(true, classComments);
        } else {
            getView().showLeaveWordRecyclerView(false, null);
        }
    }


}
