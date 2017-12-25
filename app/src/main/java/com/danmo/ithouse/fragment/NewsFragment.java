package com.danmo.ithouse.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.TextView;

import com.danmo.commonutil.SharedPreferencesHelper;
import com.danmo.ithouse.R;
import com.danmo.ithouse.activity.CalendarActivity;
import com.danmo.ithouse.base.BaseActivity;
import com.danmo.ithouse.base.BaseApplication;
import com.danmo.ithouse.base.BaseFragment;
import com.danmo.ithouse.base.BaseViewPagerAdapter;
import com.danmo.ithouse.base.ViewHolder;
import com.danmo.ithouse.fragment.sub.NewestFragment;
import com.danmo.ithouse.fragment.sub.SubFragment;
import com.danmo.ithouse.util.Config;
import com.danmo.ithouse.util.EventBusMsg;
import com.danmo.ithouse.widget.CustomSearchView;
import com.danmo.ithouse.widget.picker.SubTab;
import com.danmo.ithouse.widget.picker.TabPickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static android.os.Build.MANUFACTURER;

/**
 * 资讯
 */

public class NewsFragment extends BaseFragment {

    private TabLayout mTabNav;
    private ViewPager mBaseViewPager;
    private ImageView pickerCustomArrow;
    private TabPickerView pickerViewLayout;
    private static TabPickerView.TabPickerDataManager mTabPickerDataManager;
    private BaseViewPagerAdapter mAdapter;
    private BaseActivity activity;
    private TextView tvPickOperator;
    private CustomSearchView searchView;
    private Toolbar toolbar;

    @Override
    public void onAttach(Context context) {
        activity = (BaseActivity) context;
        activity.addOnTurnBackListener(new BaseActivity.TurnBackListener() {
            @Override
            public boolean onTurnBack() {
                return (pickerViewLayout != null && pickerViewLayout.onTurnBack()) || (searchView != null && searchView.onTurnBack());
            }
        });
        super.onAttach(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_main;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResultEvent(EventBusMsg eventBusMsg) {
        if (eventBusMsg.getFlag() == 0 || eventBusMsg.getFlag() == 1) {
            if (searchView.isShown()) {
                searchView.hide();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        if (searchView.isShown()) {
            searchView.hide();
        }
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        mTabNav = holder.get(R.id.tab_nav);
        mBaseViewPager = holder.get(R.id.viewpager);
        toolbar = holder.get(R.id.toolbar);
        toolbar.setTitle("");
        setHasOptionsMenu(true);
        ((AppCompatActivity) mContext).setSupportActionBar(toolbar);

        tvPickOperator = holder.get(R.id.tv_pick_operator);
        pickerCustomArrow = holder.get(R.id.icon_toolbar_custom);
        pickerViewLayout = holder.get(R.id.view_tab_picker);
        searchView = holder.get(R.id.view_custom_search);

        pickerViewLayout.bindViewOperator(tvPickOperator);//设置提示view

        pickerCustomArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickerCustomArrow.getRotation() != 0) {
                    pickerViewLayout.hide();
                    pickerViewLayout.onTurnBack();
                } else {
                    pickerViewLayout.show(mTabNav.getSelectedTabPosition());
                }
            }
        });
        pickerViewLayout.setTabPickerManager(initTabPickerManager());
        pickerViewLayout.setOnTabPickingListener(new TabPickerView.OnTabPickingListener() {

            @Override
            public void onSelected(final int position) {
                mAdapter.setPageInfo(getPagers());
                mAdapter.notifyDataSetChanged();
                mBaseViewPager.setCurrentItem(position);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTabNav.getTabAt(position).select();
                    }
                }, 50);
            }

            @Override
            public void onRemove(int position, SubTab tab) {
            }

            @Override
            public void onInsert(SubTab tab) {
            }

            @Override
            public void onMove(int op, int np) {
            }

            @Override
            public void onRestore(List<SubTab> activeTabs) {
                SharedPreferencesHelper.putObject(BaseApplication.sAppContext, "TabPickerActiveData", activeTabs);
            }
        });
        pickerViewLayout.setOnShowAnimation(new TabPickerView.Action1<ViewPropertyAnimator>() {
            @Override
            public void call(ViewPropertyAnimator viewPropertyAnimator) {
                mTabNav.setVisibility(View.GONE);
                tvPickOperator.setVisibility(View.VISIBLE);
                EventBus.getDefault().post(new EventBusMsg(1));//隐藏底部导航栏
                pickerCustomArrow.setPivotX(pickerCustomArrow.getMeasuredWidth() / 2);
                pickerCustomArrow.setPivotY(pickerCustomArrow.getMeasuredHeight() / 2);
                pickerCustomArrow.setRotation(180);
            }
        });
        pickerViewLayout.setOnHideAnimator(new TabPickerView.Action1<ViewPropertyAnimator>() {
            @Override
            public void call(ViewPropertyAnimator viewPropertyAnimator) {
                mTabNav.setVisibility(View.VISIBLE);
                tvPickOperator.setVisibility(View.GONE);
                EventBus.getDefault().post(new EventBusMsg(0));//显示底部导航栏
                pickerCustomArrow.setPivotX(pickerCustomArrow.getMeasuredWidth() / 2);
                pickerCustomArrow.setPivotY(pickerCustomArrow.getMeasuredHeight() / 2);
                pickerCustomArrow.setRotation(0);

            }
        });

