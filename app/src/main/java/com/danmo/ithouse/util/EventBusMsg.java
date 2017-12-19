package com.danmo.ithouse.util;

/**
 * EventBus实体类
 */

public class EventBusMsg {
    private String msg;
    private int flag;//1,up; 0,down
    private int community_fresh_new_or_hot;//0,new; 1,hot;

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

    public int getCommunity_fresh_new_or_hot() {
        return community_fresh_new_or_hot;
    }

    public void setCommunity_fresh_new_or_hot(int community_fresh_new_or_hot) {
        this.community_fresh_new_or_hot = community_fresh_new_or_hot;
    }
}
