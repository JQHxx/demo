package wenran.com.module.home.presenter;

import wenran.com.baselibrary.base.basemvp.BasePresenterImpl;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.utils.SpUtil;
import wenran.com.module.home.bean.SearchCourseResultBean;
import wenran.com.module.home.bean.SearchDetailParamBean;
import wenran.com.module.home.constant.HomeConstantTag;
import wenran.com.module.home.contract.SearchResultContract;
import wenran.com.module.home.model.NetModel;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */
public class SearchResultResultPresenterImpl extends BasePresenterImpl<SearchResultContract.ISearchResultView, NetModel>
        implements SearchResultContract.ISearchResultPresenter {
    /**
     * 当前页码
     */
    private int currentPage = 1;
    public SearchResultResultPresenterImpl(SearchResultContract.ISearchResultView mBaseView) {
        super(mBaseView);
        initModel(NetModel.class);
    }

    @Override
    public void getSearchResultData(final HomeConstantTag type, SearchDetailParamBean searchDetailParamBean) {
        if (searchDetailParamBean == null||!isViewAttach()) {
            dealException(ConstantTag.NEED_MORE_INFO.getTagValue(), true, getClass());
            return;
        }else {
            String appToken = SpUtil.getString(getView().getSelfActivity(), ConstantTag.APP_TOKEN.name());
            searchDetailParamBean.setApptoken(appToken);
        }
        netModel.getSearchResult(searchDetailParamBean, new INormalCallback<SearchCourseResultBean>() {
            @Override
            public void success(SearchCourseResultBean msg) {
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


    private void dealDataForSuccess(HomeConstantTag type, SearchCourseResultBean msg) {
        if (isViewAttach() == false) {
            return;
        }
        switch (type) {
            case INIT_RECYCLER_VIEW:
                if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                    changePage(type);
                    getView().dealInitSearchResultData(msg.getData());
                } else if (ConstantNum.NO_MORE_DATA == msg.getStatusCode()) {
                    getView().dealNoMoreData(type);
                } else {
//                    getView().dealInitSearchResultData(msg.getData());
                }
                break;
            case DO_REFRESH:
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
