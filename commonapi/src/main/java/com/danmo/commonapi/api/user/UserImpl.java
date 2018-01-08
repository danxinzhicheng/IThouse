package com.danmo.commonapi.api.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.danmo.commonapi.base.BaseImpl;
import com.danmo.commonapi.callback.BaseCallback;
import com.danmo.commonapi.event.user.GetMeEvent;
import com.danmo.commonapi.event.user.GetUserEvent;
import com.danmo.commonapi.event.user.GetUsersListEvent;
import com.danmo.commonutil.UUIDGenerator;

public class UserImpl extends BaseImpl<UserService> implements UserAPI {
    public UserImpl(@NonNull Context context, String baseUrl, int parsePattern) {
        super(context, baseUrl, parsePattern);
    }

    //--- user info --------------------------------------------------------------------------------

    /**
     * 获取用户列表
     *
     * @param limit 数量极限，默认值 20，值范围 1..150
     * @see GetUsersListEvent
     */
    @Override
    public String getUsersList(@Nullable Integer limit) {
        String uuid = UUIDGenerator.getUUID();
        mService.getUsersList(limit).enqueue(new BaseCallback<>(new GetUsersListEvent(uuid)));
        return uuid;
    }

    /**
     * 获取用户详细资料
     *
     * @param login_name 登录用户名(非昵称)
     * @see GetUserEvent
     */
    @Override
    public String getUser(@NonNull String login_name) {
        String uuid = UUIDGenerator.getUUID();
        mService.getUser(login_name).enqueue(new BaseCallback<>(new GetUserEvent(uuid)));
        return uuid;
    }

    /**
     * 获取当前登录者的详细资料
     *
     * @see GetMeEvent
     */
    @Override
    public String getMe() {
        String uuid = UUIDGenerator.getUUID();
        mService.getMe().enqueue(new BaseCallback<>(new GetMeEvent(uuid)));
        return uuid;
    }

}
