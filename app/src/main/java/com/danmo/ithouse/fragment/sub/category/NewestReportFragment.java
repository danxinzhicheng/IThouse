package com.danmo.ithouse.fragment.sub.category;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.danmo.commonapi.CommonApi;
import com.danmo.commonapi.bean.community.CommunityListItem;
import com.danmo.commonapi.event.community.GetCommunityListEvent;
import com.danmo.commonutil.recyclerview.adapter.multitype.HeaderFooterAdapter;
import com.danmo.commonutil.recyclerview.layoutmanager.SpeedyLinearLayoutManager;
import com.danmo.ithouse.activity.CommunityCategoryListActivity;
import com.danmo.ithouse.fragment.refresh.RefreshRecyclerFragment;
import com.danmo.ithouse.provider.CommunityListProvider;

import java.util.List;

/**
 * Created by user on 2017/12/26.
 */

public class NewestReportFragment extends RefreshRecyclerFragment<List<CommunityListItem>, GetCommunityListEvent> {
    private String type;
    private String cid;

    @Override
    public void initData(HeaderFooterAdapter adapter) {

        cid = ((CommunityCategoryListActivity) getActivity()).getCategoryId();
        type = CommunityCategoryListActivity.TYPE_REPORT;
        setLoadMoreEnable(true);
    }

    @Override
    protected void setAdapterRegister(Context context, RecyclerView recyclerView, HeaderFooterAdapter adapter) {
        adapter.register(CommunityListItem.class, new CommunityListProvider(getContext()));
    }

    @NonNull
    @Override
    protected RecyclerView.LayoutManager getRecyclerViewLayoutManager() {
        return new SpeedyLinearLayoutManager(getContext());
    }

    @NonNull
    @Override
    protected String request(int offset, int limit) {
        return CommonApi.getSingleInstance().getCommunityList(cid, type, "", "", "");
    }


    @NonNull
    @Override
    protected String requestHeader() {
        return null;
    }

    @NonNull
    @Override
    protected String requestMiddle() {
        return null;
    }

    @Override
    protected void onRefresh(GetCommunityListEvent event, HeaderFooterAdapter adapter) {
        adapter.clearDatas();
        adapter.addDatas(event.getBean());
    }

    @Override
    protected void onLoadMore(GetCommunityListEvent event, HeaderFooterAdapter adapter) {
        adapter.addDatas(event.getBean());
    }

    @Override
    protected void onLoadHeader(GetCommunityListEvent event, HeaderFooterAdapter adapter) {

    }

    @Override
    protected void onLoadMiddle(GetCommunityListEvent event, HeaderFooterAdapter adapter) {

    }

    @Override
    protected void onError(GetCommunityListEvent event, String postType) {
        if (postType.equals(POST_LOAD_MORE)) {
            toast("加载更多失败");
        } else if (postType.equals(POST_REFRESH)) {
            toast("刷新数据失败");
        }
    }
}
