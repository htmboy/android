package com.aooled_laptop.tofragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aooled_laptop.tofragment.fragment.ResourceFragment;
import com.aooled_laptop.tofragment.fragment.ResultFragment;

public class MainActivity extends AppCompatActivity implements ResourceFragment.MyListerner {

    private TextView tv_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_show = findViewById(R.id.tv_show);
    }

    @Override
    public void sendMessage(String str) {
        if (str != null && !"".equals(str))
            tv_show.setText(str);
    }
}
