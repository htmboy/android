package com.aooled_laptop.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 1, 创建子类继承fragment
 * 2, 重写onCreateView()方法 该方法主要定义fragment的布局 以view对象的形式返回fragment的视图
 * 3, 将fragment引入到activity中
 */

public class TitleFragment extends Fragment {
    @Nullable

    /**
     * 表示 fragment 第一次创建绘制用户界面时系统回调的方法
     * 返回值 view 表示当前加载fragment视图 如果fragment 不提供视图可以返回null
     * LayoutInflater 布局填充器或者布局加载器 将xml文件转化成view对象
     * ViewGroup container 表示当前fragment要插入avtivity的布局视图对象
     * Bundle savedInstanceState 表示存储上一个fragment的信息
      */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title, null);
        RelativeLayout layout = view.findViewById(R.id.rl_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "标题栏", Toast.LENGTH_LONG);
            }
        });
        return view;
    }
}
