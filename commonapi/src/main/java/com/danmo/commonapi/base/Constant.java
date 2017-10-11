/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-03-08 01:01:18
 *
 * GitHub:  https://github.com/GcsSloop
 * Website: http://www.gcssloop.com
 * Weibo:   http://weibo.com/GcsSloop
 */

package com.danmo.commonapi.base;

public class Constant {

    public static final int PARSE_DEFAULT = 0;
    public static final int PARSE_GSON = 1;
    public static final int PARSE_XML = 2;

    public static final String HOST_URL = "https://www.ithome.com";
    public static final String BASE_URL ="http://api.ithome.com/";
    public static final String BANNER_URL = "http://api.ithome.com/xml/slide/slide.xml?=1505559010903";//幻灯片
    public static final String NESLIST_URL = "http://api.ithome.com/xml/newslist/news.xml";//最新

    public static final String NEWS_DETAIL_CONTENT_URL = "http://api.ithome.com/xml/newscontent/329/170.xml";//文章详情
    public static final String NEWS_DETAIL_RELATED_URL = "http://api.ithome.com/json/tags/0329/329170.json";//相关文章
    public static final String NEWS_DETAIL_RECOMMEND_URL= "http://api.lapin365.com/api/apps/douzaimai?count=3&signature=0D41491DDEF77D3823EDE90ABEAA892E&timestamp=1507537231&platform=ithome_android&r=1507537231282";//商品推荐

    public static final String RECOMMEND_PIC_HOST = "http://img.lapin365.com/productpictures";//推荐图片路径前缀
}
