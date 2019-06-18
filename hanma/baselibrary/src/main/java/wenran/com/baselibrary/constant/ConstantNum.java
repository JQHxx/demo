package wenran.com.baselibrary.constant;

/**
 * Created by Crowhine on 2019/1/9
 *
 * @author Crowhine
 */
public class ConstantNum {
    //guide
    public static int TO_GUIDE_DELAY_SECOND = 1000;
    /**
     * 请求成功了
     */
    public static final int REQUEST_SUCCESS = 200;

    /**
     * 请求异常
     */
    public static final int REQUEST_EXCEPTION = -1;

    /**
     * 没有更多数据
     */
    public static final int NO_MORE_DATA = 201;

    /**
     * 第三方登录失败
     */
    public static final int ANOTHER_LOGIN_FAILUER = 207;
    /**
     * 网络请求的时间
     */
    public static final int REQUEST_TIME = 5000;

    /**
     * 重量2秒钟未改变，做处理
     */
    public static final int WEIGHT_NO_CHANGE_TIME = 2000;
    /**
     * 身份过期
     */
    public static final String APP_TOKEN_OVER_DUE = "4999";


    /**
     * 电话长度
     */
    public static final int TELEPHONE_NUM_LENGTH = 11;
    /**
     * 密码最短
     */
    public static final int PSW_LENGTH = 6;

    /**
     * 验证码长度
     */
    public static final int VERIFICATION_CODE_LENGTH = 6;
    /**
     * 倒计时时间
     */
    public static final int VERIFICATION_CODE_COUNT_TIME = 6000;

    /**
     * 用户从设置里面跳转回来
     */
    public static final int PERMISSION_SET_BACK_REQUEST = 7000;
    /**
     * 欢迎界面请求所以权限
     */
    public static final int REQUEST_PERMISSION_ALL = 7001;

    /**
     * 登录跳转到绑定的请求码
     */
    public static final int LOGIN_TO_BIND_REQUEST_CODE = 7002;

    /**
     * 绑定成功后跳转到登录
     */
    public static final int BIND_TO_LOGIN_RESULT_CODE = 7003;

    /**
     * 主页跳转到消息
     */
    public static final int MAIN_TO_MESSAGE_REQUEST_CODE = 7004;

    /**
     * 消息跳转到主页
     */
    public static final int MESSAGE_TO_MAIN_RESULT_CODE = 7005;


    /**
     * 滑动距离大于等着个值，播放器做显示或者隐藏操作
     */
    public static final int SCROLL_VALUE = 30;

}
