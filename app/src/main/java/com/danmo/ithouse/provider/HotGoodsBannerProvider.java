package com.danmo.ithouse.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.bean.hotgoods.HotGoodsItem;
import com.danmo.commonutil.UrlUtil;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.multitype.BaseViewProvider;
import com.danmo.ithouse.R;
import com.danmo.ithouse.activity.NewsDetailActivity;
import com.danmo.ithouse.activity.WebViewActivity;
import com.danmo.ithouse.util.GlideImageLoader;
import com.danmo.ithouse.util.GlideRoundTransform;
import com.danmo.ithouse.widget.banner.BannerPagerAdapter;
import com.danmo.ithouse.widget.banner.BannerWrapperView;
import com.danmo.ithouse.widget.banner.OnBannerItemClickListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 辣品banner 数据绑定
 */

public class HotGoodsBannerProvider extends BaseViewProvider<List<HotGoodsItem>> {
    public HotGoodsBannerProvider(@NonNull Context context) {
        super(context, R.layout.item_hotgoods_banner);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, final List<HotGoodsItem> bean) {
        if (bean == null) {
            return;
        }
        List<String> images = new ArrayList<String>();
        for (HotGoodsItem item : bean) {
            images.add(Constant.LAPIN_PIC_URL + item.Picture);
        }
        BannerWrapperView banner = holder.get(R.id.banner);
//        //设置banner样式
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
//        banner.setIndicatorGravity(BannerConfig.RIGHT);
//        //设置图片加载器
//        banner.setImageLoader(new GlideImageLoader());
//        //设置图片集合
//        banner.setImages(images);
//        //banner设置方法全部调用完毕时最后调用
//        banner.setDelayTime(4 * 1000);
//        banner.start();
//        banner.setOnBannerListener(new OnBannerListener() {
//            @Override
//            public void OnBannerClick(int position) {
//                if (position >= 0) {
//                    String link = bean.get(position).Url;
//                    if (UrlUtil.isUrlPrefix(link)) {
//                        WebViewActivity.start(mContext, link, WebViewActivity.PAGE_TYPE_HOTGOODS);
//                    }
//                }
//            }
//        });

        banner.setDataAdapter(images, new BannerViewAdapter());
        banner.setOnItemClickListener(new OnBannerItemClickListener() {
            @Override
            public void onBannerItemClick(Object data, int position) {
                if (position >= 0) {
                    String link = bean.get(position).Url;
                    if (UrlUtil.isUrlPrefix(link)) {
                        WebViewActivity.start(mContext, link, WebViewActivity.PAGE_TYPE_HOTGOODS);
                    }
                }
            }
        });
    }

    public class BannerViewAdapter extends BannerPagerAdapter<String> {

        @Override
        public BannerViewHolder createViewHolder(ViewGroup container) {
            return new BannerViewHolder();
        }

        public class BannerViewHolder implements BannerPagerAdapter.BaseBannerViewHolder<String> {

            private ImageView mImageView;

            @Override
            public View createView(Context context) {
                View view = LayoutInflater.from(context).inflate(R.layout.adapter_banner_item_view, null);

                mImageView = view.findViewById(R.id.ivBanner);
                return view;
            }

            @Override
            public void bind(Context context, String data, int position) {
                Glide.with(mContext)
                        .load(data)
                        .transform(new GlideRoundTransform(mContext, 2))
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into(mImageView);
            }
        }

    }
}
