package wenran.com.baselibrary.utils;

import android.os.Parcelable;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

import wenran.com.baselibrary.help.MyData;
import wenran.com.baselibrary.help.SomeData;

/**
 * Created by Crowhine on 2019/3/19
 *
 * @author Crowhine
 */
public class EventBusUtil {

    /**发送Serializable数据
     * @param tag
     * @param t
     * @param isStick
     * */
    public static <T extends Serializable>void doSerializableEvent(String tag, T t,boolean isStick){
        SomeData<T> stringSomeData = new SomeData<>();
        stringSomeData.setTag(tag);
        stringSomeData.setT(t);
        if (isStick){
            EventBus.getDefault().postSticky(stringSomeData);
        }else {
            EventBus.getDefault().post(stringSomeData);
        }
    }

    /**发送Parcelable数据
     * @param tag
     * @param t
     * @param isStick
     * */
    public static <T extends Parcelable>void doParcelableEvent(String tag, T t, boolean isStick){
        MyData<T> stringSomeData = new MyData<>();
        stringSomeData.setTag(tag);
        stringSomeData.setT(t);
        if (isStick){
            EventBus.getDefault().postSticky(stringSomeData);
        }else {
            EventBus.getDefault().post(stringSomeData);
        }
    }
}
