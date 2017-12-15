package com.danmo.ithouse.widget.menu;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;

import com.danmo.ithouse.R;

/**
 * 自定义menu:辣品 app
 */

public class HotGoodsAppMenu extends ActionProvider {
    private Context mContext;

    /**
     * Creates a new instance.
     *
     * @param context Context for accessing resources.
     */
    public HotGoodsAppMenu(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View onCreateActionView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.menu_hotgoods_app, null);
        return view;
    }
}
