package wenran.com.baselibrary.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import wenran.com.baselibrary.R;


/**
 * 注册时倒计时的
 * Created by crowhine on 2018/7/30.
 *
 * @author crowhine
 */

public class TimeCount extends CountDownTimer {
    private TextView textView;
    private Context context;
    private TimeCallBack TimeCallBack;

    /**
     * 参数依次为总时长,和计时的时间间隔
     */
    public TimeCount(Context context, TextView textView, long millisInFuture, long countDownInterval, TimeCallBack TimeCallBack) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        this.context = context;
        this.TimeCallBack = TimeCallBack;
    }

    /**
     * 计时过程显示
     */
    @Override
    public void onTick(long millisUntilFinished) {
        String time = "重新发送 ("+millisUntilFinished / 1000 + ")";
        setButtonInfo(time, false);
    }

    /**
     * 计时完毕时触发
     */
    @Override
    public void onFinish() {
        setButtonInfo("重新获取", true);
        TimeCallBack.finish();
    }

    /**
     * 验证按钮在点击前后相关设置
     *
     * @param content 要显示的内容
     * @param isClick 是否可点击
     */
    private void setButtonInfo(String content, boolean isClick) {
        textView.setText(content);
        textView.setClickable(isClick);
        if (isClick) {
            textView.setEnabled(true);
            textView.setTextAppearance(context, R.style.common_tv_yellow_ffa632_ts_11);
        } else {
            textView.setTextAppearance(context,R.style.common_tv_gray_99999_tsl_11);
            textView.setEnabled(false);
        }
    }

    /**
     * 倒计时回调
     */
    public interface TimeCallBack {
        /**
         * 结束回调
         */
        void finish();
    }
}
