package wenran.com.module.home.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
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
import wenran.com.baselibrary.base.basemvp.BaseActivityImpl;
import wenran.com.baselibrary.callbackrepalce.ErrorCallback;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.help.GlideImageLoader;
import wenran.com.baselibrary.utils.DealClick;
import wenran.com.baselibrary.utils.MyToast;
import wenran.com.baselibrary.utils.RouterPath;
import wenran.com.baselibrary.utils.RouterUtil;
import wenran.com.module.home.R;
import wenran.com.module.home.R2;
import wenran.com.module.home.adapter.RamblingSetMoreAdapter;
import wenran.com.module.home.bean.RamblingSetMoreParamBean;
import wenran.com.module.home.bean.RamblingSetMoreResultBean;
import wenran.com.module.home.constant.HomeConstantTag;
import wenran.com.module.home.contract.RamblingSetMoreContract;
import wenran.com.module.home.presenter.RamblingSetMorePresenterImpl;
import wenran.com.module_player.service.AudioPlayerBinder;

/**
 * 漫谈集更多
 *
 * @author crowhine
 */
@Route(path = RouterPath.RAMBLING_SET_MORE_ACTIVITY)
public class RamblingSetMoreActivity extends BaseActivityImpl<RamblingSetMoreContract.IRamblingSetPresenter>
        implements RamblingSetMoreContract.IRamblingSetMoreView, OnRefreshLoadMoreListener {

    @BindView(R2.id.base_title_tv_center)
    TextView baseTitleTvCenter;
    @BindView(R2.id.rambling_set_more_banner)
    Banner ramblingSetMoreBanner;
    @BindView(R2.id.home_rambling_set_more_recycler_view)
    RecyclerView homeRamblingSetMoreRecyclerView;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder bind;
    @BindView(R2.id.rambling_set_iv_order)
    ImageView ramblingSetIvOrder;
    @BindView(R2.id.rambling_set_more_content_ll)
    LinearLayout ramblingSetMoreContentLl;
    @BindView(R2.id.rambling_set_tv_order)
    TextView ramblingSetTvOrder;
    @BindView(R2.id.player_ll_all)
    LinearLayout playerLlAll;
    @BindView(R2.id.rambling_set_more_nsv)
    NestedScrollView ramblingSetMoreNsv;
    private int orderType = 1;
    private RamblingSetMoreAdapter ramblingSetMoreAdapter;
    private AudioPlayerBinder audioPlayerBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ramblingSetMoreAdapter != null) {
            ramblingSetMoreAdapter.registerEventBus(true);
        }
        if (audioPlayerBinder != null) {
            audioPlayerBinder.doResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ramblingSetMoreAdapter != null) {
            ramblingSetMoreAdapter.registerEventBus(false);
        }
        if (audioPlayerBinder != null) {
            audioPlayerBinder.doPause();
        }
    }

    @Override
    protected void onDestroy() {
        if (bind != null) {
            bind.unbind();
        }
        if (audioPlayerBinder != null) {
            audioPlayerBinder.doDestroy();
        }
        super.onDestroy();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rambling_set_more;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bind = ButterKnife.bind(this);
        ramblingSetMoreNsv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
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
        baseTitleTvCenter.setText(R.string.rambling_set_tx);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getSelfActivity());
        homeRamblingSetMoreRecyclerView.setLayoutManager(linearLayoutManager);
        getMoreData(HomeConstantTag.INIT_RECYCLER_VIEW, 1);
    }


    @Override
    protected RamblingSetMoreContract.IRamblingSetPresenter bindPresenter() {
        return new RamblingSetMorePresenterImpl(this);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishLoadMore(ConstantNum.REQUEST_TIME);
        getMoreData(HomeConstantTag.DO_MORE, getPresenter().getCurrentPage() + 1);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh(ConstantNum.REQUEST_TIME);
        getMoreData(HomeConstantTag.DO_REFRESH, 1);
    }

    @Override
    public void dealInitRamblingSetMoreData(boolean isSuccess, List<RamblingSetMoreResultBean.DataBean.RambleBean> ramble) {
        if (isSuccess) {
            showLoadServiceSuccess();
            refreshLayout.setVisibility(View.VISIBLE);
            ramblingSetMoreAdapter = new RamblingSetMoreAdapter(getSelfActivity(), ramble);
            homeRamblingSetMoreRecyclerView.setAdapter(ramblingSetMoreAdapter);
            ramblingSetMoreAdapter.registerEventBus(true);

            if (audioPlayerBinder == null) {
                audioPlayerBinder = new AudioPlayerBinder(playerLlAll);
                audioPlayerBinder.registerEventBus(true);
            }
        } else {
            refreshLayout.setVisibility(View.GONE);
            getLoadService(ramblingSetMoreContentLl, ErrorCallback.class, new Callback.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    getMoreData(HomeConstantTag.INIT_RECYCLER_VIEW, 1);
                }
            });
        }
    }

    @Override
    public void showException(String exceptionInfo) {
        super.showException(exceptionInfo);
        getLoadService(ramblingSetMoreContentLl, ErrorCallback.class, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                getMoreData(HomeConstantTag.INIT_RECYCLER_VIEW, 1);
            }
        });
    }

    @Override
    public void dealInitRamblingSetMoreBanner(List<Integer> imageIdList, List<String> imageList) {
        ramblingSetMoreBanner.setVisibility(View.VISIBLE);
        ramblingSetMoreBanner.setImages(imageList).setImageLoader(new GlideImageLoader()).start();
        ramblingSetMoreBanner.setOnBannerListener(new OnBannerListener() {
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
    public void dealRefresh(boolean isSuccess, List<RamblingSetMoreResultBean.DataBean.RambleBean> ramble) {
        refreshLayout.finishRefresh(isSuccess);
        if (ramblingSetMoreAdapter != null) {
            ramblingSetMoreAdapter.refreshData(ramble);
        }
    }

    @Override
    public void dealMore(boolean isSuccess, List<RamblingSetMoreResultBean.DataBean.RambleBean> ramble) {
        refreshLayout.finishLoadMore(isSuccess);
        if (isSuccess) {
            if (ramblingSetMoreAdapter != null) {
                ramblingSetMoreAdapter.addData(ramble);
            }
        }
    }

    @Override
    public void dealNoMoreData(HomeConstantTag type) {
        switch (type) {
            case INIT_RECYCLER_VIEW:
                //替换成没有数据的视图
                getLoadService(ramblingSetMoreContentLl, ErrorCallback.class, new Callback.OnReloadListener() {
                    @Override
                    public void onReload(View v) {
                        getMoreData(HomeConstantTag.INIT_RECYCLER_VIEW, 1);
                    }
                });
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


    @OnClick({R2.id.base_title_iv_left, R2.id.rambling_set_iv_order, R2.id.rambling_set_tv_order})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.base_title_iv_left) {
            finish();
        } else if (view.getId() == R.id.rambling_set_iv_order ||
                view.getId() == R.id.rambling_set_tv_order) {
            if (orderType == 0) {
                changeOrder(1);
                ramblingSetIvOrder.setImageResource(R.mipmap.rambling_set_order_desc);
                ramblingSetTvOrder.setText(R.string.rambling_set_tv_order_desc_tx);
                getMoreData(HomeConstantTag.DO_REFRESH, 1);
            } else {
                changeOrder(0);
                ramblingSetIvOrder.setImageResource(R.mipmap.rambling_set_order_asc);
                ramblingSetTvOrder.setText(R.string.rambling_set_tv_order_asc_tx);
                getMoreData(HomeConstantTag.DO_REFRESH, 1);
            }
        }
    }

    /**
     * 获取更多数据
     *
     * @param homeConstantTag
     * @param page
     */
    private void getMoreData(HomeConstantTag homeConstantTag, int page) {
        getPresenter().getRamblingSetMoreData(homeConstantTag,
                new RamblingSetMoreParamBean(page, orderType, null));
    }

    /**
     * 更改顺序
     */
    private void changeOrder(int orderType) {
        this.orderType = orderType;
    }

}
