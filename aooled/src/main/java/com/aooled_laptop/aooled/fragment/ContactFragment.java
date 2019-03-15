package com.aooled_laptop.aooled.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aooled_laptop.aooled.DetailsActivity;
import com.aooled_laptop.aooled.R;
import com.aooled_laptop.aooled.adapter.ContactAdapter;
import com.aooled_laptop.aooled.adapter.OrderListAdapter;
import com.aooled_laptop.aooled.model.Contact;
import com.aooled_laptop.aooled.model.Order;
import com.aooled_laptop.aooled.utils.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {

    private ListView listView;
    private List<Contact> contacts;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact, null);
        listView = view.findViewById(R.id.contactView);
        contacts = new ArrayList<>();
        Logger.i(getArguments().getString("contacts"));
        try {
            JSONArray jsonArray = new JSONObject(getArguments().getString("contacts")).optJSONArray("contacts");
            for (int i = 0; i < jsonArray.length(); i++){
                Contact contact = new Contact();
                Logger.i(jsonArray.optJSONObject(i).optString("name"));
                contact.setContactName(jsonArray.optJSONObject(i).optString("name"));
                contact.setContactNumber(jsonArray.optJSONObject(i).optString("tel_work"));
                contacts.add(contact);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new ContactAscynTask().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), orders.get(position).getId(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +  contacts.get(position).getContactNumber()));
                startActivity(intent);

            }
        });
        return view;
    }

    class ContactAscynTask extends AsyncTask<Void, Void, List<Contact>> {

        @Override
        protected List<Contact> doInBackground(Void... voids) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            Logger.i("22");
            return contacts;
        }

        @Override
        protected void onPostExecute(List<Contact> contacts) {
            super.onPostExecute(contacts);
//            Logger.i("33");
            listView.setAdapter(new ContactAdapter(getActivity(), contacts));
        }
    }
}
