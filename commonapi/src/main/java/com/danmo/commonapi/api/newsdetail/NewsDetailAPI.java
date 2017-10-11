package com.danmo.commonapi.api.newsdetail;

/**
 * Created by user on 2017/9/30.
 */

public interface NewsDetailAPI {
    String getNewsDetailContent(String url);

    String getNewsDetailRelated(String url);

    String getNewsDetailRecommend(String url);
}
