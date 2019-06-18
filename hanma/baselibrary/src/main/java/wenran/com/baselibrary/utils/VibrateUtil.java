package wenran.com.baselibrary.utils;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/**
 * Created by crowhine on 2018/9/28.
 *
 * @author crowhine
 *         <p>
 *         descripiton：手机震动类
 */

public class VibrateUtil {
    //震动milliseconds毫秒
    public static void vibrate(final Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    //以pattern[]方式震动
    public static void vibrate(final Context context, long[] pattern, int repeat) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, repeat);
    }

    //取消震动
    public static void virateCancle(final Context context) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.cancel();
    }

}
