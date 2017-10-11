package com.danmo.commonapi.event;

import android.support.annotation.Nullable;
import com.danmo.commonapi.base.BaseEvent;
import com.danmo.commonapi.bean.newest.detail.DetailRelatedTopNode;

/**
 * Created by user on 2017/9/30.
 */

public class GetNewsDetailRelatedEvent extends BaseEvent<String> {
    public GetNewsDetailRelatedEvent(@Nullable String uuid) {
        super(uuid);
    }
}
