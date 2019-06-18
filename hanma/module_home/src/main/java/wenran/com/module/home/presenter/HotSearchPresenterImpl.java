package wenran.com.module.home.presenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import wenran.com.baselibrary.base.basemvp.BasePresenterImpl;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.help.SomeData;
import wenran.com.module.home.bean.HotSearchResultBean;
import wenran.com.module.home.constant.HomeConstantTag;
import wenran.com.module.home.contract.HotSearchContract;
import wenran.com.module.home.greendao.HistorySearchControl;
import wenran.com.module.home.greendao.bean.HistorySearchBean;
import wenran.com.module.home.model.NetModel;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */
public class HotSearchPresenterImpl extends BasePresenterImpl<HotSearchContract.IHotSearchView, NetModel>
        implements HotSearchContract.IHotSearchPresenter {

    public HotSearchPresenterImpl(HotSearchContract.IHotSearchView mBaseView) {
        super(mBaseView);
        initModel(NetModel.class);
    }

    @Override
    public void getHotSearchData() {
        netModel.getHotSearch(new INormalCallback<HotSearchResultBean>() {
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
                            getView().dealHotSearchDataSuccess(getHotStrList(data));
                        } else {
                            getView().dealHotSearchDataFailure(msg.getStatusCode(), msg.getMessage());
                        }
                    }

                }
            }

            @Override
            public void failure(String failureInfo) {
                dealException(failureInfo, true, getClass());
                if (isViewAttach()){
                    getView().dealHotSearchDataFailure(ConstantNum.REQUEST_EXCEPTION, "");
                }

            }
        });
    }

    @Override
    public void getHistorySearchData() {
        if (isViewAttach() == false) {
            return;
        }
        HistorySearchControl.queryHistorySearchData(getView().getSelfActivity(), new Observer<List<HistorySearchBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<HistorySearchBean> historySearchBeans) {
                if (historySearchBeans == null || historySearchBeans.size() == 0) {
                    getView().dealHistoryDataSuccessFailure();
                } else {
                    getView().dealHistoryDataSuccess(getHistoryStrList(historySearchBeans));
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public void dealClick(final String tag) {
        if (isViewAttach() == false) {
            return;
        }
        //发送更改数据消息
        SomeData<String> stringSomeData =
                new SomeData<>(HomeConstantTag.SHOW_SEARCH_RESULT.getTagValue(), tag);
        EventBus.getDefault().post(stringSomeData);
    }


    private List<String> getHotStrList(List<HotSearchResultBean.DataBean> data) {
        List<String> stringList = new ArrayList<>();
        for (HotSearchResultBean.DataBean dataBean :
                data) {
            stringList.add(dataBean.getTitle());
        }
        return stringList;
    }

    private List<String> getHistoryStrList(List<HistorySearchBean> historySearchBeanList) {
        List<String> stringList = new ArrayList<>();
        for (HistorySearchBean dataBean :
                historySearchBeanList) {
            stringList.add(dataBean.getSearchTag());
        }
        return stringList;
    }
}
