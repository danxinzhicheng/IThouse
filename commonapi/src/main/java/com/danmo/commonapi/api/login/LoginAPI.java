package com.danmo.commonapi.api.login;

import android.support.annotation.NonNull;

import com.danmo.commonapi.bean.login.Token;


public interface LoginAPI {

    //--- login ------------------------------------------------------------------------------------

    /**
     * 登录时调用
     * 返回一个 token，用于获取各类私有信息使用，该 token 用 LoginEvent 接收。
     *
     * @param user_name 用户名
     * @param password  密码
     * @see LoginEvent
     */
    String login(@NonNull String user_name, @NonNull String password);

    /**
     * 用户登出
     */
    void logout();

    /**
     * 是否登录
     *
     * @return 是否登录
     */
    boolean isLogin();

    //--- token ------------------------------------------------------------------------------------

    /**
     * 刷新 token
     *
     * @see RefreshTokenEvent
     */
    String refreshToken();

    /**
     * 获取当前缓存的 token
     *
     * @return 当前缓存的 token
     */
    Token getCacheToken();


    //--- devices ----------------------------------------------------------------------------------

    /**
     * 更新设备信息
     * 记录用户 Device 信息，用于 Push 通知。
     * 请在每次用户打开 App 的时候调用此 API 以便更新 Token 的 last_actived_at 让服务端知道这个设备还活着。
     * Push 将会忽略那些超过两周的未更新的设备。
     */
    String updateDevices();

    /**
     * 删除 Device 信息，请注意在用户登出或删除应用的时候调用，以便能确保清理掉。
     */
    String deleteDevices();


}
