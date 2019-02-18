package com.aooled_laptop.httpurl;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class JsonAdapter extends BaseAdapter {
    private List<Person> list;
    private Context context;
    private LayoutInflater inflater;
    private Handler handler = new Handler();

    public JsonAdapter(Context context, List<Person> list){
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public JsonAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<Person> data){
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.json_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        }else
            holder = (Holder) convertView.getTag();
        Person person = list.get(position);
        holder.name.setText(person.getName());
        holder.age.setText(person.getAge() + "");
        List<SchoolInfo> schools = person.getSchoolInfo();
        SchoolInfo schoolInfo1 = schools.get(0);
        SchoolInfo schoolInfo2 = schools.get(1);

        holder.school1.setText(schoolInfo1.getSchool_name());
        holder.school2.setText(schoolInfo2.getSchool_name());

        new HttpImage(person.getUrl(), handler, holder.imageView).start();
        return convertView;
    }

    class Holder{
        private TextView name, age, school1, school2;
        private ImageView imageView;

        public Holder(View view){
            name = view.findViewById(R.id.name);
            age = view.findViewById(R.id.age);
            school1 = view.findViewById(R.id.school1);
            school2 = view.findViewById(R.id.school2);
            imageView = view.findViewById(R.id.imageView);
        }
    }
}
