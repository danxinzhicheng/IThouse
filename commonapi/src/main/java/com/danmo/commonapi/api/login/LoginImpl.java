package com.danmo.commonapi.api.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.danmo.commonapi.base.BaseImpl;
import com.danmo.commonapi.bean.login.OAuth;
import com.danmo.commonapi.bean.login.Token;
import com.danmo.commonapi.callback.BaseCallback;
import com.danmo.commonapi.callback.TokenCallback;
import com.danmo.commonapi.event.login.DeleteDevicesEvent;
import com.danmo.commonapi.event.login.LoginEvent;
import com.danmo.commonapi.event.login.LogoutEvent;
import com.danmo.commonapi.event.login.RefreshTokenEvent;
import com.danmo.commonapi.event.login.UpdateDevicesEvent;
import com.danmo.commonutil.UUIDGenerator;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;

public class LoginImpl extends BaseImpl<LoginService> implements LoginAPI {

    public LoginImpl(@NonNull Context context, String baseUrl, int parsePattern) {
        super(context, baseUrl, parsePattern);
    }

    /**
     * 登录时调用
     * 返回一个 token，用于获取各类私有信息使用，该 token 用 LoginEvent 接收。
     *
     * @param user_name 用户名
     * @param password  密码
     * @see LoginEvent
     */
    @Override
    public String login(@NonNull String user_name, @NonNull String password) {
        final String uuid = UUIDGenerator.getUUID();
        mService.getToken(OAuth.client_id, OAuth.client_secret, OAuth.GRANT_TYPE_LOGIN, user_name, password)
                .enqueue(new TokenCallback(mCacheUtil, new LoginEvent(uuid)));
        return uuid;
    }

    /**
     * 用户登出
     */
    @Override
    public void logout() {
        String uuid = UUIDGenerator.getUUID();
        mCacheUtil.clearToken();    // 清除token
        EventBus.getDefault().post(new LogoutEvent(uuid, 0, "用户登出"));
    }

    /**
     * 是否登录
     *
     * @return 是否登录
     */
    @Override
    public boolean isLogin() {
        return !(mCacheUtil.getToken() == null);
    }

    /**
     * 刷新 token
     *
     * @see RefreshTokenEvent
     */
    @Override
    public String refreshToken() {
        final String uuid = UUIDGenerator.getUUID();
        // 如果本地没有缓存的 token，则直接返回一个 401 异常
        if (null == mCacheUtil.getToken()) {
            EventBus.getDefault().post(new RefreshTokenEvent(uuid, 401, null));
            return null;
        }

        // 如果本地有缓存的 token，尝试刷新 token 信息，并缓存新的 Token
        Call<Token> call = mService.refreshToken(OAuth.client_id, OAuth.client_secret,
                OAuth.GRANT_TYPE_REFRESH, mCacheUtil.getToken().getRefresh_token());
        call.enqueue(new TokenCallback(mCacheUtil, new RefreshTokenEvent(uuid)));
        return uuid;
    }

    /**
     * 获取当前缓存的 token
     *
     * @return 当前缓存的 token
     */
    @Override
    public Token getCacheToken() {
        return mCacheUtil.getToken();
    }

    /**
     * 更新设备信息
     * 记录用户 Device 信息，用于 Push 通知。
     * 请在每次用户打开 App 的时候调用此 API 以便更新 Token 的 last_actived_at 让服务端知道这个设备还活着。
     * Push 将会忽略那些超过两周的未更新的设备。
     */
    @Deprecated
    @Override
    public String updateDevices() {
        String uuid = UUIDGenerator.getUUID();
        mService.updateDevices("android", mCacheUtil.getToken().getAccess_token())
                .enqueue(new BaseCallback<>(new UpdateDevicesEvent(uuid)));
        return uuid;
    }

    /**
     * 删除 Device 信息，请注意在用户登出或删除应用的时候调用，以便能确保清理掉。
     */
    @Deprecated
    @Override
    public String deleteDevices() {
        String uuid = UUIDGenerator.getUUID();
        mService.deleteDevices("android", mCacheUtil.getToken().getAccess_token())
                .enqueue(new BaseCallback<>(new DeleteDevicesEvent(uuid)));
        return uuid;
    }
}
