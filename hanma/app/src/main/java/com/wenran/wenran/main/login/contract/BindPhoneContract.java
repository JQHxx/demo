package com.wenran.wenran.main.login.contract;

import com.wenran.wenran.main.login.bean.AnotherLoginInfo;
import com.wenran.wenran.main.login.bean.BindPhoneParamBean;
import com.wenran.wenran.main.login.bean.PhoneVcParamBean;
import com.wenran.wenran.main.login.bean.StandardResultBean;

import java.io.File;

import wenran.com.baselibrary.base.basemvp.IBasePresenter;
import wenran.com.baselibrary.base.basemvp.IBaseView;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.constant.ConstantTag;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */
public final class BindPhoneContract {

    public interface IBindPhoneModel {
        /**
         * 绑定手机号
         *
         * @param bindPhoneParamBean 注册需要的参数
         * @param file
         * @param normalCallback
         */
        void bindPhone(BindPhoneParamBean bindPhoneParamBean, File file, INormalCallback<StandardResultBean> normalCallback);

    }


    public interface IBindPhoneView extends IBaseView {

        /**
         * 绑定手机号码返回操作
         *
         * @date 携带数据
         */
        void bindPhoneSuccess(StandardResultBean data);

        /**
         * 绑定手机号码失败
         *
         * @param code 失败码检测是否身份过期
         * @param msg
         */
        void bindPhoneFailure(Integer code, String msg);


        /**
         * 发送验证码返回操作
         *
         * @@param data  携带数据
         */
        void sendCodeSuccess(StandardResultBean data);

        /**
         * 发送验证码失败
         *
         * @param code 失败码检测是否身份过期
         * @param msg
         */
        void sendCodeFailure(Integer code, String msg);
    }

    public interface IBindPhonePresenter extends IBasePresenter {
        /**
         * 绑定手机号码
         *
         * @param paramBean 注册需要的参数
         */
        void bindPhone(AnotherLoginInfo paramBean, String phone, String vCode);

        /**
         * 发送短信
         *
         * @param paramBean 需要的参数
         * @param forWhat   验证码用于注册还是修改密码
         */
        void sendCode(PhoneVcParamBean paramBean, ConstantTag forWhat);


    }
}
