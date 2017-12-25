package com.danmo.ithouse.fragment.sub.calendar;

import android.view.View;
import android.widget.TextView;

import com.danmo.ithouse.R;
import com.danmo.ithouse.base.BaseFragment;
import com.danmo.ithouse.base.ViewHolder;

/**
 * Created by user on 2017/12/25.
 */

public class ReadHistoryFragment extends BaseFragment implements View.OnClickListener {
    private TextView mTabNews, mTabHotGoogs, mTabCommunity;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_calendar_history;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        mTabNews = holder.get(R.id.tv_tab_news);
        mTabHotGoogs = holder.get(R.id.tv_tab_hotgoods);
        mTabCommunity = holder.get(R.id.tv_tab_community);
        mTabNews.setOnClickListener(this);
        mTabHotGoogs.setOnClickListener(this);
        mTabCommunity.setOnClickListener(this);
        mTabNews.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tab_news:
                mTabNews.setSelected(true);
                mTabCommunity.setSelected(false);
                mTabHotGoogs.setSelected(false);
                break;
            case R.id.tv_tab_hotgoods:
                mTabHotGoogs.setSelected(true);
                mTabNews.setSelected(false);
                mTabCommunity.setSelected(false);
                break;
            case R.id.tv_tab_community:
                mTabCommunity.setSelected(true);
                mTabNews.setSelected(false);
                mTabHotGoogs.setSelected(false);
                break;
        }
    }
}
