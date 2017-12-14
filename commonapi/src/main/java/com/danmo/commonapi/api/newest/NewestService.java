package com.danmo.commonapi.api.newest;

import com.danmo.commonapi.bean.newest.NewestTopNode;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NewestService {
    @GET
    Call<NewestTopNode> getNewestList(@Url String url);

    @GET
    Call<NewestTopNode> getNewestBannerList(@Url String url);
}
