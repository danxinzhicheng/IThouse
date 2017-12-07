package com.danmo.commonapi.api.quanzi;

import com.danmo.commonapi.bean.quanzi.QuanziListItem;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by user on 2017/12/6.
 */

public interface QuanziService {
    @GET
    Call<List<QuanziListItem>> getQuanziCategory(@Url String url);

    @GET
    Call<List<QuanziListItem>> getQuanziListNewest(@Url String url);

    @GET
    Call<List<QuanziListItem>> getQuanziListHotest(@Url String url);
}
