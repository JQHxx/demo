package wenran.com.module.mine.model;


import io.reactivex.Observable;
import wenran.com.baselibrary.base.BaseModel;
import wenran.com.baselibrary.bean.StandardResultBean;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.server.RetrofitManager;
import wenran.com.module.mine.bean.ChangeMessageStatusParam;
import wenran.com.module.mine.bean.MessageParamBean;
import wenran.com.module.mine.bean.MessageResultBean;
import wenran.com.module.mine.contract.MessageContract;
import wenran.com.module.mine.contract.MessageDetailContract;

/**
 * 网络获取数据
 * Created by crowhine on 2018/7/26.
 *
 * @author crowhine
 */

public class NetModel extends BaseModel implements MessageContract.IMessageModel, MessageDetailContract.IMessageDetailModel {

    @Override
    public void getMessage(MessageParamBean messageParamBean, INormalCallback<MessageResultBean> normalCallback) {
        Observable<MessageResultBean> homeData = RetrofitManager.getInstance().getRetrofitService(MineService.class).
                getMessages(messageParamBean);
        sendRequest(homeData, normalCallback);
    }

    @Override
    public void deleteMessage(ChangeMessageStatusParam changeMessageStatusParam, INormalCallback<StandardResultBean> normalCallback) {
        Observable<StandardResultBean> homeData = RetrofitManager.getInstance().getRetrofitService(MineService.class).
                deleteMessage(changeMessageStatusParam);
        sendRequest(homeData, normalCallback);
    }

    @Override
    public void changeMessageStatus(ChangeMessageStatusParam changeMessageStatusParam, INormalCallback<StandardResultBean> normalCallback) {
        Observable<StandardResultBean> homeData = RetrofitManager.getInstance().getRetrofitService(MineService.class).
                changeMessageStatus(changeMessageStatusParam);
        sendRequest(homeData, normalCallback);
    }
}
