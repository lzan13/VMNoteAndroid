package com.vmloft.develop.app.vnotes.bean;

/**
 * Created by lzan13 on 2018/4/16.
 * 对数据解析的封装，这里主要解析公共参数
 */
public class BaseResult<T> {
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n\tcode:" + code);
        buffer.append("\n\tmsg:" + message);
        if (data != null) {
            buffer.append("\n\tdata:" + data.toString());
        }
        return buffer.toString();
    }
}
