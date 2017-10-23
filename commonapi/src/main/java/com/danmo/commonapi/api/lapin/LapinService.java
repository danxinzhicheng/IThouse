package com.danmo.commonapi.api.lapin;

import com.danmo.commonapi.bean.lapin.LapinTopNode;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by user on 2017/10/16.
 */

public interface LapinService {
    @GET
    Call<LapinTopNode> getLapinBannerList(@Url String url);

    @GET
    Call<LapinTopNode> getLapinRankList(@Url String url);

    @GET
    Call<LapinTopNode> getLapinList(@Url String url);


}
