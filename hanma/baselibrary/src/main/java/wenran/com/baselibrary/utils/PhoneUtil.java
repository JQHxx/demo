package wenran.com.baselibrary.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by crowhine on 2018/7/6.
 * description:关于我的手机的操作
 *
 * @author crowhine
 */

public class PhoneUtil {
    /**
     * 获取本机蓝牙的mac地址
     */
    public static String getBtAddressByReflection() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Field field = null;
        try {
            field = BluetoothAdapter.class.getDeclaredField("mService");
            field.setAccessible(true);
            Object bluetoothManagerService = field.get(bluetoothAdapter);
            if (bluetoothManagerService == null) {
                return null;
            }
            Method method = bluetoothManagerService.getClass().getMethod("getAddress");
            if (method != null) {
                Object obj = method.invoke(bluetoothManagerService);
                if (obj != null) {
                    return obj.toString();
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

//    /**
//     * 获取屏幕宽度
//     */
//    public static ScreenPropertyBean getScreenProperty(Activity context) {
//        if (context == null) {
//            return null;
//        }
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics dm = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(dm);
//        // 屏幕宽度（像素）
//
//        ScreenPropertyBean screenPropertyBean = new ScreenPropertyBean();
//        int width = dm.widthPixels;
//        screenPropertyBean.setScreenWidthPx(width);
//
//        // 屏幕高度（像素）
//        int height = dm.heightPixels;
//        screenPropertyBean.setScreenHeightPx(height);
//
//        // 屏幕密度（0.75 / 1.0 / 1.5）
//        float density = dm.density;
//        // 屏幕密度dpi（120 / 160 / 240）
//        int densityDpi = dm.densityDpi;
//        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
//        // 屏幕宽度(dp)
//        int screenWidth = (int) (width / density);
//        screenPropertyBean.setScreenWidthDp(screenWidth);
//
//        // 屏幕高度(dp)
//        int screenHeight = (int) (height / density);
//        screenPropertyBean.setScreenHeightDp(screenHeight);
//
//
//        Log.d("h_bl", "屏幕宽度（像素）：" + width);
//        Log.d("h_bl", "屏幕高度（像素）：" + height);
//        Log.d("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
//        Log.d("h_bl", "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
//        Log.d("h_bl", "屏幕宽度（dp）：" + screenWidth);
//        Log.d("h_bl", "屏幕高度（dp）：" + screenHeight);
//        return screenPropertyBean;
//    }
//
//    public static float pixToDp(Context context, float width) {
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics dm = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(dm);
//        // 屏幕宽度（像素）
//
//        // 屏幕密度（0.75 / 1.0 / 1.5）
//        float density = dm.density;
//        // 屏幕密度dpi（120 / 160 / 240）
//        int densityDpi = dm.densityDpi;
//        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
//        // 屏幕宽度(dp)
//        float v = width / density;
//        return v;
//    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenwide(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }



    public static int Dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    public static int getVersionCode(Context context)//获取版本号(内部识别号)
    {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取版本
     */
    public static String getVersion(Context context) {
        try {

            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.toString();
        }
    }

    /**
     * 是否使屏幕常亮
     *
     * @param activity
     * @param isOpenLight
     */
    public static void keepScreenLongLight(Activity activity, boolean isOpenLight) {
        Window window = activity.getWindow();
        if (isOpenLight) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

    }


//    /**
//     * 计算屏幕能放置多少个item
//     *  @param totalDimen
//     * @param itemDimen item 宽
//     * @param extra 边距
//     */
//    public static int getItemNum(Activity context, Integer totalDimen, int itemDimen, int extra) {
//        int num = 2;
//        int  screenWidthDp;
//        ScreenPropertyBean screenProperty = PhoneUtil.getScreenProperty(context);
//        if (screenProperty == null) {
//            return num;
//        }
//        if (totalDimen==null){
//            screenWidthDp = screenProperty.getScreenWidthPx();
//        }else {
//            screenWidthDp= (int) context.getResources().getDimension(totalDimen);
//        }
//        if (0 != screenWidthDp) {
//            float dimension = context.getResources().getDimension(itemDimen) + context.getResources().getDimension(extra);
//            float v1 = screenWidthDp / dimension;
//            num = (int) v1;
//        }
//        return num;
//    }
}
