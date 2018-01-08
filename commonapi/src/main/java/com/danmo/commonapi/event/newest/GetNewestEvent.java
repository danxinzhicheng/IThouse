package com.danmo.commonapi.event.newest;

import android.support.annotation.Nullable;

import com.danmo.commonapi.base.BaseEvent;
import com.danmo.commonapi.bean.newest.NewestTopNode;


public class GetNewestEvent extends BaseEvent<NewestTopNode> {

    public GetNewestEvent(@Nullable String uuid) {
        super(uuid);
    }
}
