package com.danmo.ithouse.fragment.sub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.danmo.commonapi.CommonApi;
import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.bean.hotgoods.HotGoodsItem;
import com.danmo.commonapi.bean.hotgoods.HotGoodsTopNode;
import com.danmo.commonapi.bean.hotgoods.RankBeanTmp;
import com.danmo.commonapi.event.GetHotGoodsListEvent;
import com.danmo.commonutil.recyclerview.adapter.multitype.HeaderFooterAdapter;
import com.danmo.commonutil.recyclerview.layoutmanager.SpeedyLinearLayoutManager;
import com.danmo.ithouse.fragment.refresh.RefreshRecyclerFragment;
import com.danmo.ithouse.provider.HotGoodsBannerProvider;
import com.danmo.ithouse.provider.HotGoodsListProvider;
import com.danmo.ithouse.provider.HotGoodsRankListProvider;

/**
 * 辣品viewpager的填充首页面：全部
 */
public class HotGoodsAllFragment extends RefreshRecyclerFragment<HotGoodsTopNode, GetHotGoodsListEvent> {
    @Override
    public void initData(HeaderFooterAdapter adapter) {
        loadHeader();
        loadMiddle();
        setLoadMoreEnable(true);
    }

    @Override
    protected void setAdapterRegister(Context context, RecyclerView recyclerView, HeaderFooterAdapter adapter) {
        adapter.register(HotGoodsItem.class, new HotGoodsListProvider(getContext()));
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
    protected void onRefresh(GetHotGoodsListEvent event, HeaderFooterAdapter adapter) {
        adapter.clearDatas();
        adapter.addDatas((event.getBean()).content);
    }

    @Override
    protected void onLoadMore(GetHotGoodsListEvent event, HeaderFooterAdapter adapter) {
        adapter.addDatas(event.getBean().content);
    }

    @Override
    protected void onLoadHeader(GetHotGoodsListEvent event, HeaderFooterAdapter adapter) {
        if (event.getBean() != null && event.getBean().content.size() > 0) {
            mAdapter.registerHeader(event.getBean().content, new HotGoodsBannerProvider(mContext));//添加幻灯片
        }
    }

    @Override
    protected void onLoadMiddle(GetHotGoodsListEvent event, HeaderFooterAdapter adapter) {
        if (event.getBean() != null && event.getBean().content.size() > 0) {
            RankBeanTmp tmp = new RankBeanTmp();
            tmp.setContent(event.getBean().content);
            mAdapter.registerMiddle(tmp, new HotGoodsRankListProvider(mContext));
        }
    }

    @Override
    protected void onError(GetHotGoodsListEvent event, String postType) {
        if (postType.equals(POST_LOAD_MORE)) {
            toast("加载更多失败");
        } else if (postType.equals(POST_REFRESH)) {
            toast("刷新数据失败");
        }
    }


}
