package com.danmo.ithouse.fragment.refresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.multitype.BaseViewProvider;
import com.danmo.ithouse.R;

/**
 * 必要的底部刷新footer绑定适配器
 */
public class FooterProvider extends BaseViewProvider<Footer> {
    private static final String FOOTER_LOADING = "正在加载中...";
    private static final String FOOTER_NORMAL = "已加载全部";
    private static final String FOOTER_ERROR_A = "加载失败";
    private static final String FOOTER_ERROR_B = "失败，点击重试";
    private TextView footer;
    private ProgressBar footerloading;

    public FooterProvider(@NonNull Context context) {
        super(context, R.layout.item_footer_refresh);
    }

    @Override
    public void onViewHolderIsCreated(RecyclerViewHolder holder) {
        footer = holder.get(R.id.footer);
        footerloading = holder.get(R.id.footer_loading);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, Footer bean) {
        needLoadMore(holder);
    }

    public void needLoadMore(RecyclerViewHolder holder) {
    }

    public void setFooterLoading() {
        if (null == footer) return;
        footer.setText(FOOTER_LOADING);
        footerloading.setVisibility(View.VISIBLE);

    }

    public void setFooterNormal() {
        if (null == footer) return;
        footer.setText(FOOTER_NORMAL);
        footerloading.setVisibility(View.GONE);
    }

    public void setFooterError(View.OnClickListener listener) {
        if (null == footer) return;
        if (null != listener) {
            footer.setText(FOOTER_ERROR_B);
            footerloading.setVisibility(View.GONE);
            footer.setOnClickListener(listener);
            return;
        }
        footer.setText(FOOTER_ERROR_A);
        footerloading.setVisibility(View.GONE);
    }
}
