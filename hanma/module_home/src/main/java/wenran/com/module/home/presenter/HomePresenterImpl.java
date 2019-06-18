package wenran.com.module.home.presenter;

import java.util.ArrayList;
import java.util.List;

import wenran.com.baselibrary.base.basemvp.BasePresenterImpl;
import wenran.com.baselibrary.bean.StandardResultBean;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.utils.SpUtil;
import wenran.com.baselibrary.utils.StringUtil;
import wenran.com.module.home.bean.HomeDataParamBean;
import wenran.com.module.home.bean.HomeDataResultBean;
import wenran.com.module.home.contract.HomeContract;
import wenran.com.module.home.model.NetModel;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */
public class HomePresenterImpl extends BasePresenterImpl<HomeContract.IHomeView, NetModel>
        implements HomeContract.IHomePresenter {

    public HomePresenterImpl(HomeContract.IHomeView mBaseView) {
        super(mBaseView);
        initModel(NetModel.class);
    }

    @Override
    public void getHomeData(HomeDataParamBean paramBean) {
        if (isViewAttach()) {
            String appToken = SpUtil.getString(getView().getSelfActivity(), ConstantTag.APP_TOKEN.name());
            paramBean.setApptoken(appToken);
        } else {
            dealException(ConstantTag.NEED_MORE_INFO.getTagValue(), true, getClass());
            return;
        }
        netModel.getHomeData(paramBean, new INormalCallback<HomeDataResultBean>() {
            @Override
            public void success(HomeDataResultBean msg) {
                if (msg == null) {
                    dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
                    return;
                } else {
                    if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                        if (isViewAttach()) {
                            getView().getHomeDataSuccess(msg);
                            dealData(msg);
                        }
                    } else {
                        if (isViewAttach()) {
                            getView().getHomeDataFailure(msg.getStatusCode(), msg.getMessage());
                        }
                    }
                }
            }

            @Override
            public void failure(String failureInfo) {
                dealException(failureInfo, true, getClass());
            }
        });
    }

    @Override
    public void getMessageStatus(HomeDataParamBean paramBean) {
        if (isViewAttach()) {
            String appToken = SpUtil.getString(getView().getSelfActivity(), ConstantTag.APP_TOKEN.name());
            if (StringUtil.isEmptyStr(appToken)){
                //没登录不请求
                return;
            }
            paramBean.setApptoken(appToken);
        } else {
            dealException(ConstantTag.NEED_MORE_INFO.getTagValue(), true, getClass());
            return;
        }
        netModel.getMessageStatus(paramBean, new INormalCallback<StandardResultBean>() {
            @Override
            public void success(StandardResultBean msg) {
                if (msg == null) {
                    dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
                    return;
                } else {
                    if (isViewAttach() == false) {
                        return;
                    }
                    if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                        getView().dealMessageStatus(true, msg.getStatusCode(), msg.getMessage());
                    } else {
                        getView().dealMessageStatus(false, msg.getStatusCode(), msg.getMessage());
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
    private void dealData(HomeDataResultBean msg) {
        HomeDataResultBean.DataBean data = msg.getData();
        if (data == null) {
            dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
            return;
        }
        initBanner(data.getBannerList());
        initRamblingSet(data.getRamble());
        initSingleClass(data.getIndividual());
        initSpecialClass(data.getSpecial());
    }

    /**
     * 实例化banner
     *
     * @param bannerList
     */
    private void initBanner(List<HomeDataResultBean.DataBean.BannerListBean> bannerList) {
        if (bannerList == null || bannerList.size() == 0) {
            dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
            return;
        }
        List<Integer> imageIdList = new ArrayList();
        List<String> imageList = new ArrayList();
        for (HomeDataResultBean.DataBean.BannerListBean b :
                bannerList) {
            imageIdList.add(b.getId());
            imageList.add(b.getImg());
        }
        getView().initBanner(imageIdList, imageList);
    }

    /**
     * 初始化漫谈集
     */
    private void initRamblingSet(List<HomeDataResultBean.DataBean.RambleBean> ramblingSetList) {
        if (ramblingSetList == null || ramblingSetList.size() == 0) {
            dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
            return;
        }
        getView().initRamblingSetRv(ramblingSetList);
    }

    /**
     * 初始化单项课
     */
    private void initSingleClass(List<HomeDataResultBean.DataBean.IndividualBean> singleClasses) {
        if (singleClasses == null || singleClasses.size() == 0) {
            dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
            return;
        }
        getView().initSingleClass(singleClasses);
    }

    /**
     * 初始化专题课
     */
    private void initSpecialClass(List<HomeDataResultBean.DataBean.SpecialBean> specialClasses) {
        if (specialClasses == null || specialClasses.size() == 0) {
            dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
            return;
        }
        getView().initSpecialClass(specialClasses);
    }
}
