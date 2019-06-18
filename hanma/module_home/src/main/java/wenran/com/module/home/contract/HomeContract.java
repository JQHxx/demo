package wenran.com.module.home.contract;

import java.util.List;

import wenran.com.baselibrary.base.basemvp.IBasePresenter;
import wenran.com.baselibrary.base.basemvp.IBaseView;
import wenran.com.baselibrary.bean.StandardResultBean;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.module.home.bean.HomeDataParamBean;
import wenran.com.module.home.bean.HomeDataResultBean;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */

public final class HomeContract {

    public interface IHomeModel {
        /**
         * 获取主页数据
         *
         * @param dataParam      需要的参数
         * @param normalCallback
         */
        void getHomeData(HomeDataParamBean dataParam, INormalCallback<HomeDataResultBean> normalCallback);

        /**
         * 获取消息状态，是否有新消息
         *
         * @param paramBean      需要的参数
         * @param normalCallback
         */
        void getMessageStatus(HomeDataParamBean paramBean, INormalCallback<StandardResultBean> normalCallback);
    }


    public interface IHomeView extends IBaseView {

        /**
         * 获取主页数据返回操作
         *
         * @param data 携带数据
         */
        void getHomeDataSuccess(HomeDataResultBean data);

        /**
         * 获取主页数据失败
         *
         * @param code 失败码检测是否身份过期
         * @param msg
         */
        void getHomeDataFailure(Integer code, String msg);

        /**
         * 实例化banner
         *
         * @param imageList
         * @param imageIdList
         */
        void initBanner(List<Integer> imageIdList, List<String> imageList);

        /**
         * 初始化
         *
         * @param ramblingSetList
         */
        void initRamblingSetRv(List<HomeDataResultBean.DataBean.RambleBean> ramblingSetList);

        /**
         * 初始化
         *
         * @param singleClasses
         */
        void initSingleClass(List<HomeDataResultBean.DataBean.IndividualBean> singleClasses);

        /**
         * 初始化
         *
         * @param specialClasses
         */
        void initSpecialClass(List<HomeDataResultBean.DataBean.SpecialBean> specialClasses);

        /**
         * 处理获取消息状态
         *
         * @param isSuccess
         * @param code
         * @param message
         */
        void dealMessageStatus(boolean isSuccess, int code, String message);
    }

    public interface IHomePresenter extends IBasePresenter {
        /**
         * 获取主页数据
         *
         * @param paramBean 需要的参数
         */
        void getHomeData(HomeDataParamBean paramBean);

        /**
         * 获取消息状态，是否有新消息
         *
         * @param paramBean 需要的参数
         */
        void getMessageStatus(HomeDataParamBean paramBean);
    }
}
