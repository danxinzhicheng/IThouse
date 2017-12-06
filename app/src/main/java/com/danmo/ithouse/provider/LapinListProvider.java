package com.danmo.ithouse.provider;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.bean.lapin.LapinItem;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.multitype.BaseViewProvider;
import com.danmo.ithouse.R;

/**
 * Created by user on 2017/10/17.
 */

public class LapinListProvider extends BaseViewProvider<LapinItem> {
    public LapinListProvider(@NonNull Context context) {
        super(context, R.layout.item_lapin_list);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, LapinItem bean) {
        if (bean == null) {
            return;
        }
        holder.setText(R.id.lapin_item_store, bean.OriginStoreName);
        holder.setText(R.id.lapin_item_title, bean.ProductName);

        TextView price = holder.get(R.id.lapin_item_price);
        holder.setText(R.id.lapin_item_price, "￥" + bean.RealPrice);
        price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        float f = (1 - Float.parseFloat(bean.DiscountRate)) * 10;
        float ff = (float) (Math.round(f * 10)) / 10;
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(ff).append("折").append(")");
        holder.setText(R.id.lapin_item_discount, sb.toString());

        holder.setText(R.id.lapin_promotion_price, bean.PromotionInfoPrice);
        holder.setText(R.id.lapin_item_quan_info, bean.QuanInfo);

        ImageView imageView = holder.get(R.id.lapin_item_pic);
        String url = Constant.LAPIN_PIC_URL + bean.Picture;
        Glide.with(mContext).load(url).diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView);

    }
}
