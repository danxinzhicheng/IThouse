package com.danmo.commonapi.event;

import android.support.annotation.Nullable;


public class GetNewestBannerEvent extends GetNewestEvent {
    public GetNewestBannerEvent(@Nullable String uuid) {
        super(uuid);
    }
}
