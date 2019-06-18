package wenran.com.module.home.contract;

import java.util.List;

import wenran.com.baselibrary.base.basemvp.IBasePresenter;
import wenran.com.baselibrary.base.basemvp.IBaseView;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.module.home.bean.RamblingSetMoreParamBean;
import wenran.com.module.home.bean.RamblingSetMoreResultBean;
import wenran.com.module.home.constant.HomeConstantTag;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */

public final class RamblingSetMoreContract {

    public interface IRamblingSetMoreModel {
        /**
         * 获取漫谈集更多数据
         *
         * @param ramblingSetMoreParamBean 需要的参数
         * @param normalCallback
         */
        void getRamblingSetMore(RamblingSetMoreParamBean ramblingSetMoreParamBean,
                                INormalCallback<RamblingSetMoreResultBean> normalCallback);
    }

    public interface IRamblingSetMoreView extends IBaseView {

        /**
         * 获取漫谈集更多数据返回操作
         *
         * @param ramble 携带数据
         * @param isSuccess
         */
        void dealInitRamblingSetMoreData(boolean isSuccess,List<RamblingSetMoreResultBean.DataBean.RambleBean> ramble);

        /**
         * 获取漫谈集更多banner数据返回操作
         *
         * @param imageIdList 携带数据
         * @param imageList 携带数据
         */
        void dealInitRamblingSetMoreBanner(List<Integer> imageIdList, List<String> imageList);

        /**
         * 获取漫谈集更多数据返回操作
         *
         * @param isSuccess
         * @param ramble      携带数据
         */
        void dealRefresh(boolean isSuccess, List<RamblingSetMoreResultBean.DataBean.RambleBean> ramble);

        /**
         * 刷新更多
         *
         * @param isSuccess
         * @param ramble      携带数据
         */
        void dealMore(boolean isSuccess, List<RamblingSetMoreResultBean.DataBean.RambleBean> ramble);


        /**
         * 没有更多数据
         *
         * @param type
         */
        void dealNoMoreData(HomeConstantTag type);
    }

    public interface IRamblingSetPresenter extends IBasePresenter {
        /**
         * 获取漫谈集更多数据
         *
         * @param ramblingSetMoreParamBean
         * @param type                     刷新还是加载跟多或者初始化
         */
        void getRamblingSetMoreData(HomeConstantTag type, RamblingSetMoreParamBean ramblingSetMoreParamBean);

        /**
         * 获取当前页码
         *
         * @return
         */
        int getCurrentPage();
    }
}
