package com.danmo.commonapi.api.hotgoods;

import com.danmo.commonapi.bean.hotgoods.HotGoodsTopNode;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface LapinService {
    @GET
    Call<HotGoodsTopNode> getLapinBannerList(@Url String url);

    @GET
    Call<HotGoodsTopNode> getLapinRankList(@Url String url);

    @GET
    Call<HotGoodsTopNode> getLapinList(@Url String url);


}
