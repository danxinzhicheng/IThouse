package com.danmo.commonapi.api.lapin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.danmo.commonapi.base.BaseImpl;
import com.danmo.commonapi.callback.BaseCallback;
import com.danmo.commonapi.event.GetLapinBannerEvent;
import com.danmo.commonapi.event.GetLapinListEvent;
import com.danmo.commonapi.event.GetLapinRankEvent;
import com.danmo.commonutil.UUIDGenerator;

/**
 * Created by user on 2017/10/16.
 */

public class LapinImpl extends BaseImpl<LapinService> implements LapinApi {
    public LapinImpl(@NonNull Context context, int currentParse) {
        super(context, currentParse);
    }

    @Override
    public String getLapinBannerList(String url) {
        String uuid = UUIDGenerator.getUUID();
        mService.getLapinBannerList(url).enqueue(new BaseCallback<>(new GetLapinBannerEvent(uuid)));
        return uuid;
    }

    @Override
    public String getLapinRankList(String url) {
        String uuid = UUIDGenerator.getUUID();
        Log.i("nnn","getLapinRankList===");
        mService.getLapinRankList(url).enqueue(new BaseCallback<>(new GetLapinRankEvent(uuid)));
        return uuid;
    }

    @Override
    public String getLapinList(String url) {
        String uuid = UUIDGenerator.getUUID();
        mService.getLapinList(url).enqueue(new BaseCallback<>(new GetLapinListEvent(uuid)));
        return uuid;
    }
}
