package wenran.com.baselibrary.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by crowhine on 2018/7/27.
 * @author crowhine
 * description:some fucntions about Activity
 */

public class ActivityUtil {
    /**跳转
     *
     * @param context 上下文
     * @param c 类*/
    public static void goToActivity(Context context, Class c){
        Intent intent=new Intent();
        intent.setClass(context,c);
        context.startActivity(intent);
    }


}
