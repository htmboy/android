package com.aooled_laptop.aooled.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aooled_laptop.aooled.R;
import com.aooled_laptop.aooled.model.Contact;
import com.aooled_laptop.aooled.model.Order;

import java.util.List;

public class ContactAdapter extends BaseAdapter {
    private List<Contact> contacts;
    private LayoutInflater layoutInflater;
    public ContactAdapter(Context context, List<Contact> contacts) {
        this.contacts = contacts;
        this.layoutInflater = layoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Contact getItem(int position) {
        return contacts.get(position);
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
            convertView = layoutInflater.inflate(R.layout.contact_item, null);
            viewHolder.contactName = convertView.findViewById(R.id.contactName);
            viewHolder.contactNumber = convertView.findViewById(R.id.contactNumber);
            convertView.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.contactName.setText(contacts.get(position).getContactName());
        viewHolder.contactNumber.setText(contacts.get(position).getContactNumber());
        return convertView;
    }

    class ViewHolder{
        public TextView contactName, contactNumber;
    }
}
