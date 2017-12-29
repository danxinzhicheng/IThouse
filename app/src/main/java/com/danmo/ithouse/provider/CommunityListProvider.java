package com.danmo.ithouse.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.danmo.commonapi.bean.community.CommunityListItem;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.multitype.BaseViewProvider;
import com.danmo.ithouse.R;
import com.danmo.ithouse.activity.CommunityCommentActivity;
import com.danmo.ithouse.util.GlideRoundTransform;

import java.util.HashMap;
import java.util.Map;

import static com.danmo.commonapi.base.Constant.convertRealPicUrl;

/**
 * 圈子列表数据绑定
 */

public class CommunityListProvider extends BaseViewProvider<CommunityListItem> {
    private Map<String, Integer> mMapClicked = new HashMap<String, Integer>();

    public CommunityListProvider(@NonNull Context context) {
        super(context, R.layout.item_community_list);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, final CommunityListItem item) {
        final int position = holder.getLayoutPosition();

        ImageView ivAvatar = holder.get(R.id.community_item_avatar);
        String url = convertRealPicUrl(item.uid);//解析头像url
        Glide.with(mContext)
                .load(url)
                .transform(new GlideRoundTransform(mContext, 20))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(ivAvatar);

        final TextView tvC = holder.get(R.id.community_item_c);
        final TextView tvT = holder.get(R.id.community_item_t);

        tvC.setText(item.c);
        tvT.setText(item.t);

        holder.setText(R.id.community_item_cn, item.cn);
//        holder.setText(R.id.community_item_pt,item.pt);
        holder.setText(R.id.community_item_rc, item.rc);
        holder.setText(R.id.community_item_rn, item.rn);
//        holder.setText(R.id.community_item_rt,item.rt);
        holder.setText(R.id.community_item_un, item.un);
        holder.setText(R.id.community_item_vc, item.vc);


        if (mMapClicked.containsKey(item.id)) {
            tvC.setTextColor(mContext.getResources().getColor(R.color.diy_gray2));
            tvT.setTextColor(mContext.getResources().getColor(R.color.diy_gray2));
        } else {
            tvC.setTextColor(mContext.getResources().getColor(R.color.diy_black));
            tvT.setTextColor(mContext.getResources().getColor(R.color.diy_black));
        }

        holder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommunityCommentActivity.start(mContext, item.id, item.c, item.t, item.vc, item.rc, item.uid, item.un);
                tvC.setTextColor(mContext.getResources().getColor(R.color.diy_gray2));
                tvT.setTextColor(mContext.getResources().getColor(R.color.diy_gray2));
                mMapClicked.put(item.id, position);
            }
        });
    }

}
