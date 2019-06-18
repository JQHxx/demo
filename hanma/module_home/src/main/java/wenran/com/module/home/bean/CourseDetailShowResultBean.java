package wenran.com.module.home.bean;

import java.util.List;

/**
 * Created by Crowhine on 2019/3/29
 *
 * @author Crowhine
 * 课程详情数据
 */
public class CourseDetailShowResultBean {

    /**
     * message : 加载成功
     * statusCode : 200
     * success : true
     * data : {"classInfo":{"id":48,"title":"顺风使帆PSM","teacher":"333","classIntro":"份额","classOutline":"个人维权供热个人过热各个","targetUser":"而且全","subscribeNotice":"热热投入","remark":"融为一体","cover":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/9.jpg","price":166.9,"audioLength":90,"audioCount":1000,"sales":41,"isbuy":true,"unbuy":"已购","isCollect":false,"speed":""},"classItems":[{"id":62,"title":"01","audio":"http://192.168.99.105:8080/wenran_education/uploads/audio/20180422/201804221204nd5adc09b950f43.mp3","audioLength":100,"free":1,"speed":""},{"id":63,"title":"02","audio":"http://192.168.99.105:8080/wenran_education/uploads/audio/20180422/201804221205nd5adc0a196ef98.mp3","audioLength":200,"free":1,"speed":""},{"id":64,"title":"03","audio":"http://192.168.99.105:8080/wenran_education/uploads/audio/20180422/201804221206nd5adc0a48d76ac.mp3","audioLength":300,"free":0,"speed":"已学0.00%"}],"classComments":[{"id":1,"userId":14,"content":"12112223qaaaa","count":1,"user":{"nickname":"刘老根","avatar":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/9.jpg"},"fabulous":false},{"id":2,"userId":42,"content":"测试...........","count":1,"user":{"nickname":"阿Y","avatar":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/8.jpg"},"fabulous":false}]}
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
         * classInfo : {"id":48,"title":"顺风使帆PSM","teacher":"333","classIntro":"份额","classOutline":"个人维权供热个人过热各个","targetUser":"而且全","subscribeNotice":"热热投入","remark":"融为一体","cover":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/9.jpg","price":166.9,"audioLength":90,"audioCount":1000,"sales":41,"isbuy":true,"unbuy":"已购","isCollect":false,"speed":""}
         * classItems : [{"id":62,"title":"01","audio":"http://192.168.99.105:8080/wenran_education/uploads/audio/20180422/201804221204nd5adc09b950f43.mp3","audioLength":100,"free":1,"speed":""},{"id":63,"title":"02","audio":"http://192.168.99.105:8080/wenran_education/uploads/audio/20180422/201804221205nd5adc0a196ef98.mp3","audioLength":200,"free":1,"speed":""},{"id":64,"title":"03","audio":"http://192.168.99.105:8080/wenran_education/uploads/audio/20180422/201804221206nd5adc0a48d76ac.mp3","audioLength":300,"free":0,"speed":"已学0.00%"}]
         * classComments : [{"id":1,"userId":14,"content":"12112223qaaaa","count":1,"user":{"nickname":"刘老根","avatar":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/9.jpg"},"fabulous":false},{"id":2,"userId":42,"content":"测试...........","count":1,"user":{"nickname":"阿Y","avatar":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/8.jpg"},"fabulous":false}]
         */

        private ClassInfoBean classInfo;
        private List<ClassItemsBean> classItems;
        private List<ClassCommentsBean> classComments;

        public ClassInfoBean getClassInfo() {
            return classInfo;
        }

        public void setClassInfo(ClassInfoBean classInfo) {
            this.classInfo = classInfo;
        }

        public List<ClassItemsBean> getClassItems() {
            return classItems;
        }

        public void setClassItems(List<ClassItemsBean> classItems) {
            this.classItems = classItems;
        }

        public List<ClassCommentsBean> getClassComments() {
            return classComments;
        }

        public void setClassComments(List<ClassCommentsBean> classComments) {
            this.classComments = classComments;
        }

        public static class ClassInfoBean {
            /**
             * id : 48
             * title : 顺风使帆PSM
             * teacher : 333
             * classIntro : 份额
             * classOutline : 个人维权供热个人过热各个
             * targetUser : 而且全
             * subscribeNotice : 热热投入
             * remark : 融为一体
             * cover : http://192.168.99.105:8080/wenran_education/uploads/app/avatar/9.jpg
             * price : 166.9
             * audioLength : 90
             * audioCount : 1000
             * sales : 41
             * isbuy : true
             * unbuy : 已购
             * isCollect : false
             * speed :
             */

            private int id;
            private String title;
            private String teacher;
            private String classIntro;
            private String classOutline;
            private String targetUser;
            private String subscribeNotice;
            private String remark;
            private String cover;
            private double price;
            private int audioLength;
            private int audioCount;
            private int sales;
            private boolean isbuy;
            private String unbuy;
            private boolean isCollect;
            private String speed;

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

            public String getClassIntro() {
                return classIntro;
            }

            public void setClassIntro(String classIntro) {
                this.classIntro = classIntro;
            }

            public String getClassOutline() {
                return classOutline;
            }

            public void setClassOutline(String classOutline) {
                this.classOutline = classOutline;
            }

            public String getTargetUser() {
                return targetUser;
            }

            public void setTargetUser(String targetUser) {
                this.targetUser = targetUser;
            }

            public String getSubscribeNotice() {
                return subscribeNotice;
            }

            public void setSubscribeNotice(String subscribeNotice) {
                this.subscribeNotice = subscribeNotice;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
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

            public boolean isIsCollect() {
                return isCollect;
            }

            public void setIsCollect(boolean isCollect) {
                this.isCollect = isCollect;
            }

            public String getSpeed() {
                return speed;
            }

            public void setSpeed(String speed) {
                this.speed = speed;
            }
        }

        public static class ClassItemsBean {
            /**
             * id : 62
             * title : 01
             * audio : http://192.168.99.105:8080/wenran_education/uploads/audio/20180422/201804221204nd5adc09b950f43.mp3
             * audioLength : 100
             * free : 1
             * speed :
             */

            private int id;
            private String title;
            private String audio;
            private int audioLength;
            private int free;
            private String speed;

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

            public int getFree() {
                return free;
            }

            public void setFree(int free) {
                this.free = free;
            }

            public String getSpeed() {
                return speed;
            }

            public void setSpeed(String speed) {
                this.speed = speed;
            }
        }

        public static class ClassCommentsBean {
            /**
             * id : 1
             * userId : 14
             * content : 12112223qaaaa
             * count : 1
             * user : {"nickname":"刘老根","avatar":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/9.jpg"}
             * fabulous : false
             */

            private int id;
            private int userId;
            private String content;
            private int count;
            private UserBean user;
            private boolean fabulous;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public boolean isFabulous() {
                return fabulous;
            }

            public void setFabulous(boolean fabulous) {
                this.fabulous = fabulous;
            }

            public static class UserBean {
                /**
                 * nickname : 刘老根
                 * avatar : http://192.168.99.105:8080/wenran_education/uploads/app/avatar/9.jpg
                 */

                private String nickname;
                private String avatar;

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }
            }
        }
    }
}
