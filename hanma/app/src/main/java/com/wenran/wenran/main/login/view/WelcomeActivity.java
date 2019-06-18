package com.wenran.wenran.main.login.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.wenran.wenran.R;
import com.wenran.wenran.main.MainActivity;

import wenran.com.baselibrary.base.basemvp.BasePermissionActivity;
import wenran.com.baselibrary.base.basemvp.IBasePresenter;
import wenran.com.baselibrary.constant.ConstantNum;

/**
 * 欢迎界面
 *
 * @author crowhine
 */
public class WelcomeActivity extends BasePermissionActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected IBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 23) {
            obtainPermissions();
        } else {
            isToGuide();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        doSth();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ConstantNum.PERMISSION_SET_BACK_REQUEST == requestCode) {
            obtainPermissions();
        }
    }

    private void obtainPermissions() {
        String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.GET_ACCOUNTS};
        requestPermissions(getSelfActivity(), mPermissionList,
                new RequestPermissionCallBack() {
                    @Override
                    public void granted() {
                        isToGuide();
                    }

                    @Override
                    public void denied() {
                        finish();
                    }
                }, ConstantNum.REQUEST_PERMISSION_ALL);
    }

    private void isToGuide() {
        new Handler().postDelayed(new IsGotoGuideRunnable(this), ConstantNum.TO_GUIDE_DELAY_SECOND);
    }

    public class IsGotoGuideRunnable implements Runnable {
        Context context;

        public IsGotoGuideRunnable(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            Intent intent = new Intent();
//         //需求需要，暂时去掉引导页
//        boolean aBoolean = SpUtil.getBoolean(context, HomeConstantTag.IS_FIRST_INPUT.name(), true);
//        if (aBoolean) {
//            intent.setClass(context, Guide.class);
//        } else {
//            intent.setClass(context, MainActivity.class);
//        }
            intent.setClass(context, MainActivity.class);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
    }


    /**
     * 防止重复启动启动页
     */
    private void doSth() {
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent
                    .ACTION_MAIN)) {
                finish();
                return;
            }
        }
    }
}
