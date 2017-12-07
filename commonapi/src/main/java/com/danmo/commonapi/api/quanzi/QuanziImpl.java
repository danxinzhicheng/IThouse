package com.danmo.commonapi.api.quanzi;

import android.content.Context;
import android.support.annotation.NonNull;
import com.danmo.commonapi.base.BaseImpl;
import com.danmo.commonapi.callback.BaseCallback;
import com.danmo.commonapi.event.GetQuanziCatetoryEvent;
import com.danmo.commonapi.event.GetQuanziListEvent;
import com.danmo.commonutil.UUIDGenerator;

/**
 * Created by user on 2017/12/6.
 */

public class QuanziImpl extends BaseImpl<QuanziService> implements QuanziApi {
    public QuanziImpl(@NonNull Context context, int currentParse) {
        super(context, currentParse);
    }

    @Override
    public String getQuanziCategory(String url) {
        String uuid = UUIDGenerator.getUUID();
        mService.getQuanziCategory(url).enqueue(new BaseCallback<>(new GetQuanziCatetoryEvent(uuid)));
        return uuid;
    }

    @Override
    public String getQuanziListNewest(String url) {
        String uuid = UUIDGenerator.getUUID();
        mService.getQuanziListNewest(url).enqueue(new BaseCallback<>(new GetQuanziListEvent(uuid)));
        return uuid;
    }

    @Override
    public String getQuanziListHotest(String url) {
        String uuid = UUIDGenerator.getUUID();
        mService.getQuanziListHotest(url).enqueue(new BaseCallback<>(new GetQuanziListEvent(uuid)));
        return uuid;
    }
}
