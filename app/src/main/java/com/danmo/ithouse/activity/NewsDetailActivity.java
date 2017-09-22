package com.danmo.ithouse.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.danmo.ithouse.R;

/**
 * Created by user on 2017/9/22.
 */

public class NewsDetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.flexible_example_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onBackPressed();
            }
        });
        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.flexible_example_appbar);
        appbar.addOnOffsetChangedListener(this);
    }
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
    }

    public static void start(Context c) {
        c.startActivity(new Intent(c, NewsDetailActivity.class));
    }
}
