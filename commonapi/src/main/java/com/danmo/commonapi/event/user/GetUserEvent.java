package com.danmo.commonapi.event.user;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.danmo.commonapi.base.BaseEvent;
import com.danmo.commonapi.bean.user.UserDetail;


public class GetUserEvent extends BaseEvent<UserDetail> {
    public GetUserEvent(@Nullable String uuid) {
        super(uuid);
    }

    public GetUserEvent(@Nullable String uuid, @NonNull Integer code, @Nullable UserDetail userDetail) {
        super(uuid, code, userDetail);
    }
}
