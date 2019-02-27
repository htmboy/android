package com.aooled_laptop.tofragment.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.aooled_laptop.tofragment.R;

/**
 * oncreateView() 或 oncreateDialog()方法创建
 * oncreateView()表示以xml布局文件的形式展示dialog
 * oncreateDialog() 利用alertdialog或者dialog创建
 */
public class MyDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("标题栏: 是否要退出?");
        builder.setMessage("您确定要退出吗");
        builder.setIcon(R.mipmap.ic_launcher_round);
        builder.setPositiveButton("queding?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("quxiao", null);
        return builder.create();
    }
}
