package com.danmo.ithouse.fragment;

import android.os.Bundle;
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

import com.danmo.ithouse.R;
import com.danmo.ithouse.base.BaseFragment;
import com.danmo.ithouse.base.ViewHolder;
import com.danmo.ithouse.fragment.sub.LapinAllFragment;
import com.danmo.ithouse.fragment.sub.SubFragment;

import java.lang.reflect.Method;

/**
 * Created by user on 2017/9/13.
 */

public class LapinMainFragment extends BaseFragment {
    private TabLayout mTabNav;
    private ViewPager mBaseViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lapin_main;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        mTabNav = holder.get(R.id.tab_nav);
        mBaseViewPager = holder.get(R.id.viewPager);
        Toolbar toolbar = holder.get(R.id.toolbar);
        toolbar.setTitle("辣品");

        setHasOptionsMenu(true);
        ((AppCompatActivity) mContext).setSupportActionBar(toolbar);

        LapinMainFragment.BaseViewPagerAdapter adapter = new LapinMainFragment.BaseViewPagerAdapter(getChildFragmentManager(), getPagers());
        mBaseViewPager.setAdapter(adapter);
        mTabNav.setupWithViewPager(mBaseViewPager);
        mBaseViewPager.setCurrentItem(0, true);

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
        private LapinMainFragment.PagerInfo[] mInfoList;
        private Fragment mCurFragment;

        public BaseViewPagerAdapter(FragmentManager fm, LapinMainFragment.PagerInfo[] infoList) {
            super(fm);
            mInfoList = infoList;
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
            LapinMainFragment.PagerInfo info = mInfoList[position];
            return Fragment.instantiate(getContext(), info.clx.getName(), info.args);
        }

        @Override
        public int getCount() {
            return mInfoList.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mInfoList[position].title;
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }

    private LapinMainFragment.PagerInfo[] getPagers() {

        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_tab", "tab_lapin");

        return new LapinMainFragment.PagerInfo[]{
                new LapinMainFragment.PagerInfo("全部", LapinAllFragment.class,
                        bundle),
                new LapinMainFragment.PagerInfo("辣榜", SubFragment.class,
                        bundle),
                new LapinMainFragment.PagerInfo("1元包邮", SubFragment.class,
                        bundle),
                new LapinMainFragment.PagerInfo("9块9", SubFragment.class,
                        bundle),
                new LapinMainFragment.PagerInfo("19块9", SubFragment.class,
                        bundle),
                new LapinMainFragment.PagerInfo("福包", SubFragment.class,
                        bundle),

        };
    }
}
