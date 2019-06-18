package wenran.com.module.home.bean;

import java.util.List;

/**
 * Created by Crowhine on 2019/3/26
 *
 * @author Crowhine
 */
public class CourseExplainResultBean {

    /**
     * message : 加载成功
     * statusCode : 200
     * success : true
     * data : {"bannerList":[{"id":1,"img":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/1.jpg"},{"id":2,"img":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/2.jpg"},{"id":3,"img":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/3.jpg"},{"id":4,"img":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/4.jpg"},{"id":5,"img":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/5.jpg"}],"classItem":{"title":"课程简介","audio":"http://192.168.99.105:8080/wenran_education/uploads/audio/20181031/201810311255st5bd935bec969c.mp3","content":"http://192.168.99.105:8080/wenran_education/app/home/content/83"},"teacher":"作者：粟镇宇"}
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
        /**
         * bannerList : [{"id":1,"img":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/1.jpg"},{"id":2,"img":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/2.jpg"},{"id":3,"img":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/3.jpg"},{"id":4,"img":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/4.jpg"},{"id":5,"img":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/5.jpg"}]
         * classItem : {"title":"课程简介","audio":"http://192.168.99.105:8080/wenran_education/uploads/audio/20181031/201810311255st5bd935bec969c.mp3","content":"http://192.168.99.105:8080/wenran_education/app/home/content/83"}
         * teacher : 作者：粟镇宇
         */

        private ClassItemBean classItem;
        private String teacher;
        private List<BannerListBean> bannerList;

        public ClassItemBean getClassItem() {
            return classItem;
        }

        public void setClassItem(ClassItemBean classItem) {
            this.classItem = classItem;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public List<BannerListBean> getBannerList() {
            return bannerList;
        }

        public void setBannerList(List<BannerListBean> bannerList) {
            this.bannerList = bannerList;
        }

        public static class ClassItemBean {
            /**
             * title : 课程简介
             * audio : http://192.168.99.105:8080/wenran_education/uploads/audio/20181031/201810311255st5bd935bec969c.mp3
             * content : http://192.168.99.105:8080/wenran_education/app/home/content/83
             */

            private String title;
            private String audio;
            private String content;

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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
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
    }
}
