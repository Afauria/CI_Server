package com.zwy.ciserver.websocket;

import org.springframework.stereotype.Component;

/**
 * Created by Afauria on 2019/4/2.
 */
@Component
public class MessageInfo<T> {
    private String msg;
    private boolean success;
    private T content;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static MessageInfo success(String msg){
        MessageInfo messageInfo = new MessageInfo<>();
        messageInfo.setSuccess(true);
        messageInfo.setMsg(msg);
        return messageInfo;
    }
    public static MessageInfo error(String msg){
        MessageInfo messageInfo = new MessageInfo<>();
        messageInfo.setSuccess(false);
        messageInfo.setMsg(msg);
        return messageInfo;
    }
}
