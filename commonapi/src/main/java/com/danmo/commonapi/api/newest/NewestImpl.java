package com.danmo.commonapi.api.newest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.danmo.commonapi.base.BaseImpl;
import com.danmo.commonapi.callback.BaseCallback;
import com.danmo.commonapi.event.GetNewestEvent;
import com.danmo.commonapi.event.GetNewsListEvent;
import com.danmo.commonutil.UUIDGenerator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by danmo on 2017/9/16.
 */

public class NewestImpl extends BaseImpl<NewestService> implements NewestAPI{
    public NewestImpl(@NonNull Context context) {
        super(context);
    }

    @Override
    public String getNewestList(String url) {
        String uuid = UUIDGenerator.getUUID();
        mService.getNewestList(url).enqueue(new BaseCallback<>(new GetNewestEvent(uuid) ));
//        mService.getNewestList(url).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.i("mmm","====>"+response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
        return uuid;
    }
}
