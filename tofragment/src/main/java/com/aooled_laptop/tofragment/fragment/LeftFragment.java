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

public class LeftFragment extends Fragment {
    private EditText et_content;
    private Button btn_send;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left, null);
        et_content = view.findViewById(R.id.et_content);
        btn_send = view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = et_content.getText().toString().trim();
                // 1, 方式一:可以用findFragmentById()方法获取fragment对象
//                RightFragment rightFragment = (RightFragment) getFragmentManager().findFragmentById(R.id.rightfragment);
//                rightFragment.setTextView(str);
                // 2, 方式二: 先getFragmentManager 获得fragmentmanager对象,
//                TextView tv = getFragmentManager().findFragmentById(R.id.rightfragment).getView().findViewById(R.id.tv_show);
//                tv.setText(str);
                // 3, 方式三 先getactivity获取activity对象
                TextView tv = getActivity().findViewById(R.id.tv_show);
                tv.setText(str);
            }
        });
        return view;
    }
}
