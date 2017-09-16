package com.danmo.commonapi.bean;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import retrofit2.http.GET;

/**
 * Created by danmo on 2017/9/16.
 */

@Root(name = "item", strict = false)
public class NewestItem {
    public NewestItem() {
    }

    @Element(name = "newsid", required = false)
    public String newsid;
    @Element(name = "title", required = false)
    public String title;
    @Element(name = "url", required = false)
    public String url;
    @Element(name = "postdate", required = false)
    public String postdate;
    @Element(name = "image", required = false)
    public String image;
    @Element(name = "description", required = false)
    public String description;
    @Element(name = "hitcount", required = false)
    public String hitcount;
    @Element(name = "commentcount", required = false)
    public String commentcount;
    @Element(name = "forbidcomment", required = false)
    public String forbidcomment;
    @Element(name = "cid", required = false)
    public String cid;

    public String getNewsid() {
        return newsid;
    }

    public void setNewsid(String newsid) {
        this.newsid = newsid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPostdate() {
        return postdate;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHitcount() {
        return hitcount;
    }

    public void setHitcount(String hitcount) {
        this.hitcount = hitcount;
    }

    public String getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(String commentcount) {
        this.commentcount = commentcount;
    }

    public String getForbidcomment() {
        return forbidcomment;
    }

    public void setForbidcomment(String forbidcomment) {
        this.forbidcomment = forbidcomment;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
