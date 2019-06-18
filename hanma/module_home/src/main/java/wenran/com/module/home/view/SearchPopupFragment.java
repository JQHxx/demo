package wenran.com.module.home.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import wenran.com.baselibrary.base.basemvp.BaseFragmentImpl;
import wenran.com.baselibrary.help.SomeData;
import wenran.com.baselibrary.utils.RouterPath;
import wenran.com.module.home.R;
import wenran.com.module.home.R2;
import wenran.com.module.home.adapter.PopupSearchAdapter;
import wenran.com.module.home.bean.PopupSearchParamBean;
import wenran.com.module.home.constant.HomeConstantTag;
import wenran.com.module.home.contract.PopupSearchContract;
import wenran.com.module.home.presenter.PopupSearchPresenterImpl;

/**
 * Created by Crowhine on 2019/3/4
 *
 * @author Crowhine
 */
@Route(path = RouterPath.HOME_SEARCH_POPUP_FRAGMENT)
public class SearchPopupFragment extends BaseFragmentImpl<PopupSearchContract.IPopupSearchPresenter> implements PopupSearchContract.IPopupSearchView {
    @BindView(R2.id.popup_rv)
    RecyclerView popupRv;
    Unbinder unbinder;
    private LinearLayoutManager linearLayoutManager;
    private PopupSearchAdapter popupSearchAdapter;
    @Autowired()
    String dataTag;


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
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_popup_fragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, getRootView());
        linearLayoutManager = new LinearLayoutManager(getSelfActivity());
        popupRv.setLayoutManager(linearLayoutManager);
        showLoadingProgress(null);
        getPopupSearchData();
    }


    @Override
    protected PopupSearchContract.IPopupSearchPresenter bindPresenter() {
        return new PopupSearchPresenterImpl(this);
    }

    @Override
    public void dealPopupSearchData(boolean isSuccess, List<String> data) {
        hideLoadingProgress();
        if (isSuccess){
//            showLoadServiceSuccess();
            if (popupSearchAdapter == null) {
                popupSearchAdapter = new PopupSearchAdapter(getSelfActivity(), data);
                popupRv.setAdapter(popupSearchAdapter);
            } else {
                popupSearchAdapter.refreshData(data);
            }
        }else {
//            getLoadService(getRootView(), ErrorCallback.class, new Callback.OnReloadListener() {
//                @Override
//                public void onReload(View v) {
//                    showLoadingProgress(null);
//                    getPopupSearchData();
//                }
//            });
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (popupSearchAdapter != null) {
                popupSearchAdapter.clearData();
            }
        } else {
            getPopupSearchData();
        }
    }

    private void getPopupSearchData() {
        getPresenter().getPopupSearchData(new PopupSearchParamBean(dataTag));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SomeData event) {
        if (event.getTag().equals(HomeConstantTag.UPDATE_POPUP_SEARCH.getTagValue())) {
            dataTag = (String) event.getT();
            getPopupSearchData();
        } else if (event.getTag().equals(HomeConstantTag.NO_UPDATE_POPUP_SEARCH.getTagValue())) {
            dataTag = (String) event.getT();
        }
    }
}
