package com.aooled_laptop.aooled.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aooled_laptop.aooled.model.Order;
import com.aooled_laptop.aooled.R;

import java.util.List;

public class OrderListAdapter extends BaseAdapter {
    private List<Order> orders;
    private LayoutInflater layoutInflater;
    public OrderListAdapter(Context context, List<Order> orders) {
        this.orders = orders;
        this.layoutInflater = layoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Order getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.orderlist_item, null);
            viewHolder.contact = convertView.findViewById(R.id.contact);
            viewHolder.contactTel = convertView.findViewById(R.id.contactTel);
            viewHolder.contractAmount = convertView.findViewById(R.id.contractAmount);
            viewHolder.customerCompany = convertView.findViewById(R.id.customerCompany);
            viewHolder.orderStatus = convertView.findViewById(R.id.orderStatus);
            viewHolder.orderNumber = convertView.findViewById(R.id.orderNumber);
            convertView.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.contact.setText(orders.get(position).getContact());
        viewHolder.orderNumber.setText(orders.get(position).getOrderNumber());
        viewHolder.orderStatus.setText(orders.get(position).getOrderStatus());
        viewHolder.contractAmount.setText(orders.get(position).getContractAmount());
        viewHolder.customerCompany.setText(orders.get(position).getCustomerCompany());
        viewHolder.contactTel.setText(orders.get(position).getContactTel());
        return convertView;
    }

    class ViewHolder{
        public TextView orderNumber, contractAmount, contact, contactTel, customerCompany, orderStatus;
    }
}
