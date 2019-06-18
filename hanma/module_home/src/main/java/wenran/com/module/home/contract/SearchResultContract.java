package wenran.com.module.home.contract;

import java.util.List;

import wenran.com.baselibrary.base.basemvp.IBasePresenter;
import wenran.com.baselibrary.base.basemvp.IBaseView;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.module.home.bean.SearchCourseResultBean;
import wenran.com.module.home.bean.SearchDetailParamBean;
import wenran.com.module.home.constant.HomeConstantTag;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */

public final class SearchResultContract {

    public interface ISearchResultModel {
        /**
         * 获取搜索详情数据
         *
         * @param searchDetailParamBean 需要的参数
         * @param normalCallback
         */
        void getSearchResult(SearchDetailParamBean searchDetailParamBean,
                             INormalCallback<SearchCourseResultBean> normalCallback);
    }

    public interface ISearchResultView extends IBaseView {

        /**
         * 获取搜索详情数据返回操作
         *
         * @param data 携带数据
         */
        void dealInitSearchResultData(List<SearchCourseResultBean.DataBean> data);


        /**
         * 获取搜索详情数据返回操作
         *
         * @param isSuccess
         * @param data      携带数据
         */
        void dealRefresh(boolean isSuccess,List<SearchCourseResultBean.DataBean> data);

        /**
         * 刷新更多
         *
         * @param isSuccess
         * @param data      携带数据
         */
        void dealMore(boolean isSuccess,List<SearchCourseResultBean.DataBean> data);


        /**
         * 没有更多数据
         *
         * @param type
         */
        void dealNoMoreData(HomeConstantTag type);
    }

    public interface ISearchResultPresenter extends IBasePresenter {
        /**
         * 获取搜索详情数据
         *
         * @param searchDetailParamBean
         * @param type                  刷新还是加载跟多或者初始化
         */
        void getSearchResultData(HomeConstantTag type, SearchDetailParamBean searchDetailParamBean);

        /**
         * 获取当前页码
         *
         * @return
         */
        int getCurrentPage();
    }
}
