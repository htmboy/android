package com.aooled_laptop.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.aooled_laptop.fragment.fragment.TitleFragment;

/**
 * frament的动态使用
 * 1, 创建fragment的管理器对象
 * 2, 获取fragment的事务对象并开启
 * 事务一致性
 * 3, 调用事务的动态方法, 动态添加,移除,替换fragment
 * 4, 提交事务
 */
public class FragmentActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fragment);
        // 1, 创建fragment的管理器对象
        FragmentManager manager = getSupportFragmentManager();
        // 2, 获取fragment的事务对象并开启
        FragmentTransaction transaction = manager.beginTransaction();
        // 3, 调用事务的动态方法, 动态添加,移除,替换fragment
        // add() 第一参数表示fragment 动态添加位置的资源id, 第二参数表示添加的fragment的对象
        transaction.add(R.id.title_layout, new TitleFragment());
        transaction.add(R.id.content_layout, new ContentFragment());
        // remove() 移除fragment对象
        // transaction.remove();
        // replace() 切换较为常用 第一参数表示将资源id里的fragment替换为第二参数的fragment, 第二参数表示要替换的fragment的对象
        // transaction.replace(, )
        // 提交事务
        transaction.commit();
    }
}
