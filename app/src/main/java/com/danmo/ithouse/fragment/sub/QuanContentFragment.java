package com.danmo.ithouse.fragment.sub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.danmo.commonapi.CommonApi;
import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.bean.quanzi.QuanziListItem;
import com.danmo.commonapi.event.GetQuanziListEvent;
import com.danmo.commonutil.recyclerview.adapter.multitype.HeaderFooterAdapter;
import com.danmo.commonutil.recyclerview.layoutmanager.SpeedyLinearLayoutManager;
import com.danmo.ithouse.fragment.refresh.RefreshRecyclerFragment;
import com.danmo.ithouse.provider.QuanziCategoryProvider;
import com.danmo.ithouse.provider.QuanziListProvider;
import com.danmo.ithouse.util.EventBusMsg;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by user on 2017/12/6.
 */

public class QuanContentFragment extends RefreshRecyclerFragment<List<QuanziListItem>, GetQuanziListEvent> {

    private String currentRefeshUrl;

    @Override
    public void initData(HeaderFooterAdapter adapter) {
        loadHeader();
        setLoadMoreEnable(true);
    }

    @Override
    protected void setAdapterRegister(Context context, RecyclerView recyclerView, HeaderFooterAdapter adapter) {
        adapter.register(QuanziListItem.class, new QuanziListProvider(getContext()));
    }

    @NonNull
    @Override
    protected RecyclerView.LayoutManager getRecyclerViewLayoutManager() {
        return new SpeedyLinearLayoutManager(getContext());
    }

    @NonNull
    @Override
    protected String request(int offset, int limit) {
        if (TextUtils.isEmpty(currentRefeshUrl)) {
            currentRefeshUrl = CommonApi.getSingleInstance().getQuanziListNewest(Constant.QUANZI_LIST_NEWEST);
        }
        return currentRefeshUrl;
    }

    @NonNull
    @Override
    protected String requestHeader() {
        return CommonApi.getSingleInstance().getQuanziCategory(Constant.QUANZI_CATEGORY);
    }

    @NonNull
    @Override
    protected String requestMiddle() {
        return null;
    }

    @Override
    protected void onRefresh(GetQuanziListEvent event, HeaderFooterAdapter adapter) {
        adapter.clearDatas();
        adapter.addDatas(event.getBean());
    }

    @Override
    protected void onLoadMore(GetQuanziListEvent event, HeaderFooterAdapter adapter) {
        adapter.addDatas(event.getBean());
    }

    @Override
    protected void onLoadHeader(GetQuanziListEvent event, HeaderFooterAdapter adapter) {
        if (event.getBean() != null && event.getBean().size() > 0) {
            mAdapter.unRegisterHeader();
            mAdapter.registerHeader(event.getBean(), new QuanziCategoryProvider(mContext));//添加分类
        }
    }

    @Override
    protected void onLoadMiddle(GetQuanziListEvent event, HeaderFooterAdapter adapter) {

    }

    @Override
    protected void onError(GetQuanziListEvent event, String postType) {
        if (postType.equals(POST_LOAD_MORE)) {
            toast("加载更多失败");
        } else if (postType.equals(POST_REFRESH)) {
            toast("刷新数据失败");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResultEvent(EventBusMsg event) {
        int flag = event.getQuanzi_fresh_new_or_hot();
        if (flag == 0) {
            currentRefeshUrl = CommonApi.getSingleInstance().getQuanziListNewest(Constant.QUANZI_LIST_NEWEST);
            refresh();
        } else {
            currentRefeshUrl = CommonApi.getSingleInstance().getQuanziListNewest(Constant.QUANZI_LIST_HOTEST);
            refresh();
        }
    }

}
