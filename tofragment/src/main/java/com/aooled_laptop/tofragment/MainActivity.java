package com.aooled_laptop.tofragment;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aooled_laptop.tofragment.fragment.MyDialogFragment;
import com.aooled_laptop.tofragment.fragment.ResourceFragment;
import com.aooled_laptop.tofragment.fragment.ResultFragment;

/**
 * ListFragment的使用
 * listFragment的id是固定写法
 * 自带默认带布局
 * 绑定数据 setListAdapte() 点击事件重写onList
 */
public class MainActivity extends AppCompatActivity {

    private TextView tv_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void click(View view){
        // 普通alertdialog的创建方式
//        new AlertDialog.Builder(this).setTitle("").setMessage("").setIcon(R.mipmap.ic_launcher_round).setPositiveButton("queding", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
        MyDialogFragment fragment = new MyDialogFragment();
        fragment.show(getSupportFragmentManager(), "dialog");
    }

}
