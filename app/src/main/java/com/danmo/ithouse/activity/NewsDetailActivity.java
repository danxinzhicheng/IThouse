package com.danmo.ithouse.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danmo.commonutil.recyclerview.adapter.multitype.HeaderFooterAdapter;
import com.danmo.commonutil.recyclerview.layoutmanager.SpeedyLinearLayoutManager;
import com.danmo.ithouse.R;
import com.danmo.ithouse.bean.RecommentActical;
import com.danmo.ithouse.bean.RelatedActical;
import com.danmo.ithouse.provider.RecommentActicalProvider;
import com.danmo.ithouse.provider.RelatedActicalProvider;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/9/22.
 */

public class NewsDetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final int PERCENTAGE_TO_SHOW_IMAGE = 10;
    private int mMaxScrollSize = 0;
    private boolean mIsImageHidden;
    private TextView tvTime, tvAuthor;
    private ViewGroup mTopSub;
    // 适配器
    protected HeaderFooterAdapter mAdapter;
    protected RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail_layout);
//        Slidr.attach(this);
        mTopSub = (ViewGroup) findViewById(R.id.detail_top_sub);
        tvTime = (TextView) findViewById(R.id.detail_time);
        tvAuthor = (TextView) findViewById(R.id.detail_author);
        Toolbar toolbar = (Toolbar) findViewById(R.id.flexible_example_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.appbar);
        appbar.addOnOffsetChangedListener(this);
        setSupportActionBar(toolbar);

        mAdapter = new HeaderFooterAdapter();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new SpeedyLinearLayoutManager(this));

        mAdapter.register(RelatedActical.class, new RelatedActicalProvider(this));
        mAdapter.register(RecommentActical.class, new RecommentActicalProvider(this));

        List<Serializable> list = new ArrayList<>();
        RelatedActical ss1 = new RelatedActical();
        ss1.category = "相关文章";
        ss1.time = "2018.09.21";
        ss1.title = "质朴11111生活，恒源祥泰国天然乳胶保健护颈枕枕芯2只69元";
        list.add(ss1);

        RelatedActical ss2 = new RelatedActical();
        ss2.category = "相关文章";
        ss2.time = "2014.09.21";
        ss2.title = "质朴222222生活，恒源祥泰国天然乳胶保健护颈枕枕芯2只69元";
        list.add(ss2);

        RelatedActical ss3 = new RelatedActical();
        ss3.category = "相关文章";
        ss3.time = "2013.09.21";
        ss3.title = "质朴3333333生活，恒源祥泰国天然乳胶保健护颈枕枕芯2只69元";
        list.add(ss3);

        RecommentActical bean1 = new RecommentActical();
        bean1.category = "大家都在看";
        bean1.coupon = "300元券";
        bean1.image = "http://img.ithome.com/newsuploadfiles/thumbnail/2017/9/327758.jpg";
        bean1.title = "在11111科隆游戏展上，《城市：天际线》的开发商Paradox展";
        bean1.time = "天猫|9-23";
        list.add(bean1);

        RecommentActical bean2 = new RecommentActical();
        bean2.category = "大家都在看";
        bean2.coupon = "200元券";
        bean2.image = "http://img.ithome.com/newsuploadfiles/thumbnail/2017/9/327758.jpg";
        bean2.title = "在22222科隆游戏展上，《城市：天际线》的开发商Paradox展示了《城市：天";
        bean2.time = "天猫|9-23";
        list.add(bean2);

        RecommentActical bean3 = new RecommentActical();
        bean3.category = "大家都在看";
        bean3.coupon = "100元券";
        bean3.image = "http://img.ithome.com/newsuploadfiles/thumbnail/2017/9/327758.jpg";
        bean3.title = "在33332科隆游戏展上，《城市：天际线》的开发商";
        bean3.time = "天猫|9-23";
        list.add(bean3);

        mAdapter.clearDatas();
        mAdapter.addDatas(list);

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int currentScrollPercentage = (Math.abs(i)) * 100
                / mMaxScrollSize;

        if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
            if (!mIsImageHidden) {
                mIsImageHidden = true;
                ViewCompat.animate(mTopSub).alpha(0).start();
//                ViewCompat.animate(mTopSub).scaleY(0).scaleX(0).start();
            }
        }

        if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
            if (mIsImageHidden) {
                mIsImageHidden = false;
                ViewCompat.animate(mTopSub).alpha(1).start();
//                ViewCompat.animate(mTopSub).scaleY(1).scaleX(1).start();
            }
        }
    }

    public static void start(Context c) {
        c.startActivity(new Intent(c, NewsDetailActivity.class));
    }

    //解决菜单icon不显示
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
