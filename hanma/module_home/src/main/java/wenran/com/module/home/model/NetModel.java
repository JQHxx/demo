package wenran.com.module.home.model;


import io.reactivex.Observable;
import wenran.com.baselibrary.base.BaseModel;
import wenran.com.baselibrary.bean.StandardResultBean;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.server.RetrofitManager;
import wenran.com.module.home.bean.CollectParamBean;
import wenran.com.module.home.bean.CommentParamBean;
import wenran.com.module.home.bean.CourseDetailShowParamBean;
import wenran.com.module.home.bean.CourseDetailShowResultBean;
import wenran.com.module.home.bean.LikeParamBean;
import wenran.com.module.home.bean.SearchCourseResultBean;
import wenran.com.module.home.bean.CourseMoreParamBean;
import wenran.com.module.home.bean.CourseExplainParamBean;
import wenran.com.module.home.bean.CourseExplainResultBean;
import wenran.com.module.home.bean.HomeDataParamBean;
import wenran.com.module.home.bean.HomeDataResultBean;
import wenran.com.module.home.bean.HotSearchResultBean;
import wenran.com.module.home.bean.PopupSearchParamBean;
import wenran.com.module.home.bean.RamblingSetMoreParamBean;
import wenran.com.module.home.bean.RamblingSetMoreResultBean;
import wenran.com.module.home.bean.SearchDetailParamBean;
import wenran.com.module.home.bean.SpecialMoreResultBean;
import wenran.com.module.home.contract.CourseDetailShowContract;
import wenran.com.module.home.contract.CourseExplainContract;
import wenran.com.module.home.contract.HomeContract;
import wenran.com.module.home.contract.HotSearchContract;
import wenran.com.module.home.contract.PopupSearchContract;
import wenran.com.module.home.contract.RamblingSetMoreContract;
import wenran.com.module.home.contract.SearchResultContract;
import wenran.com.module.home.contract.SpecialCourseMoreContract;

/**
 * 网络获取数据
 * Created by crowhine on 2018/7/26.
 *
 * @author crowhine
 */

public class NetModel extends BaseModel implements HomeContract.IHomeModel
        , HotSearchContract.IHotSearchModel, PopupSearchContract.IPopupSearchModel
        , SearchResultContract.ISearchResultModel, SpecialCourseMoreContract.ISpecialMoreModel
        , RamblingSetMoreContract.IRamblingSetMoreModel, CourseExplainContract.ICourseExplainModel
        , CourseDetailShowContract.ICourseDetailShowModel {
    @Override
    public void getHomeData(HomeDataParamBean dataParam, INormalCallback<HomeDataResultBean> normalCallback) {
        Observable<HomeDataResultBean> homeData = RetrofitManager.getInstance().getRetrofitService(IHomeService.class).
                getHomeData(dataParam);
        sendRequest(homeData, normalCallback);
    }

    @Override
    public void getMessageStatus(HomeDataParamBean paramBean, INormalCallback<StandardResultBean> normalCallback) {
        Observable<StandardResultBean> netDataObservable = RetrofitManager.getInstance().
                getRetrofitService(IHomeService.class).
                getMessageStatus(paramBean.getApptoken());
        sendRequest(netDataObservable, normalCallback);
    }


    @Override
    public void getHotSearch(INormalCallback<HotSearchResultBean> normalCallback) {
        Observable<HotSearchResultBean> netDataObservable = RetrofitManager.getInstance().
                getRetrofitService(IHomeService.class).
                getHotSearch();
        sendRequest(netDataObservable, normalCallback);
    }

    @Override
    public void getPopupSearch(PopupSearchParamBean popupSearchParamBean, INormalCallback<HotSearchResultBean> normalCallback) {
        Observable<HotSearchResultBean> netDataObservable = RetrofitManager.getInstance().
                getRetrofitService(IHomeService.class).
                getPopupSearch(popupSearchParamBean.getContent());
        sendRequest(netDataObservable, normalCallback);
    }

    @Override
    public void getSearchResult(SearchDetailParamBean searchDetailParamBean, INormalCallback<SearchCourseResultBean> normalCallback) {
        Observable<SearchCourseResultBean> netDataObservable = RetrofitManager.getInstance().
                getRetrofitService(IHomeService.class).
                getSearchDetailData(searchDetailParamBean.getPageIndex(), searchDetailParamBean.getTitle(), searchDetailParamBean.getApptoken());
        sendRequest(netDataObservable, normalCallback);
    }


    @Override
    public void getSpecialMore(CourseMoreParamBean classMoreParamBean, INormalCallback<SpecialMoreResultBean> normalCallback) {
        Observable<SpecialMoreResultBean> classMoreData = RetrofitManager.getInstance().
                getRetrofitService(IHomeService.class).
                getClassMoreData(classMoreParamBean.getUrl(), classMoreParamBean.getPageIndex(), classMoreParamBean.getApptoken());
        sendRequest(classMoreData, normalCallback);
    }

    @Override
    public void getRamblingSetMore(RamblingSetMoreParamBean ramblingSetMoreParamBean, INormalCallback<RamblingSetMoreResultBean> normalCallback) {
        Observable<RamblingSetMoreResultBean> classMoreData = RetrofitManager.getInstance().
                getRetrofitService(IHomeService.class).
                getRamblingSetMoreData(ramblingSetMoreParamBean);
        sendRequest(classMoreData, normalCallback);
    }

    @Override
    public void getCourseExplain(CourseExplainParamBean dataParam, INormalCallback<CourseExplainResultBean> normalCallback) {
        Observable<CourseExplainResultBean> classMoreData = RetrofitManager.getInstance().
                getRetrofitService(IHomeService.class).
                getCourseExplainData(dataParam.getId());
        sendRequest(classMoreData, normalCallback);
    }

    @Override
    public void getCourseDetailShow(String id, CourseDetailShowParamBean courseDetailShowParamBean, INormalCallback<CourseDetailShowResultBean> normalCallback) {
        Observable<CourseDetailShowResultBean> classMoreData = RetrofitManager.getInstance().
                getRetrofitService(IHomeService.class).
                getCourseDetailShowData(id, courseDetailShowParamBean);
        sendRequest(classMoreData, normalCallback);
    }

    @Override
    public void doLike(LikeParamBean likeParamBean, INormalCallback<StandardResultBean> normalCallback) {
        Observable<StandardResultBean> classMoreData = RetrofitManager.getInstance().
                getRetrofitService(IHomeService.class).
                doLike(likeParamBean);
        sendRequest(classMoreData, normalCallback);
    }

    @Override
    public void doCollect(CollectParamBean collectParamBean, INormalCallback<StandardResultBean> normalCallback) {
        Observable<StandardResultBean> classMoreData = RetrofitManager.getInstance().
                getRetrofitService(IHomeService.class).
                doCollect(collectParamBean);
        sendRequest(classMoreData, normalCallback);
    }

    @Override
    public void doComment(CommentParamBean commentParamBean, INormalCallback<StandardResultBean> normalCallback) {
        Observable<StandardResultBean> classMoreData = RetrofitManager.getInstance().
                getRetrofitService(IHomeService.class).
                doComment(commentParamBean);
        sendRequest(classMoreData, normalCallback);
    }
}
