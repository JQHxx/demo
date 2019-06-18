package wenran.com.module.home.model;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import wenran.com.baselibrary.bean.StandardResultBean;
import wenran.com.module.home.bean.CollectParamBean;
import wenran.com.module.home.bean.CommentParamBean;
import wenran.com.module.home.bean.CourseDetailShowParamBean;
import wenran.com.module.home.bean.CourseDetailShowResultBean;
import wenran.com.module.home.bean.LikeParamBean;
import wenran.com.module.home.bean.SearchCourseResultBean;
import wenran.com.module.home.bean.CourseExplainResultBean;
import wenran.com.module.home.bean.HomeDataParamBean;
import wenran.com.module.home.bean.HomeDataResultBean;
import wenran.com.module.home.bean.HotSearchResultBean;
import wenran.com.module.home.bean.RamblingSetMoreParamBean;
import wenran.com.module.home.bean.RamblingSetMoreResultBean;
import wenran.com.module.home.bean.SpecialMoreResultBean;

/**
 * @author crowhine
 */
public interface IHomeService {
    /**
     * 获取主页数据
     *
     * @param homeDataParamBean
     * @return
     */
    @POST("home/index.json")
    Observable<HomeDataResultBean> getHomeData(@Body HomeDataParamBean homeDataParamBean);

    /**
     * 获取消息状态，是否有新消息
     *
     * @param appToken
     * @return
     */
    @GET("home/isread.json")
    Observable<StandardResultBean> getMessageStatus(@Query("apptoken") String appToken);

    /**
     * 获取热门搜索
     *
     * @return
     */
    @GET("home/hotsearch.json")
    Observable<HotSearchResultBean> getHotSearch();

    /**
     * 展示联想标题
     *
     * @param title 联系需要的字段
     * @return
     */
    @GET("home/searchtitles.json")
    Observable<HotSearchResultBean> getPopupSearch(@Query("title") String title);

    /**
     * 根据字段查找课程详情列表
     *
     * @param pageIndex
     * @param title
     * @param apptoken
     * @return
     */
    @GET("home/searchinfos.json")
    Observable<SearchCourseResultBean> getSearchDetailData(@Query("pageIndex") int pageIndex,
                                                           @Query("title") String title,
                                                           @Query("apptoken") String apptoken);

    /**
     * 获取专题和单项课更多数据
     *
     * @param pageIndex
     * @param apptoken
     * @return
     */
    @GET("details/{morePath}.json")
    Observable<SpecialMoreResultBean> getClassMoreData(
            @Path("morePath") String morePath,
            @Query("pageIndex") int pageIndex,
            @Query("apptoken") String apptoken);

    /**
     * 获取专题课更多数据
     *
     * @param pageIndex
     * @param apptoken
     * @return
     */
    @GET("details/morespecial.json")
    Observable<SpecialMoreResultBean> getSpecialClassMoreData(@Query("pageIndex") int pageIndex,
                                                              @Query("apptoken") String apptoken);

    /**
     * 获取单项课更多数据
     *
     * @param pageIndex
     * @param apptoken
     * @return
     */
    @GET("details/moreindividual.json")
    Observable<SpecialMoreResultBean> getSigleClassMoreData(@Query("pageIndex") int pageIndex,
                                                            @Query("apptoken") String apptoken);

    /**
     * 获取漫谈集更多数据
     *
     * @param ramblingSetMoreParamBean
     * @return
     */
    @POST("details/moreramble.json")
    Observable<RamblingSetMoreResultBean> getRamblingSetMoreData(@Body RamblingSetMoreParamBean ramblingSetMoreParamBean);


    /**
     * 获取课程介绍数据数据
     *
     * @param id 课程id
     * @return
     */
    @GET("details/explain/{id}")
    Observable<CourseExplainResultBean> getCourseExplainData(
            @Path("id") String id);

    /**
     * 获取课程详情介绍数据
     *
     * @param id                        课程id
     * @param courseDetailShowParamBean
     * @return
     */
    @POST("details/detaile/{id}")
    Observable<CourseDetailShowResultBean> getCourseDetailShowData(
            @Path("id") String id, @Body CourseDetailShowParamBean courseDetailShowParamBean);


    /**
     * 点赞
     *
     * @param likeParamBean
     * @return
     */
    @POST("details/fabulous.json")
    Observable<StandardResultBean> doLike(@Body LikeParamBean likeParamBean);

    /**
     * 点赞
     *
     * @param collectParamBean
     * @return
     */
    @POST("details/collect.json")
    Observable<StandardResultBean> doCollect(@Body CollectParamBean collectParamBean);

    /**
     * 点赞
     *
     * @param collectParamBean
     * @return
     */
    @POST("details/addcomment.json")
    Observable<StandardResultBean> doComment(@Body CommentParamBean commentParamBean);

}
