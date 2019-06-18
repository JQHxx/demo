package wenran.com.baselibrary.base.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import wenran.com.baselibrary.R;
import wenran.com.baselibrary.constant.ConstantNum;

/**
 * Created by crowhine on 2018/8/15.
 *
 * @author crowhine
 * 网络请求的提示框
 */

public class MyProgressDialog {

    private static MyDialogWrap builder;
    private static boolean isDialoging;
    private static Timer timer;
    private static MyTimer myTimer;

    public static void show(Context context) {
        if (context == null) {
            return;
        }
        if (isDialoging) {
            return;
        }
        dismiss();
        View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null);
        builder = new MyDialogWrap(context, 0, 0, view, R.style.dialog);
        builder.show();
        delayedCheck(true);
        isDialoging = true;
    }

    public static void dismiss() {
        if (builder != null) {
            builder.dismiss();
            delayedCheck(false);
            isDialoging = false;
        }
    }

    /**
     * 5秒还没有回调的话
     */
    private static void delayedCheck(boolean isExecute) {

        if (isExecute) {
            //开启一个定时器
            if (timer == null) {
                timer = new Timer();
            }
            if (myTimer == null) {
                myTimer = new MyTimer();
            } else {
                myTimer.cancel();
                myTimer = null;
                myTimer = new MyTimer();
            }
            timer.schedule(myTimer, ConstantNum.REQUEST_TIME);
        } else {
            //关闭定时器
            if (timer != null && myTimer != null) {
                myTimer.cancel();
                myTimer = null;
                timer = null;
            }

        }
    }


    static class MyTimer extends TimerTask {
        @Override
        public void run() {
            //execute the task
            //5秒没有回调，把执行状态设置为false
            dismiss();
        }
    }
}
