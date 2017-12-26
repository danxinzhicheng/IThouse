package com.danmo.ithouse.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.danmo.ithouse.R;
import com.danmo.ithouse.base.BaseActivity;
import com.danmo.ithouse.base.BaseViewPagerAdapter;
import com.danmo.ithouse.base.ViewHolder;
import com.danmo.ithouse.fragment.sub.category.HotPostFragment;
import com.danmo.ithouse.fragment.sub.category.NewestReplyFragment;
import com.danmo.ithouse.fragment.sub.category.NewestReportFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/12/26.
 */

public class CommunityCategoryListActivity extends BaseActivity {

    public static final String TYPE_REPLYE = "0";
    public static final String TYPE_HOTPOST = "2";
    public static final String TYPE_REPORT = "1";

    public static final String INTENT_TITLE = "title";
    public static final String INTENT_CID = "cid";
    public static final String INTENT_TYPE = "type";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar mToolbar;

    private FloatingActionButton mFloatButton;
    private String cid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_community_category_list_layout;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        super.initViews(holder, root);
        mToolbar = findViewById(R.id.flexible_toolbar);
        mTabLayout = findViewById(R.id.category_tab);
        mViewPager = findViewById(R.id.category_viewpager);

        mFloatButton = findViewById(R.id.floatButton);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommunityCategoryListActivity.this.onBackPressed();
            }
        });

        String title = getIntent().getStringExtra(INTENT_TITLE);
        mToolbar.setTitle(title);

        cid = getIntent().getStringExtra(INTENT_CID);

        BaseViewPagerAdapter adapter = new BaseViewPagerAdapter(this, getSupportFragmentManager(), getPagers());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).select();
        mViewPager.setCurrentItem(0, true);
    }

    public String getCategoryId() {
        return cid;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
    }

    public static void start(Context context, String title, String cid) {
        Intent intent = new Intent(context, CommunityCategoryListActivity.class);
        intent.putExtra(INTENT_TITLE, title);
        intent.putExtra(INTENT_CID, cid);
        context.startActivity(intent);
    }

    private List<BaseViewPagerAdapter.PagerInfo> getPagers() {

        Bundle bundle = new Bundle();
        List<BaseViewPagerAdapter.PagerInfo> list = new ArrayList<>();
        list.add(new BaseViewPagerAdapter.PagerInfo("最新回复", NewestReplyFragment.class,
                bundle));
        list.add(new BaseViewPagerAdapter.PagerInfo("热帖", HotPostFragment.class,
                bundle));
        list.add(new BaseViewPagerAdapter.PagerInfo("最新发表", NewestReportFragment.class,
                bundle));
        return list;

    }


}
