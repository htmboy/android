package com.aooled_laptop.httpupload.task;

import android.util.Log;

import com.aooled_laptop.httpupload.error.ParseError;
import com.aooled_laptop.httpupload.error.TimeoutError;
import com.aooled_laptop.httpupload.error.URLError;
import com.aooled_laptop.httpupload.error.UnknowHostError;
import com.aooled_laptop.httpupload.util.Constants;
import com.aooled_laptop.httpupload.util.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class RequestTask<T> implements Runnable {

    private Request<T> mRequest;
    private HttpListener<T> httpListener;

    public RequestTask(Request<T> request, HttpListener<T> httpListener){
        this.mRequest = request;
        this.httpListener = httpListener;
    }
    @Override
    public void run() {
        Exception exception = null;
        int responseCode = -1;
        Map<String, List<String>> responseHeaders = null;
        byte[] responseBody = null;
        Logger.i("执行请求: " + mRequest.toString());
        String urlStr = mRequest.getUrl();
        RequestMethod method = mRequest.getMethod();
        Logger.i("url: " + urlStr);
        Logger.i("method: " + method);

        HttpURLConnection urlConnection = null;
        OutputStream outputStream = null;
        try {
            URL url = new URL(urlStr);

            /**
             * https的处理
             */
            urlConnection = (HttpURLConnection) url.openConnection();

            if (urlConnection instanceof HttpsURLConnection){
                HttpsURLConnection httpsURLConnection =  ((HttpsURLConnection)urlConnection);
                SSLSocketFactory sslSocketFactory = mRequest.getSslSocketFactory();
                if (sslSocketFactory != null)
                    httpsURLConnection.setSSLSocketFactory(sslSocketFactory); // https证书相关信息
                HostnameVerifier hostnameVerifier = mRequest.getHostnameVerifier();
                if(hostnameVerifier != null)
                    httpsURLConnection.setHostnameVerifier(hostnameVerifier); // 服务器主机认证
            }
            // 设置基础信息, 即请求头
            urlConnection.setRequestMethod(method.value());
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(method.isOutputMethod());
            setHeader(urlConnection, mRequest);
            Logger.i("1");
            // 发送数据
            if (method.isOutputMethod()){
                outputStream = urlConnection.getOutputStream();
                mRequest.onWriteBody(outputStream);
            }

            // 读取响应
            responseCode = urlConnection.getResponseCode();
            Logger.i("ResponseCode: " + responseCode);
            responseHeaders = urlConnection.getHeaderFields();
            if (hasResponseBody(method, responseCode)) {
                InputStream inputStream = getInputStream(urlConnection, responseCode);
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

                int len;
                byte[] buffer = new byte[2048];
                while ((len = inputStream.read(buffer)) != -1){
                    arrayOutputStream.write(buffer, 0, len);
                }

                arrayOutputStream.close();
                responseBody = arrayOutputStream.toByteArray();
                //Logger.i(new String(responseBody));
            }else
                Logger.i("没有响应包体!");

        } catch (SocketTimeoutException e){
            exception = new TimeoutError("Timeout.");
        } catch (MalformedURLException e){
            exception = new URLError("The url is error.");
        } catch (UnknownHostException e){
            exception = new UnknowHostError("The server is not found.");
        } catch (IOException e) {

            exception = e;
        }finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    exception = e;
                }
            }
        }
        // 解析服务器的数据
        T t = null;
        try {
            t = mRequest.parseResponse(responseBody);
        } catch (Exception throwable) {
            exception = new ParseError("The data parse error.");
        }

        Response<T> response = new Response<>(mRequest, responseCode, responseHeaders, exception);
        response.setResponseResult(t);
        // 发送响应数据到主线程
        Message message = new Message(response, httpListener);
        Poster.getInstance().post(message);
    }

    /**
     * 判断是否由响应包体
     * @param method 请求方法
     * @param responseCode 服务器的响应码
     * @return
     */
    private boolean hasResponseBody(RequestMethod method, int responseCode){
        // http://www.w3school.com.cn/tags/html_ref_httpmessages.asp
        // head 请求没有包体的
        return method != RequestMethod.HEAD && !(100 <= responseCode && responseCode <200)
                && responseCode != 204 && responseCode != 205 && !(300 <= responseCode && responseCode < 400);
    }

    /**
     * 根据响应码拿到服务器的流
     * @param urlConnection
     * @param responseCode
     */
    private InputStream getInputStream(HttpURLConnection urlConnection, int responseCode) throws IOException {
        InputStream inputStream;
        if(responseCode >= 400)
            inputStream = urlConnection.getErrorStream();
        else
            inputStream = urlConnection.getInputStream();
        String contentEncoding = urlConnection.getContentEncoding();
        if(contentEncoding != null && contentEncoding.contains("gzip"))
            inputStream = new GZIPInputStream(inputStream);
        return inputStream;
    }

    /**
     * 给rulconnection 设置请求头
     * @param urlConnection
     * @param request
     */
    private void setHeader(HttpURLConnection urlConnection, Request request){
        Map<String, String> requestHeader = request.getRequestHeader();
        // 处理contentType
        String contentType = request.getContentType();
        requestHeader.put("Content-Type", contentType);
        // 处理contentlength
        long contentLength = request.getContentLength();
        requestHeader.put("Content-Length", Long.toString(contentLength));

        for (Map.Entry<String, String> stringStringEntry :requestHeader.entrySet()){
            String headKey = stringStringEntry.getKey();
            String headValue = stringStringEntry.getValue();
            Logger.d(headKey + "=" + headValue);
            urlConnection.setRequestProperty(headKey, headValue);
        }
    }

}
