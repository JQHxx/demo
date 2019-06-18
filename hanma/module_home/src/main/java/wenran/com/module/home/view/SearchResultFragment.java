package wenran.com.module.home.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import wenran.com.baselibrary.base.basemvp.BaseFragmentImpl;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.help.SomeData;
import wenran.com.baselibrary.utils.RouterPath;
import wenran.com.module.home.R;
import wenran.com.module.home.R2;
import wenran.com.module.home.adapter.SearchResultAdapter;
import wenran.com.module.home.bean.SearchCourseResultBean;
import wenran.com.module.home.bean.SearchDetailParamBean;
import wenran.com.module.home.constant.HomeConstantTag;
import wenran.com.module.home.contract.SearchResultContract;
import wenran.com.module.home.presenter.SearchResultResultPresenterImpl;

/**
 * Created by Crowhine on 2019/3/4
 *
 * @author Crowhine
 */
@Route(path = RouterPath.HOME_SEARCH_RESULT_FRAGMENT)
public class SearchResultFragment extends BaseFragmentImpl<SearchResultContract.ISearchResultPresenter>
        implements SearchResultContract.ISearchResultView, OnRefreshLoadMoreListener {
    @BindView(R2.id.search_result_recycler_view)
    RecyclerView searchResultRecyclerView;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    @Autowired()
    String dataTag;
    private SearchResultAdapter searchResultAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_search_result_fragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, getRootView());
        refreshLayout.setOnRefreshLoadMoreListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getSelfActivity());
        searchResultRecyclerView.setLayoutManager(linearLayoutManager);
//        getLoadService(getRootView(), ErrorCallback.class, new Callback.OnReloadListener() {
//            @Override
//            public void onReload(View v) {
//                showLoadingProgress(null);
//                getPresenter().getSearchResultData(HomeConstantTag.INIT_RECYCLER_VIEW, new SearchDetailParamBean(1, dataTag, null));
//
//            }
//        });
        getPresenter().getSearchResultData(HomeConstantTag.INIT_RECYCLER_VIEW, new SearchDetailParamBean(1, dataTag, null));

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected SearchResultContract.ISearchResultPresenter bindPresenter() {
        return new SearchResultResultPresenterImpl(this);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishLoadMore(ConstantNum.REQUEST_TIME);
        getPresenter().getSearchResultData(HomeConstantTag.DO_MORE,
                new SearchDetailParamBean(getPresenter().getCurrentPage() + 1,
                        dataTag, null));
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh(ConstantNum.REQUEST_TIME);
        getPresenter().getSearchResultData(HomeConstantTag.DO_REFRESH,
                new SearchDetailParamBean(1, dataTag, null));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SomeData event) {
        if (event.getTag().equals(HomeConstantTag.UPDATE_SEARCH_RESULT.getTagValue())) {
            dataTag = ((String) event.getT());
            getPresenter().getSearchResultData(HomeConstantTag.DO_REFRESH, new SearchDetailParamBean(1, dataTag, null));
        }
    }

    @Override
    public void dealInitSearchResultData(List<SearchCourseResultBean.DataBean> data) {
        hideLoadingProgress();
//        showLoadServiceSuccess();
        searchResultAdapter = new SearchResultAdapter(getSelfActivity(), data);
        searchResultRecyclerView.setAdapter(searchResultAdapter);
    }

    @Override
    public void dealRefresh(boolean isSuccess, List<SearchCourseResultBean.DataBean> data) {
        refreshLayout.finishRefresh(isSuccess);
        if (searchResultAdapter != null) {
            searchResultAdapter.refreshData(data);
        }
    }

    @Override
    public void dealMore(boolean isSuccess, List<SearchCourseResultBean.DataBean> data) {
        refreshLayout.finishLoadMore(isSuccess);
        if (isSuccess) {
            if (searchResultAdapter != null) {
                searchResultAdapter.addData(data);
            }
        }
    }

    @Override
    public void dealNoMoreData(HomeConstantTag type) {
        switch (type) {
            case INIT_RECYCLER_VIEW:
                //替换成没有数据的视图

                break;
            case DO_REFRESH:
                //替换成没有数据的视图
                refreshLayout.finishRefresh(false);
                break;
            case DO_MORE:
                refreshLayout.finishLoadMoreWithNoMoreData();
                break;
            default:
                break;
        }
    }
}
