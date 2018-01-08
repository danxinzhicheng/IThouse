package com.danmo.ithouse.widget.edittext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by caihan on 2017/1/16.
 */
public class AutoCheckEditText extends TextInputEditText implements TextWatcher, View.OnFocusChangeListener {
    private static final String TAG = "AutoCheckEditText";

    private Context mContext;
    private int mType;
    private Drawable successDrawable;
    private Drawable unsuccessDrawable;
    private String userRegx;
    //左边图标
    private Drawable mLeftDrawable;
    //右侧删除图标
    private Drawable mRightDrawable;
    private final int DRAWABLE_LEFT = 0;
    private final int DRAWABLE_TOP = 1;
    private final int DRAWABLE_RIGHT = 2;
    private final int DRAWABLE_BOTTOM = 3;
    private WarnViewStatus mWarnViewListener;
    private WarningMsg mWarningMsg;
    private int mMinLength = 0;
    private int mMaxLength = Integer.MAX_VALUE;

    public AutoCheckEditText(Context context) {
        super(context);
        init(context);
    }

    public AutoCheckEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutoCheckEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        if (context == null || isInEditMode()) return;
        mContext = context;
        mRightDrawable = getCompoundDrawablesRelative()[DRAWABLE_RIGHT];
        mLeftDrawable = getCompoundDrawablesRelative()[DRAWABLE_LEFT];
        setCompoundDrawablesRelativeWithIntrinsicBounds(mLeftDrawable, null, null, null);
        this.addTextChangedListener(this);
        this.setOnFocusChangeListener(this);
        mWarningMsg = new WarningMsg();
    }

    /**
     * @param typ            要校验的类型
     * @param warnViewStatus 错误提示语状态监听
     */
    public void creatCheck(int typ, WarnViewStatus warnViewStatus) {
        this.mType = typ;
        mWarningMsg.setType(typ);
        mWarnViewListener = warnViewStatus;
    }

    /**
     * 设置判断的长度区间
     *
     * @param minLength
     * @param maxLength
     */
    public void setLength(int minLength, int maxLength) {
        setMinLength(minLength);
        setMaxLength(maxLength);
    }

    public void setMinLength(int minLength) {
        mMinLength = minLength;
    }

    public void setMaxLength(int maxLength) {
        mMaxLength = maxLength;
    }

    /**
     * @param typ       要校验的类型
     * @param success   匹配成功时的图标
     * @param unsuccess 匹配失败时的图标
     * @param userRegex 用户自定义正则
     */
    public void creatCheck(int typ, Drawable success, Drawable unsuccess, String userRegex) {
        creatCheck(typ, success, unsuccess);
        this.userRegx = userRegex;
    }

    /**
     * @param typ       要校验的类型
     * @param success   匹配成功时的图标
     * @param unsuccess 匹配失败时的图标
     */
    private void creatCheck(int typ, Drawable success, Drawable unsuccess) {
        mType = typ;
        successDrawable = success;
        successDrawable.setBounds(0, 0,
                successDrawable.getMinimumWidth(), successDrawable.getMinimumHeight());
        unsuccessDrawable = unsuccess;
        unsuccessDrawable.setBounds(0, 0,
                unsuccessDrawable.getMinimumWidth(), unsuccessDrawable.getMinimumHeight());
        mWarningMsg.setType(typ);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        updateCleanable(s.toString().length(), true);
        if (s != null && s.length() > 0) {
            if (s.length() >= mMinLength && s.length() <= mMaxLength) {
                boolean isMatch = Check.match(mType, s.toString(), userRegx);
                changeWarnStatus(!isMatch, mWarningMsg.getMsg());
            } else {
                changeWarnStatus(true, mWarningMsg.getLengthMsg());
            }
        } else {
            changeWarnStatus(false, mWarningMsg.getMsg());
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //可以获得上下左右四个drawable，右侧排第二。图标没有设置则为空。
        if (mRightDrawable != null && event.getAction() == MotionEvent.ACTION_UP) {
            //检查点击的位置是否是右侧的删除图标
            //注意，使用getRawX()是获取相对屏幕的位置，getX()可能获取相对父组件的位置
            int leftEdgeOfRightDrawable = getRight() - getPaddingRight()
                    - mRightDrawable.getBounds().width();
            if (event.getRawX() >= leftEdgeOfRightDrawable) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        mRightDrawable = null;
        super.finalize();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        //更新状态，检查是否显示删除按钮
        updateCleanable(this.getText().length(), hasFocus);
    }

    /**
     * 当内容不为空，而且获得焦点，才显示右侧删除按钮
     *
     * @param length
     * @param hasFocus
     */
    private void updateCleanable(int length, boolean hasFocus) {
        if (length > 0 && hasFocus) {
            setCompoundDrawablesRelativeWithIntrinsicBounds(mLeftDrawable, null, mRightDrawable, null);
        } else {
            setCompoundDrawablesRelativeWithIntrinsicBounds(mLeftDrawable, null, null, null);
        }
    }

    /**
     * 更新错误提示语状态
     *
     * @param isShow 是否显示提示语,true = 显示
     * @param msg    提示语
     */
    private void changeWarnStatus(boolean isShow, String msg) {
        if (mWarnViewListener != null) {
            if (isShow) {
                mWarnViewListener.show(msg);
            } else {
                mWarnViewListener.hide();
            }
        }
    }
}
