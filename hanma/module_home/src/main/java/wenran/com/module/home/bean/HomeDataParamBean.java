package wenran.com.module.home.bean;

/**
 * Created by Crowhine on 2019/2/20
 *
 * @author Crowhine
 */
public class HomeDataParamBean {
    String apptoken;

    public HomeDataParamBean() {
    }

    public HomeDataParamBean(String apptoken) {
        this.apptoken = apptoken;
    }

    public String getApptoken() {
        return apptoken;
    }

    public void setApptoken(String apptoken) {
        this.apptoken = apptoken;
    }
}
