package com.danmo.ithouse.util;

/**
 * Created by user on 2017/10/13.
 */

public class EventBusMsg {
    private String msg;
    private int flag;


    public EventBusMsg(String msg) {
        this.msg = msg;
    }

    public EventBusMsg(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
