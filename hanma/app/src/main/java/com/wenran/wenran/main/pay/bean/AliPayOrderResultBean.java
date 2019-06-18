package com.wenran.wenran.main.pay.bean;

/**
 * Created by Crowhine on 2019/5/6
 *
 * @author Crowhine
 * <p>
 * 支付宝获取的订单请求返回
 */
public class AliPayOrderResultBean {

    /**
     * message : 生成预支付订单成功
     * statusCode : 200
     * success : true
     * data : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2016093000634150&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22business_params%22%3A%22%7B%5C%22infoid%5C%22%3A47%2C%5C%22userId%5C%22%3A17%7D%22%2C%22out_trade_no%22%3A%22201905061009360537%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95Java%22%2C%22timeout_express%22%3A%2215m%22%2C%22total_amount%22%3A%220.0%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay¬ify_url=http%3A%2F%2Flocalhost%3A8080%2FWenranApp%2Fapp%2Fpay%2FaliPayInquiryNotify&sign=ag8T5MABDtAv%2FfuVGVeAbhpqHili6zreJO7gHCHaoFMpuTUbv6CoUFkTu%2BIhPsAhwpf7LhyLModoyNSlP6Vp13JHJ5rhvPW1q4COpj%2BPsWK06i37dz5vHX4sK119dqyKJone7jdYlxO9k9mHm4yzEgGxZMLLTzaXJy4SBgIM3J3Iu72S9IQ9DtcU8rxM%2FY2Qk1%2FKIsnObo1FqYysP9pbP1whb3gx2fwVpkMtCxKIH6jKuM9YtFdml8DK13mxL2h%2BXxZTBz9wqXxc1no%2FtVEFX237xisYrLLAnvqChF%2BxQTIU72vt0LV4W4AtrvrsykJiYrXymNUkgKmS55hDfYGOYA%3D%3D&sign_type=RSA2×tamp=2019-05-06+10%3A09%3A36&version=1.0
     */

    private String message;
    private int statusCode;
    private boolean success;
    private String data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
