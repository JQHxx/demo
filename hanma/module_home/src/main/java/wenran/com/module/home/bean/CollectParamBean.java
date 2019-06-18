package wenran.com.module.home.bean;

/**
 * Created by Crowhine on 2019/4/1
 *
 * @author Crowhine
 */
public class CollectParamBean {
    String apptoken;
    int classInfoId;

    public CollectParamBean() {
    }

    public CollectParamBean(String apptoken, int classInfoId) {
        this.apptoken = apptoken;
        this.classInfoId = classInfoId;
    }

    public String getApptoken() {
        return apptoken;
    }

    public void setApptoken(String apptoken) {
        this.apptoken = apptoken;
    }

    public int getClassInfoId() {
        return classInfoId;
    }

    public void setClassInfoId(int classInfoId) {
        this.classInfoId = classInfoId;
    }
}
