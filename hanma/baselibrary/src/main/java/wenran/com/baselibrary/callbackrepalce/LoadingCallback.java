package wenran.com.baselibrary.callbackrepalce;

import android.content.Context;
import android.view.View;

import com.kingja.loadsir.callback.Callback;

import wenran.com.baselibrary.R;


/**
 * Description:TODO
 * @author crowhine
 */
public class LoadingCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_loading;
    }

    @Override
    public boolean getSuccessVisible() {
        return super.getSuccessVisible();
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
