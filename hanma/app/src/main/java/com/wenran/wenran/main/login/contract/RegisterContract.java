package com.wenran.wenran.main.login.contract;

import com.wenran.wenran.main.login.bean.PhoneVcParamBean;
import com.wenran.wenran.main.login.bean.RegisterParamBean;
import com.wenran.wenran.main.login.bean.ResetPswParamBean;
import com.wenran.wenran.main.login.bean.StandardResultBean;

import wenran.com.baselibrary.base.basemvp.IBasePresenter;
import wenran.com.baselibrary.base.basemvp.IBaseView;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.constant.ConstantTag;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */
public final class RegisterContract {

    public interface IRegisterModel {
        /**
         * 注册
         *
         * @param registerParamBean 注册需要的参数
         * @param normalCallback
         */
        void register(RegisterParamBean registerParamBean, INormalCallback<StandardResultBean> normalCallback);


        /**
         * 重新设置密码
         *
         * @param resetPswParamBean 需要的参数
         * @param normalCallback
         */
        void resetPsw(ResetPswParamBean resetPswParamBean,  INormalCallback<StandardResultBean> normalCallback);

        /**
         * 注册
         *
         * @param phoneVcParamBean 发送验证码需要的参数
         * @param forWhat          验证码用于注册还是修改密码
         * @param normalCallback
         */
        void sendCode(PhoneVcParamBean phoneVcParamBean, ConstantTag forWhat, INormalCallback<StandardResultBean> normalCallback);

    }


    public interface IRegisterView extends IBaseView {

        /**
         * 注册返回操作
         *
         * @param data  携带数据
         */
        void registerSuccess(StandardResultBean data);

        /**
         * 注册失败
         *
         * @param code 失败码检测是否身份过期
         * @param msg
         */
        void registerFailure(Integer code, String msg);

        /**
         * 重新设置密码返回操作
         *
         * @date 携带数据
         */
        void resetPswSuccess(StandardResultBean data);

        /**
         * 重新设置密码失败
         *
         * @param code 失败码检测是否身份过期
         * @param msg
         */
        void resetPswFailure(Integer code, String msg);

        /**
         * 发送验证码返回操作
         *
         * @param data  携带数据
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

    public interface IRegisterPresenter extends IBasePresenter {
        /**
         * 注册
         *
         * @param paramBean 注册需要的参数
         */
        void register(RegisterParamBean paramBean);

        /**
         * 发送短信
         *
         * @param paramBean 需要的参数
         * @param forWhat   验证码用于注册还是修改密码
         */
        void sendCode(PhoneVcParamBean paramBean, ConstantTag forWhat);

        /**
         * 重新设置密码
         *
         * @param paramBean 需要的参数
         */
        void resetPsw(ResetPswParamBean paramBean);
    }
}
