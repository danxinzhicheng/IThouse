package com.danmo.ithouse.base;

import android.app.Application;
import android.content.Context;

import com.danmo.commonapi.CommonApi;
import com.danmo.commonutil.CrashHandler;
import com.danmo.commonutil.Utils;
import com.danmo.ithouse.util.Config;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

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
        initUMeng();

    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("ithome") //文件名
                .schemaVersion(1) //版本号
                .build();
        sRealm = Realm.getInstance(config);
    }

    private void initUMeng() {
        UMShareAPI.get(this);//初始化sdk
        UMConfigure.setLogEnabled(true);
        com.umeng.socialize.Config.DEBUG = true;
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, Config.appKey_UMeng);

        //UMShareAPI.init(this,Config.appKey_UMeng);

        PlatformConfig.setWeixin(Config.appKey_Weixin, Config.appSecret_Weixin);
        PlatformConfig.setSinaWeibo(Config.appKey_Weibo, Config.appSecret_Weibo, "http://danmoit.cn");
        PlatformConfig.setQQZone(Config.appID_QQ, Config.appKey_QQ);

    }

}
