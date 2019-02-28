package com.aooled_laptop.appintro.scrollview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aooled_laptop.appintro.MainActivity;
import com.aooled_laptop.appintro.R;

public class ScrollViewActivity extends AppCompatActivity {
    private LinearLayout llAnim;
    private MyScrollView scrollView;
    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);
        llAnim = findViewById(R.id.ll_anim);
        scrollView = findViewById(R.id.scrollView);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScrollViewActivity.this, MainActivity.class));
            }
        });
        scrollView.setOnScrollChangedListener(new MyScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChange(int top, int oldTop) {
                if (top > oldTop){

                }else{

                }
            }
        });
    }
}
