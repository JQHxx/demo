package com.wenran.wenran.main.login.presenter;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.wenran.wenran.R;
import com.wenran.wenran.main.login.bean.AnotherLoginInfo;
import com.wenran.wenran.main.login.bean.AnotherLoginParam;
import com.wenran.wenran.main.login.bean.LoginParamBean;
import com.wenran.wenran.main.login.bean.StandardResultBean;
import com.wenran.wenran.main.login.contract.LoginContract;
import com.wenran.wenran.main.login.model.NetModel;

import java.util.Map;

import wenran.com.baselibrary.base.basemvp.BasePresenterImpl;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.utils.SpUtil;
import wenran.com.baselibrary.utils.StringUtil;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */
public class LoginPresenterImpl extends BasePresenterImpl<LoginContract.ILoginView,NetModel>
        implements LoginContract.ILoginPresenter {
    /**
     * 第三方登录获取到的个人信息
     */
    private Map<String, String> anotherLoginInfo;
    private SHARE_MEDIA currentPlatform;
    /**
     * 第三方登录平台
     */
    private int platform;

    public LoginPresenterImpl(LoginContract.ILoginView mBaseView) {
        super(mBaseView);
        initModel(NetModel.class);
    }


    @Override
    public void login(LoginParamBean paramBean) {
        if (paramBean == null) {
            dealException(ConstantTag.NEED_MORE_INFO.getTagValue(), true, getClass());
            return;
        }
        netModel.login(paramBean, new INormalCallback<StandardResultBean>() {
            @Override
            public void success(StandardResultBean msg) {
                if (msg == null) {
                    dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
                    return;
                } else {
                    if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                        saveToken(msg.getData().getApptoken());
                        if (isViewAttach()){
                            getView().loginSuccess(msg);
                        }
                    } else {
                        if (isViewAttach()){
                            getView().loginFailure(msg.getStatusCode(), msg.getMessage());
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
    public void anotherLogin(SHARE_MEDIA media) {
        currentPlatform = media;
        if (isViewAttach()){
            getView().showLoadingProgress(null);
            UMShareAPI.get(getView().getSelfActivity()).getPlatformInfo(
                    getView().getSelfActivity(), media, authListener);
        }
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            if (isViewAttach()){
                getView().hideLoadingProgress();
            }
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回d
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            //action为2是请求数据，1的时候释放数据，data为null
            analyzePlatform(platform, data);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            if (isViewAttach()){
                getView().showHint(t.getMessage());
            }
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            if (isViewAttach()){
                getView().showHint(getView().getSelfActivity().getString(R.string.cancel));
            }
        }
    };


    /**
     * 分析是哪个平台的第三方登录
     */
    private void analyzePlatform(SHARE_MEDIA platform, Map<String, String> data) {
        if (data == null) {
            if (isViewAttach()){
                dealException(getView().getSelfActivity().getString(R.string.failure), false, getClass());
            }
            return;
        }
        anotherLoginInfo = data;
        switch (platform) {
            case WEIXIN:
                this.platform = 0;
                anotherLogin(new AnotherLoginParam(this.platform, data.get("openid")));
                break;
            case QQ:
                this.platform = 1;
                anotherLogin(new AnotherLoginParam(this.platform, data.get("openid")));
                break;
            default:
                break;
        }
    }

    /**
     * 后台第三方登录
     */
    private void anotherLogin(AnotherLoginParam paramBean) {
        if (paramBean == null) {
            dealException(ConstantTag.NEED_MORE_INFO.getTagValue(), true, getClass());
            return;
        }
        netModel.anotherLogin(paramBean, new INormalCallback<StandardResultBean>() {
            @Override
            public void success(StandardResultBean msg) {
                if (msg == null) {
                    dealException(ConstantTag.REQUEST_ERROR.getTagValue(), true, getClass());
                    return;
                } else {
                    if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                        saveToken(msg.getData().getApptoken());
                        if (isViewAttach()){
                            getView().loginSuccess(msg);
                        }
                    } else {
                        if (isViewAttach()){
                            getView().loginFailure(msg.getStatusCode(), msg.getMessage());
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
    public AnotherLoginInfo getAnotherLoginInfo() {
        AnotherLoginInfo anotherLoginInfo = new AnotherLoginInfo();
        anotherLoginInfo.setOpenid(this.anotherLoginInfo.get("openid"));
        anotherLoginInfo.setPlatform(platform);
        anotherLoginInfo.setName(this.anotherLoginInfo.get("name"));
        anotherLoginInfo.setProfileImageUrl(this.anotherLoginInfo.get("profile_image_url"));
        return anotherLoginInfo;
    }

    @Override
    public void doDestroy() {
        if (isViewAttach()){
            UMShareAPI.get(getView().getSelfActivity()).deleteOauth(getView().getSelfActivity(),
                    currentPlatform, authListener);
        }
        super.doDestroy();
        anotherLoginInfo = null;
        authListener = null;
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
