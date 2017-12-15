package com.danmo.commonapi.api.hotgoods;

import com.danmo.commonapi.bean.hotgoods.HotGoodsTopNode;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface HotGoodsService {
    @GET
    Call<HotGoodsTopNode> getHotGoodsBannerList(@Url String url);

    @GET
    Call<HotGoodsTopNode> getHotGoodsRankList(@Url String url);

    @GET
    Call<HotGoodsTopNode> getHotGoodsList(@Url String url);
}
