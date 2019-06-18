package com.wenran.wenran.main.login.bean;

/**
 * Created by Crowhine on 2019/1/14
 *
 * @author Crowhine
 */
public class RegisterParamBean {
    String phone;
    String vcode;
    String password;
    String invitedcode;

    public RegisterParamBean() {
    }

    public RegisterParamBean(String phone, String vcode, String password, String invitedcode) {
        this.phone = phone;
        this.vcode = vcode;
        this.password = password;
        this.invitedcode = invitedcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getInvitedcode() {
        return invitedcode;
    }

    public void setInvitedcode(String invitedcode) {
        this.invitedcode = invitedcode;
    }
}
