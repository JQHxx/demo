package wenran.com.module.home.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kingja.loadsir.callback.Callback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import wenran.com.baselibrary.base.basemvp.BaseActivityImpl;
import wenran.com.baselibrary.callbackrepalce.ErrorCallback;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.utils.RouterPath;
import wenran.com.baselibrary.utils.RouterUtil;
import wenran.com.module.home.R;
import wenran.com.module.home.R2;
import wenran.com.module.home.adapter.ClassMoreAdapter;
import wenran.com.module.home.bean.CourseMoreParamBean;
import wenran.com.module.home.bean.SpecialMoreResultBean;
import wenran.com.module.home.constant.HomeConstantTag;
import wenran.com.module.home.contract.SpecialCourseMoreContract;
import wenran.com.module.home.presenter.CourseMorePresenterImpl;
import wenran.com.module_player.service.AudioPlayerBinder;

/**
 * @author crowhine
 */
@Route(path = RouterPath.SPECIAL_CLASS_MORE_ACTIVITY)
public class SpecialCourseMoreActivity extends BaseActivityImpl<SpecialCourseMoreContract.ISpecialMorePresenter>
        implements SpecialCourseMoreContract.ISpecialMoreView, OnRefreshLoadMoreListener {

    @BindView(R2.id.fresh_head)
    ClassicsHeader freshHead;
    @BindView(R2.id.special_class_recycler_view)
    RecyclerView specialClassRecyclerView;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder bind;
    @Autowired()
    boolean dataTag;
    @BindView(R2.id.base_title_tv_center)
    TextView baseTitleTvCenter;
    @BindView(R2.id.player_ll_all)
    LinearLayout playerLlAll;
    @BindView(R2.id.special_class_more_nsv)
    NestedScrollView specialClassMoreNsv;
    private ClassMoreAdapter classMoreAdapter;
    private AudioPlayerBinder audioPlayerBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (audioPlayerBinder != null) {
            audioPlayerBinder.doResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (audioPlayerBinder != null) {
            audioPlayerBinder.doPause();
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_special_class_more;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        bind = ButterKnife.bind(this);
        if (dataTag) {
            baseTitleTvCenter.setText(R.string.special_class_tv);
        } else {
            baseTitleTvCenter.setText(R.string.single_class_tv);
        }
        refreshLayout.setOnRefreshLoadMoreListener(this);
        specialClassMoreNsv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getSelfActivity());
        specialClassRecyclerView.setLayoutManager(linearLayoutManager);
        showLoadingProgress(null);
        getMoreData(HomeConstantTag.INIT_RECYCLER_VIEW, 1);
    }

    @Override
    protected SpecialCourseMoreContract.ISpecialMorePresenter bindPresenter() {
        return new CourseMorePresenterImpl(this);
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
    public void dealInitSpecialMoreData(boolean isSuccess, List<SpecialMoreResultBean.DataBean> data) {
        hideLoadingProgress();
        if (isSuccess) {
            showLoadServiceSuccess();
            classMoreAdapter = new ClassMoreAdapter(getSelfActivity(), data);
            specialClassRecyclerView.setAdapter(classMoreAdapter);

            if (audioPlayerBinder == null) {
                audioPlayerBinder = new AudioPlayerBinder(playerLlAll);
                audioPlayerBinder.registerEventBus(true);
            }
        } else {
            doLoadSir();
        }

    }

    @Override
    public void dealRefresh(boolean isSuccess, List<SpecialMoreResultBean.DataBean> data) {
        refreshLayout.finishRefresh(isSuccess);
        if (classMoreAdapter != null) {
            classMoreAdapter.refreshData(data);
        }
    }

    @Override
    public void dealMore(boolean isSuccess, List<SpecialMoreResultBean.DataBean> data) {
        refreshLayout.finishLoadMore(isSuccess);
        if (isSuccess) {
            if (classMoreAdapter != null) {
                classMoreAdapter.addData(data);
            }
        }
    }

    @Override
    public void dealNoMoreData(HomeConstantTag type) {
        switch (type) {
            case INIT_RECYCLER_VIEW:
                //替换成没有数据的视图
                doLoadSir();
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

    /**
     * 获取更多数据
     *
     * @param homeConstantTag
     * @param page
     */
    private void getMoreData(HomeConstantTag homeConstantTag, int page) {
        getPresenter().getSpecialMoreData(dataTag, homeConstantTag,
                new CourseMoreParamBean(page, null, null));

    }

    @OnClick(R2.id.base_title_iv_left)
    public void onViewClicked() {
        finish();
    }


    private void doLoadSir() {
        getLoadService(refreshLayout, ErrorCallback.class, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                showLoadingProgress(null);
                getMoreData(HomeConstantTag.INIT_RECYCLER_VIEW, 1);
            }
        });
    }
}
