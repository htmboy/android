package com.aooled_laptop.httpurl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpImage extends Thread {
    private ImageView imageView;
    private String url;
    private Handler handler;

    public HttpImage(String url, Handler handler, ImageView imageView) {
        this.url = url;
        this.handler = handler;
        this.imageView = imageView;
    }

    @Override
    public void run() {
        super.run();
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            InputStream in = conn.getInputStream();
            final Bitmap bitmap = BitmapFactory.decodeStream(in);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
