/**
 *
 */
package com.danmo.ithouse.widget.comment;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.danmo.commonapi.bean.community.CommentReplyContent;
import com.danmo.ithouse.R;

import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FloorView extends LinearLayout {

    private int density;
    private Drawable drawer;
    private List<CommentReplyContent> datas;                // 评论的数据
    private SubFloorFactory factory;

    public FloorView(Context context) {
        super(context);
        init(context);
    }

    public FloorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FloorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setBoundDrawer(Drawable drawable) {
        drawer = drawable;
    }

    public void setFloorComments(List<CommentReplyContent> cmts) {
        datas = cmts;
    }

    public void setFactory(SubFloorFactory fac) {
        factory = fac;
    }

    public int getFloorNum() {
        return getChildCount();
    }

    private void init(Context context) {
        // 设置为垂直方向
        this.setOrientation(LinearLayout.VERTICAL);
        // 获取手机屏幕相对密度(常见于 dp和px转换的公式里面)
        density = (int) (3.0F * context.getResources().getDisplayMetrics().density);
    }

    public void init() {
        // 如果没有数据直接返回
        if (null == datas)
            return;
        // 如果评论回复数小于7个，则直接显示完

        if (datas.size() < 5) {
            for (int i = 0; i < datas.size(); i++) {
                View view = factory.buildSubFloor(i, datas.get(i), this);
                addView(view);
            }
        } else {
            // 如果评论回复数很多，则显示一个“显示隐藏楼层”的按钮
            View view;
            view = factory.buildSubFloor(0, datas.get(0), this);
            addView(view);
            view = factory.buildSubFloor(1, datas.get(1), this);
            addView(view);
            view = factory.buildSubFloor(2, datas.get(2), this);
            addView(view);
            view = factory.buildSubFloor(3, datas.get(3), this);
            addView(view);
            // 可以点击显示更多的楼层
            view = factory.buildSubHideFloor(datas.size() - 4, this);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView hide_text = v.findViewById(R.id.hide_text);
                    hide_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,
                            0);
                    v.findViewById(R.id.hide_pb).setVisibility(View.VISIBLE);
                    removeAllViews();
                    for (int i = 0; i < datas.size(); i++) {
                        View view = factory.buildSubFloor(i, datas.get(i), FloorView.this);
                        addView(view);
                    }
                    invalidate();
//                    reLayoutChildren();
                }
            });
            addView(view);
//            view = factory.buildSubFloor(datas.get(datas.size() - 1), this);
//            addView(view);
        }

        reLayoutChildren();
    }

    /**
     * 重新布局子View的位置，呈现出楼层的效果
     */
    public void reLayoutChildren() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            LayoutParams layout = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            layout.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
            int margin = Math.min((count - i - 1), 4) * density;
            layout.leftMargin = margin;
            layout.rightMargin = margin;
//            if (i == count - 1) {
//                layout.topMargin = 0;
//            } else {
//                layout.topMargin = Math.min((count - i), 4) * density;
//            }
            view.setLayoutParams(layout);
        }
    }

    /**
     * 分发给子组件进行绘制，给每个子View画背景
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        int i = getChildCount();
        if (null != drawer && i > 0) {
            for (int j = i - 1; j >= 0; j--) {
                View view = getChildAt(j);
                // drawable将在被绘制在canvas的哪个矩形区域内。
                drawer.setBounds(view.getLeft(), view.getLeft(),
                        view.getRight(), view.getBottom());
                drawer.draw(canvas);
            }
        }
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.getChildCount() <= 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
