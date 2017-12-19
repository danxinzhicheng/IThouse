package com.danmo.ithouse.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.danmo.ithouse.R;

import java.util.ArrayList;
import java.util.List;

public class AutoLinefeedLayout extends ViewGroup {
    private static final String TAG = "AutoLinefeedLayout";
    /**
     * TextView的style
     */
    public int TEXTVIEW_STYLE = 0;
    /**
     * Button的style
     */
    public int BUTTON_STYLE = 1;
    private int style;
    private View btn;

    public AutoLinefeedLayout(Context context) {
        super(context);
    }

    public AutoLinefeedLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoLinefeedLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setData(List<String> data, Context context) {
        if (this.getChildCount() > 0) {
            this.removeAllViews();
        }
        setData(data, context, 12, 20, 5, 20, 5, 30, 15, 0, 0);
    }

    /**
     * 设置数据
     *
     * @param data     文字
     * @param context  上下文
     * @param textSize 文字大小
     * @param pl       左内边距
     * @param pt       上内边距
     * @param pr       右内边距
     * @param pb       下内边距
     * @param ml       左外边距
     * @param mt       上外边距
     * @param mr       右外边距
     * @param mb       下外边距
     */
    public void setData(String[] data, Context context, int textSize, int pl, int pt, int pr, int pb, int ml, int mt, int mr, int mb) {
        createChild(data, context, textSize, pl, pt, pr, pb, ml, mt, mr, mb);
    }

    public void setData(List<String> data, Context context, int textSize, int pl, int pt, int pr, int pb, int ml, int mt, int mr, int mb) {
        String[] mydata = null;
        if (data != null) {
            int length = data.size();
            mydata = new String[length];
            for (int i = 0; i < length; i++) {
                mydata[i] = data.get(i);
            }
        }
        setData(mydata, context, textSize, pl, pt, pr, pb, ml, mt, mr, mb);
    }

