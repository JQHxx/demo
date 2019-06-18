package com.wenran.wenran.main.login.bean;

/**
 * Created by crowhine on 2018/7/25.
 *
 * @author crowhine
 */

public class LoginParamBean {
    String phone;
    String password;

    public LoginParamBean() {
    }

    public LoginParamBean(String phone, String password) {
        this.phone = phone;
        this.password = password;
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
}
