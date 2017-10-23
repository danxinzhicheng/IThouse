package com.danmo.commonapi.event;

import android.support.annotation.Nullable;

import com.danmo.commonapi.base.BaseEvent;
import com.danmo.commonapi.bean.lapin.LapinTopNode;

/**
 * Created by user on 2017/10/16.
 */

public class GetLapinListEvent extends BaseEvent<LapinTopNode> {
    public GetLapinListEvent(@Nullable String uuid) {
        super(uuid);
    }
}
