package com.aooled_laptop.aooled.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.aooled_laptop.aooled.DetailsActivity;
import com.aooled_laptop.aooled.LoginActivity;
import com.aooled_laptop.aooled.MainActivity;
import com.aooled_laptop.aooled.error.ParseError;
import com.aooled_laptop.aooled.error.TimeoutError;
import com.aooled_laptop.aooled.error.URLError;
import com.aooled_laptop.aooled.error.UnknowHostError;
import com.aooled_laptop.aooled.model.Order;
import com.aooled_laptop.aooled.R;
import com.aooled_laptop.aooled.adapter.OrderListAdapter;
import com.aooled_laptop.aooled.task.HttpListener;
import com.aooled_laptop.aooled.task.Request;
import com.aooled_laptop.aooled.task.RequestExecutor;
import com.aooled_laptop.aooled.task.RequestMethod;
import com.aooled_laptop.aooled.task.Response;
import com.aooled_laptop.aooled.task.StringRequest;
import com.aooled_laptop.aooled.utils.Constants;
import com.aooled_laptop.aooled.utils.Logger;
import com.aooled_laptop.aooled.utils.MD5Util;
import com.aooled_laptop.aooled.utils.SaveInfoUtils;
import com.aooled_laptop.aooled.view.FlashListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private List<Order> orders = new ArrayList<>();
    private OrderListAdapter orderListAdapter;
    private JSONObject jsonObject = null;
    private View view;
    private int totalItem;
    private boolean isDivPage;
    private int pageSize = 10;
    private int currentPage = 1;
    private int pageNumber;


    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.orderlist, container, false);
        listView = view.findViewById(R.id.listView);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        final Bundle bundle = getArguments();
        totalItem = bundle.getInt("totalItem");
        dataResource(getArguments().getString("orders"));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundleItem = new Bundle();
                bundleItem.putString("orderId", orders.get(position).getId());
                bundleItem.putString("code", bundle.getString("code"));
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtras(bundleItem);
                startActivity(intent);

            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(isDivPage && AbsListView.OnScrollListener.SCROLL_STATE_IDLE == scrollState) {

                    if(currentPage < pageNumber){
                        currentPage++;
                        dataResourcePage();

                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isDivPage = ((firstVisibleItem + visibleItemCount) == totalItemCount);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }
    public void dataResourcePage(){
        Request<String> request = new StringRequest(Constants.URL_SUBMIT, RequestMethod.JSON);
        request.add("id", getArguments().getString("id"));
        request.add("code", 21);
        request.add("page", currentPage);
        RequestExecutor.INTANCE.execute(request, new HttpListener<String>() {
            @Override
            public void onSucceed(Response<String> response) {

                Logger.i("Activity 接受到的响应码:" + response.getResponseCode());
                String str = response.get();
                int code = 0;
                try {
                    jsonObject = new JSONObject(str);
                    code = (int) jsonObject.opt("code");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switch (code){
                    case 0: Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        JSONArray jsonArray = jsonObject.optJSONArray("data");
                        setDatas(jsonArray);
                        orderListAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onFailed(Exception e) {
                if (e instanceof ParseError){
                    // 数据解析异常
                    Logger.d("数据解析异常");
                }else if (e instanceof TimeoutError){
                    // 超时
                    Logger.d("超时");
                }else if (e instanceof UnknowHostError){
                    // 没有找到服务器
                    Logger.d("没有找到服务器");
                }else if (e instanceof URLError){
                    // URL格式错误
                    Logger.d("URL格式错误");
                }

            }
        });
    }

    public void dataResource(String data){
        try {
            JSONArray jsonArray = new JSONArray(data);
            setDatas(jsonArray);
            pageNumber = (int)Math.ceil(totalItem / (float)pageSize);
            orderListAdapter = new OrderListAdapter(getActivity(), orders);
            listView.setAdapter(orderListAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setDatas(JSONArray jsonArray){
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                Order order = new Order();
                order.setId(jsonArray.getJSONObject(i).optString("orderId"));
                order.setCustomerCompany(jsonArray.getJSONObject(i).optString("customer"));
                order.setContact(jsonArray.getJSONObject(i).optString("contact"));
                order.setContactTel(jsonArray.getJSONObject(i).optString("contactTel"));
                order.setOrderStatus(Constants.getOrderStatus(jsonArray.getJSONObject(i).optString("orderStatus")));
                order.setOrderNumber(jsonArray.getJSONObject(i).optString("orderNumber"));
                order.setContractAmount(jsonArray.getJSONObject(i).optString("contractAmount"));
                orders.add(order);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {

                orders.clear();
                currentPage = 1;
                dataResourcePage();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

}
