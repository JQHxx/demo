package wenran.com.module.home.bean;

/**
 * Created by Crowhine on 2019/4/1
 *
 * @author Crowhine
 */
public class CommentParamBean {
    String apptoken;
    String content;
    int classinfoId;

    public CommentParamBean() {
    }

    public CommentParamBean(String apptoken, String content, int classinfoId) {
        this.apptoken = apptoken;
        this.content = content;
        this.classinfoId = classinfoId;
    }

    public String getApptoken() {
        return apptoken;
    }

    public void setApptoken(String apptoken) {
        this.apptoken = apptoken;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getClassinfoId() {
        return classinfoId;
    }

    public void setClassinfoId(int classinfoId) {
        this.classinfoId = classinfoId;
    }
}
