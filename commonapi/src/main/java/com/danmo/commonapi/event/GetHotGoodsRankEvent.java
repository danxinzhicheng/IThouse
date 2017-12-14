package com.danmo.commonapi.event;

import android.support.annotation.Nullable;


public class GetHotGoodsRankEvent extends GetHotGoodsListEvent {
    public GetHotGoodsRankEvent(@Nullable String uuid) {
        super(uuid);
    }
}
