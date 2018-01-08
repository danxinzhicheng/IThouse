package com.danmo.commonapi.event.newsdetail;

import android.support.annotation.Nullable;

import com.danmo.commonapi.base.BaseEvent;
import com.danmo.commonapi.bean.newest.NewestTopNode;


public class GetNewsDetailContentEvent extends BaseEvent<NewestTopNode> {
    public GetNewsDetailContentEvent(@Nullable String uuid) {
        super(uuid);
    }
}
