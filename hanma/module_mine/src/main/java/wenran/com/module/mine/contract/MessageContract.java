package wenran.com.module.mine.contract;


import wenran.com.baselibrary.base.basemvp.IBasePresenter;
import wenran.com.baselibrary.base.basemvp.IBaseView;
import wenran.com.baselibrary.bean.StandardResultBean;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.module.mine.bean.ChangeMessageStatusParam;
import wenran.com.module.mine.bean.MessageParamBean;
import wenran.com.module.mine.bean.MessageResultBean;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */
public final class MessageContract {

    public interface IMessageModel {
        /**
         * 获取消息数据
         *
         * @param messageParamBean
         * @param normalCallback
         */
        void getMessage(MessageParamBean messageParamBean, INormalCallback<MessageResultBean> normalCallback);


        /**
         * 删除消息
         *
         * @param changeMessageStatusParam
         * @param normalCallback
         */
        void deleteMessage(ChangeMessageStatusParam changeMessageStatusParam, INormalCallback<StandardResultBean> normalCallback);

    }


    public interface IMessageView extends IBaseView {

        /**
         * 初始化消息
         *
         * @param isSuccess
         * @param data      携带数据
         */
        void initMessageRecyclerView(boolean isSuccess, MessageResultBean data);

        /**
         * 获取消息返回操作
         *
         * @param isSuccess
         * @param data      携带数据
         */
        void getMessageRefresh(boolean isSuccess, MessageResultBean data);

        /**
         * 刷新更多
         *
         * @param isSuccess
         * @param data      携带数据
         */
        void getMessageMore(boolean isSuccess, MessageResultBean data);

        /**
         * 删除消息
         *
         * @param isSuccess
         * @param position
         * @param data      携带数据
         */
        void deleteMessage(boolean isSuccess, int position, StandardResultBean data);

        /**
         * 没有更多数据
         *
         * @param type
         */
        void noMoreData(ConstantTag type);

        /**
         * 设置没有数据的状态
         */
        void setNoDataView();

        /**
         * 删除信息
         *
         * @param position
         */
        void deleteMessage(int position);

    }

    public interface IMessagePresenter extends IBasePresenter {
        /**
         * 获取消息
         *
         * @param paramBean 注册需要的参数
         * @param type      获取数据的类型
         */
        void getMessage(ConstantTag type, MessageParamBean paramBean);


        /**
         * 删除消息
         *
         * @param changeMessageStatusParam
         * @param position
         */
        void deleteMessage(ChangeMessageStatusParam changeMessageStatusParam, int position);


        /**
         * 获取页码
         *
         * @return
         */
        int getCurrentPage();

    }
}
