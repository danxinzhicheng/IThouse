package com.danmo.ithouse.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.danmo.commonapi.bean.community.CommunityListItem;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.multitype.BaseViewProvider;
import com.danmo.commonutil.recyclerview.adapter.singletype.SingleTypeAdapter;
import com.danmo.ithouse.R;
import com.danmo.ithouse.activity.CommunityCategoryListActivity;
import com.danmo.ithouse.util.Config;
import com.danmo.ithouse.util.EventBusMsg;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子头部分类 数据绑定
 */

public class CommunityCategoryProvider extends BaseViewProvider<List<CommunityListItem>> {

    private EventBusMsg eventBusMsg = new EventBusMsg();

    public CommunityCategoryProvider(@NonNull Context context) {
        super(context, R.layout.item_community_category);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, List<CommunityListItem> bean) {

        RecyclerView mRecyclerView = holder.get(R.id.recycler_view_community_catetory);
//        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);

        CommunityCategoryRecyclerAdapter adapter = new CommunityCategoryRecyclerAdapter(mContext, R.layout.item_community_category_sub_item);

//      bean.remove(0);//原始第一个bean是全部分类，去除
        List<CommunityListItem> tmpItemList = new ArrayList<>();
        for (int i = 1; i < bean.size(); i++) {
            tmpItemList.add(bean.get(i));
        }
        adapter.addDatas(tmpItemList);
        mRecyclerView.setAdapter(adapter);

        RadioGroup group = holder.get(R.id.community_category_group);
        RadioButton btnNew = holder.get(R.id.community_category_newest);
        RadioButton btnHot = holder.get(R.id.community_category_hotest);
        group.setOnCheckedChangeListener(new RadioGroupListener());

    }


    class RadioGroupListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (checkedId == R.id.community_category_newest) {
                eventBusMsg.setCommunity_fresh_new_or_hot(0);
                EventBus.getDefault().post(eventBusMsg);
            } else if (checkedId == R.id.community_category_hotest) {
                eventBusMsg.setCommunity_fresh_new_or_hot(1);
                EventBus.getDefault().post(eventBusMsg);
            }
        }
    }

    class CommunityCategoryRecyclerAdapter extends SingleTypeAdapter<CommunityListItem> {

        String[] titles = Config.communityTitles;
        int[] icons = Config.communityIcons;

        public CommunityCategoryRecyclerAdapter(@NonNull Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void convert(final int position, RecyclerViewHolder holder, final CommunityListItem bean) {
            ImageView icon = holder.get(R.id.item_community_category_icon);
            icon.setImageResource(icons[position]);

            holder.setText(R.id.item_community_category_title, titles[position]);
            holder.setText(R.id.item_community_category_addnum, "+" + bean.c);

            holder.getRootView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommunityCategoryListActivity.start(mContext, titles[position], bean.id);
                }
            });

        }
    }


}
