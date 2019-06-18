package wenran.com.baselibrary.callbackrepalce;

import com.kingja.loadsir.callback.Callback;

import wenran.com.baselibrary.R;


/**
 * Description:TODO
 * @author crowhine
 */

public class PlaceholderCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_placeholder;
    }

//    @Override
//    protected boolean onReloadEvent(Context context, View view) {
//        return true;
//    }
}
