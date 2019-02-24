package com.aooled_laptop.handler_01;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class FourActivity extends AppCompatActivity implements View.OnClickListener {
    // 创建主线程 handler
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Message message = new Message();
            System.out.println("main Handler");
            // 调用子线程 handler 向子线程发送消息
            threadHandler.sendMessageDelayed(message, 1000);
        }
    };

    private Handler threadHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.four);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        HandlerThread thread = new HandlerThread("handlerThread");
        thread.start();
        // 创建子线程handler 并于handlerThread关联 handlerThread有一个luuper变量
        threadHandler = new Handler(thread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                Message message = new Message();
                System.out.println("thread handler");
                // 调用主线程handler 向主线程发送消息
                handler.sendMessageDelayed(message, 1000);

            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                handler.sendEmptyMessage(1);
                break;
            case R.id.button2:
                handler.removeMessages(1);
                break;
            default:
                break;
        }
    }
}
