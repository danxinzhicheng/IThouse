package com.danmo.commonapi.bean.community;

import java.util.List;

/**
 * Created by user on 2017/12/27.
 */

public class CommentReply {
    public CommentReplyContent M;
    public String F;
    public List<CommentReplyContent> R;

    @Override
    public String toString() {
        return "CommentReply{" +
                "M=" + M +
                ", F='" + F + '\'' +
                ", R=" + R +
                '}';
    }
}
