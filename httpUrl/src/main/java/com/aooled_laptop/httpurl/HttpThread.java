package com.aooled_laptop.httpurl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpThread extends Thread {

    private String url;
    private WebView webView;
    private Handler handler;
    private ImageView imageView;

    public HttpThread(String url, WebView webView, Handler handler){
        this.url = url;
        this.webView = webView;
        this.handler = handler;
    }

    public HttpThread(String url, ImageView imageView, Handler handler){
        this.url = url;
        this.imageView = imageView;
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        try {
            URL httpUrl = new URL(url);
            // 如果是https 就用HttpsURLConnection
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");

            // 获取图片
            conn.setDoInput(true);
            InputStream in = conn.getInputStream();

            FileOutputStream out = null;
            File downloadFile = null;
            String fileName = String.valueOf(System.currentTimeMillis());
            // 判断sd卡是否挂载
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                File parent = Environment.getExternalStorageDirectory();
                downloadFile = new File(parent, fileName);
                out = new FileOutputStream(downloadFile);
            }
            byte[] b = new byte[2 * 1024];
            int len;
            if(out != null){
                while((len = in.read(b)) != -1) {
                    out.write(b,0, len);
                }
            }
            final Bitmap bitmap = BitmapFactory.decodeFile(downloadFile.getAbsolutePath());

            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });
            // 获取网页内容f
//            final StringBuffer sb = new StringBuffer();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String str;
//            while((str = reader.readLine()) != null){
//                sb.append(str);
//            }
//
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    webView.loadData(sb.toString(), "text/html;charset=utf-8", null);
//                }
//            });
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
