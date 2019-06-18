package wenran.com.baselibrary.base.basemvp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Crowhine on 2019/2/21
 *
 * @author Crowhine
 */
public abstract class BaseOriginalFragment<P extends IBaseOriginalPresenter> extends Fragment implements IBaseOriginalView {
    private P mPresenter;

    /**
     * 创建 Presenter
     *
     * @return
     */
    protected abstract P bindPresenter();

    /**
     * 获取Presenter对象，在需要获取时才创建Presenter，起到懒加载作用
     */
    public P getPresenter() {
        if (mPresenter == null) {
            mPresenter = bindPresenter();
        }
        return mPresenter;
    }

    @Override
    public FragmentActivity getSelfActivity() {
        return getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.doDestroy();
            mPresenter=null;
        }
    }
}
