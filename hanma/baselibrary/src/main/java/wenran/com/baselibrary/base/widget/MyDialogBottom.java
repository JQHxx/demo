package wenran.com.baselibrary.base.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by ${WZH} on 2016/9/25.
 *
 * @author crowhine
 */
public class MyDialogBottom extends Dialog {
    private static int defaultWidth = 560;
    private static int defaultHeight = 300;

    public MyDialogBottom(Context context, View layout, int style) {
        this(context, defaultWidth, defaultHeight, layout, style);
    }

    public MyDialogBottom(Context context, int width, int height, View layout, int style) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        //        params.x = 100; // 新位置X坐标
        //        params.y = 100; // 新位置Y坐标

        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        // 宽度
        params.width = wm.getDefaultDisplay().getWidth();
        // 高度
        if (height==1){
            params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        }else if (height==0){
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        }else {
            params.height = height;
        }

        //        params.alpha = 0.7f; // 透明度
        window.setAttributes(params);
    }

}