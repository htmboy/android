package com.aooled_laptop.tofragment.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aooled_laptop.tofragment.R;

public class RightFragment extends Fragment {
    private TextView tv_show;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right, null);
        tv_show = view.findViewById(R.id.tv_show);
        return view;
    }
    // 定义函数给textview赋值
    public void setTextView(String str){
        tv_show.setText(str);
    }
}
