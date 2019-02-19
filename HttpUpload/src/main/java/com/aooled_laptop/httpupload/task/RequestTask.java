package com.aooled_laptop.httpupload.task;

import com.aooled_laptop.httpupload.util.Logger;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class RequestTask implements Runnable {
    private Request request;
    private HttpListener httpListener;
    public RequestTask(Request request, HttpListener httpListener){
        this.request = request;
        this.httpListener = httpListener;
    }
    @Override
    public void run() {

        Exception exception = null;
        int responseCode = -1;
        Map<String, List<String>> responseHeaders = null;
        Logger.i("执行请求: " + request.toString());
        String urlStr = request.getUrl();
        RequestMethod method = request.getMethod();
        Logger.i("url: " + urlStr);
        Logger.i("method: " + method);
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlStr);
            /**
             * https的处理
             */
            urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection instanceof HttpsURLConnection){
                HttpsURLConnection httpsURLConnection =  ((HttpsURLConnection)urlConnection);
                SSLSocketFactory sslSocketFactory = request.getSslSocketFactory();
                if (sslSocketFactory != null)
                    httpsURLConnection.setSSLSocketFactory(sslSocketFactory); // https证书相关信息
                HostnameVerifier hostnameVerifier = request.getHostnameVerifier();
                if(hostnameVerifier != null)
                    httpsURLConnection.setHostnameVerifier(hostnameVerifier); // 服务器主机认证
            }
            OutputStream outputStream = urlConnection.getOutputStream();

        } catch (java.io.IOException e) {
            exception = e;
        }finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        // TODO ...执行请求
        Response response = new Response(request, responseCode, responseHeaders, "我是响应数据", exception);
        // 发送响应数据到主线程
        Message message = new Message(response, httpListener);

        Poster.getInstance().post(message);
    }

}
