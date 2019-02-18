package com.aooled_laptop.httpurl;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DownLoadActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;
    private int count = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int result = msg.what;
            count += result;
            if(count == 3)
                textView.setText("download success!");
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.down);
        button = findViewById(R.id.button1);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        DownLoad load = new DownLoad(handler);
                        load.downLoadFile("http://139.199.77.144:8080/Upload/2019-01-26/154847496565.jpg");
                    }
                }.start();

            }
        });
    }
}
