package com.danmo.commonapi.bean.community;

/**
 * Created by user on 2017/12/27.
 */

public class CommentReplyContent {
    public String Ci;
    public String C;//内容
    public String N;//用户名
    public String Ui;
    public String T;//回复日期
    public String S;//赞
    public String A;//踩
    public String Ta;//手机型号
    public String R;
    public String Cl;
    public String Ir;
    public String Sf;//楼号
    public String City;
    public String rank;//等级

    @Override
    public String toString() {
        return "CommentReplyContent{" +
                "Ci='" + Ci + '\'' +
                ", C='" + C + '\'' +
                ", N='" + N + '\'' +
                ", Ui='" + Ui + '\'' +
                ", T='" + T + '\'' +
                ", S='" + S + '\'' +
                ", A='" + A + '\'' +
                ", Ta='" + Ta + '\'' +
                ", R='" + R + '\'' +
                ", Cl='" + Cl + '\'' +
                ", Ir='" + Ir + '\'' +
                ", Sf='" + Sf + '\'' +
                ", City='" + City + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }
}
