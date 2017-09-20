package com.danmo.commonutil.log;

import android.util.Log;

public class Logger {

    private static String DEFAULT_TAG = "danmo";

    private static Config mConfig;

    private Logger() {

    }

    public static Config init() {
        mConfig = new Config(DEFAULT_TAG);
        return mConfig;
    }

    public static Config init(String tag) {
        mConfig = new Config(tag);
        return mConfig;
    }

    public static void v(String message) {
        log(Config.LEVEL_VERBOSE, mConfig.getTag(), message);
    }

    public static void d(String message) {
        log(Config.LEVEL_DEBUG, mConfig.getTag(), message);
    }

    public static void i(String message) {
        log(Config.LEVEL_INFO, mConfig.getTag(), message);
    }

    public static void w(String message) {
        log(Config.LEVEL_WARN, mConfig.getTag(), message);
    }

    public static void e(String message) {
        log(Config.LEVEL_ERROR, mConfig.getTag(), message);
    }

    public static void v(String tag, String message) {
        log(Config.LEVEL_VERBOSE, tag, message);
    }

    public static void d(String tag, String message) {
        log(Config.LEVEL_DEBUG, tag, message);
    }

    public static void i(String tag, String message) {
        log(Config.LEVEL_INFO, tag, message);
    }

    public static void w(String tag, String message) {
        log(Config.LEVEL_WARN, tag, message);
    }

    public static void e(String tag, String message) {
        log(Config.LEVEL_ERROR, tag, message);
    }

    private static void log(int level, String tag, String message) {
        if (mConfig.getLevel() == Config.LEVEL_NONE) {
            return;
        }

        if (level < mConfig.getLevel()) {
            return;
        }

        switch (level) {
            case Config.LEVEL_VERBOSE:
                Log.v(tag, message);
                break;
            case Config.LEVEL_DEBUG:
                Log.d(tag, message);
                break;
            case Config.LEVEL_INFO:
                Log.i(tag, message);
                break;
            case Config.LEVEL_WARN:
                Log.w(tag, message);
                break;
            case Config.LEVEL_ERROR:
                Log.e(tag, message);
                break;
        }

    }
}
