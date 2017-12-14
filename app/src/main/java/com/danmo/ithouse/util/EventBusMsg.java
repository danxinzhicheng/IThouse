package com.danmo.ithouse.util;

/**
 * EventBus实体类
 */

public class EventBusMsg {
    private String msg;
    private int flag;
    private int quanzi_fresh_new_or_hot;//0,new; 1,hot;

    public EventBusMsg() {
    }

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

    public int getQuanzi_fresh_new_or_hot() {
        return quanzi_fresh_new_or_hot;
    }

    public void setQuanzi_fresh_new_or_hot(int quanzi_fresh_new_or_hot) {
        this.quanzi_fresh_new_or_hot = quanzi_fresh_new_or_hot;
    }
}
