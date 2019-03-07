package com.aooled_laptop.aooled.task;

import android.text.TextUtils;
import android.util.Log;

import com.aooled_laptop.aooled.utils.CounterOutputStream;
import com.aooled_laptop.aooled.utils.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

public abstract class Request<T> {

    private String boundary = createBoundary();
    private String startBoundary = "--" + boundary;
    private String endBoundary = startBoundary + "--";
    /**
     * 请求地址
     */
    private String url;
    /**
     * 请求方法
     */
    private RequestMethod method;
    /**
     * 用户的请求头
     */
    private Map<String, String> mRequestHead;
    /**
     * contentType
     */
    private String mContentType;
    /**
     * 是否强制开启表单提交
     */
    private boolean enableFormData;
    /**
     * 请求参数
     */
    private List<KeyValue> mKeyValues;

    private String mCharSet = "utf-8";

    private SSLSocketFactory mSslSocketFactory;

    private HostnameVerifier mHostnameVerifier;

    public Request(String url) {
        this(url, RequestMethod.GET);
    }

    public Request(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
        this.mRequestHead = new HashMap<>();
        this.mKeyValues = new ArrayList<>();
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
        mKeyValues.add(new KeyValue(key, Integer.toString(value)));
    }

    public void add(String key, long value){
        mKeyValues.add(new KeyValue(key, Long.toString(value)));
    }

    public void add(String key, String value){
        mKeyValues.add(new KeyValue(key, value));
    }

    public void add(String key, Binary value){

        mKeyValues.add(new KeyValue(key, value));
    }

    public SSLSocketFactory getSslSocketFactory() {
        return mSslSocketFactory;
    }

    public HostnameVerifier getHostnameVerifier() {
        return mHostnameVerifier;
    }

