package com.danmo.ithouse.fragment.sub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.danmo.commonapi.CommonApi;
import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.bean.lapin.LapinItem;
import com.danmo.commonapi.bean.lapin.LapinTopNode;
import com.danmo.commonapi.bean.lapin.RankBeanTmp;
import com.danmo.commonapi.event.GetLapinListEvent;
import com.danmo.commonutil.recyclerview.adapter.multitype.HeaderFooterAdapter;
import com.danmo.commonutil.recyclerview.layoutmanager.SpeedyLinearLayoutManager;
import com.danmo.ithouse.fragment.refresh.RefreshRecyclerFragment;
import com.danmo.ithouse.provider.LapinBannerProvider;
import com.danmo.ithouse.provider.LapinListProvider;
import com.danmo.ithouse.provider.LapinRankListProvider;

/**
 * Created by user on 2017/10/16.
 */

public class LapinAllFragment extends RefreshRecyclerFragment<LapinTopNode, GetLapinListEvent> {
    @Override
    public void initData(HeaderFooterAdapter adapter) {
        loadHeader();
        loadMiddle();
        setLoadMoreEnable(true);
    }

    @Override
    protected void setAdapterRegister(Context context, RecyclerView recyclerView, HeaderFooterAdapter adapter) {
//        adapter.register(LapinItem.class, new LapinRankListProvider(getContext()));
        adapter.register(LapinItem.class, new LapinListProvider(getContext()));
    }

    @NonNull
    @Override
    protected RecyclerView.LayoutManager getRecyclerViewLayoutManager() {
        return new SpeedyLinearLayoutManager(getContext());
    }

    @NonNull
    @Override
    protected String request(int offset, int limit) {
        return CommonApi.getSingleInstance().getLapinList(Constant.LAPIN_LIST_URL);
    }

    @NonNull
    @Override
    protected String requestHeader() {
        return CommonApi.getSingleInstance().getLapinBannerList(Constant.LAPIN_BANNER_URL);
    }

    @NonNull
    @Override
    protected String requestMiddle() {
        return CommonApi.getSingleInstance().getLapinRankList(Constant.LAPIN_RANK_URL);
    }

    @Override
    protected void onRefresh(GetLapinListEvent event, HeaderFooterAdapter adapter) {
        adapter.clearDatas();
        adapter.addDatas((event.getBean()).content);
    }

    @Override
    protected void onLoadMore(GetLapinListEvent event, HeaderFooterAdapter adapter) {
        adapter.addDatas(event.getBean().content);
    }

    @Override
    protected void onLoadHeader(GetLapinListEvent event, HeaderFooterAdapter adapter) {
        if (event.getBean() != null && event.getBean().content.size() > 0) {
            mAdapter.registerHeader(event.getBean().content, new LapinBannerProvider(mContext));//添加幻灯片
        }
    }

    @Override
    protected void onLoadMiddle(GetLapinListEvent event, HeaderFooterAdapter adapter) {
        if (event.getBean() != null && event.getBean().content.size() > 0) {
            RankBeanTmp tmp = new RankBeanTmp();
            tmp.setContent(event.getBean().content);
            mAdapter.registerMiddle(tmp, new LapinRankListProvider(mContext));
        }
    }

    @Override
    protected void onError(GetLapinListEvent event, String postType) {

    }


}
