package com.aooled_laptop.httpupload.task;

public interface HttpListener<T> {
    /**
     * 请求成功
     * @param response 响应数据
     */
    void onSucceed(Response<T> response);

    /**
     * 请求失败
     * @param e 失败的异常信息
     */
    void onFailed(Exception e);
}
