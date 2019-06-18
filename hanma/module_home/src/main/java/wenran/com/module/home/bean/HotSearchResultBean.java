package wenran.com.module.home.bean;

import java.util.List;

/**
 * Created by Crowhine on 2019/3/18
 *
 * @author Crowhine
 */
public class HotSearchResultBean {


    /**
     * message :
     * statusCode : 200
     * success : true
     * data : [{"title":"年轻人如何有效建立起专业能力"},{"title":"顺风使帆PSM"},{"title":"HAZOP1"}]
     */

    private String message;
    private int statusCode;
    private boolean success;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 年轻人如何有效建立起专业能力
         */

        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
