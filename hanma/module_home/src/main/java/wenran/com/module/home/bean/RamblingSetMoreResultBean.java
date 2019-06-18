package wenran.com.module.home.bean;

import java.util.List;

/**
 * Created by Crowhine on 2019/3/25
 *
 * @author Crowhine
 */
public class RamblingSetMoreResultBean {

    /**
     * message : 漫谈集更多加载成功
     * statusCode : 200
     * success : true
     * data : {"bannerList":[{"id":1,"img":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/1.jpg"},{"id":2,"img":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/2.jpg"},{"id":3,"img":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/3.jpg"},{"id":4,"img":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/4.jpg"},{"id":5,"img":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/5.jpg"}],"ramble":[{"id":77,"sortcode":4,"title":"0004 跨国企业如何开展EHS审计","audio":"http://192.168.99.105:8080/wenran_education/uploads/audio/20181030/201810301455th5bd8004421b9e.mp3","audioLength":100,"speed":"已学习0.00%"},{"id":76,"sortcode":3,"title":"0003 化学反应热风险的控制","audio":"http://192.168.99.105:8080/wenran_education/uploads/audio/20181030/201810301455th5bd8004421b9e.mp3","audioLength":100,"speed":"已学习0.00%"},{"id":75,"sortcode":2,"title":"0002 赛科储罐爆炸事故的教训","audio":"http://192.168.99.105:8080/wenran_education/uploads/audio/20181030/201810301455th5bd8004421b9e.mp3","audioLength":100,"speed":"已学习0.00%"},{"id":74,"sortcode":1,"title":"0001 序言","audio":"http://192.168.99.105:8080/wenran_education/uploads/audio/20181030/201810301455th5bd8004421b9e.mp3","audioLength":100,"speed":"已学习0.00%"}]}
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

        public static class BannerListBean {
            /**
             * id : 1
             * img : http://192.168.99.105:8080/wenran_education/uploads/app/avatar/1.jpg
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
             * audio : http://192.168.99.105:8080/wenran_education/uploads/audio/20181030/201810301455th5bd8004421b9e.mp3
             * audioLength : 100
             * speed : 已学习0.00%
             */

            private int id;
            private int sortcode;
            private String title;
            private String audio;
            private int audioLength;
            private String speed;

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

            public String getSpeed() {
                return speed;
            }

            public void setSpeed(String speed) {
                this.speed = speed;
            }
        }
    }
}
