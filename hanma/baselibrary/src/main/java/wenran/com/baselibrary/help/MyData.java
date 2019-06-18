package wenran.com.baselibrary.help;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by crowhine on 2018/8/16.
 *
 * @author crowhine
 *         需要携带的数据
 */

public class MyData<T extends Parcelable> implements Parcelable {

    String tag;
    T t;

    public MyData() {
    }

    public MyData(String tag, T t) {
        this.tag = tag;
        this.t = t;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tag);
        dest.writeParcelable(this.t, flags);
    }

    protected MyData(Parcel in) {
        this.tag = in.readString();
        this.t = in.readParcelable(this.getClass().getClassLoader());
    }

    public static final Creator<MyData> CREATOR = new Creator<MyData>() {
        @Override
        public MyData createFromParcel(Parcel source) {
            return new MyData(source);
        }

        @Override
        public MyData[] newArray(int size) {
            return new MyData[size];
        }
    };
}
