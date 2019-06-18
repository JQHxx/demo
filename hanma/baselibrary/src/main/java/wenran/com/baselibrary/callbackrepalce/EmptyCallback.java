package wenran.com.baselibrary.callbackrepalce;

import com.kingja.loadsir.callback.Callback;

import wenran.com.baselibrary.R;


/**
 * Description:TODO
 * @author crowhine
 */

public class EmptyCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_empty;
    }

}
