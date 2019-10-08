package com.domain;

import org.springframework.stereotype.Repository;

@Repository
public class DataResult {
    private boolean status;   //响应状态码
    private String msg;   //响应信息
    private Object data;  //响应数据

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

    public String toString() {
        return "DataResult [status=" + status + ", msg=" + msg + ", data=" + data + "]";
    }
}
