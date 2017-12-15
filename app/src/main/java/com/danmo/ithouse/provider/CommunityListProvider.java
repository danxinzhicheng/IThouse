package com.danmo.ithouse.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.bean.community.CommunityListItem;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.multitype.BaseViewProvider;
import com.danmo.ithouse.R;
import com.danmo.ithouse.util.GlideRoundTransform;

/**
 * 圈子列表数据绑定
 */

public class CommunityListProvider extends BaseViewProvider<CommunityListItem> {
    public CommunityListProvider(@NonNull Context context) {
        super(context, R.layout.item_community_list);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, CommunityListItem item) {
        ImageView ivAvatar = holder.get(R.id.community_item_avatar);
        String url = convertRealPicUrl(item.uid);//解析头像url
        Glide.with(mContext)
                .load(url)
                .transform(new GlideRoundTransform(mContext, 20))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(ivAvatar);

        holder.setText(R.id.community_item_c, item.c);
        holder.setText(R.id.community_item_cn, item.cn);
//        holder.setText(R.id.community_item_pt,item.pt);
        holder.setText(R.id.community_item_rc, item.rc);
        holder.setText(R.id.community_item_rn, item.rn);
//        holder.setText(R.id.community_item_rt,item.rt);
        holder.setText(R.id.community_item_un, item.un);
        holder.setText(R.id.community_item_t, item.t);
        holder.setText(R.id.community_item_vc, item.vc);
    }

    public String convertRealPicUrl(String uid) {
        if (TextUtils.isEmpty(uid)) {
            return Constant.QUANZI_PIC_URL;
        }
        if (uid.length() == 1) {
            uid = "1347938";
        }
        if (uid.length() == 7) {
            String url = Constant.QUANZI_PIC_URL + "/" + "001" + "/" + uid.substring(1, 3) + "/" + uid.substring(3, 5) + "/" + uid.substring(5, 7) + "_60.jpg";
            return url;
        }
        if (uid.length() == 6) {
            String url = Constant.QUANZI_PIC_URL + "/" + "000" + "/" + uid.substring(0, 2) + "/" + uid.substring(2, 4) + "/" + uid.substring(4, 6) + "_60.jpg";
            return url;
        }
        return Constant.QUANZI_PIC_URL;
    }
}
