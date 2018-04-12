package com.danmo.ithouse.widget.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xss on 2018/1/9.
 */

public abstract class BannerPagerAdapter<T> extends PagerAdapter {

    // 原始数据集合
    private List<T> mItems = new ArrayList<>();
    // 重新构造后的轮播数据集合
    private List<T> mLoopItems = new ArrayList<>();

    private boolean mCanLoop;

    private OnBannerItemClickListener mItemClickListener;

    public void setOnItemClickListener(OnBannerItemClickListener mOnClickListener) {
        this.mItemClickListener = mOnClickListener;
    }

    void setCanLoop(boolean mCanLoop) {
        this.mCanLoop = mCanLoop;
    }

    public void setItems(List<T> items) {
        this.mItems = items;

        if (mCanLoop) {
            initLoopData();
        } else {
            mLoopItems.clear();
            mLoopItems.addAll(mItems);
        }
        notifyDataSetChanged();
    }

    // 根据mList数据集构造循环mLoopItems
    private void initLoopData() {
        if (mItems.size() == 1) {
            mLoopItems.add(mItems.get(0));
        } else if (mItems.size() > 1) {
            createData();
        }
    }

    private void createData() {
        mLoopItems.clear();

        for (int i = 0; i < mItems.size() + 2; i++) {
            if (i == 0) {   // 判断当i=0为该处的mList的最后一个数据作为mListAdd的第一个数据
                mLoopItems.add(mItems.get(mItems.size() - 1));
            } else if (i == mItems.size() + 1) {   // 判断当i=mList.size()+1时将mList的第一个数据作为mListAdd的最后一个数据
                mLoopItems.add(mItems.get(0));
            } else {  // 其他情况
                mLoopItems.add(mItems.get(i - 1));
            }
        }
    }

    public int getRealCount() {
        return mItems.size();
    }

    public List<T> getItems() {
        return mItems;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return mLoopItems.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = getView(position, container);
        container.addView(view);

        ViewPager.LayoutParams lp = (ViewPager.LayoutParams) view.getLayoutParams();
        if (lp != null) {
            lp.width = ViewPager.LayoutParams.MATCH_PARENT;
        }

        return view;
    }

    /**
     * 创建布局
     * @param container
     * @return
     */
    public abstract BaseBannerViewHolder createViewHolder(ViewGroup container);

    //  根据图片URL创建对应的ImageView并添加到集合
    private View getView(final int position, ViewGroup container) {
        BaseBannerViewHolder holder = createViewHolder(container);  // new BannerViewHolder();
        View view = holder.createView(container.getContext());

        int originIndex = position;
        if (mLoopItems != null && !mLoopItems.isEmpty()) {
            if (mCanLoop) {
                if (position == 0) {
                    // 第一个 item，创建最后一个itemView，并用第一个数据填充
                    originIndex = mItems.size() - 1;
                    holder.bind(container.getContext(), mLoopItems.get(0), mLoopItems.size() - 1);
                } else if (position == mLoopItems.size() - 1) {
                    // 最后一个 item ，用第一个
                    originIndex = 0;
                    holder.bind(container.getContext(), mLoopItems.get(mLoopItems.size() - 1), 0);
                } else {
                    originIndex = position - 1;
                    holder.bind(container.getContext(), mLoopItems.get(position), position - 1);
                }
            } else {
                holder.bind(container.getContext(), mLoopItems.get(position), position);
            }
        }

        if (view != null) {
            final int selectedIndex = originIndex;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        if (mCanLoop) {
                            mItemClickListener.onBannerItemClick(mLoopItems.get(position), selectedIndex);
                        } else {
                            mItemClickListener.onBannerItemClick(mLoopItems.get(position), position);
                        }
                    }
                }
            });
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

    public interface BaseBannerViewHolder<T> {

        View createView(Context context);

        void bind(Context context, T data, int position);
    }
}
