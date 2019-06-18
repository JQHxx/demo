package wenran.com.baselibrary.base.basemvp;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

import wenran.com.baselibrary.base.BaseModel;

/**
 * Created by Crowhine on 2019/2/21
 *
 * @author Crowhine
 */
public abstract class BaseOriginalPresenter<V extends IBaseOriginalView,M extends BaseModel> implements IBaseOriginalPresenter {
    /**
     * 防止 Activity 不走 doDestroy() 方法，所以采用弱引用来防止内存泄漏
     */
    private WeakReference<V> mViewRef;

    protected M netModel;

    public BaseOriginalPresenter(@NonNull V view) {
        attachView(view);
    }

    private void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    public V getView() {
        return mViewRef.get();
    }

    public void initModel(Class<M> c){
        try {
            netModel = c.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isViewAttach() {
        return mViewRef != null && mViewRef.get() != null;
    }

    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
        if (netModel!=null){
            netModel=null;
        }
    }

}
