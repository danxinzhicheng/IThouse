package com.danmo.ithouse.base;

import android.app.Application;
import android.content.Context;

import com.danmo.commonapi.CommonApi;
import com.danmo.commonutil.CrashHandler;
import com.danmo.commonutil.Utils;
import com.danmo.ithouse.util.Config;
import com.tencent.bugly.crashreport.CrashReport;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.danmo.commonapi.bean.login.OAuth.client_id;
import static com.danmo.commonapi.bean.login.OAuth.client_secret;


public class BaseApplication extends Application {

    public static Context sAppContext;
    public static Realm sRealm;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
        Utils.init(this);
        CommonApi.init(this, client_id, client_secret);
        initRealm();
        sAppContext = this;
        CrashReport.initCrashReport(this, Config.appID_Bugly, true);
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("ithome") //文件名
                .schemaVersion(1) //版本号
                .build();
        sRealm = Realm.getInstance(config);
    }

}
