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

public class ResultFragment extends Fragment {

    private TextView tv_show;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, null);
        tv_show = view.findViewById(R.id.tv_show);
        // 先调用getAugments() 方法获取bundle在bundle对象中根据key获取传递的数据 展示TextView中
        Bundle bundle = getArguments();
        if(bundle != null) {
            String info = bundle.getString("info");
            tv_show.setText(info);
        }
        return view;
    }
}
