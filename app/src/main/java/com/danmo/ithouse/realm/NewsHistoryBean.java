package com.danmo.ithouse.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by user on 2017/12/18.
 */

public class NewsHistoryBean extends RealmObject {

    @PrimaryKey
    private String newsid;
    private String title;
    private String url;
    private String image;

    public String getNewsid() {
        return newsid;
    }

    public NewsHistoryBean setNewsid(String newsid) {
        this.newsid = newsid;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NewsHistoryBean setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public NewsHistoryBean setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getImage() {
        return image;
    }

    public NewsHistoryBean setImage(String image) {
        this.image = image;
        return this;
    }

}
