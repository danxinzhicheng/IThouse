package com.danmo.commonapi.api.community;

import com.danmo.commonapi.bean.community.CommunityListItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface CommunityService {
    @GET
    Call<List<CommunityListItem>> getQuanziCategory(@Url String url);

    @GET
    Call<List<CommunityListItem>> getQuanziListNewest(@Url String url);

    @GET
    Call<List<CommunityListItem>> getQuanziListHotest(@Url String url);
}
