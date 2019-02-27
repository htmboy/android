package com.aooled_laptop.tofragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.aooled_laptop.tofragment.fragment.ResourceFragment;

public class FtoFActivity extends AppCompatActivity implements ResourceFragment.MyListerner {

    private TextView tv_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftof);
        tv_show = findViewById(R.id.tv_show);
    }

    @Override
    public void sendMessage(String str) {
        if (str != null && !"".equals(str))
            tv_show.setText(str);
    }
}
