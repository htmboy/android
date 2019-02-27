package com.aooled_laptop.tofragment.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.aooled_laptop.tofragment.R;

import java.util.ArrayList;
import java.util.List;

public class MyListFragment extends ListFragment {

    private List<String> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            list.add("item" + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);
        return view;
    }

    // 为listview绑定每一项的点击事件
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), list.get(position), Toast.LENGTH_SHORT).show();
    }
}
