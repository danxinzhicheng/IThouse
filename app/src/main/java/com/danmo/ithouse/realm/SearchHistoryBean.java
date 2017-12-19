package com.danmo.ithouse.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by user on 2017/12/19.
 */

public class SearchHistoryBean  extends RealmObject{
    @PrimaryKey
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
