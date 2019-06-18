package wenran.com.module.home.constant;

/**
 * 点击了那个侧边栏
 * Created by crowhine on 2018/7/31.
 *
 * @author crowhine
 */
public enum HomeConstantTag {

    /**
     * 携带数据的标签
     */
    DATA_TAG("dataTag"),

    SEARCH_SHOW_HOT("显示热门搜索"),
    SEARCH_SHOW_POPUP("显示搜索联想"),
    SEARCH_SHOW_RESULT("显示搜索结果"),

    UPDATE_POPUP_SEARCH("更新搜索联想数据"),
    NO_UPDATE_POPUP_SEARCH("不更新更新搜索联想数据"),

    SHOW_SEARCH_RESULT("展示搜索结果fragment"),
    UPDATE_SEARCH_RESULT("更新搜索结果数据数据"),

    INIT_RECYCLER_VIEW("初始化recyclerView"),
    DO_REFRESH("刷新"),
    DO_MORE("获取更多"),

    ;



    HomeConstantTag(String witchFragment) {
        this.witchFragment = witchFragment;
    }

    /**
     * 点击了哪个fragment
     */
    private String witchFragment;

    public String getTagValue() {
        return witchFragment;
    }

    public void setTagValue(String witchFragment) {
        this.witchFragment = witchFragment;
    }
}
