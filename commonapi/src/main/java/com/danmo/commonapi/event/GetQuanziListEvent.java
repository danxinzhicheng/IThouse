package com.danmo.commonapi.event;

import android.support.annotation.Nullable;
import com.danmo.commonapi.base.BaseEvent;
import com.danmo.commonapi.bean.quanzi.QuanziListItem;

import java.util.List;

/**
 * Created by danmo on 2017/9/16.
 */

public class GetQuanziListEvent extends BaseEvent<List<QuanziListItem>> {
    public GetQuanziListEvent(@Nullable String uuid) {
        super(uuid);
    }
}
