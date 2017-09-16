package com.danmo.commonapi.bean;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by danmo on 2017/9/16.
 */
@Root(name = "channel", strict = false)
public class Newest {
    public Newest() {
    }

    @ElementList(inline = true, required = false,name = "item",entry = "item")
    public List<NewestItem> item;

    public List<NewestItem> getItem() {
        return item;
    }

    public void setItem(List<NewestItem> item) {
        this.item = item;
    }
}
