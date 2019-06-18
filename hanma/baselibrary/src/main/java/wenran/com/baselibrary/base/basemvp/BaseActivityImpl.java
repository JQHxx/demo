package wenran.com.baselibrary.base.basemvp;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;

import wenran.com.baselibrary.R;
import wenran.com.baselibrary.base.widget.MyProgressDialog;
import wenran.com.baselibrary.base.widget.ShowYesNoDialog;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.help.MyLoad;
import wenran.com.baselibrary.utils.MyToast;
import wenran.com.baselibrary.utils.StringUtil;

/**
 * Created by Crowhine on 2019/1/8
 *
 * @author crowhine
 */
public abstract class BaseActivityImpl<P extends IBasePresenter> extends BaseOriginalActivity<P>
        implements IBaseView {
    protected LoadService loadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        baseInitView();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        release();
        super.onDestroy();
    }


    @Override
    public void release() {
        if (loadService != null) {
            loadService = null;
        }
        hideLoadingProgress();
    }

    @Override
    public void showLoadingProgress(String hint) {
        if (StringUtil.isEmptyStr(hint)) {
            MyProgressDialog.show(this);
        } else {

        }
    }

    public void showLoadServiceSuccess() {
        if (loadService != null) {
            loadService.showSuccess();
        }
    }

    @Override
    public void hideLoadingProgress() {
        MyProgressDialog.dismiss();
    }

    @Override
    public void baseInitView() {
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setGuideTitle(false);
        //设置状态栏颜色
        setStatusBar(getResources().getColor(R.color.main_color));
    }

    @Override
    public void showException(String exceptionInfo) {
        if (!StringUtil.isEmptyStr(exceptionInfo)) {
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
        showHint(hint);
    }

    @Override
    public void showHint(String hint) {
        if (!StringUtil.isEmptyStr(hint)) {
            MyToast.s(this, hint, 0);
        }
    }

    @Override
    public void dealFailure(Integer code, String hint) {
        if (code == null || code == 0) {
            MyToast.s(this, hint, 0);
            return;
        }
        if (ConstantTag.APP_TOKEN_OVER_DUE.getTagValue().equals(code)) {
            final String loginHint = getResources().getString(R.string.app_token_over_due);
            String hintStr = StringUtil.isEmptyStr(hint) ? loginHint : hint;
            ShowYesNoDialog showYesNoDialog = new ShowYesNoDialog(this, new ShowYesNoDialog.DoConfirmCallBack() {
                @Override
                public void clickConfirm() {
//                    Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
//                    startActivity(intent);
                    MyToast.s(BaseActivityImpl.this, loginHint, 0);
                }
            }, loginHint);
        } else {
            if (!StringUtil.isEmptyStr(hint)) {
                MyToast.s(this, hint, 0);
            }
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
     * 导航栏去掉
     */
    public void setGuideTitle(boolean b) {
        if (b == false) {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }

    /**
     * Android 6.0 以上设置状态栏颜色
     */
    protected void setStatusBar(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 设置状态栏底色颜色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(color);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // 如果亮色，设置状态栏文字为黑色
//            if (isLightColor(color)) {
//                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            } else {
//                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//            }
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


}
