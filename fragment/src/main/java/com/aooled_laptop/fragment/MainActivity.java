package com.aooled_laptop.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;

import com.aooled_laptop.fragment.fragment.GiftFragment;
import com.aooled_laptop.fragment.fragment.OrderFragment;
import com.aooled_laptop.fragment.fragment.ShareFragment;
import com.aooled_laptop.fragment.fragment.ShopRankFragment;
import com.aooled_laptop.fragment.fragment.TitleFragment;

/**
 * frament的动态使用
 * 1, 创建fragment的管理器对象
 * 2, 获取fragment的事务对象并开启
 * 事务一致性
 * 3, 调用事务的动态方法, 动态添加,移除,替换fragment
 * 4, 提交事务
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private RadioButton rb_shoprank, rb_share, rb_gift, rb_order;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        // 1, 创建fragment的管理器对象
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.content_layout, new ShopRankFragment());
        transaction.commit();

    }

    public void initView(){
        rb_shoprank = findViewById(R.id.rb_shoprank);
        rb_share = findViewById(R.id.rb_share);
        rb_gift = findViewById(R.id.rb_gift);
        rb_order = findViewById(R.id.rb_order);

        rb_shoprank.setOnClickListener(this);
        rb_share.setOnClickListener(this);
        rb_gift.setOnClickListener(this);
        rb_order.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        transaction = manager.beginTransaction();
        switch (v.getId()){
            case R.id.rb_shoprank:
                transaction.replace(R.id.content_layout, new ShopRankFragment());
                break;
            case R.id.rb_share:
                transaction.replace(R.id.content_layout, new ShareFragment());
                break;
            case R.id.rb_gift:
                transaction.replace(R.id.content_layout, new GiftFragment());
                break;
            case R.id.rb_order:
                transaction.replace(R.id.content_layout, new OrderFragment());
                break;
            default:
                break;
        }
        transaction.commit();
    }
}
