package com.aooled_laptop.tofragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.aooled_laptop.tofragment.fragment.ResultFragment;

public class ToFragmentActivity extends AppCompatActivity {

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private EditText et_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tofragment);
        et_info = findViewById(R.id.et_content);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.contentlayout, new ResultFragment());
        transaction.commit();
    }

    public void sendValue(View view){
        String info = et_info.getText().toString().trim();
        // 创建bundle对象, 将需要传递的数据存储到bundle中 然后调用fragment的setArguments() 方法传递bundle
        ResultFragment rf = new ResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString("info", info);
        rf.setArguments(bundle);

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.contentlayout, rf);
        transaction.commit();
    }
}
