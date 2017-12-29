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
import com.danmo.commonapi.bean.community.CommentReply;
import com.danmo.commonapi.bean.community.CommentReplyContent;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.multitype.BaseViewProvider;
import com.danmo.ithouse.R;
import com.danmo.ithouse.util.GlideRoundTransform;
import com.danmo.ithouse.widget.comment.FloorView;
import com.danmo.ithouse.widget.comment.SubFloorFactory;

import java.util.List;

import static com.danmo.commonapi.base.Constant.convertRealPicUrl;

/**
 * Created by user on 2017/12/27.
 */

public class CommunityCommentProvider extends BaseViewProvider<CommentReply> {

    public CommunityCommentProvider(@NonNull Context context) {
        super(context, R.layout.community_comment_item);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, CommentReply bean) {
        int positon = holder.getLayoutPosition();
        final CommentReplyContent beanReply = bean.M;

        TextView tvRank = holder.get(R.id.item_comment_rank);
        tvRank.setText("Lv." + beanReply.rank);

        TextView tvName = holder.get(R.id.item_comment_name);
        tvName.setText(beanReply.N);

        TextView tvDevice = holder.get(R.id.item_comment_device);
        if (!TextUtils.isEmpty(beanReply.Ta)) {
            tvDevice.setText(beanReply.Ta);
        } else {
            tvDevice.setVisibility(View.GONE);
        }

        TextView tvLouNum = holder.get(R.id.item_comment_lnum);
        tvLouNum.setText(positon + 1 + "楼");

        TextView tvTime = holder.get(R.id.item_comment_time);
        tvTime.setText("2017-12-27 02:32:12");

        WebView tvContent = holder.get(R.id.item_comment_content);
        tvContent.loadDataWithBaseURL(null, beanReply.C, "text/html", "utf-8", null);

        TextView tvZan = holder.get(R.id.item_comment_zan);
        tvZan.setText(beanReply.S);

        TextView tvCai = holder.get(R.id.item_comment_cai);
        tvCai.setText(beanReply.A);

        ImageView ivAvater = holder.get(R.id.item_comment_pic);
        String url = convertRealPicUrl(beanReply.Ui);//解析头像url
        Glide.with(mContext)
                .load(url)
                .transform(new GlideRoundTransform(mContext, 20))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(ivAvater);

        setFloorComment(holder, bean.R);//设置回复楼层view data.

    }


    private void setFloorComment(RecyclerViewHolder holder, List<CommentReplyContent> bean) {

        if (bean == null || bean.size() <= 0) {
            return;
        }
        FloorView mFloorView = holder.get(R.id.comment_floors_parent);
        mFloorView.setVisibility(View.VISIBLE);
        mFloorView.setFloorComments(bean);
        mFloorView.setFactory(new SubFloorFactory());
        mFloorView.setBoundDrawer(mContext.getResources().getDrawable(R.drawable.bg_floor_comment));
        mFloorView.init();

    }

}
