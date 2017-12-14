package com.danmo.commonapi.api.community;

public interface CommunityApi {
    String getQuanziCategory(String url);

    String getQuanziListNewest(String url);

    String getQuanziListHotest(String url);
}
