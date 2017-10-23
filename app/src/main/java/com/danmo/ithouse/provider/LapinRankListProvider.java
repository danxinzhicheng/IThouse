package com.danmo.ithouse.provider;

import android.content.Context;
import android.support.annotation.NonNull;

import com.danmo.commonapi.bean.lapin.LapinItem;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.multitype.BaseViewProvider;
import com.danmo.ithouse.R;

/**
 * Created by user on 2017/10/17.
 */

public class LapinRankListProvider extends BaseViewProvider<LapinItem>{
    public LapinRankListProvider(@NonNull Context context) {
        super(context, R.layout.item_lapin_rank);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, LapinItem bean) {

    }
}
