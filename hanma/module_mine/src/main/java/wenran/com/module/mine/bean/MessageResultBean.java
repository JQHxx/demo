package wenran.com.module.mine.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Crowhine on 2019/2/28
 *
 * @author Crowhine
 */
public class MessageResultBean {

    /**
     * message : 请查阅通知
     * statusCode : 200
     * success : true
     * data : [{"rid":1,"rtype":0,"createdAt":"2019-02-27 14:57:29.0","message":{"title":"测试...........","content":"保佑老子臭傻逼","avatar":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/系统消息.png"}},{"rid":2,"rtype":0,"createdAt":"2019-02-27 14:57:36.0","message":{"title":"测试...........","content":"保佑老子臭傻逼","avatar":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/系统消息.png"}}]
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

    public static class DataBean implements Parcelable {
        /**
         * rid : 1
         * rtype : 0
         * createdAt : 2019-02-27 14:57:29.0
         * message : {"title":"测试...........","content":"保佑老子臭傻逼","avatar":"http://192.168.99.105:8080/wenran_education/uploads/app/avatar/系统消息.png"}
         */

        private int rid;
        private int rtype;
        private String createdAt;
        private MessageBean message;

        public int getRid() {
            return rid;
        }

        public void setRid(int rid) {
            this.rid = rid;
        }

        public int getRtype() {
            return rtype;
        }

        public void setRtype(int rtype) {
            this.rtype = rtype;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public MessageBean getMessage() {
            return message;
        }

        public void setMessage(MessageBean message) {
            this.message = message;
        }

        public static class MessageBean implements Parcelable {
            /**
             * title : 测试...........
             * content : 保佑老子臭傻逼
             * avatar : http://192.168.99.105:8080/wenran_education/uploads/app/avatar/系统消息.png
             */

            private String title;
            private String content;
            private String avatar;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.title);
                dest.writeString(this.content);
                dest.writeString(this.avatar);
            }

            public MessageBean() {
            }

            protected MessageBean(Parcel in) {
                this.title = in.readString();
                this.content = in.readString();
                this.avatar = in.readString();
            }

            public static final Creator<MessageBean> CREATOR = new Creator<MessageBean>() {
                @Override
                public MessageBean createFromParcel(Parcel source) {
                    return new MessageBean(source);
                }

                @Override
                public MessageBean[] newArray(int size) {
                    return new MessageBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.rid);
            dest.writeInt(this.rtype);
            dest.writeString(this.createdAt);
            dest.writeParcelable(this.message, flags);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.rid = in.readInt();
            this.rtype = in.readInt();
            this.createdAt = in.readString();
            this.message = in.readParcelable(MessageBean.class.getClassLoader());
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }
}
