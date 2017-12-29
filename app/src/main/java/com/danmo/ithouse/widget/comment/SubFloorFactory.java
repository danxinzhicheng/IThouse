/**
 *
 */
package com.danmo.ithouse.widget.comment;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.danmo.commonapi.bean.community.CommentReplyContent;
import com.danmo.ithouse.R;
import com.danmo.ithouse.util.GlideRoundTransform;

import static com.danmo.commonapi.base.Constant.convertRealPicUrl;

/**
 * @author: Long
 * @Date: 2016/3/30 15:45
 * 用于根据不同类型产生正常评论View和隐藏楼层View，同时在产生过程中，完成了数据和View的适配
 */
public class SubFloorFactory {

    /**
     * 直接显示评论
     *
     * @param cmt
     * @param group
     * @return
     */
    public View buildSubFloor(int index, CommentReplyContent cmt, ViewGroup group) {
        LayoutInflater inflater = (LayoutInflater) group.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 加载布局文件

        View view = inflater.inflate(R.layout.item_comment_floor, null);
        if (index == 0) {
            view.findViewById(R.id.comment_floor_line).setVisibility(View.GONE);
        }
        RelativeLayout show = view.findViewById(R.id.show_sub_floor_content);
        RelativeLayout hide = view.findViewById(R.id.hide_sub_floor_content);
        show.setVisibility(View.VISIBLE);
        hide.setVisibility(View.GONE);

        ImageView imgAvatar = view.findViewById(R.id.item_comment_pic);
        TextView tvRank = view.findViewById(R.id.item_comment_rank);
        TextView tvName = view.findViewById(R.id.item_comment_name);
        TextView tvLz = view.findViewById(R.id.item_comment_lz);
        TextView tvDevice = view.findViewById(R.id.item_comment_device);
        TextView tvLnum = view.findViewById(R.id.item_comment_lnum);
        TextView tvTime = view.findViewById(R.id.item_comment_time);
        WebView tvContent = view.findViewById(R.id.item_comment_content);
        TextView tvZan = view.findViewById(R.id.item_comment_zan);
        TextView tvCai = view.findViewById(R.id.item_comment_cai);
        TextView tvReply = view.findViewById(R.id.item_comment_reply);

        String url = convertRealPicUrl(cmt.Ui);//解析头像url
        Glide.with(group.getContext())
                .load(url)
                .transform(new GlideRoundTransform(group.getContext(), 20))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imgAvatar);
        tvRank.setText("Lv." + cmt.rank);
        tvName.setText(cmt.N);
        if (!TextUtils.isEmpty(cmt.Ta)) {
            tvDevice.setText(cmt.Ta);
        } else {
            tvDevice.setVisibility(View.GONE);
        }
        tvLnum.setText(cmt.Sf + "#");
        tvTime.setText("2017-12-29 11:32:23");

        tvContent.setBackgroundColor(0);
        tvContent.loadDataWithBaseURL(null, cmt.C, "text/html", "utf-8", null);

        tvZan.setText(cmt.S);
        tvCai.setText(cmt.A);

        return view;
    }

    /**
     * 不显示评论，显示加载更多
     *
     * @param size
     * @param group
     * @return
     */
    public View buildSubHideFloor(int size, ViewGroup group) {
        LayoutInflater inflater = (LayoutInflater) group.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_comment_floor, null);
        RelativeLayout show = view.findViewById(R.id.show_sub_floor_content);
        RelativeLayout hide = view.findViewById(R.id.hide_sub_floor_content);
        show.setVisibility(View.GONE);
        hide.setVisibility(View.VISIBLE);
        TextView hide_text = view.findViewById(R.id.hide_text);
        hide_text.setText("展开所有(+" + size + "条）");
//        hide_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_comment_floor_down_arrow, 0, 0, 0);
        view.findViewById(R.id.hide_pb).setVisibility(View.GONE);
        return view;
    }
}
