package wenran.com.module.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kingja.loadsir.callback.Callback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import wenran.com.baselibrary.base.basemvp.BaseActivityImpl;
import wenran.com.baselibrary.bean.StandardResultBean;
import wenran.com.baselibrary.callbackrepalce.EmptyCallback;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.utils.RouterPath;
import wenran.com.module.mine.R;
import wenran.com.module.mine.R2;
import wenran.com.module.mine.adapter.MessageAdapter;
import wenran.com.module.mine.bean.ChangeMessageStatusParam;
import wenran.com.module.mine.bean.MessageParamBean;
import wenran.com.module.mine.bean.MessageResultBean;
import wenran.com.module.mine.contract.MessageContract;
import wenran.com.module.mine.presenter.MessagePresenterImpl;

/**
 * @author crowhine
 */
@Route(path = RouterPath.MINE_MESSAGE_ACTIVITY)
public class MessageActivity extends BaseActivityImpl<MessageContract.IMessagePresenter> implements OnRefreshLoadMoreListener,
        MessageContract.IMessageView {

    @BindView(R2.id.base_title_tv_center)
    TextView baseTitleTvCenter;
    @BindView(R2.id.message_recycler_view)
    RecyclerView messageRecyclerView;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R2.id.base_title_iv_left)
    ImageView baseTitleIvLeft;
    private MessageAdapter messageAdapter;
    private Unbinder bind;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind!=null){
            bind.unbind();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bind = ButterKnife.bind(this);
        baseTitleTvCenter.setText(R.string.message_title);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        setNoDataView();
        getMessage(ConstantTag.INIT_MESSAGE_RECYCLER_VIEW, 1);
    }

    @Override
    protected MessageContract.IMessagePresenter bindPresenter() {
        return new MessagePresenterImpl(this);
    }

    @Override
    public void initMessageRecyclerView(boolean isSuccess, final MessageResultBean data) {
        hideLoadingProgress();
        if (isSuccess) {
            loadService.showSuccess();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getSelfActivity());
            messageRecyclerView.setLayoutManager(linearLayoutManager);
            messageAdapter = new MessageAdapter(getSelfActivity(), this, data.getData());
            messageRecyclerView.setAdapter(messageAdapter);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishLoadMore(ConstantNum.REQUEST_TIME);
        getMessage(ConstantTag.MESSAGE_MORE, getPresenter().getCurrentPage() + 1);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh(ConstantNum.REQUEST_TIME);
        getMessage(ConstantTag.MESSAGE_REFRESH, 1);
    }


    @Override
    public void getMessageRefresh(boolean isSuccess, MessageResultBean data) {
        refreshLayout.finishRefresh(isSuccess);
        if (isSuccess) {
            if (messageAdapter != null) {
                messageAdapter.refreshData(data.getData());
            } else {
                dealFailure(data.getStatusCode(), data.getMessage());
            }
        }
    }

    @Override
    public void getMessageMore(boolean isSuccess, MessageResultBean data) {
        refreshLayout.finishLoadMore(isSuccess);
        if (isSuccess) {
            if (messageAdapter != null) {
                messageAdapter.addData(data.getData());
            } else {
                dealFailure(data.getStatusCode(), data.getMessage());
            }
        }
    }

    @Override
    public void deleteMessage(boolean isSuccess, int position, StandardResultBean data) {
        hideLoadingProgress();
        if (isSuccess) {
            showHint(data.getMessage());
            if (messageAdapter != null) {
                messageAdapter.removeItem(position);
            }
        } else {
            dealFailure(data.getStatusCode(), data.getMessage());
        }
    }

    @Override
    public void noMoreData(ConstantTag type) {
        switch (type) {
            case INIT_MESSAGE_RECYCLER_VIEW:
                //替换成没有数据的视图

                break;
            case MESSAGE_REFRESH:
                //替换成没有数据的视图
                refreshLayout.finishRefresh(false);
                break;
            case MESSAGE_MORE:
                refreshLayout.finishLoadMoreWithNoMoreData();
                break;
            default:
                break;
        }

    }

    /**
     * 获取消息数据
     */
    private void getMessage(ConstantTag type, int page) {
        getPresenter().getMessage(type, new MessageParamBean(null, page));
    }

    @OnClick(R2.id.base_title_iv_left)
    public void onViewClicked() {
        doFinish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            doFinish();
        }
        return true;
    }

    private void doFinish() {
        Intent intent = getIntent();
        setResult(ConstantNum.MESSAGE_TO_MAIN_RESULT_CODE, intent);
        finish();
    }

    @Override
    public void setNoDataView() {
        getLoadService(refreshLayout, EmptyCallback.class, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                showLoadingProgress(null);
                getMessage(ConstantTag.INIT_MESSAGE_RECYCLER_VIEW, 1);
            }
        });
    }

    @Override
    public void deleteMessage(int position) {
        showLoadingProgress(null);
        int rid = messageAdapter == null ? 0 : messageAdapter.getRid(position);
        getPresenter().deleteMessage(new ChangeMessageStatusParam(rid), position);
    }

}
