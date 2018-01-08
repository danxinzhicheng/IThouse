package com.danmo.commonapi.bean.login;

public class OAuth {
    // 认证类型
    public static final String GRANT_TYPE_LOGIN = "password";             // 密码
    public static final String GRANT_TYPE_REFRESH = "refresh_token";      // 刷新令牌

    public static final String KEY_TOKEN = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static String client_id = "";                            // 应用ID
    public static String client_secret = "";                        // 私钥

    // debug 使用，正常情况下慎用
    private static boolean debug_remove_auto_token = false;          // 移除自定义的token

    /**
     * 为所有请求移除 token，debug 时使用
     */
    public static void removeAutoToken() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement traceElement : stackTraceElements) {
            if (traceElement.toString().contains("com.gcssloop.diycode_test.test_api")) {
                debug_remove_auto_token = true;
                break;
            }
        }
    }

    public static void resetAutoToken() {
        debug_remove_auto_token = false;
    }

    public static boolean getRemoveAutoTokenState() {
        return debug_remove_auto_token;
    }
}
