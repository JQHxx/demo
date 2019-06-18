package wenran.com.module.home.presenter;

import wenran.com.baselibrary.base.basemvp.BasePresenterImpl;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.utils.SpUtil;
import wenran.com.module.home.bean.CourseMoreParamBean;
import wenran.com.module.home.bean.SpecialMoreResultBean;
import wenran.com.module.home.constant.HomeConstantTag;
import wenran.com.module.home.contract.SpecialCourseMoreContract;
import wenran.com.module.home.model.NetModel;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */
public class CourseMorePresenterImpl extends BasePresenterImpl<SpecialCourseMoreContract.ISpecialMoreView, NetModel>
        implements SpecialCourseMoreContract.ISpecialMorePresenter {
    /**
     * 当前页码
     */
    private int currentPage = 1;
    public CourseMorePresenterImpl(SpecialCourseMoreContract.ISpecialMoreView mBaseView) {
        super(mBaseView);
        initModel(NetModel.class);
    }

    @Override
    public void getSpecialMoreData(boolean isSpecialOrSingle, final HomeConstantTag type,
                                   CourseMoreParamBean courseMoreParamBean) {
        if (courseMoreParamBean == null||!isViewAttach()) {
            dealException(ConstantTag.NEED_MORE_INFO.getTagValue(), true, getClass());
            return;
        }else {
            String appToken = SpUtil.getString(getView().getSelfActivity(), ConstantTag.APP_TOKEN.name());
            courseMoreParamBean.setApptoken(appToken);
            if (isSpecialOrSingle){
                //专题课
                courseMoreParamBean.setUrl("morespecial");
            }else {
                //单项课
                courseMoreParamBean.setUrl("moreindividual");
            }
        }
        netModel.getSpecialMore(courseMoreParamBean, new INormalCallback<SpecialMoreResultBean>() {
            @Override
            public void success(SpecialMoreResultBean msg) {
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
                dealDataForSuccess(type,null);
            }
        });
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }


    private void dealDataForSuccess(HomeConstantTag type,SpecialMoreResultBean msg) {
        if (isViewAttach() == false) {
            return;
        }

        switch (type) {
            case INIT_RECYCLER_VIEW:
                if (msg==null){
                    getView().dealInitSpecialMoreData(false,null);
                    return;
                }
                if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                    changePage(type);
                    getView().dealInitSpecialMoreData(true,msg.getData());
                } else if (ConstantNum.NO_MORE_DATA == msg.getStatusCode()) {
                    getView().dealNoMoreData(type);
                } else {
                    getView().dealInitSpecialMoreData(false,msg.getData());
                }
                break;
            case DO_REFRESH:
                if (msg==null){
                    getView().dealRefresh(false,null);
                    return;
                }
                if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                    changePage(type);
                    getView().dealRefresh(true,msg.getData());
                } else if (ConstantNum.NO_MORE_DATA == msg.getStatusCode()) {
                    getView().dealNoMoreData(type);
                } else {
                    getView().dealRefresh(false,msg.getData());
                }
                break;
            case DO_MORE:
                if (msg==null){
                    getView().dealMore(false,null);
                    return;
                }
                if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                    changePage(type);
                    getView().dealMore(true, msg.getData());
                } else if (ConstantNum.NO_MORE_DATA == msg.getStatusCode()) {
                    getView().dealNoMoreData(type);
                } else {
                    getView().dealMore(false, msg.getData());
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
}