    private void createChild(String[] data, final Context context, int textSize, int pl, int pt, int pr, int pb, int ml, int mt, int mr, int mb) {
        int size = data.length;
        for (int i = 0; i < size; i++) {
            String text = data[i];
            //通过判断style是TextView还是Button进行不同的操作，还可以继续添加不同的view
            if (style == TEXTVIEW_STYLE) {
                btn = new TextView(context);
                ((TextView) btn).setGravity(Gravity.CENTER);
                ((TextView) btn).setText(text);
                ((TextView) btn).setTextSize(textSize);
                ((TextView) btn).setTextColor(Color.BLACK);
                ((TextView) btn).setBackgroundResource(R.drawable.selector_tab_pick);
            } else if (style == BUTTON_STYLE) {
                btn = new Button(context);
                ((Button) btn).setGravity(Gravity.CENTER);
                ((Button) btn).setText(text);
                ((Button) btn).setTextSize(textSize);

            }
            btn.setClickable(true);

            btn.setPadding(dip2px(context, pl), dip2px(context, pt), dip2px(context, pr), dip2px(context, pb));
            MarginLayoutParams params = new MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT);
            params.setMargins(ml, mt, mr, mb);

            btn.setLayoutParams(params);
            final int finalI = i;
            //给每个view添加点击事件
            btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (markClickListener != null)
                        markClickListener.clickMark(finalI);
                }
            });

            this.addView(btn);
        }
    }

    private MarkClickListener markClickListener;

    public void setMarkClickListener(MarkClickListener markClickListener) {
        this.markClickListener = markClickListener;
    }

    public interface MarkClickListener {
        void clickMark(int position);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = getChildCount();
        int lineWidth = 0;
        int lineHeight = 0;
        int width = 0;//warpcontet是需要记录的宽度
        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            // 测量每一个child的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
//            Log.e(TAG, "onMeasure: lineHeight = "+lineHeight+" childHeight = "+childHeight );
            if (lineWidth + childWidth > widthSize) {
                width = Math.max(lineWidth, childWidth);//这种情况就是排除单个标签很长的情况
                lineWidth = childWidth;//开启新行
                height += lineHeight;//记录总行高
                lineHeight = childHeight;//因为开了新行，所以这行的高度要记录一下
            } else {
                lineWidth += childWidth;
//                lineHeight = Math.max(lineHeight, childHeight); //记录行高
                lineHeight = Math.max(height, childHeight); //记录行高
            }
            // 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);  //宽度
                height += lineHeight;  //
            }
        }

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize
                : width, (heightMode == MeasureSpec.EXACTLY) ? heightSize
                : height);
     /*   int width1 = (widthMode == MeasureSpec.EXACTLY)? widthSize:width;
        int height1 = (heightMode == MeasureSpec.EXACTLY)? heightSize:height;
        Log.e(TAG, "onMeasure: widthSize ="+widthSize+" heightSize = "+heightSize );
        Log.e(TAG, "onMeasure: width ="+width+" height = "+height );
        Log.e(TAG, "onMeasure: widthEnd ="+width1+" heightEnd = "+height1 );*/
    }

    /**
     * 存储所有的View，按行记录
     */
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    /**
     * 记录每一行的最大高度
     */
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    //onLayout中完成对所有childView的位置以及大小的指定
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();  //清空子控件列表
        mLineHeight.clear();  //清空高度记录列表
        int width = getWidth();//得到当前控件的宽度（在onmeasure方法中已经测量出来了）
        int childCount = getChildCount();
        // 存储每一行所有的childView
        List<View> lineViews = new ArrayList<View>();
        int lineWidth = 0;  //行宽
        int lineHeight = 0; //总行高
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();//得到属性参数
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            // 如果已经需要换行
            if (i == 3) {
                i = 3;
            }
            if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth > width)  //大于父布局的宽度
            {
                // 记录这一行所有的View以及最大高度
                mLineHeight.add(lineHeight);
                // 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
                mAllViews.add(lineViews);
                lineWidth = 0;// 重置行宽
                lineViews = new ArrayList<View>();
            }
            /**
             * 如果不需要换行，则累加
             */
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin);
            lineViews.add(child);
        }
        // 记录最后一行  (因为最后一行肯定大于父布局的宽度，所以添加最后一行是必要的)
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);
        int left = 0;
        int top = 0;
        int lineNums = mAllViews.size();
        for (int i = 0; i < lineNums; i++) {
            // 每一行的所有的views
            lineViews = mAllViews.get(i);
            // 当前行的最大高度  每一行的高度都相同  所以使用（i+1）进行设置高度
            lineHeight = (i + 1) * mLineHeight.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View lineChild = lineViews.get(j);
                if (lineChild.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) lineChild.getLayoutParams();
                //开始画标签了。左边和上边的距离是要根据累计的数确定的。
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + lineChild.getMeasuredWidth();
                int bc = tc + lineChild.getMeasuredHeight();
                lineChild.layout(lc, tc, rc, bc);
                left += lineChild.getMeasuredWidth() + lp.rightMargin + lp.leftMargin;
            }
            left = 0;//将left归零
            top = lineHeight;
        }
    }


    public void setStyle(int style) {
        this.style = style;
    }
}

//只需要初始化WrapLayout的对象后调用setData的方法即可，setData暴露在外的有两个方法 一个是设置String数组的，另一个是设置StringList的。这个布局还可以继续根据自己的需要添加各种东西，包括背景，文字大小等等。
//        在Activity中使用：
//<com.example.went_gone.demo.view.WrapLayout
//        android:id="@+id/act_wrap"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content">
//</com.example.went_gone.demo.view.WrapLayout>
//
//        myFlowLayout.setData(myData1, this, 15, 10, 10, 10, 10, 10, 10, 10, 10);
//        myFlowLayout.setMarkClickListener(new WrapLayout.MarkClickListener() {
//        @Override
//        public void clickMark(int position) {
//        Toast.makeText(WrapActivity.this, myData1[position], Toast.LENGTH_SHORT).show();
//        }
//        });
//
