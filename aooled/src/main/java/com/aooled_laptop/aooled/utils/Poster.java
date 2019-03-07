package com.aooled_laptop.aooled.utils;


import android.os.Handler;
import android.os.Looper;

/**
 * 为了把信息发到主线程, 需要用到Handler
 * 这是一个信使类
 */
public class Poster extends Handler {
    private static Poster instance;
    public static Poster getInstance(){
        if (instance == null)
            synchronized (Poster.class){
                if (instance == null)
                    instance = new Poster();
            }
        return instance;
    }

    private Poster(){
        super(Looper.getMainLooper());
    }
}
