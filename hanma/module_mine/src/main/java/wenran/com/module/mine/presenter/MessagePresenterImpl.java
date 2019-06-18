package wenran.com.module.mine.presenter;


import wenran.com.baselibrary.base.basemvp.BasePresenterImpl;
import wenran.com.baselibrary.bean.StandardResultBean;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.utils.SpUtil;
import wenran.com.module.mine.bean.ChangeMessageStatusParam;
import wenran.com.module.mine.bean.MessageParamBean;
import wenran.com.module.mine.bean.MessageResultBean;
import wenran.com.module.mine.contract.MessageContract;
import wenran.com.module.mine.model.NetModel;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */
public class MessagePresenterImpl extends BasePresenterImpl<MessageContract.IMessageView,NetModel>
        implements MessageContract.IMessagePresenter {

    public MessagePresenterImpl(MessageContract.IMessageView mBaseView) {
        super(mBaseView);
        initModel(NetModel.class);
    }

    /**
     * 当前页码
     */
    private int currentPage = 1;

    @Override
    public void getMessage(final ConstantTag type, MessageParamBean paramBean) {
        if (isViewAttach()) {
            String appToken = SpUtil.getString(getView().getSelfActivity(), ConstantTag.APP_TOKEN.name());
            paramBean.setApptoken(appToken);
        } else {
            dealException(ConstantTag.NEED_MORE_INFO.getTagValue(), true, getClass());
            return;
        }
        netModel.getMessage(paramBean, new INormalCallback<MessageResultBean>() {
            @Override
            public void success(MessageResultBean msg) {
                if (msg == null) {
                    dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
                    return;
                } else {
                    dealDataForSuccess(type, msg);
                }
            }

            @Override
            public void failure(String failureInfo) {
                dealException(failureInfo, true, getClass());
            }
        });
    }

    @Override
    public void deleteMessage(ChangeMessageStatusParam changeMessageStatusParam, final int position) {
        if (changeMessageStatusParam == null) {
            dealException(ConstantTag.NEED_MORE_INFO.getTagValue(), true, getClass());
            return;
        }
        netModel.deleteMessage(changeMessageStatusParam, new INormalCallback<StandardResultBean>() {
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
                        getView().deleteMessage(true, position, msg);
                    } else {
                        getView().deleteMessage(false, position, msg);
                    }
                }
            }

            @Override
            public void failure(String failureInfo) {
                dealException(failureInfo, true, getClass());
            }
        });
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }


    private void dealDataForSuccess(ConstantTag type, MessageResultBean msg) {
        if (isViewAttach() == false) {
            return;
        }
        switch (type) {
            case INIT_MESSAGE_RECYCLER_VIEW:
                if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                    changePage(type);
                    getView().initMessageRecyclerView(true, msg);
                } else if (ConstantNum.NO_MORE_DATA == msg.getStatusCode()) {
                    getView().noMoreData(type);
                } else {
                    getView().initMessageRecyclerView(false, msg);
                }
                break;
            case MESSAGE_REFRESH:
                if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                    changePage(type);
                    getView().getMessageRefresh(true, msg);
                } else if (ConstantNum.NO_MORE_DATA == msg.getStatusCode()) {
                    getView().noMoreData(type);
                } else {
                    getView().getMessageRefresh(false, msg);
                }
                break;
            case MESSAGE_MORE:
                if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                    changePage(type);
                    getView().getMessageMore(true, msg);
                } else if (ConstantNum.NO_MORE_DATA == msg.getStatusCode()) {
                    getView().noMoreData(type);
                } else {
                    getView().getMessageMore(false, msg);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 更改变请求页数
     *
     * @param type
     */
    private void changePage(ConstantTag type) {
        switch (type) {
            case INIT_MESSAGE_RECYCLER_VIEW:
            case MESSAGE_REFRESH:
                currentPage = 1;
                break;
            case MESSAGE_MORE:
                currentPage++;
                break;
            default:
                break;
        }
    }
}
