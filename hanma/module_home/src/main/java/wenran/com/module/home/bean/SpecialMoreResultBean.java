package wenran.com.module.home.bean;

import java.util.List;

/**
 * Created by Crowhine on 2019/3/22
 *
 * @author Crowhine
 */
public class SpecialMoreResultBean {

    /**
     * message : 单项课更多加载成功
     * statusCode : 200
     * success : true
     * data : [{"id":60,"title":"年轻人....","teacher":"粟镇宇","cover":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/6.jpg","price":99.9,"audioLength":1039,"audioCount":30,"sales":301,"isbuy":false,"unbuy":"未购"},{"id":64,"title":"年轻人如何有效建立起专业能力","teacher":"作者：粟镇宇","cover":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/7.jpg","price":29.9,"audioLength":82,"audioCount":8,"sales":123,"isbuy":false,"unbuy":"未购"},{"id":49,"title":"JHA","teacher":"段老师","cover":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/8.jpg","price":29.9,"audioLength":65,"audioCount":8,"sales":121,"isbuy":false,"unbuy":"未购"},{"id":65,"title":"作业危害分析方法及应用","teacher":"作者：粟镇宇","cover":"http://192.168.99.105:8080/wenran_education/uploads/classcover/20181031/201810311314st5bd93a373e028.png","price":29.9,"audioLength":10,"audioCount":9,"sales":90,"isbuy":false,"unbuy":"未购"}]
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
         * id : 60
         * title : 年轻人....
         * teacher : 粟镇宇
         * cover : http://192.168.99.105:8080/wenran_education/uploads/app/avatar/6.jpg
         * price : 99.9
         * audioLength : 1039
         * audioCount : 30
         * sales : 301
         * isbuy : false
         * unbuy : 未购
         */

        private int id;
        private String title;
        private String teacher;
        private String cover;
        private double price;
        private int audioLength;
        private int audioCount;
        private int sales;
        private boolean isbuy;
        private String unbuy;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getAudioLength() {
            return audioLength;
        }

        public void setAudioLength(int audioLength) {
            this.audioLength = audioLength;
        }

        public int getAudioCount() {
            return audioCount;
        }

        public void setAudioCount(int audioCount) {
            this.audioCount = audioCount;
        }

        public int getSales() {
            return sales;
        }

        public void setSales(int sales) {
            this.sales = sales;
        }

        public boolean isIsbuy() {
            return isbuy;
        }

        public void setIsbuy(boolean isbuy) {
            this.isbuy = isbuy;
        }

        public String getUnbuy() {
            return unbuy;
        }

        public void setUnbuy(String unbuy) {
            this.unbuy = unbuy;
        }
    }
}
