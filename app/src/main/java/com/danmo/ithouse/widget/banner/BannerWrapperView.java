package com.danmo.ithouse.widget.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.danmo.commonutil.DensityUtil;
import com.danmo.ithouse.R;

import java.util.ArrayList;
import java.util.List;


public class BannerWrapperView<T> extends FrameLayout {
    private static final String TAG = BannerWrapperView.class.getSimpleName();

    private static final int PADDING = 3;
    private static final int DELAY_TIME = 3000;

    // 是否展示指示器
    private boolean mShowIndicators = true;
    // 轮播延迟时间
    private int mPlayDelayTime;
    // 是否自动播放
    private boolean mPlayImageAuto;
    // 是否循环播放
    private boolean mPlayImageLoop;
    // 页与页之间的间距（用于实现画廊效果）
    private float mBannerPageMargin;
    // viewPager左右间距（用于实现画廊效果）
    private float mViewPagerHorizonMargin;
    // 是否将子控件绘制在padding内（用于实现画廊效果，需为false）
    private boolean mBannerClipChildren;

    // 指示器背景选择器(selector)
    private Drawable mIndicatorIconSelected, mIndicatorIconNormal;

    // 当前选中图片位置
    private int currentPosition;
    // 当前选中指示器位置
    private int dotPosition;
    // 是否正在轮播
    private boolean isPlaying;

    private float DEFAULT_PAGER_HEIGHT_RATE = 4f;
    private float mHeightRate = DEFAULT_PAGER_HEIGHT_RATE;

    private Context mContext;
    private ViewPager mViewPager;
    private LinearLayout mIndicatorContainer;
    private List<ImageView> mIndicators = new ArrayList<>();
    private List<T> mItems = new ArrayList<>();

    private OnBannerItemClickListener mOnClickListener;
    private BaseHandler mHandler;

    public BannerWrapperView(Context context) {
        this(context, null);
    }

