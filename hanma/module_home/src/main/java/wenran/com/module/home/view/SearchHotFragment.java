package wenran.com.module.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.gujun.android.taggroup.TagGroup;
import wenran.com.baselibrary.base.basemvp.BaseFragmentImpl;
import wenran.com.baselibrary.utils.RouterPath;
import wenran.com.module.home.R;
import wenran.com.module.home.R2;
import wenran.com.module.home.contract.HotSearchContract;
import wenran.com.module.home.presenter.HotSearchPresenterImpl;

/**
 * Created by Crowhine on 2019/3/4
 *
 * @author Crowhine
 */
@Route(path = RouterPath.HOME_SEARCH_HOT_FRAGMENT)
public class SearchHotFragment extends BaseFragmentImpl<HotSearchContract.IHotSearchPresenter> implements HotSearchContract.IHotSearchView {
    @BindView(R2.id.hot_search_tl_history)
    TagGroup hotSearchTlHistory;
    @BindView(R2.id.hot_search_ll_history)
    LinearLayout hotSearchLlHistory;
    @BindView(R2.id.hot_search_tl_hot_search)
    TagGroup hotSearchTlHotSearch;
    @BindView(R2.id.hot_search_ll_hot_search)
    LinearLayout hotSearchLlHotSearch;
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_search_hot_fragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, getRootView());
        showLoadingProgress(null);
        getPresenter().getHotSearchData();
        getPresenter().getHistorySearchData();
    }

    @Override
    protected HotSearchContract.IHotSearchPresenter bindPresenter() {
        return new HotSearchPresenterImpl(this);
    }

    @Override
    public void dealHotSearchDataSuccess(List<String> stringList) {
        hideLoadingProgress();
//        showLoadServiceSuccess();
        hotSearchLlHotSearch.setVisibility(View.VISIBLE);
        hotSearchTlHotSearch.setTags(stringList);
        hotSearchTlHotSearch.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                getPresenter().dealClick(tag);
            }
        });
    }

    @Override
    public void dealHotSearchDataFailure(int code, String msg) {
        hideLoadingProgress();
        hotSearchLlHotSearch.setVisibility(View.GONE);
        dealFailure(code,msg);
//        getLoadService(getRootView(), ErrorCallback.class, new Callback.OnReloadListener() {
//            @Override
//            public void onReload(View v) {
//                showLoadingProgress(null);
//                getPresenter().getHotSearchData();
//            }
//        });
    }

    @Override
    public void dealHistoryDataSuccess(List<String> data) {
        hotSearchLlHistory.setVisibility(View.VISIBLE);
        hotSearchTlHistory.setTags(data);
        hotSearchTlHistory.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                getPresenter().dealClick(tag);
            }
        });
    }

    @Override
    public void dealHistoryDataSuccessFailure() {
        hotSearchLlHistory.setVisibility(View.GONE);
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


}
