package com.danmo.commonapi.api.community;

import android.content.Context;
import android.support.annotation.NonNull;

import com.danmo.commonapi.base.BaseImpl;
import com.danmo.commonapi.callback.BaseCallback;
import com.danmo.commonapi.event.community.GetCommunityCatetoryEvent;
import com.danmo.commonapi.event.community.GetCommunityCommentEvent;
import com.danmo.commonapi.event.community.GetCommunityListEvent;
import com.danmo.commonutil.UUIDGenerator;

public class CommunityImpl extends BaseImpl<CommunityService> implements CommunityApi {
    public CommunityImpl(@NonNull Context context, String baseUrl, int currentParse) {
        super(context, baseUrl, currentParse);
    }

    @Override
    public String getCommunityCategory(String url) {
        String uuid = UUIDGenerator.getUUID();
        mService.getCommunityCategory(url).enqueue(new BaseCallback<>(new GetCommunityCatetoryEvent(uuid)));
        return uuid;
    }

    @Override
    public String getCommunityList(String categoryid, String type, String orderTime, String visistCount, String pageLength) {
        String uuid = UUIDGenerator.getUUID();
        mService.getCommunityList(categoryid, type, orderTime, visistCount, pageLength).enqueue(new BaseCallback<>(new GetCommunityListEvent(uuid)));
        return uuid;
    }

    @Override
    public String getCommunityComment(String id) {
        String uuid = UUIDGenerator.getUUID();
        mService.getCommunityComment(id).enqueue(new BaseCallback<>(new GetCommunityCommentEvent(uuid)));
        return uuid;
    }
}
