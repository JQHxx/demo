package wenran.com.module.mine.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Crowhine on 2019/2/15
 *
 * @author Crowhine
 */
public class AnotherLoginInfo implements Parcelable {
    String openid;
    int platform;
    String name;
    String profileImageUrl;

    public AnotherLoginInfo() {
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.openid);
        dest.writeInt(this.platform);
        dest.writeString(this.name);
        dest.writeString(this.profileImageUrl);
    }

    protected AnotherLoginInfo(Parcel in) {
        this.openid = in.readString();
        this.platform = in.readInt();
        this.name = in.readString();
        this.profileImageUrl = in.readString();
    }

    public static final Creator<AnotherLoginInfo> CREATOR = new Creator<AnotherLoginInfo>() {
        @Override
        public AnotherLoginInfo createFromParcel(Parcel source) {
            return new AnotherLoginInfo(source);
        }

        @Override
        public AnotherLoginInfo[] newArray(int size) {
            return new AnotherLoginInfo[size];
        }
    };
}
