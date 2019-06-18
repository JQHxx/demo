package wenran.com.module.home.bean;

/**
 * Created by Crowhine on 2019/3/18
 *
 * @author Crowhine
 */
public class SearchDetailParamBean {
    int pageIndex;
    String title;
    String apptoken;

    public SearchDetailParamBean(int pageIndex, String title, String apptoken) {
        this.pageIndex = pageIndex;
        this.title = title;
        this.apptoken = apptoken;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getApptoken() {
        return apptoken;
    }

    public void setApptoken(String apptoken) {
        this.apptoken = apptoken;
    }
}
