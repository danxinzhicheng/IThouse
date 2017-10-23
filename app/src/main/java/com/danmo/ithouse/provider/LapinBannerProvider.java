package com.danmo.ithouse.provider;

import android.content.Context;
import android.support.annotation.NonNull;

import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.bean.lapin.LapinItem;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.multitype.BaseViewProvider;
import com.danmo.ithouse.R;
import com.danmo.ithouse.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/10/17.
 */

public class LapinBannerProvider extends BaseViewProvider<List<LapinItem>> {
    public LapinBannerProvider(@NonNull Context context) {
        super(context, R.layout.item_lapin_banner);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, List<LapinItem> bean) {

        if(bean == null){
            return;
        }
        List<String> images = new ArrayList<String>();
        for (LapinItem item : bean) {
            images.add(Constant.LAPIN_PIC_URL + item.Picture);
        }
        Banner banner = (Banner) holder.get(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.setDelayTime(4 * 1000);
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (position >= 0) {
                }
            }
        });

    }
}
