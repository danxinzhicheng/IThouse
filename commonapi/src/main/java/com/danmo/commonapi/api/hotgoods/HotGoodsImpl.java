package com.danmo.commonapi.api.hotgoods;

import android.content.Context;
import android.support.annotation.NonNull;

import com.danmo.commonapi.base.BaseImpl;
import com.danmo.commonapi.callback.BaseCallback;
import com.danmo.commonapi.event.GetHotGoodsBannerEvent;
import com.danmo.commonapi.event.GetHotGoodsListEvent;
import com.danmo.commonapi.event.GetHotGoodsRankEvent;
import com.danmo.commonutil.UUIDGenerator;

public class HotGoodsImpl extends BaseImpl<LapinService> implements HotGoodsApi {
    public HotGoodsImpl(@NonNull Context context, int currentParse) {
        super(context, currentParse);
    }

    @Override
    public String getLapinBannerList(String url) {
        String uuid = UUIDGenerator.getUUID();
        mService.getLapinBannerList(url).enqueue(new BaseCallback<>(new GetHotGoodsBannerEvent(uuid)));
        return uuid;
    }

    @Override
    public String getLapinRankList(String url) {
        String uuid = UUIDGenerator.getUUID();
        mService.getLapinRankList(url).enqueue(new BaseCallback<>(new GetHotGoodsRankEvent(uuid)));
        return uuid;
    }

    @Override
    public String getLapinList(String url) {
        String uuid = UUIDGenerator.getUUID();
        mService.getLapinList(url).enqueue(new BaseCallback<>(new GetHotGoodsListEvent(uuid)));
        return uuid;
    }
}
