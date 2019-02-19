package com.aooled_laptop.httpupload.task;

import com.aooled_laptop.httpupload.util.Logger;

public class RequestTask implements Runnable {
    private Request request;
    private HttpListener httpListener;
    public RequestTask(Request request, HttpListener httpListener){
        this.request = request;
        this.httpListener = httpListener;
    }
    @Override
    public void run() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Logger.i("执行请求: " + request.toString());

        // TODO ...执行请求
        Response response = new Response(request, 200, null, "我是响应数据", null);
        // 发送响应数据到主线程
        Message message = new Message(response, httpListener);

        Poster.getInstance().post(message);
    }

}
