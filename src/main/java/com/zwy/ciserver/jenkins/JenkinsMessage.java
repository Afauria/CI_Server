package com.zwy.ciserver.jenkins;

/**
 * Created by Afauria on 2019/3/29.
 */
public class JenkinsMessage<T> {
    //1：成功；2：失败；
    private int status;

    private String msg;

    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
