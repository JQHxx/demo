package wenran.com.module.home.contract;

import java.util.List;

import wenran.com.baselibrary.base.basemvp.IBasePresenter;
import wenran.com.baselibrary.base.basemvp.IBaseView;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.module.home.bean.HotSearchResultBean;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */

public final class HotSearchContract {

    public interface IHotSearchModel {
        /**
         * 获取热门搜索数据
         *
         * @param dataParam      需要的参数
         * @param normalCallback
         */
        void getHotSearch(INormalCallback<HotSearchResultBean> normalCallback);

    }

    public interface IHotSearchView extends IBaseView {

        /**
         * 获取热门搜索数据返回操作
         *
         * @param isSuccess
         * @param data      携带数据
         */
        void dealHotSearchDataSuccess(List<String> data);

        /**
         * 获取历史搜索数据失败返回操作
         *
         * @param code
         * @param msg  携带数据
         */
        void dealHotSearchDataFailure(int code, String msg);

        /**
         * 获取热门搜索数据返回操作
         *
         * @param isSuccess
         * @param data      携带数据
         */
        void dealHistoryDataSuccess(List<String> data);

        /**
         * 获取历史搜索数据失败返回操作
         *
         * @param code
         * @param msg  携带数据
         */
        void dealHistoryDataSuccessFailure();
    }

    public interface IHotSearchPresenter extends IBasePresenter {
        /**
         * 获取热门搜索数据
         */
        void getHotSearchData();

        /**
         * 获取历史搜索数据
         */
        void getHistorySearchData();

        /**
         * 处理点击事件
         */
        void dealClick(String tag);

    }
}
