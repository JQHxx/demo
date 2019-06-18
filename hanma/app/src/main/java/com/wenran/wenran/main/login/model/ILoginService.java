package com.wenran.wenran.main.login.model;

import com.wenran.wenran.main.login.bean.AnotherLoginParam;
import com.wenran.wenran.main.login.bean.LoginParamBean;
import com.wenran.wenran.main.login.bean.RegisterParamBean;
import com.wenran.wenran.main.login.bean.ResetPswParamBean;
import com.wenran.wenran.main.login.bean.StandardResultBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by crowhine on 2017/12/12.
 *
 * @author crowhine
 * function：获取语音提示语句的接口
 * http://1.189.21.99:8050/BMW/bwm/getPropDatas?key=Nav.StartNavigate
 * key为空表示获取所有数据，有值的话获取单个数据
 */

public interface ILoginService {
    /**
     * 登录
     *
     * @param loginParamBean 参数
     * @return
     */
    @POST("login.json")
    Observable<StandardResultBean> login(@Body LoginParamBean loginParamBean);

    /**
     * 注册发送验证码
     *
     * @param phone 参数
     * @return
     */
    @POST("user/registercode.json")
    Observable<StandardResultBean> sendPhoneVc(@Query("mobile") String phone);

    /**
     * 修改密码发送验证码
     *
     * @param phone 参数
     * @return
     */
    @POST("user/modifycode.json")
    Observable<StandardResultBean> sendPhoneVcForReSet(@Query("mobile") String phone);


    /**
     * 绑定手机发送的验证码
     *
     * @param phone 参数
     * @return
     */
    @PUT("user/bindcode.json")
    Observable<StandardResultBean> sendPhoneVcForBindPhone(@Query("mobile") String phone);

    /**
     * 注册
     *
     * @param registerParamBean 参数
     * @return
     */
    @POST("user/register.json")
    Observable<StandardResultBean> register(@Body RegisterParamBean registerParamBean);

    /**
     * 重新修改密码
     *
     * @param registerParamBean 重新修改密码
     * @return
     */
    @POST("user/modifypwd.json")
    Observable<StandardResultBean> resetPsw(@Body ResetPswParamBean registerParamBean);


    /**
     * 第三方登录
     *
     * @param anotherLoginParam
     * @return
     */
    @POST("loginthree.json")
    Observable<StandardResultBean> anotherLogin(@Body AnotherLoginParam anotherLoginParam);


    /**
     * 第三方和手机号绑定
     *
     * @param openId
     * @param type
     * @param phone
     * @param nickName
     * @param vCode
     * @param file
     * @return
     */
    @Multipart
    @POST("user/binding.json")
    Observable<StandardResultBean> bindPhone(@Query("Openid") String openId,
                                             @Query("type") int type,
                                             @Query("phone") String phone,
                                             @Query("nickname") String nickName,
                                             @Query("vcode") String vCode,
                                             @Part MultipartBody.Part file);


}
