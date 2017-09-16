package com.danmo.commonapi.bean;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by danmo on 2017/9/16.
 */
@Root(name = "rss", strict = false)
public class NewestTopNode {
    @Element(name = "channel")
    public Newest newest;
}
