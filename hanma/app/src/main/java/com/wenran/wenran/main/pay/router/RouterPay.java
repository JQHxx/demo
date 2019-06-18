package com.wenran.wenran.main.pay.router;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.wenran.wenran.main.pay.bean.AliPayOrderParamBean;
import com.wenran.wenran.main.pay.bean.AliPayOrderResultBean;
import com.wenran.wenran.main.pay.model.NetModel;

import java.util.Map;

import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.interfaces.IPay;
import wenran.com.baselibrary.utils.RouterPath;
import wenran.com.baselibrary.utils.SpUtil;

/**
 * Created by Crowhine on 2019/5/5
 *
 * @author Crowhine
 * <p>
 * 支付类
 */
@Route(path = RouterPath.APP_PAY)
public class RouterPay implements IProvider, IPay {
    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2016093000634150";

    /**
     * 用于支付宝账户登录授权业务的入参 pid。
     */
    public static final String PID = "2088102178002654";

    /**
     * 用于支付宝账户登录授权业务的入参 target_id。
     */
    public static final String TARGET_ID = "222";

    /**
     * pkcs8 格式的商户私钥。
     * <p>
     * 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
     * 使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
     * RSA2_PRIVATE。
     * <p>
     * 建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC7986VD/erXZM63SkEAydwCWSR6nHsx+624ONIfVpaS3hUlDvaAR3c4GLVxIQulTJllGMaGuGCD3jDUXnk46OmBx2PhxnA6DSc5pl7yqRi3/XVTCZE3RHbRDz9sc0nsXm50DeALqMirGlCi+OXc/sC/9lOZfWg43tkp3EqM2n1WKa+V/fKIpbmv+FOZJ3J1F9hBJXI8in+Zym1YMVCm9HdWxnt7GDSaqKRDhLKhOBzs4pwc7m3/2O//Cs4a4TwflMtpZaDstyvi3q5+9Fj7n2KnjVZMqtSUvRZQsLc017/Yf4B8877U3VV04IL+9eowU0YNdl7PcUvqD+W8Bbm7kN7AgMBAAECggEAV7xdQpqHYgd1d15PjL3kJJldxfYGYx0XQWlK7UUFbWwYqHHgVet7guOBfoIRqKSfN2bBsyek5s5N8e0P1lSiyBY+KAZJcQ9xr2K7dVqqzDP5TSwUGMSPVydMyKW7OrHOPXsAzOX343FSWGtvOfw6x7WgOsXAlu5XZC371lTVOlzv/xEcj7UCoDxCB2qPNzQtyVroOCFN5jWuwlg1bhN9P3aHQwx1euEDU/dP13+nLAOhGfeiYOHPyddDPoym48F3hZykp4toVqyqDeeNreiWWp44WrXGc/BH3JDHX294PDl4awiJUHrzSsjkTN+e2s0VeEGNbF8qnAGzT+1WFWSgwQKBgQDcNX7VtRHteFeCDHO7sgHnikbDq8gecv4GBthGTypSEQab2/C047JeKaA3jTrEkyXMy4TOgc7jgc2yKWz/c2r/ol+HeQ2lloKrscrQE9HckBfBd0Gd1W/sMXQQAdpH6LZtrhIG4Oe9tvr9IJbcwcRXxgyAam9HLHzQQM2REHbYSwKBgQDahNJoY8BrZEYs2NMVzBLD6iazk3gGXCoMMublszbBmh6TLgVO2BYprsreR2LszCCRy5/MOeuakuooeBb/EPtUsJ0XbO1r+Indji2pPqcDIknnrnkEs/sEs1NpKItUFSH+MDkdcG67XzVRzh8JT0v0/eEx+/anbYirIDwUs/KjkQKBgQC1CmYUXKE4ks+e443iqh5WtaAoDnqgTfn6CnaUl1E71zOPv+rNTMXAFIjcIwhFxJQ3Irs07BOjh3wNFXG/MqytLllLj4JguLUFY05Sc3j91JkzPVB86pTxolbBMkS7Cd+xnILFlPHxTxZaNQjSxZsebnyotth1s2WJ8T0QHnEWsQKBgQC7p0RpkQA1OS9qY+kh1tZmmDYUEJgKHiudvji7GWP0bj3DdsnoI3kk4o4kxHkXkVSG366i2zqQy4l6p2ZYNGUhHWpKK6jYB9+c+/QKW1thPZjp9NLITydJqhX/QGomEt/WQeNKfLsa/W/0iGXuwMIh6cPyAMaYkV/EVTJDNSUxgQKBgQCP7+OmnI5Ir+H7/l3jVN8da9I46OkWBG75QuOEuKpZ+7X77t4XsBIX/3YdokeiOWrwbWgCzPLIDRPo70mjwoVmdS2oMAlcPuGorBplrmo+MifcyreEMRWX9GkrUz4AETiynfnkdJxG449gvUs7tVFAMc52+yLJ/P84LOmA1HtNYg==";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private Context context;
    INormalCallback iNormalCallback;

    @Override
    public void init(Context context) {
        this.context = context;
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
    }

    @Override
    public <T> void aliPay(final Activity activity, int classInfoId, final INormalCallback<T> iNormalCallback) {
        this.iNormalCallback = iNormalCallback;
        String appToken = SpUtil.getString(activity, ConstantTag.APP_TOKEN.name());
        new NetModel().getAliPayOrderInfo(new AliPayOrderParamBean(appToken,classInfoId), new INormalCallback<AliPayOrderResultBean>() {
            @Override
            public void success(AliPayOrderResultBean msg) {
                dealAskResult(activity,msg);
            }

            @Override
            public void failure(String failureInfo) {
                sendCallback(false, failureInfo);
            }
        });
//        goAliPay(activity,getOrderInfo());
    }

    /**
     * 处理请求订单结果
     */
    private void dealAskResult(Activity activity,AliPayOrderResultBean msg) {
        if (msg!=null){
            if (ConstantNum.REQUEST_SUCCESS == msg.getStatusCode()) {
                goAliPay(activity,msg.getData());
            }
        }else {
            sendCallback(false,"失败");
        }

    }

    private void goAliPay(final Activity activity, final String orderInfo){
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 从后来获取点单信息
     */
    public String getOrderInfo() {
        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;
        return orderInfo;
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        showAlert(context, "成功" + payResult);
                        showToast(context, "成功" + payResult);
                        sendCallback(true, "成功" + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        showAlert(context, "失败" + payResult);
                        showToast(context, "失败" + payResult);
                        sendCallback(false, "失败" + payResult);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };


    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton("确定", null)
                .setOnDismissListener(onDismiss)
                .show();
    }

    private static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }


    /**
     * 调回调
     *
     * @param isSuccess 是否成功
     * @param info      信息
     */
    private void sendCallback(boolean isSuccess, String info) {
        if (iNormalCallback != null) {
            if (isSuccess) {
                iNormalCallback.success(info);
            } else {
                iNormalCallback.failure(info);
            }
        }
    }

}
