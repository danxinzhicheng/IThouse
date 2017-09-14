package com.danmo.ithouse.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.danmo.commonutil.BarUtils;
import com.danmo.ithouse.R;
import com.danmo.ithouse.base.BaseFragment;
import com.danmo.ithouse.base.ViewHolder;
import com.danmo.ithouse.fragment.sub.SubFragment;

/**
 * Created by user on 2017/9/13.
 */

public class DynamicTabFragment extends BaseFragment {

    private TabLayout mTabNav;
    private ViewPager mBaseViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        mTabNav = holder.get(R.id.tab_nav);
        mBaseViewPager = holder.get(R.id.viewPager);
        Toolbar toolbar = holder.get(R.id.toolbar);
        toolbar.setTitle("");
        ((AppCompatActivity) mContext).setSupportActionBar(toolbar);
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);

        BaseViewPagerAdapter adapter = new BaseViewPagerAdapter(getChildFragmentManager(), getPagers());
        mBaseViewPager.setAdapter(adapter);
        mTabNav.setupWithViewPager(mBaseViewPager);
        mBaseViewPager.setCurrentItem(0, true);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    private PagerInfo[] getPagers() {

        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_tab", "tab");

        return new PagerInfo[]{
                new PagerInfo("最新", SubFragment.class,
                        bundle),
                new PagerInfo("排行榜", SubFragment.class,
                        bundle),
                new PagerInfo("华为", SubFragment.class,
                        bundle),
                new PagerInfo("上热评", SubFragment.class,
                        bundle),
                new PagerInfo("评测室", SubFragment.class,
                        bundle),
                new PagerInfo("发布会", SubFragment.class,
                        bundle),
                new PagerInfo("安卓", SubFragment.class,
                        bundle),
                new PagerInfo("苹果", SubFragment.class,
                        bundle)

        };
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
        private PagerInfo[] mInfoList;
        private Fragment mCurFragment;

        public BaseViewPagerAdapter(FragmentManager fm, PagerInfo[] infoList) {
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
            PagerInfo info = mInfoList[position];
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
}
