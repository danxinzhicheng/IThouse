package com.danmo.commonapi.event.hotgoods;

import android.support.annotation.Nullable;


public class GetHotGoodsBannerEvent extends GetHotGoodsListEvent {
    public GetHotGoodsBannerEvent(@Nullable String uuid) {
        super(uuid);
    }
}
