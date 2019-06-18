package wenran.com.baselibrary.base.basemvp;

import android.app.Activity;

/**
 * Created by Crowhine on 2019/2/21
 *
 * @author Crowhine
 */
public interface IBaseOriginalView {
    /**
     * 获取 Activity 对象
     *
     * @return activity
     */
    <T extends Activity> T getSelfActivity();
}
