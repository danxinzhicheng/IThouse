package com.danmo.ithouse.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.bean.hotgoods.HotGoodsItem;
import com.danmo.commonapi.bean.hotgoods.RankBeanTmp;
import com.danmo.commonutil.UrlUtil;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.multitype.BaseViewProvider;
import com.danmo.commonutil.recyclerview.adapter.singletype.SingleTypeAdapter;
import com.danmo.ithouse.R;
import com.danmo.ithouse.activity.WebViewActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * 辣品中间排行榜数据绑定
 */

public class HotGoodsRankListProvider extends BaseViewProvider<RankBeanTmp> {

    private Map<String, Integer> mapClicked = new HashMap<String, Integer>();

    public HotGoodsRankListProvider(@NonNull Context context) {
        super(context, R.layout.item_hotgoods_rank);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, RankBeanTmp bean) {
        RecyclerView mRecyclerView = holder.get(R.id.recycler_view_rank);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);

        RankRecyclerAdapter adapter = new RankRecyclerAdapter(mContext, R.layout.item_hotgoods_rank_sub_item);
        adapter.addDatas(bean.getContent());
        mRecyclerView.setAdapter(adapter);

    }

    class RankRecyclerAdapter extends SingleTypeAdapter<HotGoodsItem> {

        public RankRecyclerAdapter(@NonNull Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void convert(final int position, RecyclerViewHolder holder, final HotGoodsItem bean) {
            if (bean == null) {
                return;
            }

            ImageView ivRankPic = holder.get(R.id.hotgoods_rank_pic);
            String url = Constant.LAPIN_PIC_URL + bean.Picture;
            Glide.with(mContext).load(url).diskCacheStrategy(DiskCacheStrategy.RESULT).into(ivRankPic);

            String ranknum = String.valueOf(holder.getLayoutPosition() + 1);
            holder.setText(R.id.hotgoods_rank_num, ranknum);

            holder.setText(R.id.hotgoods_rank_title, bean.ProductName);
            holder.setText(R.id.hotgoods_rank_promotion_price, bean.PromotionInfoPrice);
            holder.setText(R.id.hotgoods_rank_quan_info, bean.QuanInfo);

            final TextView title = holder.get(R.id.hotgoods_rank_title);
            if (mapClicked.containsKey(bean.productid)) {
                title.setTextColor(mContext.getResources().getColor(R.color.diy_gray2));
            } else {
                title.setTextColor(mContext.getResources().getColor(R.color.diy_black));
            }
            holder.getRootView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = bean.BuyUrl;
                    if (UrlUtil.isUrlPrefix(link)) {
                        WebViewActivity.start(mContext, link, WebViewActivity.PAGE_TYPE_HOTGOODS);
                        title.setTextColor(mContext.getResources().getColor(R.color.diy_gray2));
                        mapClicked.put(bean.productid, position);
                    }
                }
            });
            holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = bean.QuanUrl;
                    if (UrlUtil.isUrlPrefix(link)) {
                        WebViewActivity.start(mContext, link, WebViewActivity.PAGE_TYPE_HOTGOODS);
                    }
                }
            },R.id.hotgoods_rank_quan_info);

        }

    }

}
