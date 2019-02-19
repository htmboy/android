package com.aooled_laptop.httpupload.task;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum RequestExecutor {
    // enum 枚举, 全局单例
    INTANCE;
    private ExecutorService mExecutorService;
    RequestExecutor(){
        mExecutorService = Executors.newSingleThreadExecutor();
    }

    /**
     * 执行一个请求
     * @param request : 请求对象
     */
    public void execute(Request request, HttpListener httpListener){

        mExecutorService.execute(new RequestTask(request, httpListener));
    }

}
