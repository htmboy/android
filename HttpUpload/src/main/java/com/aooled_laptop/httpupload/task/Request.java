package com.aooled_laptop.httpupload.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

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

    private SSLSocketFactory mSslSocketFactory;

    private HostnameVerifier mHostnameVerifier;

    public Request(String url) {
        this(url, RequestMethod.GET);
    }

    public Request(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
        this.keyValues = new ArrayList<>();
    }

    /**
     * 设置ssl证书
     * @param sslSocketFactory
     */
    public void setSSLSocketFactory(SSLSocketFactory sslSocketFactory){
        this.mSslSocketFactory = sslSocketFactory;
    }

    /**
     * 设置服务器主机认证规则
     * @param hostnameVerifier
     */
    public void setHostnameVerifier(HostnameVerifier hostnameVerifier){
        this.mHostnameVerifier = hostnameVerifier;
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

    public SSLSocketFactory getSslSocketFactory() {
        return mSslSocketFactory;
    }

    public HostnameVerifier getHostnameVerifier() {
        return mHostnameVerifier;
    }

    public String getUrl() {
        return url;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public List<KeyValue> getKeyValues() {
        return keyValues;
    }

    public String toString(){
        return "url:" + url + "; method: " + method + "; params: " + keyValues.toString();
    }
}
