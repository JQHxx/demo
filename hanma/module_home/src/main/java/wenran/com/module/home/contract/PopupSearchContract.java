package wenran.com.module.home.contract;
import java.util.List;
import wenran.com.baselibrary.base.basemvp.IBasePresenter;
import wenran.com.baselibrary.base.basemvp.IBaseView;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.module.home.bean.HotSearchResultBean;
import wenran.com.module.home.bean.PopupSearchParamBean;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */

public final class PopupSearchContract {

    public interface IPopupSearchModel {
        /**
         * 获取联系搜索数据
         *
         * @param popupSearchParamBean 需要的参数
         * @param normalCallback
         */
        void getPopupSearch(PopupSearchParamBean popupSearchParamBean, INormalCallback<HotSearchResultBean> normalCallback);
    }

    public interface IPopupSearchView extends IBaseView {

        /**
         * 获取联系搜索数据返回操作
         *
         * @param isSuccess
         * @param data      携带数据
         */
        void dealPopupSearchData(boolean isSuccess,List<String> data);
    }

    public interface IPopupSearchPresenter extends IBasePresenter {
        /**
         * 获取联系搜索数据
         *
         * @param popupSearchParamBean
         */
        void getPopupSearchData(PopupSearchParamBean popupSearchParamBean);


    }
}
