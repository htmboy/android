package com.aooled_laptop.appintro.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.aooled_laptop.appintro.MainActivity;
import com.aooled_laptop.appintro.R;

public class ContentFragment extends Fragment {
    private int[] bgRes= {R.mipmap.a, R.mipmap.b, R.mipmap.c};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, null);
        Button btn = view.findViewById(R.id.btn);
        RelativeLayout rl = view.findViewById(R.id.rl);
        int index = getArguments().getInt("index");
        rl.setBackgroundResource(bgRes[index]);;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        btn.setVisibility(index == 2 ? View.VISIBLE : View.GONE);
        return view;

    }
}
