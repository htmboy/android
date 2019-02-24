package com.aooled_laptop.handler_01;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * 更新ui的4中方法
 * handler.post
 * handler.sendEmptyMessage
 * runOnUiThread
 * textView.post
 *
 */
public class Five extends AppCompatActivity {

    private TextView textView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            textView.setText("不ok");
        }
    };

    private void handler1(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("ok");
            }
        });
    }

    private void handler2(){
        handler.sendEmptyMessage(1);
    }

    private void updateUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText("很ok");
            }
        });
    }

    private void viewUI(){
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("viewUI ok");
            }
        });
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.five);
        textView = findViewById(R.id.textView);
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    viewUI();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
