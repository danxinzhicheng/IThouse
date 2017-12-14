package com.danmo.commonapi.bean.newest;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)//xml的根节点是rss
public class NewestTopNode {
    @Element(name = "channel")
    public Newest newest;
}
