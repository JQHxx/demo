package wenran.com.baselibrary.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by crowhine on 2018/9/10.
 *
 * @author crowhine
 *         关于服务的工具
 */

public class ServiceUtils {

    /**
     * 判断服务是否运行
     */
    public boolean isServiceRunning(Context context, final String className) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> info = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (info == null || info.size() == 0) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo aInfo : info) {
            if (className.equals(aInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
