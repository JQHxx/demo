package wenran.com.module.home.presenter;

import java.util.ArrayList;
import java.util.List;

import wenran.com.baselibrary.base.basemvp.BasePresenterImpl;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.module.home.bean.CourseExplainParamBean;
import wenran.com.module.home.bean.CourseExplainResultBean;
import wenran.com.module.home.contract.CourseExplainContract;
import wenran.com.module.home.model.NetModel;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */
public class CourseExplainPresenterImpl extends BasePresenterImpl<CourseExplainContract.ICourseExplainView, NetModel>
        implements CourseExplainContract.ICourseExplainPresenter {

    public CourseExplainPresenterImpl(CourseExplainContract.ICourseExplainView mBaseView) {
        super(mBaseView);
        initModel(NetModel.class);
    }


    @Override
    public void getCourseExplainData(CourseExplainParamBean dataParam) {
        if (dataParam == null) {
            dealException(ConstantTag.NEED_MORE_INFO.getTagValue(), true, getClass());
            return;
        }
        if (isViewAttach() == false) {
            return;
        }
        getView().showLoadingProgress(null);
        netModel.getCourseExplain(dataParam, new INormalCallback<CourseExplainResultBean>() {
            @Override
            public void success(CourseExplainResultBean msg) {
                if (isViewAttach() == false) {
                    return;
                }
                getView().hideLoadingProgress();
                if (msg == null) {
                    dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
                    return;
                } else {
                    if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                        dealData(msg);
                    } else {
                        getView().dealCourseExplainFailure(msg.getStatusCode(), msg.getMessage());
                    }
                }
            }

            @Override
            public void failure(String failureInfo) {
                dealException(failureInfo, true, getClass());
            }
        });
    }

    /**
     * 处理数据
     */
    private void dealData(CourseExplainResultBean msg) {
        CourseExplainResultBean.DataBean data = msg.getData();
        if (data == null) {
            dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
            return;
        }
        initBanner(msg);
        getView().dealCourseExplainSuccess(data);
    }


    /**
     * 实例化banner
     *
     * @param msg
     */
    private void initBanner(CourseExplainResultBean msg) {
        if (msg.getData() == null) {
            dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
            return;
        }
        List<CourseExplainResultBean.DataBean.BannerListBean> bannerList
                = msg.getData().getBannerList();
        if (bannerList == null || bannerList.size() == 0) {
            getView().dealCourseExplainBannerFailure(msg.getStatusCode(), msg.getMessage());
        } else {
            List<Integer> imageIdList = new ArrayList();
            List<String> imageList = new ArrayList();
            for (CourseExplainResultBean.DataBean.BannerListBean b :
                    bannerList) {
                imageIdList.add(b.getId());
                imageList.add(b.getImg());
            }
            getView().dealCourseExplainBannerSuccess(imageIdList, imageList);
        }
    }


}
