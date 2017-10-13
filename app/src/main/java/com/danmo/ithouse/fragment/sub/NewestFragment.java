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

    private int mTouchShop;//最小滑动距离
    protected float mFirstY;//触摸下去的位置
    protected float mCurrentY;//滑动时Y的位置
    protected int direction;//判断是否上滑或者下滑的标志
    protected boolean mShow;//判断是否执行了上滑动画

    @Override
    public void initData(HeaderFooterAdapter adapter) {
        setLoadMoreEnable(true);
        loadHeader();
        loadMore();
        //获得一个最小滑动距离
        mTouchShop = ViewConfiguration.get(mContext).getScaledTouchSlop();//系统级别的一个属性,判断用户的最小滑动距离的,可查看源码为16
        mRecyclerView.setOnTouchListener(onTouchListener);
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
        if(isFirst) {
            isFirst = false;
            return CommonApi.getSingleInstance().getNewestList(Constant.NESLIST_URL);
        }else{
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

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mFirstY = event.getY();//按下时获取位置
                    break;
                case MotionEvent.ACTION_MOVE:
//                    EventBus.getDefault().post(new EventBusMsg("move"));
                    mCurrentY = event.getY();//得到滑动的位置
                    if(mCurrentY - mFirstY > mTouchShop) {//滑动的位置减去按下的位置大于最小滑动距离  则表示向下滑动
                        direction = 0;//down
                    }else if(mFirstY - mCurrentY > mTouchShop){//反之向上滑动
                        direction = 1;//up
                    }
                    if(direction == 1){//判断如果是向上滑动 则执行向上滑动的动画
                        if(mShow){//判断动画是否执行了  执行了则改变状态
                            //执行往上滑动的动画
//                            tolbarAnim(1);
                            EventBus.getDefault().post(new EventBusMsg(direction));
                            mShow = !mShow;
                        }
                    }else if(direction == 0){//判断如果是向下滑动 则执行向下滑动的动画
                        if(!mShow){//判断动画是否执行了  执行了则改变状态
                            //执行往下滑动的动画
//                            tolbarAnim(0);
                            EventBus.getDefault().post(new EventBusMsg(direction));
                            mShow = !mShow;
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            return false;
        }
    };


}
