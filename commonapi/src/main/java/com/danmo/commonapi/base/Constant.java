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

 */

package com.danmo.commonapi.base;

import android.text.TextUtils;

public class Constant {

    public static final int PARSE_DEFAULT = 0;
    public static final int PARSE_GSON = 1;
    public static final int PARSE_XML = 2;

    public static final String BASE_URL_NEWS = "http://api.ithome.com/";
    public static final String BASE_URL_LAPIN = "http://api.lapin365.com/api/";
    public static final String BASE_URL_QUANZI = "http://apiquan.ithome.com/api/";

    //=============================资讯====================================
    public static final String BANNER_URL = "http://api.ithome.com/xml/slide/slide.xml?=1505559010903";//幻灯片
    public static final String NESLIST_URL = "http://api.ithome.com/xml/newslist/news.xml";//最新
    //    public static final String NEWS_DETAIL_CONTENT_URL = "http://api.ithome.com/xml/newscontent/329/170.xml";//文章详情
    public static final String NEWS_DETAIL_CONTENT_URL = "http://api.ithome.com/xml/newscontent/%s.xml";//文章详情
    public static final String NEWS_DETAIL_CONTENT_URL_BANNER = "http://api.ithome.com/rss/%s.xml";//文章详情forBanner
    public static final String NEWS_DETAIL_RELATED_URL = "http://api.ithome.com/json/tags/0375/%s.json";//相关文章
    public static final String NEWS_DETAIL_RECOMMEND_URL = "http://api.lapin365.com/api/apps/douzaimai?count=3&platform=ithome_android";//商品推荐
    public static final String RECOMMEND_PIC_HOST = "http://img.lapin365.com/productpictures";//推荐图片路径前缀

    //============================辣品====================================
    public static final String LAPIN_BANNER_URL = "http://api.lapin365.com/api/apps/focus?signature=E8F05F3FFF677CF602D6EADA5B1A1C44&timestamp=1508135340&r=1508135340808&platform=ithome_android";//幻灯片
    public static final String LAPIN_RANK_URL = "http://api.lapin365.com/api/apps/tag?tag=%E7%88%86%E5%93%81&count=20&ver=2&platform=ithome_android&l=all";//辣榜
    public static final String LAPIN_LIST_URL = "http://api.lapin365.com/api/apps/indexlistsegbyId?productid=0&count=20&ver=3&platform=ithome_android";//列表
    public static final String LAPIN_PIC_URL = "http://img.lapin365.com";//图片路径前缀
    //图片加载
    //http://img.lapin365.com/productpictures/gitem/2017/10/14/165340094.jpg@s_0,w_180,h_180
    //http://img.lapin365.com/gitem/2017/10/10/121028623.jpg@s_0,w_400,h_400

    //============================圈子====================================
    public static final String QUANZI_CATEGORY = "http://apiquan.ithome.com/api/category?client=2";
    //    public static final String QUANZI_LIST_NEWEST = "http://apiquan.ithome.com/api/post?categoryid=0&type=0&orderTime&visistCount&pageLength";
//    public static final String QUANZI_LIST_HOTEST = "http://apiquan.ithome.com/api/post?categoryid=0&type=3&orderTime&visistCount&pageLength";
    public static final String QUANZI_PIC_URL = "http://avatar.ithome.com/avatars";//图片路径前缀
    // http://apiquan.ithome.com/api/post/?categoryid=22&type=0&orderTime&visistCount&pageLength //最新回复 畅谈id:2
    // http://apiquan.ithome.com/api/post/?categoryid=22&type=2&orderTime&visistCount&pageLength //热帖
    // http://apiquan.ithome.com/api/post/?categoryid=22&type=1&orderTime&visistCount&pageLength //最新发表

    //图片加载
    //uid:uid	1415703---/001/41/57/03_60.jpg
    //http://avatar.ithome.com/avatars/000/34/32/36_60.jpg

    //============================搜索：Android====================================
    public static final String SEARCH_NEWS = "http://dyn.ithome.com/xml/search/ce3f2d7f0740b24b.xml?client=android&r=1513750892472";
    public static final String SEARCH_LAPIN = "http://s.lapin365.com/callback/searchp?platform=ithome_android&key=Android&userid=1467532&pagenumber=1";
    public static final String SEARCH_QUANZI = "http://apiquan.ithome.com/api/post/getsearch?keyword=ce3f2d7f0740b24b&maxnewsid=0&r=1513750956822";

    //获取头像Url
    public static String convertRealPicUrl(String uid) {
        if (TextUtils.isEmpty(uid)) {
            return Constant.QUANZI_PIC_URL;
        }
        if (uid.length() == 1) {
            uid = "1347938";
        }
        if (uid.length() == 7) {
            String url = Constant.QUANZI_PIC_URL + "/" + "001" + "/" + uid.substring(1, 3) + "/" + uid.substring(3, 5) + "/" + uid.substring(5, 7) + "_60.jpg";
            return url;
        }
        if (uid.length() == 6) {
            String url = Constant.QUANZI_PIC_URL + "/" + "000" + "/" + uid.substring(0, 2) + "/" + uid.substring(2, 4) + "/" + uid.substring(4, 6) + "_60.jpg";
            return url;
        }
        return Constant.QUANZI_PIC_URL;
    }


    //============================登录=============================================
    public static final String BASE_URL_LOGIN = "https://diycode.cc/api/v3/";
    public static final String OAUTH_URL_LOGIN = "https://www.diycode.cc/oauth/token";


}
