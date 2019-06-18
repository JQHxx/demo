package com.wenran.wenran.main.login.presenter;

import com.wenran.wenran.main.login.bean.PhoneVcParamBean;
import com.wenran.wenran.main.login.bean.RegisterParamBean;
import com.wenran.wenran.main.login.bean.ResetPswParamBean;
import com.wenran.wenran.main.login.bean.StandardResultBean;
import com.wenran.wenran.main.login.contract.RegisterContract;
import com.wenran.wenran.main.login.model.NetModel;

import wenran.com.baselibrary.base.basemvp.BasePresenterImpl;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.constant.ConstantTag;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */
public class RegisterPresenterImpl extends BasePresenterImpl<RegisterContract.IRegisterView,NetModel>
        implements RegisterContract.IRegisterPresenter {


    public RegisterPresenterImpl(RegisterContract.IRegisterView mBaseView) {
        super(mBaseView);
        initModel(NetModel.class);
    }

    @Override
    public void register(RegisterParamBean paramBean) {
        if (paramBean == null) {
            dealException(ConstantTag.NEED_MORE_INFO.getTagValue(), true, getClass());
            return;
        }
        netModel.register(paramBean, new INormalCallback<StandardResultBean>() {
            @Override
            public void success(StandardResultBean msg) {
                if (msg == null) {
                    dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
                    return;
                } else {
                    if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                        if (isViewAttach()){
                            getView().registerSuccess(msg);
                        }
                    } else {
                        if (isViewAttach()){
                            getView().registerFailure(msg.getStatusCode(), msg.getMessage());
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
                            getView().registerFailure(msg.getStatusCode(), msg.getMessage());
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
    public void resetPsw(ResetPswParamBean paramBean) {
        if (paramBean == null) {
            dealException(ConstantTag.NEED_MORE_INFO.getTagValue(), true, getClass());
            return;
        }
        netModel.resetPsw(paramBean, new INormalCallback<StandardResultBean>() {
            @Override
            public void success(StandardResultBean msg) {
                if (msg == null) {
                    dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
                    return;
                } else {
                    if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                        if (isViewAttach()){
                            getView().resetPswSuccess(msg);
                        }
                    }else {
                        if (isViewAttach()){
                            getView().resetPswFailure(msg.getStatusCode(),msg.getMessage());
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
}
