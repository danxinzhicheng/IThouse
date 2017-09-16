package com.danmo.commonapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.danmo.commonapi.api.newest.NewestAPI;
import com.danmo.commonapi.api.newest.NewestImpl;
import com.danmo.commonapi.api.news.NewsAPI;
import com.danmo.commonapi.api.news.NewsImpl;
import com.danmo.commonapi.api.test.TestAPI;
import com.danmo.commonapi.api.test.TestImpl;
import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.base.OAuth;
import com.danmo.commonutil.DebugUtil;
import com.danmo.commonutil.log.Config;
import com.danmo.commonutil.log.Logger;

/**
 * Created by danmo on 2017/9/16.
 */

public class CommonApi implements TestAPI,NewsAPI,NewestAPI {

    private static TestImpl sTestImpl;
    private static NewsImpl sNewsImpl;
    private static NewestImpl sNewestImpl;
    //--- 单例 -----------------------------------------------------------------------------------

    private volatile static CommonApi mDiycode;

    private CommonApi() {
    }


    public static CommonApi getSingleInstance() {
        if (null == mDiycode) {
            synchronized (CommonApi.class) {
                if (null == mDiycode) {
                    mDiycode = new CommonApi();
                }
            }
        }
        return mDiycode;
    }

    //--- 初始化 ---------------------------------------------------------------------------------

    public static CommonApi init(@NonNull Context context, @NonNull final String client_id,
                               @NonNull final String client_secret) {
        initLogger(context);
        Logger.i("初始化 diycode");

        OAuth.client_id = client_id;
        OAuth.client_secret = client_secret;

        initImplement(context);

        return getSingleInstance();
    }
    private static void initLogger(@NonNull Context context) {
        // 在 debug 模式输出日志， release 模式自动移除
        if (DebugUtil.isInDebug(context)) {
            Logger.init("Diycode").setLevel(Config.LEVEL_FULL);
        } else {
            Logger.init("Diycode").setLevel(Config.LEVEL_NONE);
        }
    }

    private static void initImplement(Context context) {
        Logger.i("初始化 implement");
        try {
            sTestImpl = new TestImpl(context);
            sNewsImpl = new NewsImpl(context);
            sNewestImpl = new NewestImpl(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logger.i("初始化 implement 结束");
    }


    @Override
    public String hello(@Nullable Integer limit) {
        return sTestImpl.hello(limit);
    }

    @Override
    public String getNewsList(@Nullable Integer node_id, @Nullable Integer offset, @Nullable Integer limit) {

        return sNewsImpl.getNewsList(node_id, offset, limit);
    }

    @Override
    public String getNewestList(String url) {

        return sNewestImpl.getNewestList(url);
    }
}