    public BannerWrapperView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerWrapperView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.BannerWrapperViewStyleable, defStyleAttr, 0);
        mShowIndicators = ta.getBoolean(R.styleable.BannerWrapperViewStyleable_showIndicator, true);
        mPlayDelayTime = ta.getInteger(R.styleable.BannerWrapperViewStyleable_playDelayTime, DELAY_TIME);
        mPlayImageAuto = ta.getBoolean(R.styleable.BannerWrapperViewStyleable_playAuto, true);
        mPlayImageLoop = ta.getBoolean(R.styleable.BannerWrapperViewStyleable_playLoop, false);
        mBannerClipChildren = ta.getBoolean(R.styleable.BannerWrapperViewStyleable_bannerClipChildren, true);
        mIndicatorIconSelected = ta.getDrawable(R.styleable.BannerWrapperViewStyleable_indicatorIconSelected);
        mIndicatorIconNormal = ta.getDrawable(R.styleable.BannerWrapperViewStyleable_indicatorIconNormal);
        mBannerPageMargin = ta.getDimension(R.styleable.BannerWrapperViewStyleable_viewPageMargin, 0f);
        mViewPagerHorizonMargin = ta.getDimension(R.styleable.BannerWrapperViewStyleable_viewPageHorizonMargin, 0f);
        mHeightRate = ta.getFloat(R.styleable.BannerWrapperViewStyleable_heightRate, DEFAULT_PAGER_HEIGHT_RATE);
        ta.recycle();

        initView();
    }

    public void initView() {
        // 控件属性默认为true，实现画廊效果时，本父控件和viewPager均需设置clipChildren=false
        setClipChildren(mBannerClipChildren);

        this.mContext = getContext();

        LayoutInflater.from(mContext).inflate(R.layout.layout_banner_wrap_view, this, true);
        mViewPager = findViewById(R.id.viewPager);
        mIndicatorContainer = findViewById(R.id.indicatorView);

        addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(final View v) {

            }

            @Override
            public void onViewDetachedFromWindow(final View v) {
                stopPlay();
                mHandler = null;
                // TODO: 2018/1/24
                removeOnAttachStateChangeListener(this);
            }
        });
    }

    private void startPlay() {
        if (mViewPager != null && mHandler != null) {
            mHandler.removeMessages(0);
            mHandler.sendEmptyMessageDelayed(0, DELAY_TIME);
            isPlaying = true;
        }
    }

    public void stopPlay() {
        if (mViewPager != null && mHandler != null && isPlaying) {
            mHandler.removeCallbacksAndMessages(null);
            isPlaying = false;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            initViewPager();
            setIndicator();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        double newWidth = dm.widthPixels - 2d * mViewPagerHorizonMargin;
        // 图片高度为宽度的1/4
//        double newHeight = newWidth / mHeightRate + getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }

    /**
     * 设置数据源
     *
     * @param items
     */
    public void setDataAdapter(List<T> items, BannerPagerAdapter mPagerAdapter) {
        if (items == null || items.isEmpty()) {
            return;
        }

        mItems.clear();
        mItems.addAll(items);

        setIndicator();
        setViewAdapter(mPagerAdapter);
    }

    /**
     * 设置适配器
     *
     * @param mPagerAdapter
     */
    private void setViewAdapter(BannerPagerAdapter mPagerAdapter) {
        if (mPagerAdapter == null) {
            throw new NullPointerException("Adapter cannot be null !");
        }

        currentPosition = mItems.size() > 1 && mPlayImageLoop ? 1 : 0;
        mViewPager.setAdapter(mPagerAdapter);
        mPagerAdapter.setCanLoop(mPlayImageLoop);
        mPagerAdapter.setOnItemClickListener(mOnClickListener);

        mPagerAdapter.setItems(mItems);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(mItems.size());
        mViewPager.setCurrentItem(currentPosition);

        if (mHandler == null) {
            mHandler = new BaseHandler(getContext(), new BaseHandler.HandlerOperate() {
                @Override
                public void handleMessage(Message message) {
                    if (mViewPager != null && mViewPager.getChildCount() > 1) {
                        currentPosition++;
                        mViewPager.setCurrentItem(currentPosition, true);
                        mHandler.sendEmptyMessageDelayed(0, DELAY_TIME);
                    }
                }
            });
        }
        // 装载数据完成后开始轮播
        startPlay();
    }

    /**
     * 设置ViewPager
     */
    private void initViewPager() {
        // 设置页与页的间距
        mViewPager.setPageMargin((int) mBannerPageMargin);
        LayoutParams lp = (LayoutParams) mViewPager.getLayoutParams();
        if (lp != null) {
            lp.setMargins((int) mViewPagerHorizonMargin, 0, (int) mViewPagerHorizonMargin, 0);
            lp.height = getMeasuredHeight();
        }

        mViewPager.addOnPageChangeListener(new PageChangeListener());

        // 设置触摸事件，当滑动或者触摸时停止自动轮播
        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isPlaying = true;
                        stopPlay();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        isPlaying = false;
                        startPlay();
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 设置指示器
     */
    private void setIndicator() {

        int size = mItems.size();
        if (mShowIndicators && size > 1) {
            mIndicators.clear();
            mIndicatorContainer.removeAllViews();
            for (int i = 0; i < size; i++) {
                ImageView iv = new ImageView(mContext);
//                iv.setImageDrawable(getIndicatorDrawable());
//                iv.setSelected(i == 0);
                iv.setImageDrawable(i == 0 ? mIndicatorIconSelected : mIndicatorIconNormal);
                int padding = DensityUtil.dip2px(mContext, PADDING);
                iv.setPadding(padding, padding, padding, padding);
                mIndicators.add(iv);
                mIndicatorContainer.addView(iv);
            }
        }

        // 设置可见性
        mIndicatorContainer.setVisibility(mShowIndicators ? VISIBLE : GONE);
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        public void onPageScrollStateChanged(int state) {
            //  当state为SCROLL_STATE_IDLE即没有滑动的状态时切换页面
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                mViewPager.setCurrentItem(currentPosition, false);
            }
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        public void onPageSelected(int position) {
            pageSelected(position);
        }
    }

    private void pageSelected(int position) {
        int itemCount = mItems.size();
        if (mPlayImageLoop) {
            if (position == 0) {
                // 判断当切换到第0个页面时把currentPosition设置为list.size(),即倒数第二个位置，小圆点位置为length-1
                currentPosition = itemCount;
                dotPosition = itemCount - 1;
            } else if (position == itemCount + 1) {
                // 当切换到最后一个页面时currentPosition设置为第一个位置，小圆点位置为0
                currentPosition = 1;
                dotPosition = 0;
            } else {
                currentPosition = position;
                dotPosition = position - 1;
            }
        } else {
            currentPosition = position;
            dotPosition = position;
        }

        setSelectedIndicatorIcons();
    }

    /**
     * 设置指示器选中状态
     */
    private void setSelectedIndicatorIcons() {
        int size = mIndicators.size();
        for (int i = 0; i < size; i++) {
//            mIndicators.get(i).setSelected(i == dotPosition);
            mIndicators.get(i).setImageDrawable(i == dotPosition ? mIndicatorIconSelected : mIndicatorIconNormal);
        }
    }

    /**
     * 设置指示器可见性，需要在填充数据前设置
     *
     * @param isShow
     */
    public void setShowIndicators(boolean isShow) {
        this.mShowIndicators = isShow;
        invalidate();
    }

    /**
     * 设置图片宽高比例
     *
     * @param rate
     */
    public void setHeightRate(float rate) {
        this.mHeightRate = rate;
        invalidate();
    }

    public void setOnItemClickListener(OnBannerItemClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }
}