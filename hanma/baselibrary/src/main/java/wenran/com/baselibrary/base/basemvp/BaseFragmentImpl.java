package wenran.com.baselibrary.base.basemvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;

import wenran.com.baselibrary.R;
import wenran.com.baselibrary.base.BaseApplication;
import wenran.com.baselibrary.base.widget.MyProgressDialog;
import wenran.com.baselibrary.base.widget.ShowYesNoDialog;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.help.MyLoad;
import wenran.com.baselibrary.utils.FragmentControl;
import wenran.com.baselibrary.utils.MyLog;
import wenran.com.baselibrary.utils.MyToast;
import wenran.com.baselibrary.utils.StringUtil;


/**
 * Created by Crowhine on 2019/1/8
 *
 * @author crowhine
 */

public abstract class BaseFragmentImpl<P extends IBasePresenter> extends BaseOriginalFragment<P>
        implements IBaseView {
    private View mRootView;
    /**
     * Fragment的View加载完毕的标记
     */
    protected boolean isViewCreated;
    /**
     * Fragment对用户可见的标记
     */
    protected boolean isUIVisible;
    protected FragmentControl fragmentControl;
    protected LoadService loadService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentControl = new FragmentControl(getSelfActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == mRootView) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }
        baseInitView();
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        //替换成个mRootView的时候需要，并且loadSir需要在onCreateView方法中初始化
        return loadService == null ? mRootView : loadService.getLoadLayout();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    @Override
    public void onDestroy() {
        release();
        super.onDestroy();
    }

    /**
     * 获取根布局
     */
    protected View getRootView() {
        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            MyLog.i(BaseApplication.app, "onHiddenChanged:setUserVisibleHint：显示");
            isUIVisible = true;
            lazyLoad();
        } else {
            MyLog.i(BaseApplication.app, "onHiddenChanged:setUserVisibleHint：隐藏");
            isUIVisible = false;
        }
    }

    @Override
    public void release() {
        if (fragmentControl != null) {
            fragmentControl = null;
        }

        if (loadService != null) {
            loadService = null;
        }
        hideLoadingProgress();
    }

    @Override
    public void baseInitView() {

    }

    @Override
    public void showException(String exceptionInfo) {
        if (!StringUtil.isEmptyStr(exceptionInfo)){
            View decorView = getSelfActivity().getWindow().getDecorView();
            if (decorView == null) {
                Log.d("bug", "decorView==null");
                return;
            }
            MyToast.sb(decorView, exceptionInfo, 1);
        }
    }

    @Override
    public void dealSuccess(String hint) {
        if (!StringUtil.isEmptyStr(hint)) {
            MyToast.s(getSelfActivity(), hint, 0);
        }
    }

    @Override
    public void showHint(String hint) {
        if (!StringUtil.isEmptyStr(hint)) {
            MyToast.s(getSelfActivity(), hint, 0);
        }
    }

    @Override
    public void dealFailure(Integer code, String hint) {
        if (code == null || code == 0) {
            MyToast.s(getSelfActivity(), hint, 0);
            return;
        }
        if (ConstantTag.APP_TOKEN_OVER_DUE.getTagValue().equals(code)) {
            final String loginHint = getResources().getString(R.string.app_token_over_due);
            String hintStr = StringUtil.isEmptyStr(hint) ? loginHint : hint;
            ShowYesNoDialog showYesNoDialog = new ShowYesNoDialog(getSelfActivity(), new ShowYesNoDialog.DoConfirmCallBack() {
                @Override
                public void clickConfirm() {
//                    Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
//                    startActivity(intent);
                    MyToast.s(getSelfActivity(), loginHint, 0);
                }
            }, loginHint);
        } else {
            if (!StringUtil.isEmptyStr(hint)) {
                MyToast.s(getSelfActivity(), hint, 0);
            }
        }
    }

    @Override
    public void showLoadingProgress(String hint) {
        if (StringUtil.isEmptyStr(hint)) {
            MyProgressDialog.show(getSelfActivity());
        } else {

        }
    }

    @Override
    public void hideLoadingProgress() {
        MyProgressDialog.dismiss();
    }

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
            loadData();
        }
    }

    @Override
    public void getLoadService(Object target, Class defaultShow, Callback.OnReloadListener onReloadListener) {
        if (loadService == null) {
            loadService = new MyLoad().initLoadSir(defaultShow).register(target, onReloadListener);
        } else {
            loadService.showCallback(defaultShow);
        }
    }

    public void showLoadServiceSuccess() {
        if (loadService != null) {
            loadService.showSuccess();
        }
    }

    /**
     * 指定布局文件
     *
     * @return
     */
    protected abstract int getLayoutId();


    /**
     * 初始化布局
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 子类加载数据
     */
    protected void loadData() {

    }
}
