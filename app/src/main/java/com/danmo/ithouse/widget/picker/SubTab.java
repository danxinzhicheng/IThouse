package com.danmo.ithouse.widget.picker;

import java.io.Serializable;

/**
 * 自定义资讯分类实体
 */
public class SubTab implements Serializable {

    private String name;
    private String tag;
    private int type;
    private boolean fixed;
    private boolean isActived;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public boolean isActived() {
        return isActived;
    }

    public void setActived(boolean actived) {
        isActived = actived;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof SubTab) {
            SubTab tab = (SubTab) obj;
            if (tab.getName() == null) return false;
            if (this.name == null) return false;
            return tab.getName().equals(this.name);
        }
        return false;
    }

    @Override
    public String toString() {
        return "SubTab{" +
                ", name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", type=" + type +
                '}';
    }
}
