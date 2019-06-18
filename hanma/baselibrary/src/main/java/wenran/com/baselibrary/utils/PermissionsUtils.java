package wenran.com.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by crowh on 2018/7/2.
 *
 * @author crowhine
 */

public class PermissionsUtils {


    Context context;

    final static int PERMISSION_REQUEST_COARSE_LOCATION = 123;

    public PermissionsUtils() {
    }

    public PermissionsUtils(Context context) {
        this.context = context;
    }

    /**
     * 获取定位权限
     */
    public static void obtainLocationPermission(Activity activity, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity,
                    permissions,
                    PERMISSION_REQUEST_COARSE_LOCATION);
        }
    }

    /**
     * Gps是否可用
     */
    public static final boolean isGpsEnable(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

}
