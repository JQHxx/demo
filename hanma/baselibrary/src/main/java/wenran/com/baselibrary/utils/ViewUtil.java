package wenran.com.baselibrary.utils;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by crowhine on 2018/7/26.
 *
 * @author crowhine
 */

public class ViewUtil {
    /**
     * 是否为空
     *
     * @param view widget
     */
    public static boolean isEmptyText(View view) {
        String text = getTextFromView(view);
        boolean emptyStr = StringUtil.isEmptyStr(text);
        if (emptyStr) {
            return true;
        }
        return false;
    }

    /**
     * get text of view
     *
     * @param view widget
     */
    public static String getTextFromView(View view) {
        String string = "";
        if (view instanceof EditText) {
            EditText editText = (EditText) view;
            string = editText.getText().toString();
        } else if (view instanceof TextView) {
            TextView textView = (TextView) view;
            string = textView.getText().toString();
        }else if (view instanceof CheckBox){
            CheckBox checkBox = (CheckBox) view;
            string = checkBox.getText().toString();
        }else if (view instanceof RadioButton){
            RadioButton radioButton = (RadioButton) view;
            string = radioButton.getText().toString();
        }
        return string;
    }


    /**
     * 删除edittext一位数
     * @param view widget
     */
    public static void delete(View view) {
        if (view instanceof EditText) {
            if (!isEmptyText(view)) {
                EditText editText = (EditText) view;
                String text = getTextFromView(editText);
                editText.setText(text.substring(0, text.length() - 1));
                editText.setSelection(text.length()-1);
            }
        }
    }
}
