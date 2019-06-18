package com.wenran.wenran.main.pay.bean;

/**
 * Created by Crowhine on 2019/5/6
 *
 * @author Crowhine
 * <p>
 * 支付宝获取订单请求参数
 */
public class AliPayOrderParamBean {
    String apptoken;
    int classInfoId;

    public AliPayOrderParamBean() {
    }

    public AliPayOrderParamBean(String apptoken, int classInfoId) {
        this.apptoken = apptoken;
        this.classInfoId = classInfoId;
    }

    public String getApptoken() {
        return apptoken;
    }

    public void setApptoken(String apptoken) {
        this.apptoken = apptoken;
    }

    public int getClassInfoId() {
        return classInfoId;
    }

    public void setClassInfoId(int classInfoId) {
        this.classInfoId = classInfoId;
    }
}
