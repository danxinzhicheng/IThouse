package com.danmo.ithouse.base;

import android.app.Application;

import com.danmo.commonutil.CrashHandler;
import com.danmo.commonutil.Utils;

/**
 * Created by user on 2017/9/12.
 */

public class DanmoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
        Utils.init(this);
    }
}
