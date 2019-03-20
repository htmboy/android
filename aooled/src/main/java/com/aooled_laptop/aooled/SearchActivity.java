package com.aooled_laptop.aooled;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.search);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
