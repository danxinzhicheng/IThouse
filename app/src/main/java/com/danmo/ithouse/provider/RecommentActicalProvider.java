package com.danmo.ithouse.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.bean.newest.detail.DetailRecommendItem;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.multitype.BaseViewProvider;
import com.danmo.ithouse.R;

/**
 * 内容详情页 大家都在买 数据绑定
 */

public class RecommentActicalProvider extends BaseViewProvider<DetailRecommendItem> {

    public RecommentActicalProvider(@NonNull Context context) {
        super(context, R.layout.item_detail_recommend_layout);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, DetailRecommendItem bean) {
        if (bean == null) return;

        if (holder.getLayoutPosition() == 3) {//这里必须是三个item
            holder.get(R.id.rl_detail_recomment_head).setVisibility(View.VISIBLE);
            holder.setText(R.id.detail_recomment_head, bean.category);
        } else {
            holder.get(R.id.rl_detail_recomment_head).setVisibility(View.GONE);
        }

        holder.setText(R.id.detail_recommend_item_coupon, bean.PromotionInfo);
        holder.setText(R.id.detail_recommend_item_title, bean.ProductName);
        holder.setText(R.id.detail_recommend_item_time, bean.CreateTime);
        ImageView icon = holder.get(R.id.detail_recomment_item_image);
        String url = bean.Picture;
        if (!url.startsWith("http")) {
            url = Constant.LAPIN_PIC_URL + url;
        }
        Glide.with(mContext).load(url).into(icon);


    }
}
