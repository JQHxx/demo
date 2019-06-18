package wenran.com.baselibrary.base.basemvp;

import android.support.annotation.NonNull;

import wenran.com.baselibrary.base.BaseModel;
import wenran.com.baselibrary.exception.MyException;


/**
 * Created by crowhine on 2018/8/6.
 *
 * @author crowhine
 */

public class BasePresenterImpl<V extends IBaseView, M extends BaseModel> extends BaseOriginalPresenter<V, M> implements IBasePresenter {
    public BasePresenterImpl(@NonNull V view) {
        super(view);
    }

    @Override
    public void dealException(String failureInfo, boolean isShow, Class c) {
        MyException myException = new MyException(c.getSimpleName(), failureInfo);
        if (isShow && isViewAttach()) {
            getView().hideLoadingProgress();
            myException.showException(getView());
        }
        try {
            throw myException;
        } catch (MyException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void doDestroy() {
        detachView();
    }

    @Override
    public void cancelNet(Object tag) {

    }

    @Override
    public void cancelAllNet() {

    }
}
