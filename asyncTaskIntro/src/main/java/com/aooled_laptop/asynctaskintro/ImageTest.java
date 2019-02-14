package com.aooled_laptop.asynctaskintro;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ImageTest extends Activity {
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private static String url = "https://www.baidu.com/img/xinshouye_f097208390e839e5b5295804227d94e9.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);
        mImageView = findViewById(R.id.image);
        mProgressBar = findViewById(R.id.progressbar);
        // AsycTask() 的execute() 方法会调用doInBackground() 方法
        new MyAsycTask().execute(url);
    }

    class MyAsycTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mProgressBar.setVisibility(View.GONE);
            mImageView.setImageBitmap(bitmap);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            // 以下是访问网络最基本的参数
            String url = strings[0]; // 取出对应URL
            Bitmap bitmap = null;
            URLConnection connection;
            InputStream is;
            try {
                connection = new URL(url).openConnection();
                is = connection.getInputStream(); // 获取输入流
                BufferedInputStream bis = new BufferedInputStream(is);
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bitmap = BitmapFactory.decodeStream(bis);
                is.close();
                bis.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }
}
