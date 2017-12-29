package com.danmo.commonapi.bean.community;

import java.util.List;

/**
 * Created by user on 2017/12/27.
 */

public class Comment {
    public String content;
    public String ul;
    public String rc;
    public String Ta;
    public String Cl;
    public String IC;
    public String IH;
    public String City;
    public String rank;
    public List<String> imgs;
    public List<CommentReply> reply;

    @Override
    public String toString() {
        return "Comment{" +
                "content='" + content + '\'' +
                ", ul='" + ul + '\'' +
                ", rc='" + rc + '\'' +
                ", Ta='" + Ta + '\'' +
                ", Cl='" + Cl + '\'' +
                ", IC='" + IC + '\'' +
                ", IH='" + IH + '\'' +
                ", City='" + City + '\'' +
                ", rank='" + rank + '\'' +
                ", imgs=" + imgs +
                ", reply=" + reply +
                '}';
    }
}
