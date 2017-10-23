package com.danmo.commonapi.event;

import android.support.annotation.Nullable;

/**
 * Created by user on 2017/10/16.
 */

public class GetLapinRankEvent extends GetLapinListEvent {
    public GetLapinRankEvent(@Nullable String uuid) {
        super(uuid);
    }
}
