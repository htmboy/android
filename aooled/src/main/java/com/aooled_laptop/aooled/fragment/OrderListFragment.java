package com.aooled_laptop.aooled.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aooled_laptop.aooled.model.Order;
import com.aooled_laptop.aooled.R;
import com.aooled_laptop.aooled.adapter.OrderListAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderListFragment extends Fragment {
    private ListView listView;
    private List<Order> orders;
    private OrderListAdapter orderListAdapter;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orderlist, container, false);
        listView = view.findViewById(R.id.listView);
        new OrderListAscynTask().execute();

        return view;
    }

    public List<Order> dataResource(){
        orders = new ArrayList<Order>();
        for (int i = 0; i < 20; i++){
            Order order = new Order();
            order.setCustomerCompany("分公司" + i);
            order.setContact("威廉" + i);
            order.setContactTel("1589955554" + i);
            order.setOrderStatus(i);
            order.setOrderNumber("201903" + i);
            order.setContractAmount(3.42f);
            orders.add(order);
        }
        return orders;
    }

    class OrderListAscynTask extends AsyncTask<Void, Void, List<Order>>{

        @Override
        protected List<Order> doInBackground(Void... voids) {
            return dataResource();
        }

        @Override
        protected void onPostExecute(List<Order> orders) {
            super.onPostExecute(orders);
            orderListAdapter = new OrderListAdapter(getActivity(), orders);
            listView.setAdapter(orderListAdapter);
        }
    }
}
