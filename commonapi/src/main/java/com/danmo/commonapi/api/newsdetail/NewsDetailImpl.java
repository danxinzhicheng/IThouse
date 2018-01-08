package com.danmo.commonapi.api.newsdetail;

import android.content.Context;
import android.support.annotation.NonNull;

import com.danmo.commonapi.base.BaseImpl;
import com.danmo.commonapi.callback.BaseCallback;
import com.danmo.commonapi.event.newsdetail.GetNewsDetailContentEvent;
import com.danmo.commonapi.event.newsdetail.GetNewsDetailRecommendEvent;
import com.danmo.commonapi.event.newsdetail.GetNewsDetailRelatedEvent;
import com.danmo.commonutil.UUIDGenerator;

public class NewsDetailImpl extends BaseImpl<NewsDetailService> implements NewsDetailAPI {
    public NewsDetailImpl(@NonNull Context context, String baseUrl, int parsePattern) {
        super(context, baseUrl, parsePattern);
    }

    @Override
    public String getNewsDetailContent(String url) {
        String uuid = UUIDGenerator.getUUID();
        mService.getNewsDetailContent(url).enqueue(new BaseCallback<>(new GetNewsDetailContentEvent(uuid)));
        return uuid;
    }

    @Override
    public String getNewsDetailRelated(String url) {
        String uuid = UUIDGenerator.getUUID();
        mService.getNewsDetailRelated(url).enqueue(new BaseCallback<>(new GetNewsDetailRelatedEvent(uuid)));
        return uuid;
    }

    @Override
    public String getNewsDetailRecommend(String url) {
        String uuid = UUIDGenerator.getUUID();
        mService.getNewsDetailRecommend(url).enqueue(new BaseCallback<>(new GetNewsDetailRecommendEvent(uuid)));
        return uuid;
    }
}
