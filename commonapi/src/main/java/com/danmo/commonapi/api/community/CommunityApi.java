package com.danmo.commonapi.api.community;

public interface CommunityApi {
    String getCommunityCategory(String url);

    String getCommunityListNewest(String url);

    String getCommunityListHotest(String url);
}
