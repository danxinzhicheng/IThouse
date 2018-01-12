package com.danmo.ithouse.util;

/**
 * EventBus实体类
 */

public class EventBusMsg {
    private String msg;
    private int flag;//1,up; 0,down
    private int community_fresh_new_or_hot;//0,new; 1,hot;
    private int user_name;
    private int user_icon;

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

    public int getUser_name() {
        return user_name;
    }

    public void setUser_name(int user_name) {
        this.user_name = user_name;
    }

    public int getUser_icon() {
        return user_icon;
    }

    public void setUser_icon(int user_icon) {
        this.user_icon = user_icon;
    }
}
