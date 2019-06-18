package wenran.com.module.home.bean;

/**
 * Created by Crowhine on 2019/4/1
 *
 * @author Crowhine
 */
public class LikeParamBean {
    int classCommentId;
    String apptoken;

    public LikeParamBean() {
    }

    public LikeParamBean(int classCommentId, String apptoken) {
        this.classCommentId = classCommentId;
        this.apptoken = apptoken;
    }

    public int getClassCommentId() {
        return classCommentId;
    }

    public void setClassCommentId(int classCommentId) {
        this.classCommentId = classCommentId;
    }

    public String getApptoken() {
        return apptoken;
    }

    public void setApptoken(String apptoken) {
        this.apptoken = apptoken;
    }
}
