package com.danmo.ithouse.fragment.sub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.danmo.commonapi.CommonApi;
import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.bean.newest.NewestItem;
import com.danmo.commonapi.bean.newest.NewestTopNode;
import com.danmo.commonapi.event.GetNewestBannerEvent;
import com.danmo.commonapi.event.GetNewestEvent;
import com.danmo.commonutil.recyclerview.adapter.multitype.HeaderFooterAdapter;
import com.danmo.commonutil.recyclerview.layoutmanager.SpeedyLinearLayoutManager;
import com.danmo.ithouse.fragment.refresh.RefreshRecyclerFragment;
import com.danmo.ithouse.provider.NewestBannerProvider;
import com.danmo.ithouse.provider.NewestProvider;
import com.danmo.ithouse.util.EventBusMsg;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by danmo on 2017/9/16.
 */

public class NewestFragment extends RefreshRecyclerFragment<NewestTopNode, GetNewestEvent> {

    private Boolean isFirst = true;

    private Boolean isRequestShowed = true;

    public static int SCROLL_STATE_DOWN = 0;
    public static int SCROLL_STATE_UP = 1;



    @Override
    public void initData(HeaderFooterAdapter adapter) {
        setLoadMoreEnable(true);
        loadHeader();
        loadMore();
        mRecyclerView.setOnScrollListener(mOnScrollListener);
    }


    @Override
    protected void setAdapterRegister(Context context, RecyclerView recyclerView, HeaderFooterAdapter adapter) {
        adapter.register(NewestItem.class, new NewestProvider(getContext()));
    }

    @NonNull
    @Override
    protected RecyclerView.LayoutManager getRecyclerViewLayoutManager() {
        return new SpeedyLinearLayoutManager(getContext());
    }

    @NonNull
    @Override
    protected String request(int offset, int limit) {
        if (isFirst) {
            isFirst = false;
            return CommonApi.getSingleInstance().getNewestList(Constant.NESLIST_URL);
        } else {
            return CommonApi.getSingleInstance().getNewestList(Constant.NESLIST_URL);
        }
    }

    @NonNull
    @Override
    protected String requestHeader() {
        return CommonApi.getSingleInstance().getNewestBannerList(Constant.BANNER_URL);
    }

    @Override
    protected void onLoadHeader(GetNewestBannerEvent event, HeaderFooterAdapter mAdapter) {
        super.onLoadHeader(event, mAdapter);
        if (event.getBean().newest != null && event.getBean().newest.item.size() > 0) {
            mAdapter.registerHeader(event.getBean().newest, new NewestBannerProvider(mContext));//添加幻灯片
        }
    }

    @Override
    protected void onRefresh(GetNewestEvent event, HeaderFooterAdapter adapter) {
        adapter.clearDatas();
        adapter.addDatas((event.getBean()).newest.item);
    }

    @Override
    protected void onLoadMore(GetNewestEvent event, HeaderFooterAdapter adapter) {
        adapter.addDatas(event.getBean().newest.item);
    }

    @Override
    protected void onError(GetNewestEvent event, String postType) {
        if (postType.equals(POST_LOAD_MORE)) {
            toast("加载更多失败");
        } else if (postType.equals(POST_REFRESH)) {
            toast("刷新数据失败");
        }
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int mScrollState = recyclerView.getScrollState();
            Log.i("nnn","mScrollState:"+mScrollState);
            Log.i("nnn","dy:"+dy);
            Log.i("nnn","isRequestShowed:"+isRequestShowed);
            if (mScrollState == RecyclerView.SCROLL_STATE_DRAGGING || mScrollState == RecyclerView.SCROLL_STATE_SETTLING) {
                if (dy > 0) {//up -> hide
                    if (isRequestShowed) {
                        EventBus.getDefault().post(new EventBusMsg(SCROLL_STATE_UP));
                        isRequestShowed = false;
                    }
                } else {//down -> show
                    if (!isRequestShowed) {
                        EventBus.getDefault().post(new EventBusMsg(SCROLL_STATE_DOWN));
                        isRequestShowed = true;
                    }
                }
            }
        }
    };
}
