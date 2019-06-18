package wenran.com.baselibrary.utils;

/**
 * Created by crowhine on 2018/7/17.
 *
 * @author crowhine
 */

public class TimeUtils {
    /**
     * 两次点击间隔不能少于1000ms
     */
    private static final int MIN_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    public static String getIntMinutes(int seconds) {
        String showStr = "";
        int i = seconds / 60;
        return i + "";
    }


    public static String getDetailTime(int seconds) {
        String showStr = "";
        int hour, minute, currentSeconds;
        hour = seconds / 3600;
        minute = (seconds - hour * 3600) / 60;
        currentSeconds = seconds - hour * 300 - minute * 60;
        if (hour!=0){
            if (String.valueOf(hour).length() == 1) {
                showStr = showStr + "0" + hour + "小时";
            } else {
                showStr = showStr + hour + "小时";
            }
        }

        if (minute!=0){
            if (String.valueOf(minute).length() == 1) {
                showStr = showStr + "0" + minute + "分";
            } else {
                showStr = showStr + minute + "分";
            }
        }

        if (currentSeconds!= 0){
            if (String.valueOf(currentSeconds).length() == 1) {
                showStr = showStr + "0" + currentSeconds+"秒";
            } else {
                showStr = showStr + currentSeconds+"秒";
            }
        }


        return showStr;
    }

    public static String getDetailTime2(int seconds) {
        String showStr = "";
        int hour, minute, currentSeconds;
        hour = seconds / 3600;
        minute = (seconds - hour * 3600) / 60;
        currentSeconds = seconds - hour * 300 - minute * 60;
        if (hour!=0){
            if (String.valueOf(hour).length() == 1) {
                showStr = showStr + "0" + hour + ":";
            } else {
                showStr = showStr + hour + ":";
            }
        }

        if (minute!=0){
            if (String.valueOf(minute).length() == 1) {
                showStr = showStr + "0" + minute + ":";
            } else {
                showStr = showStr + minute + ":";
            }
        }else {
            showStr = showStr +"00:";
        }

        if (currentSeconds!= 0){
            if (String.valueOf(currentSeconds).length() == 1) {
                showStr = showStr + "0" + currentSeconds;
            } else {
                showStr = showStr + currentSeconds;
            }
        }else {
            showStr = showStr +"00";
        }


        return showStr;
    }
}
