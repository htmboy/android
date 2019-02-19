package com.aooled_laptop.httpupload.task;

import java.util.List;
import java.util.Map;

public class Response {
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
    private String result;

    /**
     * 服务器的响应头
     */
    private Map<String, List<String>> responseHeaders;
    /**
     * 请求过程中发生的错误
     */
    private  Exception exception;

    public Response(Request request, int responseCode, Map<String, List<String>> responseHeaders, String result, Exception exception) {
        this.request = request;
        this.responseCode = responseCode;
        this.responseHeaders = responseHeaders;
        this.result = result;
        this.exception = exception;
    }

    public Request getRequest() {
        return request;
    }

    public int getResponseCode() {
        return responseCode;
    }


    public String getResult() {
        return result;
    }

    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }

    Exception getException() {
        return exception;
    }
}
