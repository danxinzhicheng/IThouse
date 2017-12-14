package com.danmo.commonapi.event;

import android.support.annotation.Nullable;

import com.danmo.commonapi.base.BaseEvent;
import com.danmo.commonapi.bean.newest.detail.DetailRecommendTopNode;


public class GetNewsDetailRecommendEvent extends BaseEvent<DetailRecommendTopNode> {
    public GetNewsDetailRecommendEvent(@Nullable String uuid) {
        super(uuid);
    }
}
