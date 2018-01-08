package com.danmo.commonapi.event.community;

import android.support.annotation.Nullable;

import com.danmo.commonapi.base.BaseEvent;
import com.danmo.commonapi.bean.community.CommunityListItem;

import java.util.List;


public class GetCommunityListEvent extends BaseEvent<List<CommunityListItem>> {
    public GetCommunityListEvent(@Nullable String uuid) {
        super(uuid);
    }
}
