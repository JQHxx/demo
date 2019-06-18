package wenran.com.baselibrary.base.basemvp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Crowhine on 2019/2/21
 *
 * @author Crowhine
 */
public abstract class BaseOriginalActivity<P extends IBaseOriginalPresenter> extends AppCompatActivity implements IBaseOriginalView {
    private P mPresenter;

    /**
     * 创建 Presenter
     *
     * @return
     */
    protected abstract P bindPresenter();

    /**
     * 获取 Presenter 对象，在需要获取时才创建`Presenter`，起到懒加载作用
     */
    public P getPresenter() {
        if (mPresenter == null) {
            mPresenter = bindPresenter();
        }
        return mPresenter;
    }

    @Override
    public Activity getSelfActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.doDestroy();
            mPresenter=null;
        }
    }
}
