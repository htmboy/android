package com.aooled_laptop.aooled.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aooled_laptop.aooled.LoginActivity;
import com.aooled_laptop.aooled.R;


public class SetFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set, null);
        view.findViewById(R.id.exit).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        startActivity(new Intent(getActivity(),LoginActivity.class));

        getActivity().finish();
    }
}
