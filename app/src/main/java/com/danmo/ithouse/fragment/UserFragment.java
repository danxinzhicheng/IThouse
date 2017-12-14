package com.danmo.ithouse.fragment;

import android.view.View;

import com.danmo.ithouse.R;
import com.danmo.ithouse.base.BaseFragment;
import com.danmo.ithouse.base.ViewHolder;

/**
 * 我
 */

public class UserFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_userinfo;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
//        Toolbar toolbar = holder.get(R.id.toolbar);
//        CollapsingToolbarLayout ctl = holder.get(R.id.ctl);
//        ctl.setExpandedTitleColor(Color.parseColor("#00FFFFFF"));
//        ((AppCompatActivity) mContext).setSupportActionBar(toolbar);
//        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
    }
}
