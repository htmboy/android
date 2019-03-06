package com.aooled_laptop.aooled.task;

public class Message implements Runnable{
    private Response response;

    private HttpListener httpListener;

    public Message(Response response, HttpListener httpListener) {
        this.response = response;
        this.httpListener = httpListener;
    }

    @Override
    public void run() {
        // 这里被回调到主线程
        Exception exception = response.getException();
        if(exception != null)
            httpListener.onFailed(exception);
        else // 如果没有发生异常则请求成功
            httpListener.onSucceed(response);
    }
}
