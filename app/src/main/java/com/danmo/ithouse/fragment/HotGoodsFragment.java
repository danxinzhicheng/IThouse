package com.danmo.ithouse.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.danmo.ithouse.R;
import com.danmo.ithouse.base.BaseFragment;
import com.danmo.ithouse.base.ViewHolder;
import com.danmo.ithouse.fragment.sub.HotGoodsAllFragment;
import com.danmo.ithouse.fragment.sub.SubFragment;
import com.danmo.ithouse.util.Config;
import com.danmo.ithouse.widget.menu.HotGoodsAppMenu;

/**
 * 辣品
 */

public class HotGoodsFragment extends BaseFragment {
    private TabLayout mTabNav;
    private ViewPager mBaseViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hotgoods_main;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        mTabNav = holder.get(R.id.tab_nav);
        mBaseViewPager = holder.get(R.id.viewPager);
        Toolbar toolbar = holder.get(R.id.toolbar);
        toolbar.setTitle("辣品");
        setHasOptionsMenu(true);
        ((AppCompatActivity) mContext).setSupportActionBar(toolbar);

        HotGoodsFragment.BaseViewPagerAdapter adapter = new HotGoodsFragment.BaseViewPagerAdapter(getChildFragmentManager(), getPagers());
        mBaseViewPager.setAdapter(adapter);
        mTabNav.setupWithViewPager(mBaseViewPager);
        mBaseViewPager.setCurrentItem(0, true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.hotgoods_toolbar_menu, menu);
        MenuItem item = menu.findItem(R.id.option_cal);
        HotGoodsAppMenu cartActionProvider = (HotGoodsAppMenu) MenuItemCompat.getActionProvider(item);
        super.onCreateOptionsMenu(menu, inflater);
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
        private HotGoodsFragment.PagerInfo[] mInfoList;
        private Fragment mCurFragment;

        public BaseViewPagerAdapter(FragmentManager fm, HotGoodsFragment.PagerInfo[] infoList) {
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
            HotGoodsFragment.PagerInfo info = mInfoList[position];
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

    private HotGoodsFragment.PagerInfo[] getPagers() {

        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_tab", "tab_hotgoods");

        if (Config.hotgoodsTabTitles.length < 6) {
            return null;
        }
        return new HotGoodsFragment.PagerInfo[]{
                new HotGoodsFragment.PagerInfo(Config.hotgoodsTabTitles[0], HotGoodsAllFragment.class,
                        bundle),
                new HotGoodsFragment.PagerInfo(Config.hotgoodsTabTitles[1], SubFragment.class,
                        bundle),
                new HotGoodsFragment.PagerInfo(Config.hotgoodsTabTitles[2], SubFragment.class,
                        bundle),
                new HotGoodsFragment.PagerInfo(Config.hotgoodsTabTitles[3], SubFragment.class,
                        bundle),
                new HotGoodsFragment.PagerInfo(Config.hotgoodsTabTitles[4], SubFragment.class,
                        bundle),
                new HotGoodsFragment.PagerInfo(Config.hotgoodsTabTitles[5], SubFragment.class,
                        bundle),

        };
    }
}
