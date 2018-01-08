package com.danmo.commonapi.event.login;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.danmo.commonapi.base.BaseEvent;
import com.danmo.commonapi.bean.login.State;

public class DeleteDevicesEvent extends BaseEvent<State> {
    public DeleteDevicesEvent(@Nullable String uuid) {
        super(uuid);
    }

    public DeleteDevicesEvent(@Nullable String uuid, @NonNull Integer code, @Nullable State state) {
        super(uuid, code, state);
    }
}
