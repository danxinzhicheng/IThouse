package com.danmo.commonapi.event.hotgoods;

import android.support.annotation.Nullable;

import com.danmo.commonapi.base.BaseEvent;
import com.danmo.commonapi.bean.hotgoods.HotGoodsTopNode;


public class GetHotGoodsListEvent extends BaseEvent<HotGoodsTopNode> {
    public GetHotGoodsListEvent(@Nullable String uuid) {
        super(uuid);
    }
}
