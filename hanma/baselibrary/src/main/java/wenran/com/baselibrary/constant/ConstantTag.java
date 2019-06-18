package wenran.com.baselibrary.constant;

/**
 * 点击了那个侧边栏
 * Created by crowhine on 2018/7/31.
 *
 * @author crowhine
 */
public enum ConstantTag {

    /**
     * 携带数据的标签
     */
    DATA_TAG("dataTag"),

    APP_TOKEN_OVER_DUE("4999"),
    AUDIO_LENGTH("audioLength"),

    /**
     * 异常提示
     */
    NO_NET_DATA("未获取到数据"),
    NEED_MORE_INFO("请求参数不完善"),
    NO_CHANGE("未修改数据"),
    REQUEST_ERROR("请求错误"),


    /**
     * sharedPreferences需要的tag
     * userId
     */
    APP_TOKEN("app_token"),
    TAG("tag"),

    /**
     * 数据库的名字
     */
    GREEN_DAO_CROWHINE("数据库的名字呀"),
    ASKED_NET("请求了网络"),
    IS_FIRST_INPUT("第一次进入app的标识"),

    /**
     * eventBus_Tag
     */

    OBTAIN_LOCATION_PERMISSION("获取了定位权限"),
    BLE_DATA("蓝牙数据"),
    ZERO("0"),


    /**
     * 拍照，还是选择照片
     */
    SELECT_PHOTO("选择照片"),
    PHOTOGRAPH("拍照"),

    /**
     * 回调成功
     */
    SUCCESS("成功"),
    /**
     * 回调失败
     */
    FAILURE("失败"),

    //    login_register
    LOGIN_TO_REGISTER("登录跳转到注册"),
    LOGIN_TO_RESET_PSW("登录跳转到重新设置密码"),
    LOGIN_TO_BIND_PHONE("登录跳转到绑定手机"),
    V_CODE_FOR_REGISTER("验证码用于注册"),
    V_CODE_FOR_RESET_PSW("验证码用于重新注册密码"),


    INIT_MESSAGE_RECYCLER_VIEW("初始化recyclerView"),
    MESSAGE_REFRESH("消息刷新"),
    MESSAGE_MORE("消息获取更多"),
    UPDATE_BUY_STATUS("更新购买状态"),
    ;

    ConstantTag(String witchFragment) {
        this.witchFragment = witchFragment;
    }

    /**
     * 点击了哪个fragment
     */
    private String witchFragment;

    public String getTagValue() {
        return witchFragment;
    }

    public void setTagValue(String witchFragment) {
        this.witchFragment = witchFragment;
    }
}
