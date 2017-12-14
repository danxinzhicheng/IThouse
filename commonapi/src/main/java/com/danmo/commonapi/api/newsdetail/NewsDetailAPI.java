package com.danmo.commonapi.api.newsdetail;

public interface NewsDetailAPI {
    String getNewsDetailContent(String url);

    String getNewsDetailRelated(String url);

    String getNewsDetailRecommend(String url);
}
