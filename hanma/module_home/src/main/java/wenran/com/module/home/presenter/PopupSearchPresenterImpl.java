package wenran.com.module.home.presenter;


import java.util.ArrayList;
import java.util.List;

import wenran.com.baselibrary.base.basemvp.BasePresenterImpl;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.module.home.bean.HotSearchResultBean;
import wenran.com.module.home.bean.PopupSearchParamBean;
import wenran.com.module.home.contract.PopupSearchContract;
import wenran.com.module.home.model.NetModel;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */
public class PopupSearchPresenterImpl extends BasePresenterImpl<PopupSearchContract.IPopupSearchView, NetModel>
        implements PopupSearchContract.IPopupSearchPresenter {

    public PopupSearchPresenterImpl(PopupSearchContract.IPopupSearchView mBaseView) {
        super(mBaseView);
        initModel(NetModel.class);
    }


    @Override
    public void getPopupSearchData(PopupSearchParamBean popupSearchParamBean) {
        netModel.getPopupSearch(popupSearchParamBean, new INormalCallback<HotSearchResultBean>() {
            @Override
            public void success(HotSearchResultBean msg) {
                if (msg == null) {
                    dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
                    return;
                } else {
                    if (isViewAttach()) {
                        List<HotSearchResultBean.DataBean> data = msg.getData();
                        if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()
                                && data != null && data.size() != 0) {
                            getView().dealPopupSearchData(true,getStrList(data));
                        } else {
                            getView().dealPopupSearchData(false,null);
                        }
                    }

                }
            }

            @Override
            public void failure(String failureInfo) {
                dealException(failureInfo, true, getClass());
                if (isViewAttach()){
                    getView().dealPopupSearchData(false,null);
                }
            }
        });
    }

    private List<String> getStrList(List<HotSearchResultBean.DataBean> data) {
        List<String> stringList = new ArrayList<>();
        for (HotSearchResultBean.DataBean dataBean :
                data) {
            stringList.add(dataBean.getTitle());
        }
        return stringList;
    }
}
