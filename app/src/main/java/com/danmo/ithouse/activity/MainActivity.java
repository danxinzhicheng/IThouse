package com.danmo.ithouse.activity;

import android.animation.ValueAnimator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.danmo.ithouse.R;
import com.danmo.ithouse.base.BaseActivity;
import com.danmo.ithouse.fragment.nav.NavFragment;
import com.danmo.ithouse.fragment.nav.NavigationButton;
import com.danmo.ithouse.fragment.nav.OnTabReselectListener;
import com.danmo.ithouse.fragment.refresh.RefreshRecyclerFragment;
import com.danmo.ithouse.util.EventBusMsg;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 带底部导航的主Activity
 */
public class MainActivity extends BaseActivity implements NavFragment.OnNavigationReselectListener {

    private NavFragment mNavBar;

    @Override
    protected void initViews() {
        super.initViews();
        mNavBar = new NavFragment();
        addFragment(R.id.fag_nav, mNavBar);
        mNavBar.setup(R.id.main_container, this);
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
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResultEvent(EventBusMsg eventBusMsg) {
        showOrHideNavAnim(eventBusMsg.getFlag());
    }


    private void showOrHideNavAnim(int flag) {
        if (flag == RefreshRecyclerFragment.SCROLL_STATE_UP) {
            hideBottomNav(mNavBar.getView());
        } else if (flag == RefreshRecyclerFragment.SCROLL_STATE_DOWN) {
            showBottomNav(mNavBar.getView());
        }

    }

    private void showBottomNav(final View mTarget) {
        ValueAnimator va = ValueAnimator.ofFloat(mTarget.getY(), 0);
        va.setDuration(200);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTarget.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        va.start();
    }

    private void hideBottomNav(final View mTarget) {
        ValueAnimator va = ValueAnimator.ofFloat(mTarget.getY(), mTarget.getHeight());
        va.setDuration(200);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTarget.setY((Float) valueAnimator.getAnimatedValue());
            }
        });

        va.start();
    }

}
