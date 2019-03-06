package com.aooled_laptop.aooled.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.aooled_laptop.aooled.MainActivity;

public class BaseFragment extends Fragment {
    private Activity activity;

    @Nullable
    @Override
    public Context getContext() {
        if(activity == null) {
            return MyApplication.getInstance();
        }
        return activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }
}
