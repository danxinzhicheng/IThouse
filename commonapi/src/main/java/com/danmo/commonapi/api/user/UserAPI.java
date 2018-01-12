package com.danmo.commonapi.api.user;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface UserAPI {

    //--- user info --------------------------------------------------------------------------------

    /**
     * 获取用户列表
     *
     * @param limit 数量极限，默认值 20，值范围 1..150
     */
    String getUsersList(@Nullable Integer limit);

    /**
     * 获取用户详细资料
     *
     * @param login_name 登录用户名(非昵称)
     */
    String getUser(@NonNull String login_name);

    /**
     * 获取当前登录者的详细资料
     */
    String getMe();

}
