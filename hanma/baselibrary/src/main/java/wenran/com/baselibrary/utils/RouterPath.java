package wenran.com.baselibrary.utils;

/**
 * Created by Crowhine on 2019/3/14
 *
 * @author Crowhine
 */
public class RouterPath {
    //不同module的一级路径必须不同，否则会导致一个moudle中的一级路径失效
    //app
    /**
     * 登录
     */
    public static final String MINE_LOGIN_ACTIVITY = "/app/login_activity";
    /**
     * 支付
     */
    public static final String APP_PAY ="/app/pay";

    //home
    /**
     * 主页home界面
     */
    public static final String HOME_MAIN_ACTIVITY = "/home/main_activity";
    /**
     * 主页搜索界面
     */
    public static final String HOME_SEARCH_ACTIVITY = "/home/search_activity";
    /**
     * 热门搜索
     */
    public static final String HOME_SEARCH_HOT_FRAGMENT = "/home/search/hot_fragment";
    /**
     * 联系搜索
     */
    public static final String HOME_SEARCH_POPUP_FRAGMENT = "/home/search/popup_fragment";
    /**
     * 搜索结果
     */
    public static final String HOME_SEARCH_RESULT_FRAGMENT = "/home/search/result_fragment";
    /**
     * 单项课，专题课更多
     */
    public static final String SPECIAL_CLASS_MORE_ACTIVITY = "/home/special_class_activity";
    /**
     * 漫谈集更多
     */
    public static final String RAMBLING_SET_MORE_ACTIVITY = "/home/rambling_set_activity";
    /**
     * 课程详情界面
     */
    public static final String COURSE_DETAIL_SHOW_ACTIVITY = "/home/course_detail_show_activity";

    /**
     * 课程介绍界面
     */
    public static final String COURSE_EXPLAIN_ACTIVITY = "/home/course_explain_activity";


    //store
    /**
     * 购买store界面
     */
    public static final String STORE_MAIN_ACTIVITY = "/store/main_activity";

    //mine
    /**
     * 我的，消息界面
     */
    public static final String MINE_MESSAGE_ACTIVITY = "/mine/message_activity";
    /**
     * 我的，消息界面
     */
    public static final String MINE_MAIN_ACTIVITY = "/mine/main_activity";


    //player
    /**
     * 播放器模块
     */
    public static final String PLAYER_AUDIO_PLAYER_SERVICE = "/player/audio_player_service";
}
