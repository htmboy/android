package com.aooled_laptop.httpupload.task;

public interface HttpListener {
    /**
     * 请求成功
     * @param response 响应数据
     */
    void onSucceed(Response response);

    /**
     * 请求失败
     * @param e 失败的异常信息
     */
    void onFailed(Exception e);
}
