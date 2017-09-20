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
 * Last modified 2017-04-09 05:15:40
 *
 * GitHub:  https://github.com/GcsSloop
 * Website: http://www.gcssloop.com
 * Weibo:   http://weibo.com/GcsSloop
 */

package com.danmo.ithouse.fragment.sub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.danmo.commonapi.CommonApi;
import com.danmo.commonapi.bean.New;
import com.danmo.commonapi.event.GetNewsListEvent;
import com.danmo.commonutil.recyclerview.adapter.multitype.HeaderFooterAdapter;
import com.danmo.ithouse.fragment.refresh.SimpleRefreshRecyclerFragment;
import com.danmo.ithouse.provider.NewsProvider;

import java.util.List;

/**
 * 首页 news 列表
 */
public class NewsListFragment extends SimpleRefreshRecyclerFragment<List<New>, GetNewsListEvent> {

    private boolean isFirstLaunch = true;

//    public static NewsListFragment newInstance() {
//        Bundle args = new Bundle();
//        NewsListFragment fragment = new NewsListFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void initData(HeaderFooterAdapter adapter) {
        // 优先从缓存中获取数据，如果是第一次加载则恢复滚动位置，如果没有缓存则从网络加载
//        List<Object> news = mDataCache.getNewsListObj();
//        if (null != news && news.size() > 0) {
//            Logger.e("news : " + news.size());
//            pageIndex = mConfig.getNewsListPageIndex();
//            adapter.addDatas(news);
//            if (isFirstLaunch) {
//                int lastPosition = mConfig.getNewsListLastPosition();
//                mRecyclerView.getLayoutManager().scrollToPosition(lastPosition);
//                isFirstAddFooter = false;
//                isFirstLaunch = false;
//            }
//        } else {
//            loadMore();
//        }

        loadMore();
    }

    @Override
    protected void setAdapterRegister(Context context, RecyclerView recyclerView,
                                      HeaderFooterAdapter adapter) {
        adapter.register(New.class, new NewsProvider(getContext()));
    }

    @NonNull
    @Override
    protected String request(int offset, int limit) {
        return CommonApi.getSingleInstance().getNewsList(null, offset, limit);
    }

    @NonNull
    @Override
    protected String requestHeader() {
        return null;
    }

    @Override
    protected void onRefresh(GetNewsListEvent event, HeaderFooterAdapter adapter) {
        super.onRefresh(event, adapter);
//        mDataCache.saveNewsListObj(adapter.getDatas());
    }

    @Override
    protected void onLoadMore(GetNewsListEvent event, HeaderFooterAdapter adapter) {
        super.onLoadMore(event, adapter);
//        mDataCache.saveNewsListObj(adapter.getDatas());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        // 存储 PageIndex
//        mConfig.saveNewsListPageIndex(pageIndex);
//        // 存储 RecyclerView 滚动位置
//        View view = mRecyclerView.getLayoutManager().getChildAt(0);
//        int lastPosition = mRecyclerView.getLayoutManager().getPosition(view);
//        mConfig.saveNewsListPosition(lastPosition);
    }
}