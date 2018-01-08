package com.danmo.commonutil.recyclerview.adapter.singletype;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class SingleTypeAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    protected Context mContext;
    private LayoutInflater mInflater;
    private List<T> mDatas = new ArrayList<>();
    private int mLayoutId;

    public SingleTypeAdapter(@NonNull Context context, @LayoutRes int layoutId) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mLayoutId = layoutId;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = null;
        try {
            rootView = mInflater.inflate(mLayoutId, parent, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RecyclerViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        convert(position, holder, mDatas.get(position));
    }

    /**
     * 在此处处理数据
     *
     * @param position 位置
     * @param holder   view holder
     * @param bean     数据
     */
    public abstract void convert(int position, RecyclerViewHolder holder, T bean);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addDatas(List<T> datas) {
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setDatas(List<T> datas) {
        clearDatas();
        addDatas(datas);
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void clearDatas() {
        this.mDatas.clear();
        notifyDataSetChanged();
    }
}
