package com.aooled_laptop.asynctask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends BaseAdapter {

    private List<NewsBean> mList;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;

    public NewsAdapter(Context context, List<NewsBean> data) {
        mList = data;
        mInflater = LayoutInflater.from(context);
        mImageLoader = new ImageLoader();
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_layout, null);
            viewHolder.ivIcon = convertView.findViewById(R.id.iv_icon);
            viewHolder.tvTitile = convertView.findViewById(R.id.tv_title);
            viewHolder.tvContent = convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivIcon.setImageResource(R.mipmap.ic_launcher);
        String url = mList.get(position).newsIconUrl;
        viewHolder.ivIcon.setTag(url);
        // 线程加载
        // new ImageLoader().showImageByThread(viewHolder.ivIcon, url);
        // 异步加载
        // new ImageLoader().showImageByAsyncTask(viewHolder.ivIcon, url);
        // Lru缓存异步加载
        mImageLoader.showImageByAsyncTask(viewHolder.ivIcon, url);

        viewHolder.tvTitile.setText(mList.get(position).newsTitle);
        viewHolder.tvContent.setText(mList.get(position).newsContent);
        return convertView;
    }

    class ViewHolder {
        public TextView tvTitile, tvContent;
        public ImageView ivIcon;
    }
}
