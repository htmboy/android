package com.aooled_laptop.httpupload;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aooled_laptop.httpupload.error.ParseError;
import com.aooled_laptop.httpupload.error.TimeoutError;
import com.aooled_laptop.httpupload.error.URLError;
import com.aooled_laptop.httpupload.error.UnknowHostError;
import com.aooled_laptop.httpupload.task.FileBinary;
import com.aooled_laptop.httpupload.task.HttpListener;
import com.aooled_laptop.httpupload.task.InputStreamBinary;
import com.aooled_laptop.httpupload.task.OnBinaryProgressListener;
import com.aooled_laptop.httpupload.task.Request;
import com.aooled_laptop.httpupload.task.RequestExecutor;
import com.aooled_laptop.httpupload.task.RequestMethod;
import com.aooled_laptop.httpupload.task.Response;
import com.aooled_laptop.httpupload.task.StringRequest;
import com.aooled_laptop.httpupload.util.Constants;
import com.aooled_laptop.httpupload.util.Logger;
import com.aooled_laptop.httpupload.util.ThreadUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv1, tv2, tv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        findViewById(R.id.btn_get).setOnClickListener(this);
        findViewById(R.id.btn_post).setOnClickListener(this);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);

    }

    /**
     * 异步执行get请求
     */
    private void requestGet(){
        Request<String> request = new StringRequest(Constants.URL_UPLOAD);
        request.add("username", "htmboy");
        request.add("password", "123456");
        // 执行器 使用枚举
        RequestExecutor.INTANCE.execute(request, new HttpListener<String>() {

            @Override
            public void onSucceed(Response<String> response) {
                Logger.i("Activity 接受到的响应码:" + response.getResponseCode());

                String str = response.get();
                Logger.i("Activity 接受到的结果:" + str);
            }

            @Override
            public void onFailed(Exception e) {
                if (e instanceof ParseError){
                    // 数据解析异常
                    Logger.d("数据解析异常");
                }else if (e instanceof TimeoutError){
                    // 超时
                    Logger.d("超时");
                }else if (e instanceof UnknowHostError){
                    // 没有找到服务器
                    Logger.d("没有找到服务器");
                }else if (e instanceof URLError){
                    // URL格式错误
                    Logger.d("URL格式错误");
                }

            }
        });
    }


    /**
     * 异步执行post请求
     */
    private void requestPost(){
        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(rootPath + "/image/01.jpg");
        File file2 = new File(rootPath + "/image/02.jpg");
        File file3 = new File(rootPath + "/image/03.jpg");
        File file4 = new File(rootPath + "/image/04.jpg");

        Request<String> request = new StringRequest(Constants.URL_UPLOAD, RequestMethod.POST);
        request.add("username", "htmboy");
        request.add("password", "123456");
        FileBinary fileBinary = new FileBinary(file);
        fileBinary.setProgressListener(1, onBinaryProgressListener);
        request.add("image", fileBinary);
        FileBinary fileBinary2 = new FileBinary(file2);
        fileBinary2.setProgressListener(2,onBinaryProgressListener );
        request.add("image2", fileBinary2);
        FileBinary fileBinary3 = new FileBinary(file3);
        fileBinary3.setProgressListener(3,onBinaryProgressListener );
        request.add("image3", fileBinary3);

//        try {
//            request.add("image3", new InputStreamBinary(new FileInputStream(file3),"ban.jpg", "image/jpeg"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        RequestExecutor.INTANCE.execute(request, new HttpListener<String>() {

            @Override
            public void onSucceed(Response<String> response) {
                Logger.i("Activity 接受到的响应码:" + response.getResponseCode());

                String str = response.get();
                Logger.i("Activity 接受到的结果:" + str);
            }

            @Override
            public void onFailed(Exception e) {
                if (e instanceof ParseError){
                    // 数据解析异常
                }else if (e instanceof TimeoutError){
                    // 超时

                }else if (e instanceof UnknowHostError){
                    // 没有找到服务器
                }else if (e instanceof URLError){
                    // URL格式错误
                }

            }
        });
    }

    private OnBinaryProgressListener onBinaryProgressListener = new OnBinaryProgressListener() {
        @Override
        public void onProgress(int what, int progress) {
            if (what == 1)
                tv1.setText("文件1, 进度: " + progress);
            if (what == 2)
                tv2.setText("文件2, 进度: " + progress);
            if (what == 3)
                tv3.setText("文件3, 进度: " + progress);
        }

        @Override
        public void onError(int what) {

        }
    };

    /**
     * post请求
     */
    private void postRequest(){
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                requestPost();
            }
        });
    }
    /**
     * get请求
     */
    private void getRequest(){
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                requestGet();
            }
        });
    }
    // 执行get请求, 此方法在子线程中执行

    /**
     * 读取数据信息
     * @param inputStream
     * @throws IOException
     */
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
            case R.id.btn_post:{
                postRequest();
                break;
            }
            default:
                break;
        }
    }
}
