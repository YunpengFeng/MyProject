package com.personal.feng.utils;

/*一般的数据*/
public class ResultJO {
    private Object data; //数据
    private String message; //信息
    public ResultJO(Object data,String message){
        this.data = data;
        message =  message;
    }

    public ResultJO(String message) {
        this.message = message;
    }


    public ResultJO(Object data) {
        this.data = data;
    }

    public ResultJO() {
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ResultJO backMessage(Object data,String message){
        ResultJO rjo = new ResultJO();
        rjo.setData(data);
        rjo.setMessage(message);
        return rjo;
    }
}
