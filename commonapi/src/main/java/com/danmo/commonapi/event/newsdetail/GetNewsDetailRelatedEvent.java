package com.danmo.commonapi.event.newsdetail;

import android.support.annotation.Nullable;

import com.danmo.commonapi.base.BaseEvent;


public class GetNewsDetailRelatedEvent extends BaseEvent<String> {
    public GetNewsDetailRelatedEvent(@Nullable String uuid) {
        super(uuid);
    }
}
