package com.danmo.commonapi.api.newest;

import com.danmo.commonapi.bean.New;
import com.danmo.commonapi.bean.Newest;
import com.danmo.commonapi.bean.NewestItem;
import com.danmo.commonapi.bean.NewestTopNode;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by danmo on 2017/9/16.
 */

public interface NewestService {
    @GET
    Call<NewestTopNode> getNewestList(@Url String url);
}
