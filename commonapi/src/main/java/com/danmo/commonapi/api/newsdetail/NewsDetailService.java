package com.danmo.commonapi.api.newsdetail;

import com.danmo.commonapi.bean.newest.NewestTopNode;
import com.danmo.commonapi.bean.newest.detail.DetailRecommendTopNode;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NewsDetailService {

    @GET
    Call<NewestTopNode> getNewsDetailContent(@Url String url);

    @GET
    Call<String> getNewsDetailRelated(@Url String url);

    @GET
    Call<DetailRecommendTopNode> getNewsDetailRecommend(@Url String url);
}
