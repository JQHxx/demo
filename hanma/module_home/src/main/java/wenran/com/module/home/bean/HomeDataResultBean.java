package wenran.com.module.home.bean;

import java.util.List;

/**
 * Created by Crowhine on 2019/2/20
 *
 * @author Crowhine
 */
public class HomeDataResultBean {

    /**
     * message : 主页加载成功
     * statusCode : 200
     * success : true
     * data : {"bannerList":[{"id":1,"img":"http://192.168.99.105:8080/uploads/wenran/avatar/1.jpg"},{"id":2,"img":"http://192.168.99.105:8080/uploads/wenran/avatar/2.jpg"},{"id":3,"img":"http://192.168.99.105:8080/uploads/wenran/avatar/3.jpg"},{"id":4,"img":"http://192.168.99.105:8080/uploads/wenran/avatar/4.jpg"},{"id":5,"img":"http://192.168.99.105:8080/uploads/wenran/avatar/5.jpg"}],"ramble":[{"id":77,"sortcode":4,"title":"0004 跨国企业如何开展EHS审计","audio":"http://192.168.99.105:8080/uploads/audio/20181030/201810301455th5bd8004421b9e.mp3","audioLength":100,"content":"http://192.168.99.105:8080/WenranApp/app/home/content/77"},{"id":76,"sortcode":3,"title":"0003 化学反应热风险的控制","audio":"http://192.168.99.105:8080/uploads/audio/20181030/201810301455th5bd8004421b9e.mp3","audioLength":100,"content":"http://192.168.99.105:8080/WenranApp/app/home/content/76"},{"id":75,"sortcode":2,"title":"0002 赛科储罐爆炸事故的教训","audio":"http://192.168.99.105:8080/uploads/audio/20181030/201810301455th5bd8004421b9e.mp3","audioLength":100,"content":"http://192.168.99.105:8080/WenranApp/app/home/content/75"}],"individual":[{"id":60,"title":"年轻人....","teacher":"粟镇宇","cover":"http://192.168.99.105:8080/uploads/wenran/avatar/6.jpg","price":99.9,"audioLength":180,"audioCount":30,"sales":301,"isbuy":false,"unbuy":"未购"},{"id":64,"title":"年轻人如何有效建立起专业能力","teacher":"作者：粟镇宇","cover":"http://192.168.99.105:8080/uploads/wenran/avatar/7.jpg","price":29.9,"audioLength":82,"audioCount":8,"sales":123,"isbuy":false,"unbuy":"未购"},{"id":49,"title":"JHA","teacher":"段老师","cover":"http://192.168.99.105:8080/uploads/wenran/avatar/8.jpg","price":29.9,"audioLength":65,"audioCount":8,"sales":121,"isbuy":false,"unbuy":"未购"}],"special":[{"id":48,"title":"顺风使帆PSM","teacher":"333","cover":"http://192.168.99.105:8080/uploads/wenran/avatar/9.jpg","price":166,"audioLength":900,"audioCount":3,"sales":41,"isbuy":false,"unbuy":"未购"},{"id":53,"title":"PSM综合学习","teacher":"张银银","cover":"http://192.168.99.105:8080/uploads/wenran/avatar/8.jpg","price":122,"audioLength":1200,"audioCount":80,"sales":31,"isbuy":false,"unbuy":"未购"},{"id":46,"title":"危化企业全面提升安全管理水平的实践","teacher":"0","cover":"http://192.168.99.105:8080/uploads/wenran/avatar/6.jpg","price":0,"audioLength":0,"audioCount":0,"sales":31,"isbuy":false,"unbuy":"未购"}]}
     */

    private String message;
    private int statusCode;
    private boolean success;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<BannerListBean> bannerList;
        private List<RambleBean> ramble;
        private List<IndividualBean> individual;
        private List<SpecialBean> special;

        public List<BannerListBean> getBannerList() {
            return bannerList;
        }

        public void setBannerList(List<BannerListBean> bannerList) {
            this.bannerList = bannerList;
        }

        public List<RambleBean> getRamble() {
            return ramble;
        }

        public void setRamble(List<RambleBean> ramble) {
            this.ramble = ramble;
        }

        public List<IndividualBean> getIndividual() {
            return individual;
        }

        public void setIndividual(List<IndividualBean> individual) {
            this.individual = individual;
        }

        public List<SpecialBean> getSpecial() {
            return special;
        }

        public void setSpecial(List<SpecialBean> special) {
            this.special = special;
        }

        public static class BannerListBean {
            /**
             * id : 1
             * img : http://192.168.99.105:8080/uploads/wenran/avatar/1.jpg
             */

            private int id;
            private String img;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public static class RambleBean {
            /**
             * id : 77
             * sortcode : 4
             * title : 0004 跨国企业如何开展EHS审计
             * audio : http://192.168.99.105:8080/uploads/audio/20181030/201810301455th5bd8004421b9e.mp3
             * audioLength : 100
             * content : http://192.168.99.105:8080/WenranApp/app/home/content/77
             */

            private int id;
            private int sortcode;
            private String title;
            private String audio;
            private int audioLength;
            private String content;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSortcode() {
                return sortcode;
            }

            public void setSortcode(int sortcode) {
                this.sortcode = sortcode;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAudio() {
                return audio;
            }

            public void setAudio(String audio) {
                this.audio = audio;
            }

            public int getAudioLength() {
                return audioLength;
            }

            public void setAudioLength(int audioLength) {
                this.audioLength = audioLength;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public static class IndividualBean {
            /**
             * id : 60
             * title : 年轻人....
             * teacher : 粟镇宇
             * cover : http://192.168.99.105:8080/uploads/wenran/avatar/6.jpg
             * price : 99.9
             * audioLength : 180
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

        public static class SpecialBean {
            /**
             * id : 48
             * title : 顺风使帆PSM
             * teacher : 333
             * cover : http://192.168.99.105:8080/uploads/wenran/avatar/9.jpg
             * price : 166
             * audioLength : 900
             * audioCount : 3
             * sales : 41
             * isbuy : false
             * unbuy : 未购
             */

            private int id;
            private String title;
            private String teacher;
            private String cover;
            private int price;
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

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
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
}
