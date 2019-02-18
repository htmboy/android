package com.aooled_laptop.httpurl;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class JsonActivity extends AppCompatActivity {
    private ListView listView;
    private JsonAdapter adapter;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json);
        listView = findViewById(R.id.listView);
        adapter = new JsonAdapter(this);
        String url = "http://139.199.77.144:8080";
        new HttpJson(url, listView, adapter, handler).start();

    }
}
