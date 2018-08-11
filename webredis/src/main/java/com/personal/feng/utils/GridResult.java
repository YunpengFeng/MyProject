package com.personal.feng.utils;

/*返回的数据带有总数*/
public class GridResult {
    private  Integer total ;//总数
    private Object data; //数据
    private String message; //信息辨识

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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

    public GridResult backGridMessage(Object data,String message,int total){
        GridResult grid = new GridResult();
        grid.setData(data);
        grid.setMessage(message);
        grid.setTotal(total);
        return grid;
    }
}
