package wenran.com.baselibrary.utils;

import android.content.Context;
import android.widget.Toast;

import wenran.com.baselibrary.R;


/**
 * Created by crowhine on 2018/7/26.
 *
 * @author crowhine
 */

public class DealClick {
    /**
     * \
     * 点击做处理
     * 防止重复点击
     */
    public static void deal(Context context, ClickCallback callback) {
        if (TimeUtils.isFastClick()) {
            MyToast.s(context, context.getString(R.string.no_fast_click), Toast.LENGTH_SHORT);
            return;
        }
        callback.isSuccess();
    }

    public interface ClickCallback {
        /**
         * 成功做处理
         */
        void isSuccess();
    }
}
