package wenran.com.module.home.bean;

/**
 * Created by Crowhine on 2019/3/25
 *
 * @author Crowhine
 */
public class RamblingSetMoreParamBean {
    int pageIndex;
    int type;
    String apptoken;

    public RamblingSetMoreParamBean() {
    }

    public RamblingSetMoreParamBean(int pageIndex, int type, String apptoken) {
        this.pageIndex = pageIndex;
        this.type = type;
        this.apptoken = apptoken;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getApptoken() {
        return apptoken;
    }

    public void setApptoken(String apptoken) {
        this.apptoken = apptoken;
    }
}
