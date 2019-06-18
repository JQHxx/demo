package wenran.com.module_player.bean;

/**
 * Created by Crowhine on 2019/5/5
 *
 * @author Crowhine
 * <p>
 * 更新课程进度的参数bean
 */
public class CourseProgressParamBean {
    String apptoken;
    int lookAt;
    int struts;

    public CourseProgressParamBean() {
    }

    public CourseProgressParamBean(String apptoken, int lookAt, int struts) {
        this.apptoken = apptoken;
        this.lookAt = lookAt;
        this.struts = struts;
    }

    public String getApptoken() {
        return apptoken;
    }

    public void setApptoken(String apptoken) {
        this.apptoken = apptoken;
    }

    public int getLookAt() {
        return lookAt;
    }

    public void setLookAt(int lookAt) {
        this.lookAt = lookAt;
    }

    public int getStruts() {
        return struts;
    }

    public void setStruts(int struts) {
        this.struts = struts;
    }
}
