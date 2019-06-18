package wenran.com.module.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kingja.loadsir.callback.Callback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import wenran.com.baselibrary.base.basemvp.BaseFragmentImpl;
import wenran.com.baselibrary.callbackrepalce.PlaceholderCallback;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.help.GlideImageLoader;
import wenran.com.baselibrary.help.MyData;
import wenran.com.baselibrary.utils.DealClick;
import wenran.com.baselibrary.utils.GlideUtil;
import wenran.com.baselibrary.utils.MyToast;
import wenran.com.baselibrary.utils.RouterPath;
import wenran.com.baselibrary.utils.RouterUtil;
import wenran.com.module.home.R;
import wenran.com.module.home.R2;
import wenran.com.module.home.adapter.RamblingSetAdapter;
import wenran.com.module.home.adapter.SingleClassAdapter;
import wenran.com.module.home.adapter.SpecialClassAdapter;
import wenran.com.module.home.bean.HomeDataParamBean;
import wenran.com.module.home.bean.HomeDataResultBean;
import wenran.com.module.home.contract.HomeContract;
import wenran.com.module.home.presenter.HomePresenterImpl;

/**
 * Created by Crowhine on 2019/1/29
 *
 * @author Crowhine
 */
@Route(path = RouterPath.HOME_MAIN_ACTIVITY)
public class
HomeFragment extends BaseFragmentImpl<HomeContract.IHomePresenter> implements
        HomeContract.IHomeView, OnRefreshLoadMoreListener {
    @BindView(R2.id.search_tv_search)
    TextView searchTvSearch;
    @BindView(R2.id.search_iv_msg)
    ImageView searchIvMsg;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    @BindView(R2.id.home_banner)
    Banner homeBanner;
    @BindView(R2.id.home_rambling_set_more)
    TextView homeRamblingSetMore;
    @BindView(R2.id.home_rambling_set_more_arrow)
    ImageView homeRamblingSetMoreArrow;
    @BindView(R2.id.home_rambling_set_recycler_view)
    RecyclerView homeRamblingSetRecyclerView;
    @BindView(R2.id.home_single_class_more)
    TextView homeSingleClassMore;
    @BindView(R2.id.home_single_class_more_arrow)
    ImageView homeSingleClassMoreArrow;
    @BindView(R2.id.home_single_class_recycler_view)
    RecyclerView homeSingleClassRecyclerView;
    @BindView(R2.id.home_special_class_more)
    TextView homeSpecialClassMore;
    @BindView(R2.id.home_special_class_more_arrow)
    ImageView homeSpecialClassMoreArrow;
    @BindView(R2.id.home_special_class_recycler_view)
    RecyclerView homeSpecialClassRecyclerView;
    @BindView(R2.id.home_nsv)
    NestedScrollView homeNsv;
    private RamblingSetAdapter ramblingSetAdapter;
    private View rootView;
    private SpecialClassAdapter specialClassAdapter;
    private SingleClassAdapter singleClassAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        homeNsv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (oldScrollY - scrollY > ConstantNum.SCROLL_VALUE) {
                    //向上滑,隐藏
                    RouterUtil.setPlayerShowStatus(false);
                } else if (scrollY - oldScrollY > ConstantNum.SCROLL_VALUE) {
                    //向下滑，显示
                    RouterUtil.setPlayerShowStatus(true);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (homeBanner != null) {
            homeBanner.startAutoPlay();
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (ramblingSetAdapter != null) {
            ramblingSetAdapter.registerEventBus(isVisibleToUser);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (homeBanner != null) {
            homeBanner.stopAutoPlay();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (specialClassAdapter!=null){
            specialClassAdapter.registerEventBus(false);
        }
        if (singleClassAdapter!=null){
            singleClassAdapter.registerEventBus(false);
        }
        unbinder.unbind();
    }

    /**
     * 实例化
     *
     * @param data
     */
    public static HomeFragment newInstance(MyData data) {
        HomeFragment f = new HomeFragment();
        Bundle b = new Bundle();
        b.putParcelable(ConstantTag.DATA_TAG.name(), data);
        f.setArguments(b);
        return f;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_home_fragment;
    }

    @Override
    protected HomeContract.IHomePresenter bindPresenter() {
        return new HomePresenterImpl(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        refreshLayout.setOnRefreshLoadMoreListener(this);
        getLoadService(refreshLayout, PlaceholderCallback.class, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                getPresenter().getHomeData(new HomeDataParamBean());
                showLoadingProgress(null);
            }
        });
        getPresenter().getHomeData(new HomeDataParamBean());
        getPresenter().getMessageStatus(new HomeDataParamBean());
    }

    @Override
    public void initBanner(List<Integer> imageIdList, List<String> imageList) {
        homeBanner.setImages(imageList).setImageLoader(new GlideImageLoader()).start();
        homeBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(final int position) {
                DealClick.deal(getSelfActivity(), new DealClick.ClickCallback() {
                    @Override
                    public void isSuccess() {
                        MyToast.s(getSelfActivity(), getResources().getString(R.string.developing) + position, 0);
                    }
                });
            }
        });
    }

    @Override
    public void initRamblingSetRv(List<HomeDataResultBean.DataBean.RambleBean> ramblingSetList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getSelfActivity());
        homeRamblingSetRecyclerView.setLayoutManager(linearLayoutManager);
        ramblingSetAdapter = new RamblingSetAdapter(getSelfActivity(), ramblingSetList);
        homeRamblingSetRecyclerView.setAdapter(ramblingSetAdapter);
        //注册播放器消息接受
        ramblingSetAdapter.registerEventBus(true);
    }

    @Override
    public void initSingleClass(List<HomeDataResultBean.DataBean.IndividualBean> singleClasses) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getSelfActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        homeSingleClassRecyclerView.setLayoutManager(linearLayoutManager);
        singleClassAdapter = new SingleClassAdapter(getSelfActivity(), singleClasses);
        homeSingleClassRecyclerView.setAdapter(singleClassAdapter);
    }

    @Override
    public void initSpecialClass(List<HomeDataResultBean.DataBean.SpecialBean> specialClasses) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getSelfActivity());
        homeSpecialClassRecyclerView.setLayoutManager(linearLayoutManager);
        specialClassAdapter = new SpecialClassAdapter(getSelfActivity(), specialClasses);
        homeSpecialClassRecyclerView.setAdapter(specialClassAdapter);
    }

    @Override
    public void dealMessageStatus(boolean isSuccess, int code, String message) {
        if (isSuccess) {
            GlideUtil.setImageByRes(getSelfActivity(), R.mipmap.message_new, searchIvMsg);
        } else {
            GlideUtil.setImageByRes(getSelfActivity(), R.mipmap.search_msg_normal, searchIvMsg);
        }
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishLoadMore(ConstantNum.REQUEST_TIME);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getPresenter().getHomeData(new HomeDataParamBean());
        getPresenter().getMessageStatus(new HomeDataParamBean());
        refreshLayout.finishRefresh(ConstantNum.REQUEST_TIME);
    }

    @Override
    public void getHomeDataSuccess(HomeDataResultBean data) {
        loadService.showSuccess();
        hideLoadingProgress();
        refreshLayout.finishRefresh(true);
    }

    @Override
    public void getHomeDataFailure(Integer code, String msg) {
        dealFailure(code, msg);
        hideLoadingProgress();
        refreshLayout.finishRefresh(false);
    }


    @OnClick({R2.id.search_tv_search, R2.id.search_iv_msg, R2.id.home_rambling_set_more,
            R2.id.home_rambling_set_more_arrow, R2.id.home_single_class_more,
            R2.id.home_single_class_more_arrow, R2.id.home_special_class_more,
            R2.id.home_special_class_more_arrow})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.search_tv_search) {
            ARouter.getInstance().build(RouterPath.HOME_SEARCH_ACTIVITY).navigation();
        } else if (view.getId() == R.id.search_iv_msg) {
            DealClick.deal(getSelfActivity(), new DealClick.ClickCallback() {
                @Override
                public void isSuccess() {
                    ARouter.getInstance().build(RouterPath.MINE_MESSAGE_ACTIVITY).navigation(getActivity(), ConstantNum.MAIN_TO_MESSAGE_REQUEST_CODE);
                }
            });
        } else if (view.getId() == R.id.home_rambling_set_more
                || view.getId() == R.id.home_rambling_set_more_arrow) {
            ARouter.getInstance().build(RouterPath.RAMBLING_SET_MORE_ACTIVITY)
                    .navigation(getActivity());
        } else if (view.getId() == R.id.home_single_class_more ||
                view.getId() == R.id.home_single_class_more_arrow) {
            ARouter.getInstance().build(RouterPath.SPECIAL_CLASS_MORE_ACTIVITY)
                    .withBoolean(ConstantTag.DATA_TAG.getTagValue(), false)
                    .navigation(getActivity());
        } else if (view.getId() == R.id.home_special_class_more ||
                view.getId() == R.id.home_special_class_more_arrow) {
            ARouter.getInstance().build(RouterPath.SPECIAL_CLASS_MORE_ACTIVITY)
                    .withBoolean(ConstantTag.DATA_TAG.getTagValue(), true)
                    .navigation(getActivity());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantNum.MAIN_TO_MESSAGE_REQUEST_CODE
                && resultCode == ConstantNum.MESSAGE_TO_MAIN_RESULT_CODE) {
            //更新消息状态
            getPresenter().getMessageStatus(new HomeDataParamBean());
        }
    }
}
