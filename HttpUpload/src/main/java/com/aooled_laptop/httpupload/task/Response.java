package com.aooled_laptop.httpupload.task;

import java.util.List;
import java.util.Map;

public class Response<T> {
    /**
     * 请求对象
     */
    private Request request;
    /**
     * 服务器的响应码
     */
    private int responseCode;
    /**
     * 服务器的响应数据
     */
    private T responseBody;

    /**
     * 服务器的响应头
     */
    private Map<String, List<String>> responseHeaders;
    /**
     * 请求过程中发生的错误
     */
    private  Exception exception;
//     public Response(Request request, int responseCode, Map<String, List<String>> responseHeaders, byte[] responseBody, Exception exception) {
    public Response(Request request, int responseCode, Map<String, List<String>> responseHeaders, Exception exception) {
        this.request = request;
        this.responseCode = responseCode;
        this.responseHeaders = responseHeaders;
        this.exception = exception;
    }

    public Request getRequest() {
        return request;
    }

    public int getResponseCode() {
        return responseCode;
    }

    /**
     * 设置响应
     * @param responseResult
     */
    public void setResponseResult(T responseResult){
        this.responseBody = responseResult;
    }
//    public byte[] getResponseBody(){
//        return responseBody;
//    }

    /**
     * 拿到服务器的响应
     * @return
     */
    public T get(){
        return responseBody;
    }

    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }

    Exception getException() {
        return exception;
    }
}