    /**
     * 拿到请求的完整的url
     * @return
     */
    public String getUrl() {
        StringBuilder urlBuilder = new StringBuilder(url);
        String paramsStr = buildParamsString();

        if(!method.isOutputMethod() && getMethod() != method.JSON){
            // http:www.yanzhenjie.com?name=1234
            if (paramsStr.length() > 0 && url.contains("?") && url.contains("=")) {
                urlBuilder.append("&");
                // http:www.yanzhenjie.com?
            }
            else if (paramsStr.length() > 0 && !url.endsWith("?")) {
                urlBuilder.append("?");
            }
            urlBuilder.append(paramsStr);
        }
        return urlBuilder.toString();
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setHeader(String key, String value){
        mRequestHead.put(key, value);
    }

    /**
     * 拿到请求头
     * @return
     */
    Map<String, String> getRequestHeader(){
        return mRequestHead;
    }

    public void setContentType(String contentType){
        this.mContentType = contentType;
    }

    /**
     * 设置提交参数的编码格式
     * @param charset
     */
    public void setCharset(String charset){
        mCharSet = charset;
    }

    public String getContentType(){
        if (!TextUtils.isEmpty(mContentType))
            // 返回表单特殊contentType
            return mContentType;
//        else if(getMethod() == RequestMethod.JSON)
//            return "application/json";
        else if(enableFormData || hasFile()) {// 是否强制表单提交, 是否有文件(文件只能通过模拟表单和body提交)
            /**
             *  Content-Type:multipart/form-data; boundary=--d1sajoapidfgl
             *  -----------------------------------------
             *
             */
            return "multipart/form-data; boundary=" + boundary; // 提交表单特殊contentType
        }
        // 如果用户没有设置,并且没有文件, 则视为一般性的提交
        return "application/x-www-form-urlencoded";
    }

    /**
     * 判断是否有文件
     * @return
     */
    protected boolean hasFile(){
        for (KeyValue keyValue : mKeyValues){
            Object value = keyValue.getValue();
            if (value instanceof Binary)
                return true;
        }
        return false;
    }

    /**
     * 拿到包体的大小
     * @return
     */
    public long getContentLength() {
        // post类型的请求才需要知道, 一般都是上传文件的时候
        // 普通数据的post不需要
        // form : 1, 普通string的表单 2, 带文件的表单
        CounterOutputStream counterOutputStream = new CounterOutputStream();
        try {
            onWriteBody(counterOutputStream);
        } catch (Exception e) {
            return 0;
        }
        return counterOutputStream.get();
    }

    /**
     * 写出包体的方法
     * @param outputStream
     * @throws IOException
     */
    public void onWriteBody(OutputStream outputStream) throws IOException {
        if(enableFormData || hasFile())
            writeFormData(outputStream);
        else
            writeStringData(outputStream);

    }

    /**
     * 写出普通数据
     * @param outputStream
     */
    private void writeStringData(OutputStream outputStream) throws IOException {
        Logger.i("已执行");
        String params = "";
        if (getMethod() == RequestMethod.JSON)
            params = buildParamsJson();
        else
            params = buildParamsString();
        outputStream.write(params.getBytes());
    }

    /**
     * 写出表单数据
     * @param outputStream
     */
    private void writeFormData(OutputStream outputStream) throws IOException {
        for (KeyValue mKeyValue : mKeyValues) {
            String key = mKeyValue.getKey();
            Object value = mKeyValue.getValue();
            if (value instanceof Binary)
                writeFormFileData(outputStream, key, (Binary) value);
            else
                writeFormStringData(outputStream, key, (String) value);
            outputStream.write("\r\n".getBytes());
        }
        outputStream.write(endBoundary.getBytes());
    }

    /**
     * 写出表单中的文件item
     * @param outputStream
     * @param key
     * @param value
     */
    private void writeFormFileData(OutputStream outputStream, String key, Binary value) throws IOException {

        String builder = startBoundary + "\r\n" +
                "Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" +
                value.getFileName() + "\"" + "\r\n" +
                "Content-Type: " + value.getMimeType() +
                "\r\n\r\n";
        outputStream.write(builder.getBytes(mCharSet));

        if (outputStream instanceof CounterOutputStream) {
            ((CounterOutputStream)outputStream).write(value.getBinaryLength());
        } else {
            value.onWriteBinary(outputStream);
        }

    }

    /**
     * 写出表单中的string item
     * @param outputStream
     * @param key
     * @param value
     */
    private void writeFormStringData(OutputStream outputStream, String key, String value) throws IOException {
        String builder = startBoundary + "\r\n" +
                "Content-Disposition: form-data; name=\"" + key + "\"" + "\r\n" +
                "Content-Type: text/plain; charset=\"" + mCharSet + "\"" + "\r\n" +
                "\r\n\r\n" +
                value;
        outputStream.write(builder.getBytes());
    }

    /**
     * 写出表单中的string item
     * @param outputStream
     * @param value
     */
    private void writeFormJsonData(OutputStream outputStream, JSONObject value) throws IOException {

        String builder = startBoundary + "\r\n" +
//                "Content-Disposition: form-data; name=\"" + key + "\"" + "\r\n" +
                "Content-Type: text/json; charset=\"" + mCharSet + "\"" + "\r\n" +
                "\r\n\r\n" +
                value.toString();
        outputStream.write(builder.getBytes());
        Log.i("Json", builder);
    }
    /**
     * 是否强制开启表单提交
     * @param enable
     */
    public void formData(boolean enable){
        if (!method.isOutputMethod())
            throw new IllegalArgumentException(method.value() + " is not support output");
        enableFormData = enable;
    }

    List<KeyValue> getmKeyValues() {
        return mKeyValues;
    }

    protected String createBoundary(){
        return "Imooc" + UUID.randomUUID();
    }

    /**
     * 以key=value&key1=value1的形式构建用户添加的所有的string
     * @return
     */
    protected String buildParamsString(){
            StringBuilder builder = new StringBuilder();
            for (KeyValue mKeyValue : mKeyValues) {
                Object value = mKeyValue.getValue();
                if (value instanceof String) {
                    builder.append("&");
                    try {
                        builder.append(URLEncoder.encode(mKeyValue.getKey(), mCharSet));
                        builder.append("=");
                        builder.append(URLEncoder.encode((String) value, mCharSet));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (builder.length() > 0)
                builder.deleteCharAt(0);
            return builder.toString();

    }

    protected String buildParamsJson(){
        JSONObject jsonObject = new JSONObject();
        for (KeyValue mKeyValue : mKeyValues) {
            String key = mKeyValue.getKey();
            String value = (String) mKeyValue.getValue();
            try {
                jsonObject.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject.toString();
    }

    /**
     * 解析服务器的数据
     * @param responseBody
     * @return
     */
    public abstract T parseResponse(byte[] responseBody) throws Exception;

    public String toString(){
        return "url:" + url + "; method: " + method + "; params: " + mKeyValues.toString();
    }
}
