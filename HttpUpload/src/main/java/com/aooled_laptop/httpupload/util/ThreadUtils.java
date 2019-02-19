package com.aooled_laptop.httpupload.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 安卓的主线程是不能处理网络类
 * 需要用到线程类处理网络网络类
 */
public class ThreadUtils {
    // 使用了单例的方法建立一个线程池,
    private static ExecutorService sExecutorService = Executors.newSingleThreadExecutor();

    public static void execute(Runnable runnable) {
        sExecutorService.execute(runnable);
    }

}
