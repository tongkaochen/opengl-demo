package com.tifone.opengl.demo.common.util;

import android.util.Log;

/**
 * Create by Tifone on 2019/4/6.
 */
public class LoggerConfig {
    public static final boolean DEBUG = true;
    public static final String TAG = "tifone";

    public static void tlogd(String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void tlogd(String msg, Throwable throwable) {
        if (DEBUG) {
            Log.d(TAG, msg, throwable);
        }
    }


    public static void tlogw(String msg) {
        if (DEBUG) {
            Log.w(TAG, msg);
        }
    }

    public static void tlogw(String msg, Throwable throwable) {
        if (DEBUG) {
            Log.w(TAG, msg, throwable);
        }
    }

    public static void tloge(String msg) {
        Log.e(TAG, msg);
    }

    public static void tloge(String msg, Throwable throwable) {
        Log.e(TAG, msg, throwable);
    }

    public static void tlogi(String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void tlogi(String msg, Throwable throwable) {
        if (DEBUG) {
            Log.i(TAG, msg, throwable);
        }
    }
}
