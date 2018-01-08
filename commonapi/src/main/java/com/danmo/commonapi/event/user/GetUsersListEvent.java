package com.danmo.commonapi.event.user;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.danmo.commonapi.base.BaseEvent;
import com.danmo.commonapi.bean.user.User;

import java.util.List;

public class GetUsersListEvent extends BaseEvent<List<User>> {
    /**
     * @param uuid 唯一识别码
     */
    public GetUsersListEvent(@Nullable String uuid) {
        super(uuid);
    }

    /**
     * @param uuid  唯一识别码
     * @param code  网络返回码
     * @param users 实体数据
     */
    public GetUsersListEvent(@Nullable String uuid, @NonNull Integer code, @Nullable List<User> users) {
        super(uuid, code, users);
    }
}
