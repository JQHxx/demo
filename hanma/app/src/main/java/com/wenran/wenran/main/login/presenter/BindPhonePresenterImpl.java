package com.wenran.wenran.main.login.presenter;

import com.wenran.wenran.main.login.bean.AnotherLoginInfo;
import com.wenran.wenran.main.login.bean.BindPhoneParamBean;
import com.wenran.wenran.main.login.bean.PhoneVcParamBean;
import com.wenran.wenran.main.login.bean.StandardResultBean;
import com.wenran.wenran.main.login.contract.BindPhoneContract;
import com.wenran.wenran.main.login.model.NetModel;

import java.io.File;

import wenran.com.baselibrary.base.basemvp.BasePresenterImpl;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.callback.IStandardCallBack;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.utils.FileUtil;
import wenran.com.baselibrary.utils.SpUtil;
import wenran.com.baselibrary.utils.StringUtil;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */
public class BindPhonePresenterImpl extends BasePresenterImpl<BindPhoneContract.IBindPhoneView,NetModel>
        implements BindPhoneContract.IBindPhonePresenter {

    public BindPhonePresenterImpl(BindPhoneContract.IBindPhoneView mBaseView) {
        super(mBaseView);
        initModel(NetModel.class);
    }

    @Override
    public void sendCode(PhoneVcParamBean paramBean, ConstantTag forWhat) {
        if (paramBean == null) {
            dealException(ConstantTag.NEED_MORE_INFO.getTagValue(), true, getClass());
            return;
        }
        netModel.sendCode(paramBean, forWhat, new INormalCallback<StandardResultBean>() {
            @Override
            public void success(StandardResultBean msg) {
                if (msg == null) {
                    dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
                    return;
                } else {
                    if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                        if (isViewAttach()){
                            getView().sendCodeSuccess(msg);
                        }
                    } else {
                        if (isViewAttach()){
                            getView().sendCodeFailure(msg.getStatusCode(), msg.getMessage());
                        }
                    }
                }
            }

            @Override
            public void failure(String failureInfo) {
                dealException(failureInfo, true, getClass());
            }
        });
    }

    @Override
    public void bindPhone(AnotherLoginInfo paramBean, String phone, String vCode) {
        if (paramBean == null || StringUtil.isEmptyStr(phone) || StringUtil.isEmptyStr(vCode)) {
            dealException(ConstantTag.NEED_MORE_INFO.getTagValue(), true, getClass());
            return;
        }
        getHeadPortraitFile(paramBean, phone, vCode);
    }

    /**
     * 获取file格式的头像
     *
     * @param paramBean
     * @param vCode
     * @param phone
     */
    private void getHeadPortraitFile(AnotherLoginInfo paramBean, String phone, String vCode) {
        final BindPhoneParamBean bindPhoneParamBean = new BindPhoneParamBean();
        bindPhoneParamBean.setOpenId(paramBean.getOpenid());
        bindPhoneParamBean.setNickName(paramBean.getName());
        bindPhoneParamBean.setPhone(phone);
        bindPhoneParamBean.setType(paramBean.getPlatform());
        bindPhoneParamBean.setvCode(vCode);
        new FileUtil().download(paramBean.getProfileImageUrl(), new IStandardCallBack<File>() {
            @Override
            public void success(File msg) {
                bindPhone(bindPhoneParamBean, msg);
            }

            @Override
            public void failure(String code, String failureInfo) {
                dealException(failureInfo, true, getClass());
            }
        });
    }

    /**
     * 后台绑定
     *
     * @param bindPhoneParamBean
     * @param headPortraitFile
     */
    private void bindPhone(BindPhoneParamBean bindPhoneParamBean, File headPortraitFile) {
        netModel.bindPhone(bindPhoneParamBean, headPortraitFile, new INormalCallback<StandardResultBean>() {
            @Override
            public void success(StandardResultBean msg) {
                if (msg == null) {
                    dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
                    return;
                } else {
                    if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                        saveToken(msg.getData().getApptoken());
                        if (isViewAttach()) {
                            getView().bindPhoneSuccess(msg);
                        }
                    } else {
                        if (isViewAttach()) {
                            getView().bindPhoneFailure(msg.getStatusCode(), msg.getMessage());
                        }
                    }
                }
            }

            @Override
            public void failure(String failureInfo) {
                dealException(failureInfo, true, getClass());
            }
        });
    }

    /**
     * 保存appTaken
     */
    private void saveToken(String appToken) {
        if (!StringUtil.isEmptyStr(appToken)) {
            //做保存token操作
            if (isViewAttach()){
                SpUtil.putString(getView().getSelfActivity(), ConstantTag.APP_TOKEN.name(), appToken);
            }
        }
    }
}
