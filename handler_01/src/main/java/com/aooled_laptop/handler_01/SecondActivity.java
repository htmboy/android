package com.aooled_laptop.handler_01;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    // 整个应用程序是通过 ActivityThread 进行创建 并回调Activity的各种方法
    // activityThread 会默认创建一个线程, main线程, 各种应用程序通过main线程创建 main中会创建一个looper 和messagequeue对象
    // threadlocal 在线程当中, 保存变量信息
    // 在这里创建的handler拿到的是主线程
    private Handler handler = new Handler(){
        @Override
        // ui线程[main线程]
        public void handleMessage(Message msg) {
            System.out.println("UI----->" + Thread.currentThread());
        }
    };

    class MyThread extends Thread{
        public Handler handler;
        public Looper looper;

        @Override
        public void run() {
            looper.prepare();
            // 在这里创建的handler拿到的是子线程
            handler = new Handler(){
                @Override
                // 子线程[Thread-6190]
                public void handleMessage(Message msg) {
                    System.out.println("currentThread: " + Thread.currentThread());
                }
            };
            Looper.loop();
        }
    }

    private MyThread thread;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("hello handler");
        setContentView(textView);
        thread = new MyThread();
        thread.start();
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        thread.handler.sendEmptyMessage(1);
//        handler.sendEmptyMessage(1);
        handler = new Handler(thread.looper){ // 这个looper是空指针 是由多线程引起的 这个时候就需要由handlerThread来处理
            @Override
            public void handleMessage(Message msg) {
                System.out.println(msg);
            }
        };
        handler.sendEmptyMessage(1);
    }
}
