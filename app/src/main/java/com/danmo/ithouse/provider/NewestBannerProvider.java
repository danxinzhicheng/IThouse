package com.danmo.ithouse.provider;

import android.content.Context;
import android.support.annotation.NonNull;

import com.danmo.commonapi.bean.newest.Newest;
import com.danmo.commonutil.UrlUtil;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.multitype.BaseViewProvider;
import com.danmo.ithouse.R;
import com.danmo.ithouse.activity.NewsDetailActivity;
import com.danmo.ithouse.activity.WebViewActivity;
import com.danmo.ithouse.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 资讯banner数据绑定
 */

public class NewestBannerProvider extends BaseViewProvider<Newest> {


    public NewestBannerProvider(@NonNull Context context) {
        super(context, R.layout.item_fragment_newest_banner);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, final Newest bean) {
        List<String> images = new ArrayList<String>();
        List<String> titles = new ArrayList<String>();
        for (int i = 0; i < bean.item.size(); i++) {
            titles.add(bean.item.get(i).title);
            images.add(bean.item.get(i).image);
        }
        Banner banner = (Banner) holder.get(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
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
                    String link = bean.item.get(position).link;
                    if (UrlUtil.isUrlPrefix(link)) {
                        WebViewActivity.start(mContext, link);
                    } else {
                        NewsDetailActivity.start(mContext, NewsDetailActivity.TYPE_BANNER, link);
                    }
                }
            }
        });

    }
}
