package com.danmo.ithouse.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.multitype.BaseViewProvider;
import com.danmo.ithouse.R;
import com.danmo.ithouse.bean.RecommentActical;

/**
 * Created by user on 2017/9/27.
 */

public class RecommentActicalProvider extends BaseViewProvider<RecommentActical> {

    public RecommentActicalProvider(@NonNull Context context) {
        super(context, R.layout.item_detail_recommend_layout);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, RecommentActical bean) {
        if (bean==null) return;

        if(holder.getLayoutPosition() == 3){//==todo
            holder.get(R.id.detail_recomment_head).setVisibility(View.VISIBLE);
            holder.setText(R.id.detail_recomment_head,bean.category);
        }else{
            holder.get(R.id.detail_recomment_head).setVisibility(View.GONE);
        }

        holder.setText(R.id.detail_recommend_item_coupon,bean.coupon);
        holder.setText(R.id.detail_recommend_item_title,bean.title);
        holder.setText(R.id.detail_recommend_item_time,bean.time);
        ImageView icon = holder.get(R.id.detail_recomment_item_image);
        Glide.with(mContext).load(bean.image).into(icon);


    }
}
