package com.danmo.commonapi.event.community;

import android.support.annotation.Nullable;

import com.danmo.commonapi.base.BaseEvent;
import com.danmo.commonapi.bean.community.Comment;


public class GetCommunityCommentEvent extends BaseEvent<Comment> {
    public GetCommunityCommentEvent(@Nullable String uuid) {
        super(uuid);
    }
}
