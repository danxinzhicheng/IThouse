package com.danmo.commonapi.bean.community;

import java.util.List;

/**
 * Created by user on 2017/12/28.
 */

public class CommentHeader {
    public CommentHeader(String commentId, String type, String title, String viewNum, String commentNum, String commentUid, String commentLzName,
                         String lz_content, String lz_ul, String lz_rc, String lz_Ta, String lz_cl, String lz_IC,
                         String lz_IH, String lz_City, String lz_rank, List<String> lz_imgs) {
        this.commentId = commentId;
        this.type = type;
        this.title = title;
        this.viewNum = viewNum;
        this.commentNum = commentNum;
        this.commentUid = commentUid;
        this.commentLzName = commentLzName;
        this.lz_content = lz_content;
        this.lz_ul = lz_ul;
        this.lz_rc = lz_rc;
        this.lz_Ta = lz_Ta;
        this.lz_cl = lz_cl;
        this.lz_IC = lz_IC;
        this.lz_IH = lz_IH;
        this.lz_City = lz_City;
        this.lz_rank = lz_rank;
        this.lz_imgs = lz_imgs;
    }

    public String commentId;
    public String type;
    public String title;
    public String viewNum;
    public String commentNum;
    public String commentUid;
    public String commentLzName;
    public String lz_content;
    public String lz_ul;
    public String lz_rc;
    public String lz_Ta;
    public String lz_cl;
    public String lz_IC;
    public String lz_IH;
    public String lz_City;
    public String lz_rank;
    public List<String> lz_imgs;
}
