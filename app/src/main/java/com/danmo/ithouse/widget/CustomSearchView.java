package com.danmo.ithouse.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.singletype.SingleTypeAdapter;
import com.danmo.ithouse.R;
import com.danmo.ithouse.activity.NewsDetailActivity;
import com.danmo.ithouse.base.BaseApplication;
import com.danmo.ithouse.realm.NewsHistoryBean;
import com.danmo.ithouse.realm.SearchHistoryBean;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by user on 2017/12/18.
 */

public class CustomSearchView extends LinearLayout {
    private View mRootView;
    private EditText editTextSearch;
    private RealmResults typeHistoryList, searchHistoryList;
    private LinearLayout linearLayoutRead, linearLayoutSearch;
    private AutoLinefeedLayout autoLinefeedLayout;
    private RecyclerView recyclerViewReadHistory;
    private ReadHistoryRecyclerAdapter readHistoryRecyclerAdapter;
    private int mSearchType;
    public static final int SEARCH_TYPE_NEWS = 0;
    public static final int SEARCH_TYPE_HOTGOODS = 1;
    public static final int SEARCH_TYPE_COMMUNITY = 2;

    public CustomSearchView(Context context) {
        this(context, null);
    }

    public CustomSearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setSearchType(int type) {
        this.mSearchType = type;
    }

    public void initHistoryData() {
        if (mSearchType == SEARCH_TYPE_NEWS) {
            typeHistoryList = BaseApplication.sRealm.where(NewsHistoryBean.class).findAll();
        } else if (mSearchType == SEARCH_TYPE_HOTGOODS) {

        } else if (mSearchType == SEARCH_TYPE_COMMUNITY) {

        }
        searchHistoryList = BaseApplication.sRealm.where(SearchHistoryBean.class).findAll();
    }


    public void init() {

        this.setOrientation(LinearLayout.VERTICAL);
        this.setBackgroundColor(getResources().getColor(R.color.white));
        mRootView = LayoutInflater.from(getContext())
                .inflate(R.layout.custom_search_view, this, false);
        this.addView(mRootView);
        editTextSearch = mRootView.findViewById(R.id.et_search);
        editTextSearch.requestFocus();

        linearLayoutRead = mRootView.findViewById(R.id.ll_read_history);
        linearLayoutSearch = mRootView.findViewById(R.id.ll_search_history);
        autoLinefeedLayout = mRootView.findViewById(R.id.custom_search_history);
        autoLinefeedLayout.setMarkClickListener(new AutoLinefeedLayout.MarkClickListener() {
            @Override
            public void clickMark(int position) {
                //todo 跳转
                Toast.makeText(getContext(), "功能待开发", Toast.LENGTH_LONG).show();
            }
        });
        recyclerViewReadHistory = mRootView.findViewById(R.id.recycler_view_read_history);
        recyclerViewReadHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        readHistoryRecyclerAdapter = new ReadHistoryRecyclerAdapter(getContext(), R.layout.item_search_read_history);
        recyclerViewReadHistory.setAdapter(readHistoryRecyclerAdapter);

        mRootView.findViewById(R.id.tv_search_more).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "功能待开发", Toast.LENGTH_LONG).show();
            }
        });

        mRootView.findViewById(R.id.iv_search_back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onTurnBack();
            }
        });
        mRootView.findViewById(R.id.iv_search_clear).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextSearch.setText("");
            }
        });
        mRootView.findViewById(R.id.custom_search_clear).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseApplication.sRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        searchHistoryList.deleteAllFromRealm();
                        linearLayoutSearch.setVisibility(View.GONE);
                    }
                });
            }
        });
        final SearchHistoryBean searchHistoryBean = new SearchHistoryBean();

        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (editTextSearch.getText().toString().trim().length() > 0) {
                        ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(CustomSearchView.this.getWindowToken(), 0);

                        hide();

                        searchHistoryBean.setTitle(editTextSearch.getText().toString());
                        BaseApplication.sRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(searchHistoryBean);
                            }
                        });

                        Toast.makeText(getContext(), "功能待开发", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
                return false;
            }
        });

    }

    private void refreshView() {
        if (typeHistoryList != null && typeHistoryList.size() > 0) {
            linearLayoutRead.setVisibility(View.VISIBLE);
            readHistoryRecyclerAdapter.setDatas(typeHistoryList);
        }
        if (searchHistoryList != null && searchHistoryList.size() > 0) {
            linearLayoutSearch.setVisibility(View.VISIBLE);
            List<String> list = new ArrayList<>();
            for (int i = 0; i < searchHistoryList.size(); i++) {
                SearchHistoryBean bean = (SearchHistoryBean) searchHistoryList.get(i);
                list.add(bean.getTitle());
            }
            autoLinefeedLayout.setData(list, getContext());
        }

    }

    public boolean onTurnBack() {

        if (this.isShown()) {
            hide();
            return true;
        }
        return false;
    }

    public void show() {
        initHistoryData();
        refreshView();
        roundLoadView();
    }

    public void hide() {
        roundHideView();
    }

    public void roundHideView() {
        final Animator anim = ViewAnimationUtils.createCircularReveal(this,
                this.getWidth() - 150,
                50,
                (float) Math.hypot(this.getWidth(), this.getHeight()),
                0);

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                CustomSearchView.this.setVisibility(View.INVISIBLE);
                ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(CustomSearchView.this.getWindowToken(), 0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.setDuration(300);
        anim.start();

    }

    public void roundLoadView() {
        AnimatorSet animatorSet = new AnimatorSet();
        final Animator anim = ViewAnimationUtils.createCircularReveal(this,
                this.getWidth() - 150,
                50,
                0,
                (float) Math.hypot(this.getWidth(), this.getHeight()));

        anim.setInterpolator(new AccelerateInterpolator());

        Animator anim1 = ObjectAnimator.ofFloat(this, "translationZ", 0f, 50f);
        anim1.setDuration(500);
        anim1.setInterpolator(new AccelerateInterpolator());

        animatorSet.play(anim).with(anim1);
        animatorSet.start();
        this.setVisibility(View.VISIBLE);
        ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        editTextSearch.requestFocus();

    }

    class ReadHistoryRecyclerAdapter extends SingleTypeAdapter<NewsHistoryBean> {

        public ReadHistoryRecyclerAdapter(@NonNull Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void convert(int position, RecyclerViewHolder holder, final NewsHistoryBean bean) {
            TextView textView = holder.get(R.id.tv_search_read_history);
            textView.setText(bean.getTitle());
            holder.getRootView().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    hide();
                    NewsDetailActivity.start(mContext, NewsDetailActivity.TYPE_LIST, bean.getNewsid());
                }
            });
        }
    }


}
