package com.danmo.commonapi.api.quanzi;

/**
 * Created by user on 2017/12/6.
 */

public interface QuanziApi {
    String getQuanziCategory(String url);

    String getQuanziListNewest(String url);

    String getQuanziListHotest(String url);
}
