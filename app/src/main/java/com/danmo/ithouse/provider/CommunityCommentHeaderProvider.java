package com.danmo.ithouse.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.danmo.commonapi.bean.community.CommentHeader;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.multitype.BaseViewProvider;
import com.danmo.ithouse.R;
import com.danmo.ithouse.util.GlideRoundTransform;

import static com.danmo.commonapi.base.Constant.convertRealPicUrl;

/**
 * Created by user on 2017/12/27.
 */

public class CommunityCommentHeaderProvider extends BaseViewProvider<CommentHeader> {

    public CommunityCommentHeaderProvider(@NonNull Context context) {
        super(context, R.layout.community_comment_header);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, CommentHeader bean) {
        holder.setText(R.id.community_comment_title, bean.type + bean.title);
        holder.setText(R.id.community_comment_view_num, bean.viewNum);
        holder.setText(R.id.community_comment_reply_num, bean.commentNum);

        View view = holder.get(R.id.item_comment_zan_cai);
        view.setVisibility(View.GONE);

        TextView tvRank = holder.get(R.id.item_comment_rank);
        tvRank.setText("Lv." + bean.lz_rank);

        TextView tvName = holder.get(R.id.item_comment_name);
        tvName.setText(bean.commentLzName);

        TextView tvDevice = holder.get(R.id.item_comment_device);
        if (!TextUtils.isEmpty(bean.lz_Ta)) {
            tvDevice.setText(bean.lz_Ta);
        } else {
            tvDevice.setVisibility(View.GONE);
        }

        TextView tvLouNum = holder.get(R.id.item_comment_lnum);
        tvLouNum.setText("1楼");

        TextView tvTime = holder.get(R.id.item_comment_time);
        tvTime.setText("2017-12-27 02:32:12");

        WebView tvContent = holder.get(R.id.item_comment_content);
        tvContent.loadDataWithBaseURL(null, bean.lz_content, "text/html", "utf-8", null);

        ImageView ivAvater = holder.get(R.id.item_comment_pic);
        String url = convertRealPicUrl(bean.commentUid);//解析头像url
        Glide.with(mContext)
                .load(url)
                .transform(new GlideRoundTransform(mContext, 20))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(ivAvater);

    }


}
