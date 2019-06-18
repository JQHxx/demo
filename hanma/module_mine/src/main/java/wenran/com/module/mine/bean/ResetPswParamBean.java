package wenran.com.module.mine.bean;

/**
 * Created by Crowhine on 2019/1/14
 *
 * @author Crowhine
 * <p>
 * description:重新找回密码；
 */
public class ResetPswParamBean {
    String phone;
    String vcode;
    String password;
    String rpassword;


    public ResetPswParamBean() {
    }

    public ResetPswParamBean(String phone, String vcode, String password, String rpassword) {
        this.phone = phone;
        this.vcode = vcode;
        this.password = password;
        this.rpassword = rpassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRpassword() {
        return rpassword;
    }

    public void setRpassword(String rpassword) {
        this.rpassword = rpassword;
    }
}
