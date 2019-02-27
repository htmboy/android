package com.aooled_laptop.tofragment.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aooled_laptop.tofragment.R;

public class ResourceFragment extends Fragment {

    private EditText et_content;
    private Button btn_pass;
    private MyListerner listerner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 得到MainActivity对象
        listerner = (MyListerner) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resource, null);
        // 先调用getAugments() 方法获取bundle在bundle对象中根据key获取传递的数据 展示TextView中
        et_content = view.findViewById(R.id.et_content);
        btn_pass = view.findViewById(R.id.btn_pass);
        btn_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = et_content.getText().toString().trim();
                listerner.sendMessage(info);
            }
        });
        return view;
    }
    // 定义接口 接口中定义回传数据的函数
    public interface MyListerner{
        public abstract void sendMessage(String str);
    }
}
