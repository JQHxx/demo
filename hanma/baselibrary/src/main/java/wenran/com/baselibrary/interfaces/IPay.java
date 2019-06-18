package wenran.com.baselibrary.interfaces;

import android.app.Activity;

import wenran.com.baselibrary.callback.INormalCallback;

/**
 * Created by Crowhine on 2019/5/5
 *
 * @author Crowhine
 * <p>
 * 支付接口
 */
public interface IPay {

    /**
     * 阿里支付
     *
     * @param activity        当前activity
     * @param classInfoId
     * @param iNormalCallback
     */
    <T>void aliPay(Activity activity, int classInfoId, INormalCallback<T> iNormalCallback);
}
