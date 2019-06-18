package com.wenran.wenran.main.pay.model;

import android.support.annotation.NonNull;

import com.wenran.wenran.main.pay.bean.AliPayOrderParamBean;
import com.wenran.wenran.main.pay.bean.AliPayOrderResultBean;

import io.reactivex.Observable;
import wenran.com.baselibrary.base.BaseModel;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.server.RetrofitManager;

/**
 * Created by Crowhine on 2019/5/6
 *
 * @author Crowhine
 */
public class NetModel extends BaseModel {
    /**
     * 获取订单信息
     *
     * @param aliPayOrderParamBean
     * @param normalCallback
     */
    public void getAliPayOrderInfo(@NonNull AliPayOrderParamBean aliPayOrderParamBean, INormalCallback<AliPayOrderResultBean> normalCallback) {
        Observable<AliPayOrderResultBean> aliPayOrderInfo = RetrofitManager.getInstance().getRetrofitService(IPayService.class).
                getAliPayOrderInfo(aliPayOrderParamBean);
        sendRequest(aliPayOrderInfo, normalCallback);
    }
}
