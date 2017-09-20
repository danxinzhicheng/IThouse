package com.danmo.commonapi.bean.newest;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by danmo on 2017/9/16.
 */

@Root(name = "item", strict = false)
public class NewestItem {
    @Element(name = "newsid", required = false)
    public String newsid;
    @Element(name = "title", required = false)
    public String title;
    @Element(name = "url", required = false)
    public String url;
    @Element(name = "postdate", required = false)
    public String postdate;
    @Element(name = "image", required = false)
    public String image;
    @Element(name = "description", required = false)
    public String description;
    @Element(name = "hitcount", required = false)
    public String hitcount;
    @Element(name = "commentcount", required = false)
    public String commentcount;
    @Element(name = "forbidcomment", required = false)
    public String forbidcomment;
    @Element(name = "cid", required = false)
    public String cid;
    @Element(name = "v", required = false)
    public String v;//001为视频
    @Element(name = "live", required = false)
    public String live;//1为直播
    //==========banner special==============
    @Element(name = "link", required = false)
    public String link;
    @Element(name = "newstype", required = false)
    public String newstype;
    @Element(name = "opentype", required = false)
    public String opentype;
    @Element(name = "device", required = false)
    public String device;
    public NewestItem() {
    }
    //==========banner special==============


}
