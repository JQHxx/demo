package wenran.com.module.home.util;

import wenran.com.baselibrary.utils.StringUtil;

/**
 * Created by Crowhine on 2019/3/29
 *
 * @author Crowhine
 */
public class CourseProgressUtil {

    /**
     * 把百分百转换成int类型的进度
     *
     * @param str
     */
    public static int getIntProgress(String str) {
        int progress = 0;
        if (!StringUtil.isEmptyStr(str)) {
            if (str.contains("%")) {
                String replace = str.replace("%", "");
                String trim = replace.trim();
                try {
                    int i = (int) Double.parseDouble(trim);
                    progress = i;
                } catch (Exception e) {

                } finally {
                    return progress;
                }
            }
        }
        return progress;
    }
}
