package com.danmo.ithouse.widget.edittext;

import android.text.TextUtils;

import com.danmo.commonutil.RegexUtils;


/**
 * Created by caihan on 2017/1/16.
 * 正则判断
 */
public class Check {
    private static final String TAG = "Check";
    //验证座机号,正确格式：xxx/xxxx-xxxxxxx/xxxxxxxx
    private static final String REGEX_TEL = "^0\\d{2,3}[- ]?\\d{7,8}";

    public static boolean match(int typ, String string) {
        return getMatch(typ, string, null);
    }

    public static boolean match(int typ, String string, String userRegxs) {
        return getMatch(typ, string, userRegxs);
    }

    private static boolean getMatch(int typ, String string, String userRegxs) {
        boolean isMatch = false;
        switch (typ) {
            case EditTextType.TYPE_OF_MOBILE:
                isMatch = isMobile(string);
                break;
            case EditTextType.TYPE_OF_TEL:
                isMatch = isTel(string);
                break;
            case EditTextType.TYPE_OF_EMAIL:
                isMatch = isEmail(string);
                break;
            case EditTextType.TYPE_OF_URL:
                isMatch = isURL(string);
                break;
            case EditTextType.TYPE_OF_CHZ:
                isMatch = isChz(string);
                break;
            case EditTextType.TYPE_OF_USERNAME:
                isMatch = isUsername(string);
                break;
            case EditTextType.TYPE_OF_USER_DEFINE:
                if (userRegxs != null && !TextUtils.isEmpty(userRegxs)) {
                    isMatch = isMatch(userRegxs, string);
                }
                break;
            case EditTextType.TYPE_OF_NULL:
                isMatch = true;
                break;
            default:
                break;
        }
        return isMatch;
    }

    /**
     * @param string 待验证文本
     * @return 是否符合手机号格式
     */
    private static boolean isMobile(String string) {
        return RegexUtils.isMobileSimple(string);
    }

    /**
     * @param string 待验证文本
     * @return 是否符合座机号码格式
     */
    private static boolean isTel(String string) {
        return RegexUtils.isMatch(REGEX_TEL, string);
    }

    /**
     * @param string 待验证文本
     * @return 是否符合邮箱格式
     */
    private static boolean isEmail(String string) {
        return RegexUtils.isEmail(string);
    }

    /**
     * @param string 待验证文本
     * @return 是否符合网址格式
     */
    private static boolean isURL(String string) {
        return RegexUtils.isURL(string);
    }

    /**
     * @param string 待验证文本
     * @return 是否符合汉字
     */
    private static boolean isChz(String string) {
        return RegexUtils.isZh(string);
    }

    /**
     * @param string 待验证文本
     * @return 是否符合用户名
     */
    private static boolean isUsername(String string) {
        return RegexUtils.isUsername(string);
    }

    /**
     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    private static boolean isMatch(String regex, CharSequence input) {
        return RegexUtils.isMatch(regex, input);
    }
}
