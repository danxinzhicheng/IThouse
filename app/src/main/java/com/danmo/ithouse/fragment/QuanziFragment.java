package com.danmo.ithouse.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.danmo.ithouse.R;
import com.danmo.ithouse.base.BaseFragment;
import com.danmo.ithouse.base.ViewHolder;
import com.danmo.ithouse.fragment.sub.QuanContentFragment;

/**
 * Created by user on 2017/9/13.
 */

public class QuanziFragment extends BaseFragment {
    private FragmentManager fm;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_quanzi;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        Toolbar toolbar = holder.get(R.id.toolbar);
        toolbar.setTitle("圈子");
        setHasOptionsMenu(true);
        ((AppCompatActivity) mContext).setSupportActionBar(toolbar);

        QuanContentFragment fragment = new QuanContentFragment();
        fm = this.getChildFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.quan_content, fragment);
        ft.show(fragment);
        ft.commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.quan_toolbar_menu, menu);
    }


}
