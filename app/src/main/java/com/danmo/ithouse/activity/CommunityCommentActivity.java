package com.danmo.ithouse.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.danmo.commonapi.CommonApi;
import com.danmo.commonapi.base.BaseEvent;
import com.danmo.commonapi.bean.community.Comment;
import com.danmo.commonapi.bean.community.CommentHeader;
import com.danmo.commonapi.bean.community.CommentReply;
import com.danmo.commonutil.DensityUtil;
import com.danmo.commonutil.recyclerview.adapter.multitype.HeaderFooterAdapter;
import com.danmo.commonutil.recyclerview.layoutmanager.SpeedyLinearLayoutManager;
import com.danmo.ithouse.R;
import com.danmo.ithouse.base.BaseActivity;
import com.danmo.ithouse.fragment.nav.BorderShape;
import com.danmo.ithouse.provider.CommunityCommentHeaderProvider;
import com.danmo.ithouse.provider.CommunityCommentProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by user on 2017/12/27.
 */

public class CommunityCommentActivity extends BaseActivity implements View.OnClickListener {
    public static final String INTENT_ID = "id";
    public static final String INTENT_TYPE = "type";
    public static final String INTENT_TITLE = "title";
    public static final String INTENT_NUM_VIEW = "num_view";
    public static final String INTENT_NUM_COMMENT = "num_comment";
    public static final String INTENT_UID_COMMENT = "uid";
    public static final String INTENT_NAME_COMMENT = "name";
    private String mCommentId;
    private String mType;
    private String mTitle;
    private String mViewNum;
    private String mCommentNum;
    private String mUid;
    private String mLzName;
    private RecyclerView mRecyclerView;
    protected HeaderFooterAdapter mAdapter;
    private Boolean isRequestShowed = true;
    private LinearLayout mBottomLayout;
    private LinearLayout mCommentWrite;
    private ImageView mCommentRefresh, mCommentScrollTop, mCommentScrollBottom, mCommentShare;


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_community_comment_layout;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mRecyclerView = findViewById(R.id.comment_recycler_view);
        mAdapter = new HeaderFooterAdapter();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new SpeedyLinearLayoutManager(this));
        mRecyclerView.setOnScrollListener(mOnScrollListener);
        mAdapter.register(CommentReply.class, new CommunityCommentProvider(this));

        mBottomLayout = findViewById(R.id.comment_bottom_ll);
        ShapeDrawable lineDrawable = new ShapeDrawable(new BorderShape(new RectF(0, 1, 0, 0)));
        lineDrawable.getPaint().setColor(getResources().getColor(R.color.list_divider_color));
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{
                new ColorDrawable(getResources().getColor(R.color.white)),
                lineDrawable
        });
        mBottomLayout.setBackgroundDrawable(layerDrawable);

        mCommentWrite = findViewById(R.id.comment_write);
        mCommentRefresh = findViewById(R.id.comment_refresh);
        mCommentScrollTop = findViewById(R.id.comment_scroll_top);
        mCommentScrollBottom = findViewById(R.id.comment_scroll_bottom);
        mCommentShare = findViewById(R.id.comment_share);
        mCommentWrite.setOnClickListener(this);
        mCommentRefresh.setOnClickListener(this);
        mCommentScrollTop.setOnClickListener(this);
        mCommentScrollBottom.setOnClickListener(this);
        mCommentShare.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mCommentId = getIntent().getStringExtra(INTENT_ID);
        mType = getIntent().getStringExtra(INTENT_TYPE);
        mTitle = getIntent().getStringExtra(INTENT_TITLE);
        mViewNum = getIntent().getStringExtra(INTENT_NUM_VIEW);
        mCommentNum = getIntent().getStringExtra(INTENT_NUM_COMMENT);
        mUid = getIntent().getStringExtra(INTENT_UID_COMMENT);
        mLzName = getIntent().getStringExtra(INTENT_NAME_COMMENT);
        CommonApi.getSingleInstance().getCommunityComment(mCommentId);
    }

    public static void start(Context context, String commentId, String type, String title, String viewNum, String commentNum, String uid, String name) {
        Intent intent = new Intent(context, CommunityCommentActivity.class);
        intent.putExtra(INTENT_ID, commentId);
        intent.putExtra(INTENT_TYPE, type);
        intent.putExtra(INTENT_TITLE, title);
        intent.putExtra(INTENT_NUM_VIEW, viewNum);
        intent.putExtra(INTENT_NUM_COMMENT, commentNum);
        intent.putExtra(INTENT_UID_COMMENT, uid);
        intent.putExtra(INTENT_NAME_COMMENT, name);
        context.startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResultEvent(BaseEvent event) {
        String uuid = event.getUUID();
        Comment mCommentBean = (Comment) event.getBean();
        mAdapter.clearDatas();
        mAdapter.addDatas(mCommentBean.reply);

        CommentHeader mHeader = new CommentHeader(mCommentId, mType, mTitle, mViewNum, mCommentNum, mUid, mLzName,
                mCommentBean.content, mCommentBean.ul, mCommentBean.rc, mCommentBean.Ta, mCommentBean.Cl,
                mCommentBean.IC, mCommentBean.IH, mCommentBean.City, mCommentBean.rank, mCommentBean.imgs);

        mAdapter.registerHeader(mHeader, new CommunityCommentHeaderProvider(CommunityCommentActivity.this));

    }


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int mScrollState = recyclerView.getScrollState();
            if (mScrollState == RecyclerView.SCROLL_STATE_DRAGGING || mScrollState == RecyclerView.SCROLL_STATE_SETTLING) {
                if (dy > 50) {//up -> hide
                    if (isRequestShowed) {
                        hideBottomLay();
                        isRequestShowed = false;
                    }
                } else if (dy < -50) {//down -> show
                    if (!isRequestShowed) {
                        showBottomLay();
                        isRequestShowed = true;
                    }
                }
            }
        }
    };

    private void showBottomLay() {
        ValueAnimator va = ValueAnimator.ofFloat(mBottomLayout.getY(), mBottomLayout.getY() - mBottomLayout.getMeasuredHeight());
        va.setDuration(200);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mBottomLayout.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        va.start();
    }

    private void hideBottomLay() {

        ValueAnimator va = ValueAnimator.ofFloat(mBottomLayout.getY(), mBottomLayout.getY() + mBottomLayout.getHeight());
        va.setDuration(200);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mBottomLayout.setY((Float) valueAnimator.getAnimatedValue());
            }

        });
        va.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment_write:
                Toast.makeText(this, "功能待开发", Toast.LENGTH_SHORT).show();
                break;
            case R.id.comment_refresh:
                CommonApi.getSingleInstance().getCommunityComment(mCommentId);
                break;
            case R.id.comment_scroll_top:
                mRecyclerView.smoothScrollToPosition(0);
                break;
            case R.id.comment_scroll_bottom:
                mRecyclerView.smoothScrollToPosition(DensityUtil.getSceenHeight(this));
                break;
            case R.id.comment_share:
                Toast.makeText(this, "功能待开发", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
