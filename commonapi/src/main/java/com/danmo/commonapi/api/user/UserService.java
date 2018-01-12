package com.danmo.commonapi.api.user;

import com.danmo.commonapi.bean.user.User;
import com.danmo.commonapi.bean.user.UserDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface UserService {

    /**
     * 获取用户列表
     *
     * @param limit 数量极限，默认值 20，值范围 1..150
     * @return 用户列表
     */
    @GET("users.json")
    Call<List<User>> getUsersList(@Query("limit") Integer limit);

    /**
     * 获取用户详细资料
     *
     * @param login_name 登录用户名(非昵称)
     * @return 用户详情
     */
    @GET("users/{login}.json")
    Call<UserDetail> getUser(@Path("login") String login_name);

    /**
     * 获取当前登录者的详细资料
     *
     * @return 用户详情
     */
    @GET("users/me.json")
    Call<UserDetail> getMe();


}
