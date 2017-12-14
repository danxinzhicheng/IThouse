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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;

import com.danmo.commonutil.SharedPreferencesHelper;
import com.danmo.ithouse.R;
import com.danmo.ithouse.base.BaseActivity;
import com.danmo.ithouse.base.BaseApplication;
import com.danmo.ithouse.base.BaseFragment;
import com.danmo.ithouse.base.ViewHolder;
import com.danmo.ithouse.fragment.sub.NewestFragment;
import com.danmo.ithouse.fragment.sub.SubFragment;
import com.danmo.ithouse.util.Config;
import com.danmo.ithouse.widget.picker.SubTab;
import com.danmo.ithouse.widget.picker.TabPickerView;

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

    @Override
    public void onAttach(Context context) {
        activity = (BaseActivity) context;
        activity.addOnTurnBackListener(new BaseActivity.TurnBackListener() {
            @Override
            public boolean onTurnBack() {
                return pickerViewLayout != null && pickerViewLayout.onTurnBack();
            }
        });
        super.onAttach(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_main;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        mTabNav = holder.get(R.id.tab_nav);
        mBaseViewPager = holder.get(R.id.viewPager);
        Toolbar toolbar = holder.get(R.id.toolbar);
        toolbar.setTitle("");
        setHasOptionsMenu(true);
        ((AppCompatActivity) mContext).setSupportActionBar(toolbar);

        pickerCustomArrow = holder.get(R.id.icon_toolbar_custom);
        pickerViewLayout = holder.get(R.id.view_tab_picker);
        pickerCustomArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickerCustomArrow.getRotation() != 0) {
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

            }
        });
        pickerViewLayout.setOnHideAnimator(new TabPickerView.Action1<ViewPropertyAnimator>() {
            @Override
            public void call(ViewPropertyAnimator viewPropertyAnimator) {

            }
        });

        mAdapter = new BaseViewPagerAdapter(getChildFragmentManager(), getPagers());

        mBaseViewPager.setAdapter(mAdapter);
        mTabNav.setupWithViewPager(mBaseViewPager);
        mBaseViewPager.setCurrentItem(0, true);

    }

    public static TabPickerView.TabPickerDataManager initTabPickerManager() {
        if (mTabPickerDataManager == null) {
            mTabPickerDataManager = new TabPickerView.TabPickerDataManager() {
                @Override
                public List<SubTab> setupActiveDataSet() {

                    Object obj = SharedPreferencesHelper.getObject(BaseApplication.sAppContext, "TabPickerActiveData");
                    List<SubTab> list = (List<SubTab>) obj;
                    if (list == null || list.size() <= 0) {
                        list = new ArrayList<>();
                        for (int i = 0; i < 13; i++) {
                            SubTab tab = new SubTab();
                            tab.setName(Config.newsTabTitles[i]);
                            list.add(tab);
                        }
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
                            tab.setName(Config.newsTabTitles[i]);
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
    protected void initData() {
        super.initData();
    }

    private List<PagerInfo> getPagers() {
        List<PagerInfo> listPage = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_tab", "tab");

        Object obj = SharedPreferencesHelper.getObject(BaseApplication.sAppContext, "TabPickerActiveData");
        List<SubTab> list = (List<SubTab>) obj;

        if (list == null || list.size() <= 0) {
            list = new ArrayList<>();
            for (int i = 0; i < 13; i++) {
                SubTab tab = new SubTab();
                tab.setName(Config.newsTabTitles[i]);
                list.add(tab);
            }
            SharedPreferencesHelper.putObject(BaseApplication.sAppContext, "TabPickerActiveData", list);
        }

        for (int i = 0; i < list.size(); i++) {
            SubTab item = list.get(i);
            if (i == 2) {
                item.setName(MANUFACTURER);
            }
            switch (item.getType()) {
                case 0:
                    listPage.add(new PagerInfo(item.getName(), NewestFragment.class, bundle));
                    break;
                case 1:
                    listPage.add(new PagerInfo(item.getName(), SubFragment.class, bundle));
                    break;
                case 2:
                    listPage.add(new PagerInfo(item.getName(), SubFragment.class, bundle));
                    break;
            }
        }

        return listPage;
    }

    public static class PagerInfo {
        private String title;
        private Class<?> clx;
        private Bundle args;

        public PagerInfo(String title, Class<?> clx, Bundle args) {
            this.title = title;
            this.clx = clx;
            this.args = args;
        }
    }

    public class BaseViewPagerAdapter extends FragmentPagerAdapter {
        private List<PagerInfo> mInfoList;
        private Fragment mCurFragment;

        public BaseViewPagerAdapter(FragmentManager fm, List<PagerInfo> infoList) {
            super(fm);
            mInfoList = infoList;
        }

        public void setPageInfo(List<PagerInfo> infoList) {
            mInfoList.clear();
            mInfoList.addAll(infoList);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            if (object instanceof Fragment) {
                mCurFragment = (Fragment) object;
            }
        }

        public Fragment getCurFragment() {
            return mCurFragment;
        }

        @Override
        public Fragment getItem(int position) {
            PagerInfo info = mInfoList.get(position);
            return Fragment.instantiate(getContext(), info.clx.getName(), info.args);
        }

        @Override
        public int getCount() {
            return mInfoList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mInfoList.get(position).title;
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }

}
