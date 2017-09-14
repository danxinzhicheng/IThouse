package com.danmo.ithouse;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import com.danmo.ithouse.base.BaseActivity;
import com.danmo.ithouse.base.ViewHolder;
import com.danmo.ithouse.fragment.nav.NavFragment;
import com.danmo.ithouse.fragment.nav.NavigationButton;
import com.danmo.ithouse.fragment.nav.OnTabReselectListener;

public class MainActivity extends BaseActivity implements NavFragment.OnNavigationReselectListener {

    private NavFragment mNavBar;
    @Override
    protected void initViews(ViewHolder holder, View root) {
        super.initViews(holder,root);
        FragmentManager manager = getSupportFragmentManager();
        mNavBar = new NavFragment();
        addFragment(R.id.fag_nav,mNavBar);
        mNavBar.setup(this, manager, R.id.main_container, this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onReselect(NavigationButton navigationButton) {
        Fragment fragment = navigationButton.getFragment();
        if (fragment != null
                && fragment instanceof OnTabReselectListener) {
            OnTabReselectListener listener = (OnTabReselectListener) fragment;
            listener.onTabReselect();
        }
    }
}
