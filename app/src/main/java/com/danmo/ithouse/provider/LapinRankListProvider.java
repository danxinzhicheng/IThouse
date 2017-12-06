package com.danmo.ithouse.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.bean.lapin.LapinItem;
import com.danmo.commonapi.bean.lapin.RankBeanTmp;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.multitype.BaseViewProvider;
import com.danmo.commonutil.recyclerview.adapter.singletype.SingleTypeAdapter;
import com.danmo.ithouse.R;

/**
 * Created by user on 2017/10/17.
 */

public class LapinRankListProvider extends BaseViewProvider<RankBeanTmp> {

    public LapinRankListProvider(@NonNull Context context) {
        super(context, R.layout.item_lapin_rank);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, RankBeanTmp bean) {
        RecyclerView mRecyclerView = holder.get(R.id.recycler_view_rank);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);

        RankRecyclerAdapter adapter = new RankRecyclerAdapter(mContext, R.layout.item_lapin_rank_sub_item);
        adapter.addDatas(bean.getContent());
        mRecyclerView.setAdapter(adapter);

    }

    class RankRecyclerAdapter extends SingleTypeAdapter<LapinItem> {

        public RankRecyclerAdapter(@NonNull Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void convert(int position, RecyclerViewHolder holder, LapinItem bean) {
            if (bean == null) {
                return;
            }
            ImageView ivRankPic = holder.get(R.id.lapin_rank_pic);
            String url = Constant.LAPIN_PIC_URL + bean.Picture;
            Glide.with(mContext).load(url).diskCacheStrategy(DiskCacheStrategy.RESULT).into(ivRankPic);

            String ranknum = String.valueOf(holder.getLayoutPosition() + 1);
            holder.setText(R.id.lapin_rank_num, ranknum);

            holder.setText(R.id.lapin_rank_title, bean.ProductName);
            holder.setText(R.id.lapin_rank_promotion_price, bean.PromotionInfoPrice);
            holder.setText(R.id.lapin_rank_quan_info, bean.QuanInfo);

        }

    }

}
