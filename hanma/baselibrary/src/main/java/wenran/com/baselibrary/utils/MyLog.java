package wenran.com.baselibrary.utils;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;

/**
 * Created by crowh on 2018/7/2.
 *
 * @author crowhine
 */

public class MyLog {

    public static void i(Context context, String msg) {
        if (msg == null || "".equals(msg)) {
            msg = "null";
        }
        if (context==null){
            return;
        }
        Log.i(context.getClass().getSimpleName(), msg);
    }

    public static void d(Context context, String msg) {
        if (msg == null || "".equals(msg)) {
            msg = "null";
        }
        if (context==null){
            return;
        }
        Log.d(context.getClass().getSimpleName(), msg);
    }

    public static void e(Context context, String msg) {
        if (msg == null || "".equals(msg)) {
            msg = "null";
        }
        if (context==null){
            return;
        }
        Log.e(context.getClass().getSimpleName(), msg);
    }

    public static void printDeviceInfo(BluetoothDevice device, Context context) {
        if (device == null) {
            return;
        }
        String msg = "目标设备信息" +
                "Name:" + device.getName() + ";" +
                "Address:" + device.getAddress() + ";" +
                "BondState:" + device.getBondState();
        if (device.getUuids() != null) {
            msg = msg + "Uuids" + device.getUuids();
        } else {
            msg = msg + "Uuids为null";
        }
        MyLog.i(context, msg
        );

    }
}
