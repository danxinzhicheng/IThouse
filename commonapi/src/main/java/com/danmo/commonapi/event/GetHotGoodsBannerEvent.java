package com.danmo.commonapi.event;

import android.support.annotation.Nullable;


public class GetHotGoodsBannerEvent extends GetHotGoodsListEvent {
    public GetHotGoodsBannerEvent(@Nullable String uuid) {
        super(uuid);
    }
}
