package com.danmo.commonapi.base.cache;

import android.content.Context;
import android.support.annotation.NonNull;

import com.danmo.commonapi.bean.login.Token;

/**
 * 缓存工具类，用于缓存各类数据
 */
public class CacheUtil {

    ACache cache;

    public CacheUtil(Context context) {
        cache = ACache.get(context);
    }

    public void saveToken(@NonNull Token token) {
        cache.put("token", token);
    }

    public Token getToken() {
        return (Token) cache.getAsObject("token");
    }

    public void clearToken() {
        cache.remove("token");
    }
}
