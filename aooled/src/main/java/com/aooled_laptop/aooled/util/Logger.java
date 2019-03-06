package com.aooled_laptop.aooled.util;

import android.util.Log;

/**
 * 封装简单的logger类
 */
public class Logger {

    // 打印log的tag
    public static final String TAG = "IMOOCUpload";

    // 控制打印log的开关
    public static final boolean DEBUG = true;

    public static String getMessage(Object o) {
        return o == null ? "null" : o.toString();
    }

    // 打印正常信息
    public static void i(Object msg){
        if (DEBUG)
            Log.i(TAG, getMessage(msg));
    }

    // 打印调试信息
    public static void d(Object msg){
        if (DEBUG)
            Log.d(TAG, getMessage(msg));
    }

    // 打印警告信息
    public static void w(Object msg){
        if (DEBUG)
            Log.w(TAG, getMessage(msg));
    }

    // 打印错误信息
    public static void e(Object msg){
        if (DEBUG)
            Log.w(TAG, getMessage(msg));
    }
}
