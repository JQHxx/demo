package wenran.com.module.mine.bean;

/**
 * Created by Crowhine on 2019/1/14
 *
 * @author Crowhine
 */
public class SendVcResultBean {


    /**
     * message : 验证码发送成功
     * statusCode : 200
     * success : true
     * data : 0C1E49B942CB3B624861342753BA0522
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
