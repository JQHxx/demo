package wenran.com.baselibrary.base.widget;

/**
 * Created by ${WZH} on 2016/9/18.
 */

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * @author crowhine
 */
public class MyDialogWrap extends Dialog {
    /**
     * 默认宽度
     */
    private static int default_width = 560;
    /**
     * 默认高度
     */
    private static int default_height = 300;

    public MyDialogWrap(Context context, View layout, int style) {
        this(context, default_width, default_height, layout, style);
    }

    public MyDialogWrap(Context context, int width, int height, View layout, int style) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
//        params.x = 100; // 新位置X坐标
//        params.y = 100; // 新位置Y坐标
        if (0 == width) {
            // 宽度 0为自适配，1为和父类填满，其他设置他的高度为传过来的值；
            params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        } else if (1 == width) {
            // 宽度
            params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        } else {
            // 宽度
            params.width = width;
        }
        if (0 == height) {
            // 高度
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        } else if (1 == height) {
            // 高度
            params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        } else {
            // 高度
            params.height = height;
        }
//        params.alpha = 0.7f; // 透明度
        window.setAttributes(params);
    }

}