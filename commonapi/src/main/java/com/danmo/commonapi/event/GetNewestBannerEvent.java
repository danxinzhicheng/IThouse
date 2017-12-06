package com.danmo.commonapi.event;

import android.support.annotation.Nullable;

/**
 * Created by user on 2017/9/20.
 */

public class GetNewestBannerEvent extends GetNewestEvent {
    public GetNewestBannerEvent(@Nullable String uuid) {
        super(uuid);
    }
}
