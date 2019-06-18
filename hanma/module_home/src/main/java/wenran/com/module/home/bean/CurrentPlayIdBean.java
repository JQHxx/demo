package wenran.com.module.home.bean;

/**
 * Created by Crowhine on 2019/4/19
 *
 * @author Crowhine
 * <p>
 * 被选中的课程item
 */
public class CurrentPlayIdBean {
    int position = -1;
    int playId = -1;

    public CurrentPlayIdBean() {
    }

    public CurrentPlayIdBean(int position, int playId) {
        this.position = position;
        this.playId = playId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPlayId() {
        return playId;
    }

    public void setPlayId(int playId) {
        this.playId = playId;
    }
}
