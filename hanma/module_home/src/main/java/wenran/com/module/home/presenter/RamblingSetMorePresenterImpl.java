package wenran.com.module.home.presenter;

import java.util.ArrayList;
import java.util.List;

import wenran.com.baselibrary.base.basemvp.BasePresenterImpl;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.utils.SpUtil;
import wenran.com.module.home.bean.RamblingSetMoreParamBean;
import wenran.com.module.home.bean.RamblingSetMoreResultBean;
import wenran.com.module.home.constant.HomeConstantTag;
import wenran.com.module.home.contract.RamblingSetMoreContract;
import wenran.com.module.home.model.NetModel;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */
public class RamblingSetMorePresenterImpl extends BasePresenterImpl<RamblingSetMoreContract.IRamblingSetMoreView, NetModel>
        implements RamblingSetMoreContract.IRamblingSetPresenter {
    /**
     * 当前页码
     */
    private int currentPage = 1;

    public RamblingSetMorePresenterImpl(RamblingSetMoreContract.IRamblingSetMoreView mBaseView) {
        super(mBaseView);
        initModel(NetModel.class);
    }

    @Override
    public void getRamblingSetMoreData(final HomeConstantTag type, RamblingSetMoreParamBean ramblingSetMoreParamBean) {
        if (ramblingSetMoreParamBean == null || !isViewAttach()) {
            dealException(ConstantTag.NEED_MORE_INFO.getTagValue(), true, getClass());
            return;
        } else {
            getView().showLoadingProgress(null);
            String appToken = SpUtil.getString(getView().getSelfActivity(), ConstantTag.APP_TOKEN.name());
            ramblingSetMoreParamBean.setApptoken(appToken);
        }
        getView().showLoadingProgress(null);
        netModel.getRamblingSetMore(ramblingSetMoreParamBean, new INormalCallback<RamblingSetMoreResultBean>() {
            @Override
            public void success(RamblingSetMoreResultBean msg) {
                if (isViewAttach() == false) {
                    return;
                }
                getView().hideLoadingProgress();
                if (msg == null) {
                    dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
                    return;
                } else {
                    dealDataForSuccess(type, msg);
                }
            }

            @Override
            public void failure(String failureInfo) {
                dealException(failureInfo, true, getClass());
            }
        });
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }


    private void dealDataForSuccess(HomeConstantTag type, RamblingSetMoreResultBean msg) {
        if (isViewAttach() == false) {
            return;
        }
        switch (type) {
            case INIT_RECYCLER_VIEW:
                if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                    changePage(type);
                    initBanner(msg.getData().getBannerList());
                    getView().dealInitRamblingSetMoreData(true, msg.getData().getRamble());
                } else if (ConstantNum.NO_MORE_DATA == msg.getStatusCode()) {
                    getView().dealNoMoreData(type);
                } else {
                    getView().dealInitRamblingSetMoreData(false, null);
                }
                break;
            case DO_REFRESH:
                if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                    changePage(type);
                    getView().dealRefresh(true, msg.getData().getRamble());
                } else if (ConstantNum.NO_MORE_DATA == msg.getStatusCode()) {
                    getView().dealNoMoreData(type);
                } else {
                    getView().dealRefresh(false, msg.getData().getRamble());
                }
                break;
            case DO_MORE:
                if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                    changePage(type);
                    getView().dealMore(true, msg.getData().getRamble());
                } else if (ConstantNum.NO_MORE_DATA == msg.getStatusCode()) {
                    getView().dealNoMoreData(type);
                } else {
                    getView().dealMore(false, msg.getData().getRamble());
                }
                break;
            default:
                break;
        }
    }

    /**
     * 更改变请求页数
     *
     * @param type
     */
    private void changePage(HomeConstantTag type) {
        switch (type) {
            case INIT_RECYCLER_VIEW:
            case DO_REFRESH:
                currentPage = 1;
                break;
            case DO_MORE:
                currentPage++;
                break;
            default:
                break;
        }
    }

    /**
     * 实例化banner
     *
     * @param bannerList
     */
    private void initBanner(List<RamblingSetMoreResultBean.DataBean.BannerListBean> bannerList) {
        if (bannerList == null || bannerList.size() == 0) {
            dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
            return;
        }
        List<Integer> imageIdList = new ArrayList();
        List<String> imageList = new ArrayList();
        for (RamblingSetMoreResultBean.DataBean.BannerListBean b :
                bannerList) {
            imageIdList.add(b.getId());
            imageList.add(b.getImg());
        }
        getView().dealInitRamblingSetMoreBanner(imageIdList, imageList);
    }
}
