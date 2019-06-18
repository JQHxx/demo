package wenran.com.module.mine.bean;

/**
 * Created by Crowhine on 2019/1/28
 *
 * @author Crowhine
 */
public class BindPhoneParamBean {
    String openId;
    int type;
    String phone;
    String nickName;
    String vCode;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getvCode() {
        return vCode;
    }

    public void setvCode(String vCode) {
        this.vCode = vCode;
    }


}
