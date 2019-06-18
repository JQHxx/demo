package wenran.com.module.home.bean;

/**
 * Created by Crowhine on 2019/3/29
 *
 * @author Crowhine
 */
public class CourseDetailShowParamBean {
    String apptoken;
    int type;
    boolean search;

    public CourseDetailShowParamBean() {
    }

    public CourseDetailShowParamBean(String apptoken, int type, boolean search) {
        this.apptoken = apptoken;
        this.type = type;
        this.search = search;
    }

    public String getApptoken() {
        return apptoken;
    }

    public void setApptoken(String apptoken) {
        this.apptoken = apptoken;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }
}
