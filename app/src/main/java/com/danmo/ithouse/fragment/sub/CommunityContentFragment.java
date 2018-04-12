package com.danmo.ithouse.fragment.sub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.danmo.commonapi.CommonApi;
import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.bean.community.CommunityListItem;
import com.danmo.commonapi.event.community.GetCommunityListEvent;
import com.danmo.commonutil.recyclerview.adapter.multitype.HeaderFooterAdapter;
import com.danmo.commonutil.recyclerview.layoutmanager.SpeedyLinearLayoutManager;
import com.danmo.ithouse.fragment.refresh.RefreshRecyclerFragment;
import com.danmo.ithouse.provider.CommunityCategoryProvider;
import com.danmo.ithouse.provider.CommunityListProvider;
import com.danmo.ithouse.util.EventBusMsg;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 圈子的内容填充页面
 */

public class CommunityContentFragment extends RefreshRecyclerFragment<List<CommunityListItem>, GetCommunityListEvent> {

    private String mCurRefeshType = "0";
    private String mCurCategory = "0";

    @Override
    public void initData(HeaderFooterAdapter adapter) {
        loadHeader();
        setLoadMoreEnable(true);
    }

    @Override
    protected void setAdapterRegister(Context context, RecyclerView recyclerView, HeaderFooterAdapter adapter) {
        adapter.register(CommunityListItem.class, new CommunityListProvider(getContext()));
    }

    @Override
    protected RecyclerView.LayoutManager getRecyclerViewLayoutManager() {
        return new SpeedyLinearLayoutManager(getContext());
    }

    @Override
    protected String request(int offset, int limit) {
        return CommonApi.getSingleInstance().getCommunityList(mCurCategory, mCurRefeshType, "", "", "");
    }

    @Override
    protected String requestHeader() {
        return CommonApi.getSingleInstance().getCommunityCategory(Constant.QUANZI_CATEGORY);
    }

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
        if (event.getBean() != null && event.getBean().size() > 0) {
            adapter.registerHeader(event.getBean(), new CommunityCategoryProvider(mContext));//添加分类
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResultEvent(EventBusMsg event) {
        int flag = event.getCommunity_fresh_new_or_hot();
        if (flag == 0) {//最新
            mCurRefeshType = "0";
            refresh(POST_HEADER);
            refresh(POST_REFRESH);
        } else {//最热
            mCurRefeshType = "3";
            refresh(POST_HEADER);
            refresh(POST_REFRESH);
        }
    }

}
