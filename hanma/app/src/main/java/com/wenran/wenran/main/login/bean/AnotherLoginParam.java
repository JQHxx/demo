package com.wenran.wenran.main.login.bean;

/**
 * Created by Crowhine on 2019/2/15
 *
 * @author Crowhine
 * <p>
 * function:第三方登录参数
 */
public class AnotherLoginParam {
    int type;
    String Openid;

    public AnotherLoginParam(int type, String openid) {
        Openid = openid;
        this.type = type;
    }

    public String getOpenid() {
        return Openid;
    }

    public void setOpenid(String openid) {
        Openid = openid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
