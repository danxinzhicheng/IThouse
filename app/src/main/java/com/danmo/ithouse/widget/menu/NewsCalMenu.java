package com.danmo.ithouse.widget.menu;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.danmo.commonutil.TimeUtil;
import com.danmo.ithouse.R;
import com.danmo.ithouse.activity.CalendarActivity;

/**
 * 自定义menu:资讯 日历
 */

public class NewsCalMenu extends ActionProvider {

    private Context mContext;
    private TextView tvMenu_calendar;

    /**
     * Creates a new instance.
     *
     * @param context Context for accessing resources.
     */
    public NewsCalMenu(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View onCreateActionView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.menu_news_cal, null);
        tvMenu_calendar = view.findViewById(R.id.menu_calendar);
        int day = TimeUtil.getCurrentDay();
        tvMenu_calendar.setText(String.valueOf(day));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarActivity.start(mContext);
            }
        });
        return view;
    }
}
