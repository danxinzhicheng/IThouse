package com.danmo.commonapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.danmo.commonapi.api.community.CommunityApi;
import com.danmo.commonapi.api.community.CommunityImpl;
import com.danmo.commonapi.api.hotgoods.HotGoodsApi;
import com.danmo.commonapi.api.hotgoods.HotGoodsImpl;
import com.danmo.commonapi.api.login.LoginAPI;
import com.danmo.commonapi.api.login.LoginImpl;
import com.danmo.commonapi.api.newest.NewestAPI;
import com.danmo.commonapi.api.newest.NewestImpl;
import com.danmo.commonapi.api.newsdetail.NewsDetailAPI;
import com.danmo.commonapi.api.newsdetail.NewsDetailImpl;
import com.danmo.commonapi.api.user.UserAPI;
import com.danmo.commonapi.api.user.UserImpl;
import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.bean.login.OAuth;
import com.danmo.commonapi.bean.login.Token;
import com.danmo.commonutil.DebugUtil;
import com.danmo.commonutil.log.Config;
import com.danmo.commonutil.log.Logger;


public class CommonApi implements NewestAPI, NewsDetailAPI, HotGoodsApi, CommunityApi, LoginAPI, UserAPI {

    private static Context mContext;
    private static NewestImpl sNewestImpl;
    private static NewsDetailAPI sNewsDetailAPI;
    private static HotGoodsApi sHotGoodsApi;
    private static CommunityApi sCommunityApi;
    private static LoginImpl sLoginImpl;
    private static UserImpl sUserImpl;

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
        try {
            sNewestImpl = new NewestImpl(context, Constant.BASE_URL_NEWS, Constant.PARSE_XML);
            sNewsDetailAPI = new NewsDetailImpl(context, Constant.BASE_URL_NEWS, Constant.PARSE_XML);
            sHotGoodsApi = new HotGoodsImpl(context, Constant.BASE_URL_LAPIN, Constant.PARSE_GSON);
            sCommunityApi = new CommunityImpl(context, Constant.BASE_URL_QUANZI, Constant.PARSE_GSON);
            sLoginImpl = new LoginImpl(context, Constant.BASE_URL_LOGIN, Constant.PARSE_GSON);
            sUserImpl = new UserImpl(context, Constant.BASE_URL_LOGIN, Constant.PARSE_GSON);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public String getCommunityList(String categoryid, String type, String orderTime, String visistCount, String pageLength) {
        return sCommunityApi.getCommunityList(categoryid, type, orderTime, visistCount, pageLength);
    }

    @Override
    public String getCommunityComment(String id) {
        return sCommunityApi.getCommunityComment(id);
    }


    //--- login ------------------------------------------------------------------------------------

    /**
     * 登录时调用
     * 返回一个 token，用于获取各类私有信息使用，该 token 用 LoginEvent 接收。
     *
     * @param user_name 用户名
     * @param password  密码
     */
    @Override
    public String login(@NonNull String user_name, @NonNull String password) {
        return sLoginImpl.login(user_name, password);
    }

    /**
     * 用户登出
     */
    @Override
    public void logout() {
        sLoginImpl.logout();
    }

    /**
     * 是否登录
     *
     * @return 是否登录
     */
    @Override
    public boolean isLogin() {
        return sLoginImpl.isLogin();
    }

    //--- token ------------------------------------------------------------------------------------

    /**
     * 刷新 token
     */
    @Override
    public String refreshToken() {
        return sLoginImpl.refreshToken();
    }

    /**
     * 获取当前缓存的 token
     *
     * @return 当前缓存的 token
     */
    @Override
    public Token getCacheToken() {
        return sLoginImpl.getCacheToken();
    }

    //--- devices ----------------------------------------------------------------------------------

    /**
     * 更新设备信息
     * 记录用户 Device 信息，用于 Push 通知。
     * 请在每次用户打开 App 的时候调用此 API 以便更新 Token 的 last_actived_at 让服务端知道这个设备还活着。
     * Push 将会忽略那些超过两周的未更新的设备。
     */
    @Deprecated
    @Override
    public String updateDevices() {
        return sLoginImpl.updateDevices();
    }

    /**
     * 删除 Device 信息，请注意在用户登出或删除应用的时候调用，以便能确保清理掉。
     */
    @Deprecated
    @Override
    public String deleteDevices() {
        return sLoginImpl.deleteDevices();
    }

    //---个人信息------------------------------------------------------------------------------------

    /**
     * 获取用户列表
     *
     * @param limit 数量极限，默认值 20，值范围 1..150
     */
    @Override
    public String getUsersList(@Nullable Integer limit) {
        return sUserImpl.getUsersList(limit);
    }

    /**
     * 获取用户详细资料
     *
     * @param login_name 登录用户名(非昵称)
     */
    @Override
    public String getUser(@NonNull String login_name) {
        return sUserImpl.getUser(login_name);
    }

    /**
     * 获取当前登录者的详细资料
     */
    @Override
    public String getMe() {
        return sUserImpl.getMe();
    }
}
