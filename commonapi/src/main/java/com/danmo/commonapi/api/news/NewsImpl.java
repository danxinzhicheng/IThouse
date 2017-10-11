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
 * Last modified 2017-03-08 23:50:55
 *
 * GitHub:  https://github.com/GcsSloop
 * Website: http://www.gcssloop.com
 * Weibo:   http://weibo.com/GcsSloop
 */

package com.danmo.commonapi.api.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.danmo.commonapi.base.BaseImpl;
import com.danmo.commonapi.callback.BaseCallback;
import com.danmo.commonapi.event.GetNewsListEvent;
import com.danmo.commonutil.UUIDGenerator;


public class NewsImpl extends BaseImpl<NewsService> implements NewsAPI {

    public NewsImpl(@NonNull Context context,int parsePattern) {
        super(context,parsePattern);
    }

    /**
     * 获取 news 列表
     *
     * @param node_id 如果你需要只看某个节点的，请传此参数, 如果不传 则返回全部
     * @param offset  偏移数值，默认值 0
     * @param limit   数量极限，默认值 20，值范围 1..150
     * @see GetNewsListEvent
     */
    @Override
    public String getNewsList(@Nullable Integer node_id, @Nullable Integer offset, @Nullable Integer limit) {
        String uuid = UUIDGenerator.getUUID();
        mService.getNewsList(node_id, offset, limit).enqueue(new BaseCallback<>(new GetNewsListEvent(uuid)));
        return uuid;
    }


}
