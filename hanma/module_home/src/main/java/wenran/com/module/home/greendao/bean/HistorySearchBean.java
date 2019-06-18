package wenran.com.module.home.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Crowhine on 2019/3/20
 *
 * @author Crowhine
 */
@Entity
public class HistorySearchBean {
    @Id(autoincrement = true)
    private Long id;
    @Index(unique = true)
    private String searchTag;
    private int searchNum;
    @Generated(hash = 1754969004)
    public HistorySearchBean(Long id, String searchTag, int searchNum) {
        this.id = id;
        this.searchTag = searchTag;
        this.searchNum = searchNum;
    }
    @Generated(hash = 954352461)
    public HistorySearchBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSearchTag() {
        return this.searchTag;
    }
    public void setSearchTag(String searchTag) {
        this.searchTag = searchTag;
    }
    public int getSearchNum() {
        return this.searchNum;
    }
    public void setSearchNum(int searchNum) {
        this.searchNum = searchNum;
    }
}
