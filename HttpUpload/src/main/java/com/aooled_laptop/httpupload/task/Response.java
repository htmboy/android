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
    private int respinseCode;
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

    public Response(Request request, int respinseCode, Map<String, List<String>> responseHeaders, String result, Exception exception) {
        this.request = request;
        this.respinseCode = respinseCode;
        this.responseHeaders = responseHeaders;
        this.result = result;
        this.exception = exception;
    }

    public Request getRequest() {
        return request;
    }

    public int getRespinseCode() {
        return respinseCode;
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
