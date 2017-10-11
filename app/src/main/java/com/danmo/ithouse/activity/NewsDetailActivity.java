package com.danmo.ithouse.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.danmo.commonapi.CommonApi;
import com.danmo.commonapi.base.BaseEvent;
import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.bean.newest.NewestItem;
import com.danmo.commonapi.bean.newest.detail.DetailRecommendItem;
import com.danmo.commonapi.bean.newest.detail.DetailRelatedItem;
import com.danmo.commonapi.event.GetNewsDetailContentEvent;
import com.danmo.commonapi.event.GetNewsDetailRecommendEvent;
import com.danmo.commonapi.event.GetNewsDetailRelatedEvent;
import com.danmo.commonutil.recyclerview.adapter.multitype.HeaderFooterAdapter;
import com.danmo.commonutil.recyclerview.layoutmanager.SpeedyLinearLayoutManager;
import com.danmo.ithouse.R;
import com.danmo.ithouse.provider.RecommentActicalProvider;
import com.danmo.ithouse.provider.RelatedActicalProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private TextView tvTime, tvAuthor, tvZebian;
    private ViewGroup mTopSub;
    // 适配器
    protected HeaderFooterAdapter mAdapter;
    protected RecyclerView mRecyclerView;

    private ArrayMap<String, String> mPostTypes = new ArrayMap<>();    // 请求类型
    // 请求状态
    public static final String POST_DETAIL_CONTENT = "content";
    public static final String POST_DETAIL_RELATED = "related";
    public static final String POST_DETAIL_RECOMMEND = "recommend";

    public static final String INTENT_DETAIL_CONTENT_TITLE = "title";
    public static final String INTENT_DETAIL_CONTENT_TIME = "time";

    private CollapsingToolbarLayout detailTitle;
    private WebView wvDetailContent;
    private NestedScrollView nestedScrollView;
    private List<Serializable> listItem = new ArrayList<Serializable>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail_layout);
//        Slidr.attach(this);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nsv_detail_content);
        mTopSub = (ViewGroup) findViewById(R.id.detail_top_sub);
        tvTime = (TextView) findViewById(R.id.detail_time);
        tvAuthor = (TextView) findViewById(R.id.detail_author);
        tvZebian = (TextView) findViewById(R.id.tv_detail_zebian);

        wvDetailContent = (WebView) findViewById(R.id.wv_detail_content);
        wvDetailContent.getSettings().setJavaScriptEnabled(true);
        wvDetailContent.setBackgroundColor(0x00000000); // 设置背景色
        wvDetailContent.getSettings().setDefaultFontSize(16);

        detailTitle = (CollapsingToolbarLayout) findViewById(R.id.detail_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.flexible_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDetailActivity.this.onBackPressed();
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

        mAdapter.register(DetailRelatedItem.class, new RelatedActicalProvider(this));
        mAdapter.register(DetailRecommendItem.class, new RecommentActicalProvider(this));

        String uuid = CommonApi.getSingleInstance().getNewsDetailContent(Constant.NEWS_DETAIL_CONTENT_URL);
        mPostTypes.put(POST_DETAIL_CONTENT, uuid);

        String uuid2 = CommonApi.getSingleInstance().getNewsDetailRelated(Constant.NEWS_DETAIL_RELATED_URL);
        mPostTypes.put(POST_DETAIL_RELATED, uuid2);

        String uuid3 = CommonApi.getSingleInstance().getNewsDetailRecommend(Constant.NEWS_DETAIL_RECOMMEND_URL);
        mPostTypes.put(POST_DETAIL_RECOMMEND, uuid3);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResultEvent(BaseEvent event) {
        Log.i("msg", "onResultEvent event:" + event);
        if (event.isOk()) {
            if (event instanceof GetNewsDetailContentEvent) {
                GetNewsDetailContentEvent ev = (GetNewsDetailContentEvent) event;
                List<NewestItem> list = ev.getBean().newest.item;//detail详情只有一个item,故list的size为1
                if (list != null && list.size() > 0) {
                    setDetaiContentToView(list.get(0));
                }
            } else if (event instanceof GetNewsDetailRelatedEvent) {
                GetNewsDetailRelatedEvent ev = (GetNewsDetailRelatedEvent) event;
                String response = ev.getBean();
                String[] ss = response.split("=");
                if (ss.length < 1) {
                    return;
                }
                try {
                    JSONArray jsonArray = new JSONArray(ss[1]);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        if (i >= 3) {
                            continue;
                        }
                        JSONObject jo = jsonArray.optJSONObject(i);
                        DetailRelatedItem item = new DetailRelatedItem();
                        item.category = "相关文章";
                        item.newsid = jo.optString("newsid");
                        item.newstitle = jo.optString("newstitle");
                        item.postdate = jo.optString("postdate");
                        item.url = jo.optString("url");
                        listItem.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("msg", "listItem:" + listItem.size());
//                mAdapter.addDatas(listItem);
            } else if (event instanceof GetNewsDetailRecommendEvent) {
                GetNewsDetailRecommendEvent ev = (GetNewsDetailRecommendEvent) event;
                List<DetailRecommendItem> list = ev.getBean().content;
                for (DetailRecommendItem item : list) {
                    item.category = "大家都在看";
                }
                listItem.addAll(list);
                mAdapter.addDatas(listItem);
            }

        } else {
        }
    }

    private void setDetaiContentToView(NewestItem item) {
        String title = getIntent().getStringExtra(INTENT_DETAIL_CONTENT_TITLE);
        String time = getIntent().getStringExtra(INTENT_DETAIL_CONTENT_TIME);
        if (!TextUtils.isEmpty(time)) {
            tvTime.setText(time);
        }

        if (!TextUtils.isEmpty(title)) {
            detailTitle.setTitle(title);
        }

        if (!TextUtils.isEmpty(item.detail)) {
            wvDetailContent.loadDataWithBaseURL(null, item.detail, "text/html", "utf-8", null);
            nestedScrollView.smoothScrollTo(0, 0);
        }

        if (!TextUtils.isEmpty(item.newssource) && !TextUtils.isEmpty(item.newsauthor)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(item.newssource);
            stringBuilder.append("(");
            stringBuilder.append(item.newsauthor);
            stringBuilder.append(")");
            tvAuthor.setText(stringBuilder.toString());
        }
        if (!TextUtils.isEmpty(item.z)) {
            tvZebian.setText("责编:" + item.z);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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

    public static void start(Context c, String time, String title) {
        Intent intent = new Intent(c, NewsDetailActivity.class);
        intent.putExtra(INTENT_DETAIL_CONTENT_TIME, time);
        intent.putExtra(INTENT_DETAIL_CONTENT_TITLE, title);
        c.startActivity(intent);
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
