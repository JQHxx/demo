package com.wenran.wenran.main.login.contract;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.wenran.wenran.main.login.bean.AnotherLoginInfo;
import com.wenran.wenran.main.login.bean.AnotherLoginParam;
import com.wenran.wenran.main.login.bean.LoginParamBean;
import com.wenran.wenran.main.login.bean.StandardResultBean;

import wenran.com.baselibrary.base.basemvp.IBasePresenter;
import wenran.com.baselibrary.base.basemvp.IBaseView;
import wenran.com.baselibrary.callback.INormalCallback;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */
public final class LoginContract {

    public interface ILoginModel {
        /**
         * 登录
         *
         * @param loginParamBean 登录需要的参数
         * @param normalCallback
         */
        void login(LoginParamBean loginParamBean, INormalCallback<StandardResultBean> normalCallback);

        /**
         * 第三方登录
         *
         * @param anotherLoginParam 登录需要的参数
         * @param normalCallback
         */
        void anotherLogin(AnotherLoginParam anotherLoginParam, INormalCallback<StandardResultBean> normalCallback);
    }


    public interface ILoginView extends IBaseView {

        /**
         * 登录返回操作
         *
         * @param data 携带数据
         */
        void loginSuccess(StandardResultBean data);

        /**
         * 登录失败
         *
         * @param code 失败码检测是否身份过期
         * @param msg
         */
        void loginFailure(Integer code, String msg);
    }

    public interface ILoginPresenter extends IBasePresenter {
        /**
         * 登录
         *
         * @param paramBean 登录需要的参数
         */
        void login(LoginParamBean paramBean);

        /**
         * 第三方登录
         *
         * @param media 什么平台
         */
        void anotherLogin(SHARE_MEDIA media);

        /**
         * @return
         */
        AnotherLoginInfo getAnotherLoginInfo();
    }
}
