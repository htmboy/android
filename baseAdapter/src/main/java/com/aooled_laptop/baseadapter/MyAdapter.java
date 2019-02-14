package com.aooled_laptop.baseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private List<ItemBean> mList;
    private LayoutInflater mInflater;

    public MyAdapter(Context context, List<ItemBean> list) {
        mList = list;
        mInflater = LayoutInflater.from(context); // 布局装载器对象
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 返回每一项的内容
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 没有利用到ListView的缓存机制
        // 逗比式
//        View view = mInflater.inflate(R.layout.item, null); // 浪费资源
//        ImageView imageView = view.findViewById(R.id.iv_image);
//        TextView title = view.findViewById(R.id.tv_title);
//        TextView content = view.findViewById(R.id.tv_content);
//        ItemBean bean = mList.get(position);
//        imageView.setImageResource(mList.get(position).ItemImageResid);
//        title.setText(bean.ItemTitle);
//        content.setText(bean.ItemContent);
//
//        return view;

        // 普通式
//        if (convertView == null){
//            convertView = mInflater.inflate(R.layout.item, null);
//        }
//        ImageView imageView = convertView.findViewById(R.id.iv_image); // findViewById 浪费资源
//        TextView title = convertView.findViewById(R.id.tv_title);
//        TextView content = convertView.findViewById(R.id.tv_content);
//        ItemBean bean = mList.get(position);
//        imageView.setImageResource(mList.get(position).ItemImageResid);
//        title.setText(bean.ItemTitle);
//        content.setText(bean.ItemContent);
//        return convertView;

        // 文艺式
        // 避免重复的findViewById的操作 从而提高效率
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item, null);
            viewHolder.imageView = convertView.findViewById(R.id.iv_image);
            viewHolder.title = convertView.findViewById(R.id.tv_title);
            viewHolder.content = convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ItemBean bean = mList.get(position);
        viewHolder.imageView.setImageResource(mList.get(position).ItemImageResid);
        viewHolder.title.setText(bean.ItemTitle);
        viewHolder.content.setText(bean.ItemContent);
        return convertView;


    }
}
// 避免重复的findViewById的操作 从而提高效率
class ViewHolder {
    public ImageView imageView;
    public TextView title;
    public TextView content;
}