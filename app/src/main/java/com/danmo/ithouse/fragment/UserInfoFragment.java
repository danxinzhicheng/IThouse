package com.danmo.ithouse.fragment;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.danmo.commonutil.BarUtils;
import com.danmo.ithouse.R;
import com.danmo.ithouse.base.BaseFragment;
import com.danmo.ithouse.base.ViewHolder;

/**
 * Created by user on 2017/9/13.
 */

public class UserInfoFragment extends BaseFragment {
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
