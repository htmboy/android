package com.aooled_laptop.appintro.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aooled_laptop.appintro.MainActivity;
import com.aooled_laptop.appintro.R;

public class SplashActivity extends AppCompatActivity {
    private static final long DELAY_TIME = 3000L;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish(); // 结束fragment
            }
        }, DELAY_TIME);
    }
}
