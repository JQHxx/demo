package wenran.com.module.mine.contract;


import wenran.com.baselibrary.base.basemvp.IBasePresenter;
import wenran.com.baselibrary.base.basemvp.IBaseView;
import wenran.com.baselibrary.bean.StandardResultBean;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.module.mine.bean.ChangeMessageStatusParam;
import wenran.com.module.mine.bean.MessageResultBean;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */
public final class MessageDetailContract {

    public interface IMessageDetailModel {
        /**
         * 更改消息已读状态
         *
         * @param changeMessageStatusParam
         * @param normalCallback
         */
        void changeMessageStatus(ChangeMessageStatusParam changeMessageStatusParam, INormalCallback<StandardResultBean> normalCallback);
    }


    public interface IMessageDetailView extends IBaseView {
        /**
         * 更改消息已读状态
         *
         * @param isSuccess
         * @param code
         * @param msg
         */
        void changeMessageStatus(boolean isSuccess, int code, String msg);

        /**
         * 初始化view
         *
         * @param dataBean
         */
        void initView(MessageResultBean.DataBean dataBean);

    }

    public interface IMessageDetailPresenter extends IBasePresenter {

        /**
         * 获取intentData
         *
         * @return
         */
        void getIntentData();


    }
}
