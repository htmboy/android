package com.aooled_laptop.handler_01;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ThreeActivity extends AppCompatActivity {
    private TextView text;
    private HandlerThread thread;
    private Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text = new TextView(this);
        text.setText("hander Thread");
        setContentView(text);
        thread = new HandlerThread("handler thread");
        thread.start();
        handler = new Handler(thread.getLooper()){
            @Override
            // hander 线程
            public void handleMessage(Message msg) {
                System.out.println("current thread -----> " + Thread.currentThread());
            }
        };
        handler.sendEmptyMessage(1);

    }
}
