package com.wenran.wenran.main.login.model;


import android.support.annotation.NonNull;

import com.wenran.wenran.main.login.bean.AnotherLoginParam;
import com.wenran.wenran.main.login.bean.BindPhoneParamBean;
import com.wenran.wenran.main.login.bean.LoginParamBean;
import com.wenran.wenran.main.login.bean.PhoneVcParamBean;
import com.wenran.wenran.main.login.bean.RegisterParamBean;
import com.wenran.wenran.main.login.bean.ResetPswParamBean;
import com.wenran.wenran.main.login.bean.StandardResultBean;
import com.wenran.wenran.main.login.contract.BindPhoneContract;
import com.wenran.wenran.main.login.contract.LoginContract;
import com.wenran.wenran.main.login.contract.RegisterContract;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import wenran.com.baselibrary.base.BaseModel;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.server.RetrofitManager;

/**
 * 网络获取数据
 * Created by crowhine on 2018/7/26.
 *
 * @author crowhine
 */

public class NetModel extends BaseModel implements LoginContract.ILoginModel,
        RegisterContract.IRegisterModel, BindPhoneContract.IBindPhoneModel{

    @Override
    public void login(@NonNull LoginParamBean loginParamBean, INormalCallback<StandardResultBean> normalCallback) {
        Observable<StandardResultBean> netDataObservable = RetrofitManager.getInstance().
                getRetrofitService(ILoginService.class).login(loginParamBean);
        sendRequest(netDataObservable, normalCallback);
    }

    @Override
    public void anotherLogin(@NonNull AnotherLoginParam anotherLoginParam, INormalCallback<StandardResultBean> normalCallback) {
        Observable<StandardResultBean> netDataObservable = RetrofitManager.getInstance().
                getRetrofitService(ILoginService.class).anotherLogin(anotherLoginParam);
        sendRequest(netDataObservable, normalCallback);
    }

    @Override
    public void register(@NonNull RegisterParamBean registerParamBean, INormalCallback<StandardResultBean> normalCallback) {
        Observable<StandardResultBean> netDataObservable = RetrofitManager.getInstance().
                getRetrofitService(ILoginService.class).register(registerParamBean);
        sendRequest(netDataObservable, normalCallback);
    }

    @Override
    public void resetPsw(@NonNull ResetPswParamBean resetPswParamBean, INormalCallback<StandardResultBean> normalCallback) {
        Observable<StandardResultBean> netDataObservable = RetrofitManager.getInstance().
                getRetrofitService(ILoginService.class).resetPsw(resetPswParamBean);
        sendRequest(netDataObservable, normalCallback);
    }

    @Override
    public void sendCode(@NonNull PhoneVcParamBean phoneVcParamBean, ConstantTag forWhat, INormalCallback<StandardResultBean> normalCallback) {
        switch (forWhat) {
            case LOGIN_TO_REGISTER:
                Observable<StandardResultBean> netDataObservable = RetrofitManager.getInstance().
                        getRetrofitService(ILoginService.class).sendPhoneVc(phoneVcParamBean.getMobile());
                sendRequest(netDataObservable, normalCallback);
                break;
            case LOGIN_TO_RESET_PSW:
                Observable<StandardResultBean> netDataObservable2 = RetrofitManager.getInstance().
                        getRetrofitService(ILoginService.class).sendPhoneVcForReSet(phoneVcParamBean.getMobile());
                sendRequest(netDataObservable2, normalCallback);
                break;
            case LOGIN_TO_BIND_PHONE:
                Observable<StandardResultBean> netDataObservable3 = RetrofitManager.getInstance().
                        getRetrofitService(ILoginService.class).sendPhoneVcForBindPhone(phoneVcParamBean.getMobile());
                sendRequest(netDataObservable3, normalCallback);
                break;
            default:
                break;
        }
    }

    @Override
    public void bindPhone(@NonNull BindPhoneParamBean bindPhoneParamBean, File file, INormalCallback<StandardResultBean> normalCallback) {
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part  和后端约定好Key，这里的partName是用file
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Observable<StandardResultBean> netDataObservable = RetrofitManager.getInstance().
                getRetrofitService(ILoginService.class).
                bindPhone(bindPhoneParamBean.getOpenId(), bindPhoneParamBean.getType(),
                        bindPhoneParamBean.getPhone(), bindPhoneParamBean.getNickName(),
                        bindPhoneParamBean.getvCode(), body);
        sendRequest(netDataObservable, normalCallback);
    }


}
