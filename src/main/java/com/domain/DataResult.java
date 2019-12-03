package com.domain;

import org.springframework.stereotype.Repository;

@Repository
public class DataResult {
    private boolean status;   //响应状态码
    private String msg;   //响应信息
    private Object data;  //响应数据


    private DataResult() {
        this.status = true;
    }

    private DataResult(String msg) {
        this();
        this.msg = msg;
    }

    private DataResult(boolean status) {
        this.status=status;
    }

    private DataResult(String msg, Object data) {
        this();
        this.msg = msg;
        this.data = data;
    }

    private DataResult(boolean status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private DataResult(boolean status, Object data) {
        this.status = status;
        this.data = data;
    }

    private DataResult(boolean status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static DataResult success() {
        return new DataResult();
    }

    public static DataResult success(String msg) {
        return new DataResult(msg);
    }

    public static DataResult success(Object data) {
        return new DataResult(true,data);
    }

    public static DataResult success(String msg, Object data) {
        return new DataResult(msg, data);
    }

    public static DataResult fail(String msg) {
        return new DataResult(false, msg);
    }

    public static DataResult fail() {
        return new DataResult(false);
    }

    public static DataResult fail(Object data) {
        return new DataResult(false,data);
    }

    public static DataResult fail(String msg, Object data) {
        return new DataResult(msg, data);
    }

    public boolean isSuccess() {
        return status;
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

    @Override
    public String toString() {
        return "DataResult [status=" + status + ", msg=" + msg + ", data=" + data + "]";
    }
}
