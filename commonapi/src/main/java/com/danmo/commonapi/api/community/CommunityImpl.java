package com.danmo.commonapi.api.community;

import android.content.Context;
import android.support.annotation.NonNull;

import com.danmo.commonapi.base.BaseImpl;
import com.danmo.commonapi.callback.BaseCallback;
import com.danmo.commonapi.event.GetCommunityCatetoryEvent;
import com.danmo.commonapi.event.GetCommunityListEvent;
import com.danmo.commonutil.UUIDGenerator;

public class CommunityImpl extends BaseImpl<CommunityService> implements CommunityApi {
    public CommunityImpl(@NonNull Context context, int currentParse) {
        super(context, currentParse);
    }

    @Override
    public String getQuanziCategory(String url) {
        String uuid = UUIDGenerator.getUUID();
        mService.getQuanziCategory(url).enqueue(new BaseCallback<>(new GetCommunityCatetoryEvent(uuid)));
        return uuid;
    }

    @Override
    public String getQuanziListNewest(String url) {
        String uuid = UUIDGenerator.getUUID();
        mService.getQuanziListNewest(url).enqueue(new BaseCallback<>(new GetCommunityListEvent(uuid)));
        return uuid;
    }

    @Override
    public String getQuanziListHotest(String url) {
        String uuid = UUIDGenerator.getUUID();
        mService.getQuanziListHotest(url).enqueue(new BaseCallback<>(new GetCommunityListEvent(uuid)));
        return uuid;
    }
}
