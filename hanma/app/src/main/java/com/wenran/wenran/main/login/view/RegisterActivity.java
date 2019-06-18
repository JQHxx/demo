package com.wenran.wenran.main.login.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wenran.wenran.R;
import com.wenran.wenran.main.login.bean.PhoneVcParamBean;
import com.wenran.wenran.main.login.bean.RegisterParamBean;
import com.wenran.wenran.main.login.bean.ResetPswParamBean;
import com.wenran.wenran.main.login.bean.StandardResultBean;
import com.wenran.wenran.main.login.contract.RegisterContract;
import com.wenran.wenran.main.login.presenter.RegisterPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;
import wenran.com.baselibrary.base.basemvp.BaseActivityImpl;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.utils.CheckUserInfoAvailable;
import wenran.com.baselibrary.utils.DealClick;
import wenran.com.baselibrary.utils.MyToast;
import wenran.com.baselibrary.utils.TimeCount;
import wenran.com.baselibrary.utils.ViewUtil;

/**
 * @author crowhine
 */
public class RegisterActivity extends BaseActivityImpl<RegisterContract.IRegisterPresenter> implements RegisterContract.IRegisterView, TextWatcher {
    @BindView(R.id.register_iv_head_portrait)
    ImageView registerIvHeadPortrait;
    @BindView(R.id.register_et_username)
    EditText registerEtUsername;
    @BindView(R.id.register_et_verification_code)
    EditText registerEtVerificationCode;
    @BindView(R.id.register_tv_send_code)
    TextView registerTvSendCode;
    @BindView(R.id.register_et_psw)
    EditText registerEtPsw;
    @BindView(R.id.register_et_invitation_code)
    EditText registerEtInvitationCode;
    @BindView(R.id.register_tv_register)
    TextView registerTvRegister;
    @BindView(R.id.register_cb_agree)
    CheckBox registerCbAgree;
    @BindView(R.id.register_tv_agree)
    TextView registerTvAgree;
    @BindView(R.id.register_ll_agree)
    LinearLayout registerLlAgree;
    @BindView(R.id.register_title)
    TextView registerTitle;
    /**
     * 注册还是重新设置密码
     */
    private ConstantTag toWhichClass;
    /**
     * 发送短信的计时器
     */
    private CountDownTimer countDownTimer;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer = null;
        }
        if (bind!=null){
            bind.unbind();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }


    @Override
    protected RegisterContract.IRegisterPresenter bindPresenter() {
        return new RegisterPresenterImpl(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        bind = ButterKnife.bind(this);
        Intent intent = getIntent();
        toWhichClass = (ConstantTag) intent.getSerializableExtra(ConstantTag.DATA_TAG.name());
        if (toWhichClass.equals(ConstantTag.LOGIN_TO_RESET_PSW)) {
            //重新设置密码需要初始化的数据
            registerEtPsw.setHint(R.string.reset_psw_et_psw_ht);
            Drawable drawable = getResources().getDrawable(R.mipmap.login_user_psw);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            registerEtInvitationCode.setCompoundDrawables(drawable, null, null, null);
            registerEtInvitationCode.setHint(R.string.reset_psw_et_confirm_psw_ht);
            registerLlAgree.setVisibility(View.GONE);
            registerTvRegister.setText(R.string.common_finish);
            registerTitle.setText(R.string.reset_reset);
            registerEtInvitationCode.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
            registerEtInvitationCode.addTextChangedListener(this);
        }
        registerEtUsername.addTextChangedListener(this);
        registerEtVerificationCode.addTextChangedListener(this);
        registerEtPsw.addTextChangedListener(this);
    }


    @OnClick({R.id.register_tv_send_code, R.id.register_tv_register, R.id.register_tv_agree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_tv_send_code:
                obtainVCode();
                break;
            case R.id.register_tv_register:
                DealClick.deal(this, new DealClick.ClickCallback() {
                    @Override
                    public void isSuccess() {
                        clickConfirm();
                    }
                });
                break;
            case R.id.register_tv_agree:
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

    /**
     * 获取验证码
     */
    private void obtainVCode() {
        if (ConstantNum.TELEPHONE_NUM_LENGTH != (ViewUtil.getTextFromView(registerEtUsername).length())) {
            showHint(getString(R.string.register_hint_no_phone));
            return;
        }
        showLoadingProgress(null);
        String textFromView = ViewUtil.getTextFromView(registerEtUsername);
        getPresenter().sendCode(new PhoneVcParamBean(textFromView), toWhichClass);
//        MyToast.s(this, getResources().getString(R.string.developing), 0);
//        timeCount();
    }

    /**
     * 点击了提交按钮，注册或重新修改密码
     */
    private void clickConfirm() {
        showLoadingProgress(null);
        String phone = ViewUtil.getTextFromView(registerEtUsername);
        String psw = ViewUtil.getTextFromView(registerEtPsw);
        String vCode = ViewUtil.getTextFromView(registerEtVerificationCode);
        String iCode = ViewUtil.getTextFromView(registerEtInvitationCode);
        if (toWhichClass.equals(ConstantTag.LOGIN_TO_REGISTER)) {
            getPresenter().register(new RegisterParamBean(phone, vCode, psw, iCode));
        } else {
            getPresenter().resetPsw(new ResetPswParamBean(phone, vCode, psw, iCode));
        }
    }


    @Override
    public void registerSuccess(StandardResultBean data) {
        hideLoadingProgress();
        finish();
    }

    @Override
    public void registerFailure(Integer code, String msg) {
        hideLoadingProgress();
        dealFailure(code, msg);
    }

    @Override
    public void resetPswSuccess(StandardResultBean data) {
        hideLoadingProgress();
        dealSuccess(data.getMessage());
    }

    @Override
    public void resetPswFailure(Integer code, String msg) {
        hideLoadingProgress();
        dealFailure(code, msg);
    }

    @Override
    public void sendCodeSuccess(StandardResultBean data) {
        hideLoadingProgress();
        dealSuccess(data.getMessage());
        timeCount();
    }

    @Override
    public void sendCodeFailure(Integer code, String msg) {
        hideLoadingProgress();
        dealFailure(code, msg);
    }

    /**
     * 文字输入监听
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        isAvailableRegister();
    }


    @OnCheckedChanged(R.id.register_cb_agree)
    void radioButtonCheckChange(CheckBox checkBox, boolean is) {
        checkBox.setSelected(is);
        isAvailableRegister();
    }

    /**
     * 信息是否都可用
     */
    private void isAvailableRegister() {
        if (toWhichClass.equals(ConstantTag.LOGIN_TO_RESET_PSW)) {
            //修改密码
            if (checkTelLength() & checkVerificationCodeLength()
                    && checkPswLength() && confirmPswLength()) {
                setRegisterTvStatus(true);
                return;
            }
        } else {
            //注册
            if (checkTelLength() & checkVerificationCodeLength()
                    && checkPswLength() && registerCbAgree.isChecked()) {
                setRegisterTvStatus(true);
                return;
            }
        }
        setRegisterTvStatus(false);
    }

    /**
     * 检查手机号码长度
     */
    private boolean checkTelLength() {
        String textFromView = ViewUtil.getTextFromView(registerEtUsername);
        if (textFromView.length() < ConstantNum.TELEPHONE_NUM_LENGTH) {
            return false;
        } else if (textFromView.length() > ConstantNum.TELEPHONE_NUM_LENGTH) {
            registerEtUsername.setText(textFromView.substring(0, ConstantNum.TELEPHONE_NUM_LENGTH));
            registerEtUsername.setSelection(ConstantNum.TELEPHONE_NUM_LENGTH);
        }
        return true;
    }

    /**
     * 检查验证码长度
     */
    private boolean checkVerificationCodeLength() {
        String textFromView = ViewUtil.getTextFromView(registerEtVerificationCode);
        if (textFromView.length() < ConstantNum.VERIFICATION_CODE_LENGTH) {
            return false;
        } else if (textFromView.length() > ConstantNum.VERIFICATION_CODE_LENGTH) {
            registerEtVerificationCode.setText(textFromView.substring(0, ConstantNum.VERIFICATION_CODE_LENGTH));
            registerEtVerificationCode.setSelection(ConstantNum.VERIFICATION_CODE_LENGTH);
        }
        return true;
    }

    /**
     * 检查密码长度
     */
    private boolean checkPswLength() {
        return CheckUserInfoAvailable.checkLength(registerEtPsw,
                ConstantNum.PSW_LENGTH);
    }


    /**
     * 重新设置psw为：确认密码长度
     * 注册为：邀请码，不需要检查
     */
    private boolean confirmPswLength() {
        return CheckUserInfoAvailable.checkLength(registerEtInvitationCode,
                ConstantNum.PSW_LENGTH);
    }

    /**
     * 设置注册按钮的状态
     *
     * @param isAvailable 是否是可以注册状态
     */
    private void setRegisterTvStatus(boolean isAvailable) {
        if (isAvailable) {
            registerTvRegister.setEnabled(true);
            registerTvRegister.setBackgroundResource(R.drawable.radius16_button_orange_ffffa632);
        } else {
            registerTvRegister.setEnabled(false);
            registerTvRegister.setBackgroundResource(R.drawable.radius16_button_gray_ffd2d2d2);
        }
    }


    /**
     * 发送验证码成功后，倒计时
     */
    private void timeCount() {
        if (countDownTimer != null) {
            return;
        }
        countDownTimer = new TimeCount(this, registerTvSendCode, ConstantNum.VERIFICATION_CODE_COUNT_TIME, 1000, new TimeCount.TimeCallBack() {
            @Override
            public void finish() {
                countDownTimer = null;
            }
        }).start();
    }

}
