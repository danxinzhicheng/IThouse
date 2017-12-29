package com.danmo.commonapi;

import android.content.Context;
import android.support.annotation.NonNull;

import com.danmo.commonapi.api.community.CommunityApi;
import com.danmo.commonapi.api.community.CommunityImpl;
import com.danmo.commonapi.api.hotgoods.HotGoodsApi;
import com.danmo.commonapi.api.hotgoods.HotGoodsImpl;
import com.danmo.commonapi.api.newest.NewestAPI;
import com.danmo.commonapi.api.newest.NewestImpl;
import com.danmo.commonapi.api.newsdetail.NewsDetailAPI;
import com.danmo.commonapi.api.newsdetail.NewsDetailImpl;
import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.base.OAuth;
import com.danmo.commonutil.DebugUtil;
import com.danmo.commonutil.log.Config;
import com.danmo.commonutil.log.Logger;


public class CommonApi implements NewestAPI, NewsDetailAPI, HotGoodsApi, CommunityApi {

    private static Context mContext;
    private static NewestImpl sNewestImpl;
    private static NewsDetailAPI sNewsDetailAPI;
    private static HotGoodsApi sHotGoodsApi;
    private static CommunityApi sCommunityApi;

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
        Logger.i("初始化IThouse");

        OAuth.client_id = client_id;
        OAuth.client_secret = client_secret;

        initImplement(context);

        return getSingleInstance();
    }

    private static void initLogger(@NonNull Context context) {
        // 在 debug 模式输出日志， release 模式自动移除
        if (DebugUtil.isInDebug(context)) {
            Logger.init("IThouse").setLevel(Config.LEVEL_FULL);
        } else {
            Logger.init("IThouse").setLevel(Config.LEVEL_NONE);
        }
    }

    private static void initImplement(Context context) {
        mContext = context;
        Logger.i("初始化 implement");
        try {
            sNewestImpl = new NewestImpl(context, Constant.BASE_URL_NEWS, Constant.PARSE_XML);
            sNewsDetailAPI = new NewsDetailImpl(context, Constant.BASE_URL_NEWS, Constant.PARSE_XML);
            sHotGoodsApi = new HotGoodsImpl(context, Constant.BASE_URL_LAPIN, Constant.PARSE_GSON);
            sCommunityApi = new CommunityImpl(context, Constant.BASE_URL_QUANZI, Constant.PARSE_GSON);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logger.i("初始化 implement 结束");
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
        sNewsDetailAPI = new NewsDetailImpl(mContext, Constant.BASE_URL_NEWS, Constant.PARSE_XML);
        return sNewsDetailAPI.getNewsDetailContent(url);
    }

    @Override
    public String getNewsDetailRelated(String url) {
        sNewsDetailAPI = new NewsDetailImpl(mContext, Constant.BASE_URL_NEWS, Constant.PARSE_DEFAULT);
        return sNewsDetailAPI.getNewsDetailRelated(url);
    }

    @Override
    public String getNewsDetailRecommend(String url) {
        sNewsDetailAPI = new NewsDetailImpl(mContext, Constant.BASE_URL_NEWS, Constant.PARSE_GSON);
        return sNewsDetailAPI.getNewsDetailRecommend(url);
    }

    @Override
    public String getHotGoodsBannerList(String url) {
        return sHotGoodsApi.getHotGoodsBannerList(url);
    }

    @Override
    public String getHotGoodsRankList(String url) {
        return sHotGoodsApi.getHotGoodsRankList(url);
    }

    @Override
    public String getHotGoodsList(String url) {
        return sHotGoodsApi.getHotGoodsList(url);
    }

    @Override
    public String getCommunityCategory(String url) {
        return sCommunityApi.getCommunityCategory(url);
    }

    @Override
    public String getCommunityListNewest(String url) {
        return sCommunityApi.getCommunityListNewest(url);
    }

    @Override
    public String getCommunityListHotest(String url) {
        return sCommunityApi.getCommunityListHotest(url);
    }

    @Override
    public String getCommunityCategoryList(String categoryid, String type, String orderTime, String visistCount, String pageLength) {
        return sCommunityApi.getCommunityCategoryList(categoryid, type, orderTime, visistCount, pageLength);
    }

    @Override
    public String getCommunityComment(String id) {
        return sCommunityApi.getCommunityComment(id);
    }
}
