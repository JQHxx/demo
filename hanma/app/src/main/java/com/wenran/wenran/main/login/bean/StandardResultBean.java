package com.wenran.wenran.main.login.bean;

/**
 * Created by Crowhine on 2019/1/10
 *
 * @author Crowhine
 */
public class StandardResultBean {

    /**
     * message : 登入成功
     * statusCode : 200
     * success : true
     * data : {"id":85,"apptoken":"D433F73A34B44E4C804F98763BB96FD8"}
     */

    private String message;
    private int statusCode;
    private boolean success;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 85
         * apptoken : D433F73A34B44E4C804F98763BB96FD8
         */

        private int id;
        private String apptoken;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getApptoken() {
            return apptoken;
        }

        public void setApptoken(String apptoken) {
            this.apptoken = apptoken;
        }
    }
}
