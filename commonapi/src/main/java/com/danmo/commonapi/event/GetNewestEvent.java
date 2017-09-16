package com.danmo.commonapi.event;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.danmo.commonapi.base.BaseEvent;
import com.danmo.commonapi.bean.New;
import com.danmo.commonapi.bean.Newest;
import com.danmo.commonapi.bean.NewestItem;
import com.danmo.commonapi.bean.NewestTopNode;

import java.util.List;

/**
 * Created by danmo on 2017/9/16.
 */

public class GetNewestEvent extends BaseEvent<NewestTopNode> {


    public GetNewestEvent(@Nullable String uuid) {
        super(uuid);
    }

    public GetNewestEvent(@Nullable String uuid, @NonNull Integer code, @Nullable NewestTopNode newest) {
        super(uuid, code, newest);
    }
}
