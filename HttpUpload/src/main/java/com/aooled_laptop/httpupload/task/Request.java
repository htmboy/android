package com.aooled_laptop.httpupload.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Request {
    /**
     * 请求地址
     */
    private String url;
    /**
     * 请求方法
     */
    private RequestMethod method;
    /**
     * 请求参数
     */
    private List<KeyValue> keyValues;

    public Request(String url) {
        this(url, RequestMethod.GET);
    }

    public Request(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
        this.keyValues = new ArrayList<>();
    }

    public void add(String key, int value){
        keyValues.add(new KeyValue(key, Integer.toString(value)));
    }

    public void add(String key, long value){
        keyValues.add(new KeyValue(key, Long.toString(value)));
    }

    public void add(String key, String value){
        keyValues.add(new KeyValue(key, value));
    }

    public void add(String key, File value){

        keyValues.add(new KeyValue(key, value));
    }

    public String toString(){
        return "url:" + url + "; method: " + method + "; params: " + keyValues.toString();
    }
}
