package com.danmo.commonapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.danmo.commonapi.api.lapin.LapinApi;
import com.danmo.commonapi.api.lapin.LapinImpl;
import com.danmo.commonapi.api.newest.NewestAPI;
import com.danmo.commonapi.api.newest.NewestImpl;
import com.danmo.commonapi.api.news.NewsAPI;
import com.danmo.commonapi.api.news.NewsImpl;
import com.danmo.commonapi.api.newsdetail.NewsDetailAPI;
import com.danmo.commonapi.api.newsdetail.NewsDetailImpl;
import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.base.OAuth;
import com.danmo.commonutil.DebugUtil;
import com.danmo.commonutil.log.Config;
import com.danmo.commonutil.log.Logger;

/**
 * Created by danmo on 2017/9/16.
 */

public class CommonApi implements NewsAPI, NewestAPI,NewsDetailAPI,LapinApi {

    private static Context mContext;
    private static NewsImpl sNewsImpl;
    private static NewestImpl sNewestImpl;
    private static NewsDetailAPI sNewsDetailAPI;
    private static LapinApi sLapinApi;

    //--- 单例 -----------------------------------------------------------------------------------
    private volatile static CommonApi mCommonApi;
    private CommonApi() {
    }
    public static CommonApi getSingleInstance() {
        if (null == mCommonApi) {
            synchronized (CommonApi.class) {
                if (null == mCommonApi) {
                    mCommonApi = new CommonApi();
                }
            }
        }
        return mCommonApi;
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
        mContext = context;
        Logger.i("初始化 implement");
        try {
            sNewsImpl = new NewsImpl(context, Constant.PARSE_XML);
            sNewestImpl = new NewestImpl(context,Constant.PARSE_XML);
            sNewsDetailAPI = new NewsDetailImpl(context,Constant.PARSE_XML);
            sLapinApi = new LapinImpl(context,Constant.PARSE_GSON);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logger.i("初始化 implement 结束");
    }

    @Override
    public String getNewsList(@Nullable Integer node_id, @Nullable Integer offset, @Nullable Integer limit) {

        return sNewsImpl.getNewsList(node_id, offset, limit);
    }

    @Override
    public String getNewestList(String url) {
        return sNewestImpl.getNewestList(url);
    }

    @Override
    public String getNewestBannerList(String url) {
        return sNewestImpl.getNewestBannerList(url);
    }

    @Override
    public String getNewsDetailContent(String url) {
        sNewsDetailAPI = new NewsDetailImpl(mContext,Constant.PARSE_XML);
        return sNewsDetailAPI.getNewsDetailContent(url);
    }

    @Override
    public String getNewsDetailRelated(String url) {
        sNewsDetailAPI = new NewsDetailImpl(mContext,Constant.PARSE_DEFAULT);
        return sNewsDetailAPI.getNewsDetailRelated(url);
    }

    @Override
    public String getNewsDetailRecommend(String url) {
        sNewsDetailAPI = new NewsDetailImpl(mContext,Constant.PARSE_GSON);
        return sNewsDetailAPI.getNewsDetailRecommend(url);
    }

    @Override
    public String getLapinBannerList(String url) {
        return sLapinApi.getLapinBannerList(url);
    }

    @Override
    public String getLapinRankList(String url) {
        return sLapinApi.getLapinRankList(url);
    }

    @Override
    public String getLapinList(String url) {
        return sLapinApi.getLapinList(url);
    }
}
