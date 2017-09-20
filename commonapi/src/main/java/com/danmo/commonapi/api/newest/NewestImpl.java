package com.danmo.commonapi.api.newest;

import android.content.Context;
import android.support.annotation.NonNull;

import com.danmo.commonapi.base.BaseImpl;
import com.danmo.commonapi.callback.BaseCallback;
import com.danmo.commonapi.event.GetNewestBannerEvent;
import com.danmo.commonapi.event.GetNewestEvent;
import com.danmo.commonutil.UUIDGenerator;


/**
 * Created by danmo on 2017/9/16.
 */

public class NewestImpl extends BaseImpl<NewestService> implements NewestAPI {
    public NewestImpl(@NonNull Context context) {
        super(context);
    }

    @Override
    public String getNewestList(String url) {
        String uuid = UUIDGenerator.getUUID();
        mService.getNewestList(url).enqueue(new BaseCallback<>(new GetNewestEvent(uuid)));
        return uuid;
    }

    @Override
    public String getNewestBannerList(String url) {
        String uuid = UUIDGenerator.getUUID();
        mService.getNewestBannerList(url).enqueue(new BaseCallback<>(new GetNewestBannerEvent(uuid)));
        return uuid;
    }
}
