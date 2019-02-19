package com.aooled_laptop.httpupload;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.aooled_laptop.httpupload.util.Constants;
import com.aooled_laptop.httpupload.util.Logger;
import com.aooled_laptop.httpupload.util.ThreadUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        findViewById(R.id.btn_get).setOnClickListener(this);
        findViewById(R.id.btn_head).setOnClickListener(this);
        findViewById(R.id.btn_post).setOnClickListener(this);

    }

    // get请求
    private void getRequest(){
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                executeGet();
            }
        });
    }
    // 执行get请求, 此方法在子线程中执行
    private void executeGet(){
        // 我们暂时使用URLConection来执行网络请求
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        // 1, 建立连接
        try {
            URL url = new URL(Constants.URL_UPLOAD);
            urlConnection = (HttpURLConnection) url.openConnection();
//            if (urlConnection instanceof HttpsURLConnection){
//            ((HttpsURLConnection)httpURLConnection).setSSLSocketFactory();
//            ((HttpsURLConnection)httpURLConnection).setHostnameVerifier();
//             }
             urlConnection.setRequestMethod("GET"); // 默认是get
//              urlConnection.setRequestProperty(, );
                // get方法 不能 获取 输出流 getOutputStream
                // 2, 连接数据
                urlConnection.connect();
                // 3, 拿到响应
                int responserCode = urlConnection.getResponseCode();
                if (responserCode == 200){
                    inputStream = urlConnection.getInputStream();
                    readServerData(inputStream);
                }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (urlConnection != null)
                urlConnection.disconnect();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

//

    }


    // head 请求
    private void headRequest(){
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                executeHead();
            }
        });
    }
    private void executeHead(){
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        // 1, 建立连接
        try {
            URL url = new URL(Constants.URL_UPLOAD);
            urlConnection = (HttpURLConnection) url.openConnection();
            // head服务器只能发送响应头, 不能发送body, 客户端也读不到body
            urlConnection.setRequestMethod("HEAD");

            // 2, 连接数据
            urlConnection.connect();
            // 3, 拿到响应
            int responserCode = urlConnection.getResponseCode();
            if (responserCode == 200){
                // 获取响应头
                Map<String, List<String>> stringListMap = urlConnection.getHeaderFields();
                Set<Map.Entry<String, List<String>>> entries = stringListMap.entrySet();
                for(Map.Entry<String, List<String>> entry : entries){
                    String headKey = entry.getKey();
                    List<String> headValues = entry.getValue();
                    for (String HeadValue : headValues){
                        Logger.i(headKey + " => " + HeadValue);
                    }
                }
                inputStream = urlConnection.getInputStream();
                readServerData(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (urlConnection != null)
                urlConnection.disconnect();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    // post put delete 请求
    private void postRequest(){
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                executePost();
            }
        });
    }
    private void executePost() {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(Constants.URL_UPLOAD);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");

            urlConnection.connect();

            // post 有输出流
            OutputStream outputStream = urlConnection.getOutputStream();

            String iLikeYou = "i like you.";

            outputStream.write(iLikeYou.getBytes());

            int responserCode = urlConnection.getResponseCode();
            if (responserCode == 200){
                inputStream = urlConnection.getInputStream();
                readServerData(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (urlConnection != null)
                urlConnection.disconnect();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    // 读取数据
    private void readServerData(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, len);
        }
        outputStream.close();
        inputStream.close();
        String data = new String(outputStream.toByteArray());
        Logger.i("服务器响应数据:" + data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get:{
                getRequest();
                break;
            }
            case R.id.btn_head:{
                headRequest();
                break;
            }
            case R.id.btn_post:{
                postRequest();
                break;
            }
            default:
                break;
        }
    }
}
