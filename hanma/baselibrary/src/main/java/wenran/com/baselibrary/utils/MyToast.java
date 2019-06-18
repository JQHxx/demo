package wenran.com.baselibrary.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import wenran.com.baselibrary.callback.IClickCallback;


/**
 * Created by crowh on 2018/7/2.
 *
 * @author crowhine
 */

public class MyToast {
    public static void s(Context context, String msg, int showLength) {
        if (showLength != Toast.LENGTH_LONG || showLength != Toast.LENGTH_SHORT) {
            showLength = Toast.LENGTH_LONG;
        }
        Toast toast = Toast.makeText(context, msg, showLength);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void sb(View view, String str1, int showLength) {
        boolean emptyStr1 = StringUtil.isEmptyStr(str1);
        if (emptyStr1) {
            str1 = "";
        }
        if (showLength != Snackbar.LENGTH_LONG || showLength != Snackbar.LENGTH_SHORT) {
            showLength = Snackbar.LENGTH_LONG;
        }
        Snackbar make = Snackbar.make(view, str1, showLength);
        make.show();
    }

    public static void snackBar(View view, String str1, String str2, int showLength, final IClickCallback IClickCallback) {
        boolean emptyStr1 = StringUtil.isEmptyStr(str1);
        boolean emptyStr2 = StringUtil.isEmptyStr(str2);
        if (emptyStr1) {
            str1 = "";
        }
        if (emptyStr2) {
            str2 = "";
        }
        if (showLength != Snackbar.LENGTH_LONG || showLength != Snackbar.LENGTH_SHORT) {
            showLength = Snackbar.LENGTH_LONG;
        }
        Snackbar make = Snackbar.make(view, str1, showLength);
        if (!emptyStr2 && IClickCallback != null) {
            make.setAction(str2, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IClickCallback.isSuccess();
                }
            });
        }
        make.show();
    }

}
