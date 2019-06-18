package com.wenran.wenran.main.pay.model;

import com.wenran.wenran.main.pay.bean.AliPayOrderParamBean;
import com.wenran.wenran.main.pay.bean.AliPayOrderResultBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Crowhine on 2019/5/6
 *
 * @author Crowhine
 */
public interface IPayService {
    /**
     * 登录
     *
     * @param aliPayOrderParamBean 参数
     * @return AliPayOrderResultBean
     */
    @POST("alipay/aliPayOrder")
    Observable<AliPayOrderResultBean> getAliPayOrderInfo(@Body AliPayOrderParamBean aliPayOrderParamBean);
}
