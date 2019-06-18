package com.wenran.wenran.main.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenran.wenran.R;
import com.wenran.wenran.main.login.bean.AnotherLoginInfo;
import com.wenran.wenran.main.login.bean.PhoneVcParamBean;
import com.wenran.wenran.main.login.bean.StandardResultBean;
import com.wenran.wenran.main.login.contract.BindPhoneContract;
import com.wenran.wenran.main.login.presenter.BindPhonePresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import wenran.com.baselibrary.base.basemvp.BaseActivityImpl;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.utils.DealClick;
import wenran.com.baselibrary.utils.GlideUtil;
import wenran.com.baselibrary.utils.StringUtil;
import wenran.com.baselibrary.utils.TimeCount;
import wenran.com.baselibrary.utils.ViewUtil;

/**
 * @author crowhine
 * <p>
 * function:第三方登录绑定用户信息界面
 */
public class BindPhoneActivity extends BaseActivityImpl<BindPhoneContract.IBindPhonePresenter> implements BindPhoneContract.IBindPhoneView, TextWatcher {

    @BindView(R.id.base_title_iv_left)
    ImageView baseTitleIvLeft;
    @BindView(R.id.base_title_tv_center)
    TextView baseTitleTvCenter;
    @BindView(R.id.bind_phone_iv_head_portrait)
    ImageView bindPhoneIvHeadPortrait;
    @BindView(R.id.bind_phone_et_username)
    EditText bindPhoneEtUsername;
    @BindView(R.id.bind_phone_et_verification_code)
    EditText bindPhoneEtVerificationCode;
    @BindView(R.id.bind_phone_tv_send_code)
    TextView bindPhoneTvSendCode;
    @BindView(R.id.bind_phone_tv_bind)
    TextView bindPhoneTvBind;
    /**
     * 发送短信的计时器
     */
    private CountDownTimer countDownTimer;
    private AnotherLoginInfo anotherLoginInfo;
    private Unbinder bind;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected BindPhoneContract.IBindPhonePresenter bindPresenter() {
        return new BindPhonePresenterImpl(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bind = ButterKnife.bind(this);
        baseTitleIvLeft.setImageResource(R.mipmap.base_title_cancel);
        baseTitleTvCenter.setText(R.string.bind_phone);
        bindPhoneEtUsername.addTextChangedListener(this);
        bindPhoneEtVerificationCode.addTextChangedListener(this);

        getIntentData();
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

    @OnClick({R.id.base_title_iv_left, R.id.bind_phone_tv_send_code, R.id.bind_phone_tv_bind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.base_title_iv_left:
                finish();
                break;
            case R.id.bind_phone_tv_send_code:
                obtainVCode();
                break;
            case R.id.bind_phone_tv_bind:
                DealClick.deal(this, new DealClick.ClickCallback() {
                    @Override
                    public void isSuccess() {
                        bindPhone();
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void bindPhoneSuccess(StandardResultBean data) {
        hideLoadingProgress();
        dealSuccess(data.getMessage());
        Intent intent = getIntent();
        setResult(ConstantNum.BIND_TO_LOGIN_RESULT_CODE,intent);
        finish();
    }

    @Override
    public void bindPhoneFailure(Integer code, String msg) {
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


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (checkTelLength() & checkVerificationCodeLength()) {
            setBindPhoneTvStatus(true);
        } else {
            setBindPhoneTvStatus(false);
        }
    }

    /**
     * 获取验证码
     */
    private void obtainVCode() {
        if (ConstantNum.TELEPHONE_NUM_LENGTH != (ViewUtil.getTextFromView(bindPhoneEtUsername).length())) {
            showHint(getString(R.string.register_hint_no_phone));
            return;
        }
        showLoadingProgress(null);
        String textFromView = ViewUtil.getTextFromView(bindPhoneEtUsername);
        getPresenter().sendCode(new PhoneVcParamBean(textFromView), ConstantTag.LOGIN_TO_BIND_PHONE);
    }

    /**
     * 发送验证码成功后，倒计时
     */
    private void timeCount() {
        if (countDownTimer != null) {
            return;
        }
        countDownTimer = new TimeCount(this, bindPhoneTvSendCode, ConstantNum.VERIFICATION_CODE_COUNT_TIME, 1000, new TimeCount.TimeCallBack() {
            @Override
            public void finish() {
                countDownTimer = null;
            }
        }).start();
    }

    /**
     * 检查手机号码长度
     */
    private boolean checkTelLength() {
        String textFromView = ViewUtil.getTextFromView(bindPhoneEtUsername);
        if (textFromView.length() < ConstantNum.TELEPHONE_NUM_LENGTH) {
            return false;
        } else if (textFromView.length() > ConstantNum.TELEPHONE_NUM_LENGTH) {
            bindPhoneEtUsername.setText(textFromView.substring(0, ConstantNum.TELEPHONE_NUM_LENGTH));
            bindPhoneEtUsername.setSelection(ConstantNum.TELEPHONE_NUM_LENGTH);
        }
        return true;
    }

    /**
     * 检查验证码长度
     */
    private boolean checkVerificationCodeLength() {
        String textFromView = ViewUtil.getTextFromView(bindPhoneEtVerificationCode);
        if (textFromView.length() < ConstantNum.VERIFICATION_CODE_LENGTH) {
            return false;
        } else if (textFromView.length() > ConstantNum.VERIFICATION_CODE_LENGTH) {
            bindPhoneEtVerificationCode.setText(textFromView.substring(0, ConstantNum.VERIFICATION_CODE_LENGTH));
            bindPhoneEtVerificationCode.setSelection(ConstantNum.VERIFICATION_CODE_LENGTH);
        }
        return true;
    }

    /**
     * 设置注册按钮的状态
     *
     * @param isAvailable 是否是可以的状态状态
     */
    private void setBindPhoneTvStatus(boolean isAvailable) {
        if (isAvailable) {
            bindPhoneTvBind.setEnabled(true);
            bindPhoneTvBind.setBackgroundResource(R.drawable.radius16_button_orange_ffffa632);
        } else {
            bindPhoneTvBind.setEnabled(false);
            bindPhoneTvBind.setBackgroundResource(R.drawable.radius16_button_gray_ffd2d2d2);
        }
    }

    /**
     * 绑定手机
     */
    private void bindPhone() {
        if (anotherLoginInfo == null) {
            showHint(getString(R.string.failure));
            return;
        }
        showLoadingProgress(null);

        String phone = ViewUtil.getTextFromView(bindPhoneEtUsername);
        String vCode = ViewUtil.getTextFromView(bindPhoneEtVerificationCode);
        getPresenter().bindPhone(anotherLoginInfo, phone, vCode);
    }

    /**
     * 获取从登录界面过来的第三方个人信息
     */
    public void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            anotherLoginInfo = intent.getParcelableExtra(ConstantTag.DATA_TAG.getTagValue());
            if (anotherLoginInfo != null && !StringUtil.isEmptyStr(anotherLoginInfo.getProfileImageUrl())) {
                String profileImageUrl = anotherLoginInfo.getProfileImageUrl();
                GlideUtil.setCircleImageByUrl(getSelfActivity(),profileImageUrl,
                        bindPhoneIvHeadPortrait,null,null);

            }
        }

    }
}
