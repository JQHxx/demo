package wenran.com.baselibrary.utils;

import android.widget.EditText;

/**
 * Created by Crowhine on 2019/1/15
 *
 * @author Crowhine
 * description:检查用户输入的信息是否合格
 */
public class CheckUserInfoAvailable {

    /**
     * 检查用户信息长度是否合格
     *
     * @param editText
     * @param length
     */
    public static boolean checkLength(EditText editText, int length) {
        String textFromView = ViewUtil.getTextFromView(editText);
        if (textFromView.length() < length) {
            return false;
        }
        return true;
    }
}
