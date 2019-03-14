package com.aooled_laptop.aooled.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aooled_laptop.aooled.R;

public class PerasonFragment extends Fragment {
    TextView name;
    TextView code;
    TextView username;
    TextView sex;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person, null);
        name = view.findViewById(R.id.name);
        code = view.findViewById(R.id.code);
        username = view.findViewById(R.id.username);
        sex = view.findViewById(R.id.sex);
        Bundle bundle = getArguments();
//        Toast.makeText(getActivity(), bundle.getString("username"), Toast.LENGTH_SHORT).show();
        name.setText(bundle.getString("name"));
        code.setText(bundle.getString("code"));
        username.setText(bundle.getString("username"));
        sex.setText(bundle.getString("sex").equals("1") ? "男" : "女");
        return view;
    }
}
