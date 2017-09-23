package com.danmo.ithouse.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danmo.ithouse.R;

import java.lang.reflect.Method;

/**
 * Created by user on 2017/9/22.
 */

public class NewsDetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final int PERCENTAGE_TO_SHOW_IMAGE = 10;
    private int mMaxScrollSize = 0;
    private boolean mIsImageHidden;
    private TextView tvTime,tvAuthor;
    private ViewGroup mTopSub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail_layout);
        mTopSub = (ViewGroup) findViewById(R.id.detail_top_sub);
        tvTime = (TextView) findViewById(R.id.detail_time);
        tvAuthor = (TextView) findViewById(R.id.detail_author);
        Toolbar toolbar = (Toolbar) findViewById(R.id.flexible_example_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onBackPressed();
            }
        });
        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.appbar);
        appbar.addOnOffsetChangedListener(this);
        setSupportActionBar(toolbar);
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
