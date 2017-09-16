package com.danmo.ithouse.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.danmo.commonapi.bean.Newest;
import com.danmo.commonapi.bean.NewestItem;
import com.danmo.commonapi.bean.User;
import com.danmo.commonutil.TimeUtil;
import com.danmo.commonutil.UrlUtil;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.multitype.BaseViewProvider;
import com.danmo.ithouse.R;

/**
 * Created by danmo on 2017/9/16.
 */

public class NewestProvider extends BaseViewProvider<NewestItem> {
    public NewestProvider(@NonNull Context context) {
        super(context, R.layout.item_newest);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, NewestItem bean) {
//        holder.setText(R.id.time, TimeUtil.computePastTime(bean.postdate));
        holder.setText(R.id.title, bean.title);
        holder.setText(R.id.host_name, bean.commentcount);

        // 加载头像
        ImageView imageView = holder.get(R.id.avatar);
        String url = bean.image;
        Glide.with(mContext).load(url).diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView);

    }
}
