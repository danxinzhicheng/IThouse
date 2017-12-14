package com.danmo.commonapi.event;

import android.support.annotation.Nullable;


public class GetCommunityCatetoryEvent extends GetCommunityListEvent {
    public GetCommunityCatetoryEvent(@Nullable String uuid) {
        super(uuid);
    }
}
