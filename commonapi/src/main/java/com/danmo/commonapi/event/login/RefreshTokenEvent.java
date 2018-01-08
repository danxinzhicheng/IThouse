package com.danmo.commonapi.event.login;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.danmo.commonapi.base.BaseEvent;
import com.danmo.commonapi.bean.login.Token;


public class RefreshTokenEvent extends BaseEvent<Token> {
    /**
     * @param uuid 唯一识别码
     */
    public RefreshTokenEvent(@Nullable String uuid) {
        super(uuid);
    }

    /**
     * @param uuid  唯一识别码
     * @param code  网络返回码
     * @param token 实体数据
     */
    public RefreshTokenEvent(@Nullable String uuid, @NonNull Integer code, @Nullable Token token) {
        super(uuid, code, token);
    }
}
