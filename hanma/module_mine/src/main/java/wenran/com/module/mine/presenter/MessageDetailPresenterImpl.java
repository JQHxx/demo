package wenran.com.module.mine.presenter;

import android.content.Intent;
import android.os.Parcelable;

import wenran.com.baselibrary.base.basemvp.BasePresenterImpl;
import wenran.com.baselibrary.bean.StandardResultBean;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.module.mine.bean.ChangeMessageStatusParam;
import wenran.com.module.mine.bean.MessageResultBean;
import wenran.com.module.mine.contract.MessageDetailContract;
import wenran.com.module.mine.model.NetModel;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */
public class MessageDetailPresenterImpl extends BasePresenterImpl<MessageDetailContract.IMessageDetailView,NetModel>
        implements MessageDetailContract.IMessageDetailPresenter {

    public MessageDetailPresenterImpl(MessageDetailContract.IMessageDetailView mBaseView) {
        super(mBaseView);
        initModel(NetModel.class);
    }

    @Override
    public void getIntentData() {
        if (isViewAttach()) {
            Intent intent = getView().getSelfActivity().getIntent();
            if (intent != null) {
                Parcelable parcelableExtra = intent.getParcelableExtra(ConstantTag.DATA_TAG.getTagValue());
                if (parcelableExtra!=null&&parcelableExtra instanceof MessageResultBean.DataBean) {
                    MessageResultBean.DataBean dataBean = (MessageResultBean.DataBean) parcelableExtra;
                    changeMessageStatus(new ChangeMessageStatusParam(dataBean.getRid()));
                    getView().initView(dataBean);
                }
            }
        }
    }

    /**
     * 改变消息状态
     *
     * @param paramBean 注册需要的参数
     */
    public void changeMessageStatus(ChangeMessageStatusParam paramBean) {
        if (paramBean == null) {
            dealException(ConstantTag.NEED_MORE_INFO.getTagValue(), true, getClass());
            return;
        }
        netModel.changeMessageStatus(paramBean, new INormalCallback<StandardResultBean>() {
            @Override
            public void success(StandardResultBean msg) {
                if (msg == null) {
                    dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
                    return;
                } else {
                    if (isViewAttach() == false) {
                        return;
                    }
                    if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                        getView().changeMessageStatus(true, msg.getStatusCode(), msg.getMessage());
                    } else {
                        getView().changeMessageStatus(false, msg.getStatusCode(), msg.getMessage());
                    }
                }
            }

            @Override
            public void failure(String failureInfo) {
                dealException(failureInfo, true, getClass());
            }
        });
    }
}
