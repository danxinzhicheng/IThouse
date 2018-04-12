package com.danmo.ithouse.widget.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by xss on 2018/1/24.
 */

public class BaseHandler extends Handler {

    private final WeakReference<Context> mActivity;
    private BaseHandler.HandlerOperate handlerOperate;

    BaseHandler(Context activity, BaseHandler.HandlerOperate operate) {
        mActivity = new WeakReference<>(activity);
        this.handlerOperate = operate;
    }

    @Override
    public void handleMessage(Message msg) {
        Context activity = mActivity.get();
        if (activity != null && handlerOperate != null) {
            handlerOperate.handleMessage(msg);
        }
    }

    interface HandlerOperate {
        void handleMessage(Message message);
    }
}
