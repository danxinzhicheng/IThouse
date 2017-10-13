package com.danmo.ithouse;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.danmo.ithouse.base.BaseActivity;
import com.danmo.ithouse.base.ViewHolder;
import com.danmo.ithouse.fragment.nav.NavFragment;
import com.danmo.ithouse.fragment.nav.NavigationButton;
import com.danmo.ithouse.fragment.nav.OnTabReselectListener;
import com.danmo.ithouse.util.EventBusMsg;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity implements NavFragment.OnNavigationReselectListener {

    private NavFragment mNavBar;
    private Animator mAnimator;//动画属性

    @Override
    protected void initViews(ViewHolder holder, View root) {
        super.initViews(holder, root);
        FragmentManager manager = getSupportFragmentManager();
        mNavBar = new NavFragment();
        addFragment(R.id.fag_nav, mNavBar);
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

        Log.i("nnn","eventmsg mainactivity-->"+eventBusMsg.getFlag());
        showOrHideNavAnim(eventBusMsg.getFlag());

    }

    private void showOrHideNavAnim(int flag){
        if(mAnimator != null && mAnimator.isRunning()){//判断动画存在  如果启动了,则先关闭
            mAnimator.cancel();
        }
        if(flag != 0){//up
            mAnimator = ObjectAnimator.ofFloat(mNavBar.getView(), "translationY", mNavBar.getView().getTranslationY(),0);//从当前位置位移到0位置
        }else{//down
            mAnimator = ObjectAnimator.ofFloat(mNavBar.getView(), "translationY", mNavBar.getView().getTranslationY(),-mNavBar.getView().getHeight());//从当前位置移动到布局负高度的wiz
        }
        mAnimator.start();//执行动画

    }
}
