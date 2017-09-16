package com.danmo.ithouse.fragment.sub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.danmo.commonapi.CommonApi;
import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.bean.New;
import com.danmo.commonapi.bean.Newest;
import com.danmo.commonapi.bean.NewestItem;
import com.danmo.commonapi.bean.NewestTopNode;
import com.danmo.commonapi.event.GetNewestEvent;
import com.danmo.commonutil.recyclerview.adapter.multitype.HeaderFooterAdapter;
import com.danmo.ithouse.fragment.refresh.SimpleRefreshRecyclerFragment;
import com.danmo.ithouse.provider.NewestProvider;
import com.danmo.ithouse.provider.NewsProvider;

/**
 * Created by danmo on 2017/9/16.
 */

public class NewestFragment extends SimpleRefreshRecyclerFragment<NewestTopNode, GetNewestEvent> {
    @Override
    public void initData(HeaderFooterAdapter adapter) {
        loadMore();
    }

    @Override
    protected void setAdapterRegister(Context context, RecyclerView recyclerView, HeaderFooterAdapter adapter) {
        adapter.register(NewestItem.class, new NewestProvider(getContext()));
    }

    @NonNull
    @Override
    protected String request(int offset, int limit) {
        return  CommonApi.getSingleInstance().getNewestList(Constant.NESLIST_URL);
    }
}
