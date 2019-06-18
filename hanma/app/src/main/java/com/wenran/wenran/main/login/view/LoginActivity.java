package com.wenran.wenran.main.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.wenran.wenran.R;
import com.wenran.wenran.main.login.bean.LoginParamBean;
import com.wenran.wenran.main.login.bean.StandardResultBean;
import com.wenran.wenran.main.login.contract.LoginContract;
import com.wenran.wenran.main.login.presenter.LoginPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import wenran.com.baselibrary.base.basemvp.BaseActivityImpl;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.utils.CheckUserInfoAvailable;
import wenran.com.baselibrary.utils.DealClick;
import wenran.com.baselibrary.utils.MyLog;
import wenran.com.baselibrary.utils.MyToast;
import wenran.com.baselibrary.utils.RouterPath;
import wenran.com.baselibrary.utils.ViewUtil;

/**
 * @author crowhine
 */
@Route(path = RouterPath.MINE_LOGIN_ACTIVITY)
public class LoginActivity extends BaseActivityImpl<LoginContract.ILoginPresenter>
        implements LoginContract.ILoginView, TextWatcher {
    @BindView(R.id.login_iv_head_portrait)
    ImageView loginIvHeadPortrait;
    @BindView(R.id.login_et_username)
    EditText loginEtUsername;
    @BindView(R.id.login_et_psw)
    EditText loginEtPsw;
    @BindView(R.id.login_tv_to_register)
    TextView loginTvToRegister;
    @BindView(R.id.login_tv_forget_psw)
    TextView loginTvForgetPsw;
    @BindView(R.id.login_tv_login)
    TextView loginTvLogin;
    @BindView(R.id.login_iv_we_chat)
    ImageView loginIvWeChat;
    @BindView(R.id.login_iv_qq)
    ImageView loginIvQq;
    private Unbinder bind;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind!=null){
            bind.unbind();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginContract.ILoginPresenter bindPresenter() {
        return new LoginPresenterImpl(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bind = ButterKnife.bind(this);
        loginEtUsername.addTextChangedListener(this);
        loginEtPsw.addTextChangedListener(this);

        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);
    }

    @OnClick({R.id.login_tv_to_register, R.id.login_tv_forget_psw, R.id.login_tv_login, R.id.login_iv_we_chat, R.id.login_iv_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_tv_to_register:
                DealClick.deal(this, new DealClick.ClickCallback() {
                    @Override
                    public void isSuccess() {
                        Intent intent = new Intent(getSelfActivity(), RegisterActivity.class);
                        intent.putExtra(ConstantTag.DATA_TAG.name(), ConstantTag.LOGIN_TO_REGISTER);
                        startActivity(intent);
                    }
                });

                break;
            case R.id.login_tv_forget_psw:
                DealClick.deal(this, new DealClick.ClickCallback() {
                    @Override
                    public void isSuccess() {
                        Intent intent2 = new Intent(getSelfActivity(), RegisterActivity.class);
                        intent2.putExtra(ConstantTag.DATA_TAG.name(), ConstantTag.LOGIN_TO_RESET_PSW);
                        startActivity(intent2);
                    }
                });

                break;
            case R.id.login_tv_login:
                DealClick.deal(this, new DealClick.ClickCallback() {
                    @Override
                    public void isSuccess() {
                        showLoadingProgress(null);
                        String user = ViewUtil.getTextFromView(loginEtUsername);
                        String psw = ViewUtil.getTextFromView(loginEtPsw);
                        getPresenter().login(new LoginParamBean(user, psw));
                    }
                });
                break;
            case R.id.login_iv_we_chat:
                DealClick.deal(this, new DealClick.ClickCallback() {
                    @Override
                    public void isSuccess() {
                        getPresenter().anotherLogin(SHARE_MEDIA.WEIXIN);
                    }
                });

                break;
            case R.id.login_iv_qq:
                DealClick.deal(this, new DealClick.ClickCallback() {
                    @Override
                    public void isSuccess() {
                        MyToast.s(getSelfActivity(), getResources().getString(R.string.developing), 0);
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void loginSuccess(StandardResultBean data) {
        hideLoadingProgress();
        dealSuccess(data.getMessage());
        finish();
    }

    @Override
    public void loginFailure(Integer code, String msg) {
        hideLoadingProgress();
        if (ConstantNum.ANOTHER_LOGIN_FAILUER == code) {
            Intent intent = new Intent(this, BindPhoneActivity.class);
            intent.putExtra(ConstantTag.DATA_TAG.getTagValue(), getPresenter().getAnotherLoginInfo());
            startActivityForResult(intent, ConstantNum.LOGIN_TO_BIND_REQUEST_CODE);
        } else {
            dealFailure(code, msg);
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        MyLog.i(this, "msg:tel");
        if (checkTelLength() && checkPswLength()) {
            setLoginTvStatus(true);
        } else {
            setLoginTvStatus(false);
        }
    }


    /**
     * 检查手机号码长度
     */
    private boolean checkTelLength() {
        String textFromView = ViewUtil.getTextFromView(loginEtUsername);
        if (textFromView.length() < ConstantNum.TELEPHONE_NUM_LENGTH) {
            return false;
        } else if (textFromView.length() > ConstantNum.TELEPHONE_NUM_LENGTH) {
            loginEtUsername.setText(textFromView.substring(0, ConstantNum.TELEPHONE_NUM_LENGTH));
            loginEtUsername.setSelection(ConstantNum.TELEPHONE_NUM_LENGTH);
        }
        return true;
    }

    /**
     * 检查密码长度
     */
    private boolean checkPswLength() {
        return CheckUserInfoAvailable.checkLength(loginEtPsw,
                ConstantNum.PSW_LENGTH);
    }

    /**
     * 设置登录按钮的状态
     *
     * @param isAvailable 是否是可以登录状态
     */
    private void setLoginTvStatus(boolean isAvailable) {
        if (isAvailable) {
            loginTvLogin.setEnabled(true);
            loginTvLogin.setBackgroundResource(R.drawable.radius16_button_orange_ffffa632);
        } else {
            loginTvLogin.setEnabled(false);
            loginTvLogin.setBackgroundResource(R.drawable.radius16_button_gray_ffd2d2d2);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantNum.LOGIN_TO_BIND_REQUEST_CODE
                && resultCode == ConstantNum.BIND_TO_LOGIN_RESULT_CODE) {
            finish();
        }
    }



}
