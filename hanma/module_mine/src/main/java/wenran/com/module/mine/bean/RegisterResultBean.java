package wenran.com.module.mine.bean;

/**
 * Created by Crowhine on 2019/1/14
 *
 * @author Crowhine
 */
public class RegisterResultBean {

    /**
     * message : 注册成功
     * statusCode : 200
     * success : true
     * data : true
     */

    private String message;
    private int statusCode;
    private boolean success;
    private boolean data;

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

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
