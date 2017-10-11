package com.danmo.ithouse.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.danmo.commonapi.bean.newest.NewestItem;
import com.danmo.commonutil.TimeUtil;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.multitype.BaseViewProvider;
import com.danmo.ithouse.R;
import com.danmo.ithouse.activity.NewsDetailActivity;

import java.text.ParseException;

/**
 * Created by danmo on 2017/9/16.
 */

public class NewestProvider extends BaseViewProvider<NewestItem> {
    public NewestProvider(@NonNull Context context) {
        super(context, R.layout.item_fragment_newest);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, final NewestItem bean) {
        holder.setText(R.id.item_title, bean.title);
        holder.setText(R.id.item_commentcount, bean.commentcount + mContext.getString(R.string.comment_text));

        //todo 时间判断待fix
        try {
            if (TimeUtil.IsToday(bean.postdate)) {
                String[] ss = bean.postdate.split("\\s+");
                String str = ss[1];
                holder.setText(R.id.item_postdate, str);
            } else if (TimeUtil.IsYesterday(bean.postdate)) {
                String[] ss = bean.postdate.split("\\s+");
                String str = ss[1];
                holder.setText(R.id.item_postdate, mContext.getString(R.string.yesterday) + str);
            } else {
                String[] ss = bean.postdate.split("\\s+");
                String str = ss[1];
                holder.setText(R.id.item_postdate, str);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 加载头像
        ImageView imageView = holder.get(R.id.item_image);
        String url = bean.image;
        Glide.with(mContext).load(url).diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView);

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("mmm","onClick");
                NewsDetailActivity.start(mContext,bean.postdate,bean.title);
            }
        },R.id.item_container);

    }
}
