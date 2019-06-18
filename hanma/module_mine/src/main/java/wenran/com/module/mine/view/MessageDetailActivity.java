package wenran.com.module.mine.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import wenran.com.baselibrary.base.basemvp.BaseActivityImpl;
import wenran.com.baselibrary.utils.GlideUtil;
import wenran.com.module.mine.R;
import wenran.com.module.mine.R2;
import wenran.com.module.mine.bean.MessageResultBean;
import wenran.com.module.mine.contract.MessageDetailContract;
import wenran.com.module.mine.presenter.MessageDetailPresenterImpl;

/**
 * @author crowhine
 */
public class MessageDetailActivity extends BaseActivityImpl<MessageDetailContract.IMessageDetailPresenter>
        implements MessageDetailContract.IMessageDetailView {

    @BindView(R2.id.base_title_tv_center)
    TextView baseTitleTvCenter;
    @BindView(R2.id.message_detail_tv_time)
    TextView messageDetailTvTime;
    @BindView(R2.id.message_detail_iv_img)
    ImageView messageDetailIvImg;
    @BindView(R2.id.message_detail_tv_title)
    TextView messageDetailTvTitle;
    @BindView(R2.id.message_detail_tv_content)
    TextView messageDetailTvContent;
    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind!=null){
            bind.unbind();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bind = ButterKnife.bind(this);
        baseTitleTvCenter.setText(R.string.message_detail_title);
        showLoadingProgress(null);
        getPresenter().getIntentData();
    }

    @Override
    protected MessageDetailContract.IMessageDetailPresenter bindPresenter() {
        return new MessageDetailPresenterImpl(this);
    }


    @Override
    public void changeMessageStatus(boolean isSuccess, int code, String msg) {
        hideLoadingProgress();
    }

    @Override
    public void initView(MessageResultBean.DataBean dataBean) {
        messageDetailTvTime.setText(dataBean.getCreatedAt());
        MessageResultBean.DataBean.MessageBean message = dataBean.getMessage();
        GlideUtil.setImageByUrl(getSelfActivity(), message.getAvatar(),
                messageDetailIvImg, null, null);
        messageDetailTvTitle.setText(message.getTitle());
        messageDetailTvContent.setText(message.getContent());
    }

    @OnClick(R2.id.base_title_iv_left)
    public void onViewClicked() {
        finish();
    }
}
