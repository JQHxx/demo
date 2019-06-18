package wenran.com.module.home.bean;

/**
 * Created by Crowhine on 2019/3/18
 *
 * @author Crowhine
 */
public class CourseMoreParamBean {
    int pageIndex;
    String url;
    String apptoken;

    public CourseMoreParamBean(int pageIndex, String url, String apptoken) {
        this.pageIndex = pageIndex;
        this.url = url;
        this.apptoken = apptoken;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApptoken() {
        return apptoken;
    }

    public void setApptoken(String apptoken) {
        this.apptoken = apptoken;
    }
}
