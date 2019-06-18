package wenran.com.baselibrary.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Crowhine on 2019/4/10
 *
 * @author Crowhine
 */
@Entity
public class CourseInfo {
    @Id(autoincrement = true)
    private Long id;
    @Index(unique = true)
    private int courseId;
    private String title;
    private String imgUrl;
    private String audioUrl;
    private int audioLength;
    private int currentPlayLength;
    private int totalPlayLength;
    @Generated(hash = 1861713330)
    public CourseInfo(Long id, int courseId, String title, String imgUrl,
            String audioUrl, int audioLength, int currentPlayLength,
            int totalPlayLength) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.imgUrl = imgUrl;
        this.audioUrl = audioUrl;
        this.audioLength = audioLength;
        this.currentPlayLength = currentPlayLength;
        this.totalPlayLength = totalPlayLength;
    }
    @Generated(hash = 1849777725)
    public CourseInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getCourseId() {
        return this.courseId;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getImgUrl() {
        return this.imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getAudioUrl() {
        return this.audioUrl;
    }
    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
    public int getAudioLength() {
        return this.audioLength;
    }
    public void setAudioLength(int audioLength) {
        this.audioLength = audioLength;
    }
    public int getCurrentPlayLength() {
        return this.currentPlayLength;
    }
    public void setCurrentPlayLength(int currentPlayLength) {
        this.currentPlayLength = currentPlayLength;
    }
    public int getTotalPlayLength() {
        return this.totalPlayLength;
    }
    public void setTotalPlayLength(int totalPlayLength) {
        this.totalPlayLength = totalPlayLength;
    }
}
