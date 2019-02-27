package com.aooled_laptop.tofragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * ListFragment的使用
 * listFragment的id是固定写法
 * 自带默认带布局
 * 绑定数据 setListAdapte() 点击事件重写onList
 */
public class ListFragmentActivity extends AppCompatActivity {

    private TextView tv_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listfragment);
    }

}
