package wenran.com.module.home.bean;

/**
 * Created by Crowhine on 2019/2/28
 *
 * @author Crowhine
 * <p>
 * function:消息请求参数
 */
public class MessageParamBean {
    String apptoken;
    int page;

    public MessageParamBean() {
    }

    public MessageParamBean(String apptoken, int page) {
        this.apptoken = apptoken;
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getApptoken() {
        return apptoken;
    }

    public void setApptoken(String apptoken) {
        this.apptoken = apptoken;
    }
}
