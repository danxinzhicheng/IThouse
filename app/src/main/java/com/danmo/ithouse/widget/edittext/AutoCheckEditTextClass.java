package com.danmo.ithouse.widget.edittext;

import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;

/**
 * Created by caihan on 2017/1/22.
 * 专门处理EditText的类
 * 请使用widget_autocheck_et_layout.xml
 */
public class AutoCheckEditTextClass implements WarnViewStatus {
    private static final String TAG = "AutoCheckEditTextClass";

    private TextInputLayout mTextInputLayout;
    private AutoCheckEditText mAutoCheckEditText;

    /**
     * 传入相关联的TextInputLayout与AutoCheckEditText
     *
     * @param inputLayout
     * @param editText
     */
    public AutoCheckEditTextClass(TextInputLayout inputLayout, AutoCheckEditText editText) {
        mTextInputLayout = inputLayout;
        mAutoCheckEditText = editText;
    }

    /**
     * 设置正则校验类型
     *
     * @param typ
     * @return
     */
    public AutoCheckEditTextClass checkType(int typ) {
        mAutoCheckEditText.creatCheck(typ, this);
        return this;
    }

    /**
     * 设置最小判断长度(一般不设置,默认0)
     *
     * @param minLength
     * @return
     */
    public AutoCheckEditTextClass setMinLength(int minLength) {
        mAutoCheckEditText.setMinLength(minLength);
        return this;
    }

    /**
     * @param maxLength      设置最大长度的时候,一并设置计算器的最大字数限制
     * @param counterEnabled 计算器是否开启
     * @return
     */
    public AutoCheckEditTextClass setMaxLength(int maxLength, boolean counterEnabled) {
        mTextInputLayout.setCounterMaxLength(maxLength);
        mTextInputLayout.setCounterEnabled(counterEnabled);
        mAutoCheckEditText.setMaxLength(maxLength);
        return this;
    }

    /**
     * @param maxLength 设置最大长度的时候,一并设置计算器的最大字数限制
     * @return
     */
    public AutoCheckEditTextClass setMaxLength(int maxLength) {
        mTextInputLayout.setCounterMaxLength(maxLength);
        mTextInputLayout.setCounterEnabled(false);
        mAutoCheckEditText.setMaxLength(maxLength);
        return this;
    }

    /**
     * TextInputLayout hint开关
     * 如果只想用EditText默认效果的话,请传false,默认是true
     *
     * @param hintEnabled
     * @return
     */
    public AutoCheckEditTextClass setHintEnabled(boolean hintEnabled) {
        mTextInputLayout.setHintEnabled(hintEnabled);
        return this;
    }

    @Override
    public void show(String... msgs) {
        mTextInputLayout.setErrorEnabled(true);
        if (msgs.length > 0 && !TextUtils.isEmpty(msgs[0])) {
            mTextInputLayout.setError(msgs[0]);
        }
    }

    @Override
    public void hide() {
        mTextInputLayout.setErrorEnabled(false);
    }
}