//        mAdapter = new BaseViewPagerAdapter(getChildFragmentManager(), getPagers());
//        mBaseViewPager.setAdapter(mAdapter);
//        mTabNav.setupWithViewPager(mBaseViewPager);
//        mBaseViewPager.setCurrentItem(0, true);

        mAdapter = new BaseViewPagerAdapter(mContext, getChildFragmentManager(), getPagers());
        mBaseViewPager.setAdapter(mAdapter);
        mTabNav.setupWithViewPager(mBaseViewPager);
        mBaseViewPager.setCurrentItem(0, true);

    }

    public TabPickerView.TabPickerDataManager initTabPickerManager() {
        if (mTabPickerDataManager == null) {
            mTabPickerDataManager = new TabPickerView.TabPickerDataManager() {
                @Override
                public List<SubTab> setupActiveDataSet() {

                    Object obj = SharedPreferencesHelper.getObject(BaseApplication.sAppContext, "TabPickerActiveData");
                    List<SubTab> list = (List<SubTab>) obj;
                    if (list == null || list.size() <= 0) {
                        list = buildPickActiveData();
                        SharedPreferencesHelper.putObject(BaseApplication.sAppContext, "TabPickerActiveData", list);
                    }
                    return list;
                }

                @Override
                public List<SubTab> setupOriginalDataSet() {

                    Object obj = SharedPreferencesHelper.getObject(BaseApplication.sAppContext, "TabPickerOriginalData");
                    List<SubTab> list = (List<SubTab>) obj;
                    if (list == null || list.size() <= 0) {
                        list = new ArrayList<>();
                        for (int i = 0; i < Config.newsTabTitles.length; i++) {
                            SubTab tab = new SubTab();
                            if (i == 0) {
                                tab.setFixed(true);
                            }
                            if (i == 2) {
                                tab.setName(MANUFACTURER);
                            } else {
                                tab.setName(Config.newsTabTitles[i]);
                            }
                            list.add(tab);
                        }
                        SharedPreferencesHelper.putObject(BaseApplication.sAppContext, "TabPickerOriginalData", list);
                    }
                    return list;
                }

                @Override
                public void restoreActiveDataSet(List<SubTab> mActiveDataSet) {
                    SharedPreferencesHelper.putObject(BaseApplication.sAppContext, "TabPickerActiveData", mActiveDataSet);
                }
            };
        }
        return mTabPickerDataManager;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_toolbar_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
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
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_search:
                if (!searchView.isShown()) {
                    searchView.show();
                } else {
                    searchView.hide();
                }
                break;
            case R.id.option_cal:
                CalendarActivity.start(mContext);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public List<SubTab> buildPickActiveData() {
        List<SubTab> list = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            SubTab tab = new SubTab();
            if (i == 0) {
                tab.setFixed(true);
            }
            if (i == 2) {
                tab.setName(MANUFACTURER);
            } else {
                tab.setName(Config.newsTabTitles[i]);
            }
            list.add(tab);
        }
        return list;
    }


    @Override
    protected void initData() {
        super.initData();
    }

    private List<BaseViewPagerAdapter.PagerInfo> getPagers() {
        List<BaseViewPagerAdapter.PagerInfo> listPage = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_tab", "tab");

        Object obj = SharedPreferencesHelper.getObject(BaseApplication.sAppContext, "TabPickerActiveData");
        List<SubTab> list = (List<SubTab>) obj;

        if (list == null || list.size() <= 0) {
            list = buildPickActiveData();
            SharedPreferencesHelper.putObject(BaseApplication.sAppContext, "TabPickerActiveData", list);
        }

        for (int i = 0; i < list.size(); i++) {
            SubTab item = list.get(i);
            if (i == 2) {
                item.setName(MANUFACTURER);
            }
            switch (item.getType()) {
                case 0:
                    listPage.add(new BaseViewPagerAdapter.PagerInfo(item.getName(), NewestFragment.class, bundle));
                    break;
                case 1:
                    listPage.add(new BaseViewPagerAdapter.PagerInfo(item.getName(), SubFragment.class, bundle));
                    break;
                case 2:
                    listPage.add(new BaseViewPagerAdapter.PagerInfo(item.getName(), SubFragment.class, bundle));
                    break;
            }
        }

        return listPage;
    }

}
