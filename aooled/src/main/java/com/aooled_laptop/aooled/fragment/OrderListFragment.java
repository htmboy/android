package com.aooled_laptop.aooled.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderListFragment extends Fragment {
    private ListView listView;
    private List<Order> orders;
    private OrderListAdapter orderListAdapter;
    private JSONObject jsonObject = null;
    private View view;
    private int totalItem;
    private boolean isDivPage;
    private int pageSize = 10;
    private int currentPage = 1;
    private int pageNumber;

//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
////        Logger.i("setUserVisibleHint");
////        listView.setAdapter(orderListAdapter);
//    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if (view != null) {
//            ViewGroup parent = (ViewGroup) view.getParent();
//            if (parent != null) {
//                parent.removeView(view);
//            }
//            return view;
//        }
        View view = inflater.inflate(R.layout.orderlist, container, false);
        listView = view.findViewById(R.id.listView);

        dataResource();
        new OrderListAscynTask().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), orders.get(position).getId(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("orderId", orders.get(position).getId());
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(isDivPage && AbsListView.OnScrollListener.SCROLL_STATE_IDLE == scrollState) {

                    if(currentPage < pageNumber){
                        currentPage++;
                        Toast.makeText(getActivity(), pageNumber + "", Toast.LENGTH_SHORT).show();
                        dataResourcePage();

                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isDivPage = ((firstVisibleItem + visibleItemCount) == totalItemCount);
//                Logger.i(firstVisibleItem + visibleItemCount);
            }
        });


        return view;
    }
    public void dataResourcePage(){
        Request<String> request = new StringRequest(Constants.URL_UPLOAD, RequestMethod.JSON);
        request.add("id", getArguments().getString("id"));
        request.add("code", 21);
        request.add("page", pageNumber);
//        orders = new ArrayList<Order>();
        RequestExecutor.INTANCE.execute(request, new HttpListener<String>() {
            @Override
            public void onSucceed(Response<String> response) {

                Logger.i("Activity 接受到的响应码:" + response.getResponseCode());
                String str = response.get();
//                Logger.i("Activity 接受到的结果:" + str);
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
                        try {
                            for (int i = 0; i < jsonArray.length(); i++){
                                Order order = new Order();
                                order.setId(jsonArray.getJSONObject(i).optString("orderId"));
                                order.setCustomerCompany(jsonArray.getJSONObject(i).optString("customer"));
                                order.setContact(jsonArray.getJSONObject(i).optString("contact"));
                                order.setContactTel(jsonArray.getJSONObject(i).optString("contactTel"));
                                order.setOrderStatus(jsonArray.getJSONObject(i).optString("orderStatus"));
                                order.setOrderNumber(jsonArray.getJSONObject(i).optString("orderNumber"));
                                order.setContractAmount(jsonArray.getJSONObject(i).optString("contractAmount"));
                                orders.add(order);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    public void dataResource(){
        Request<String> request = new StringRequest(Constants.URL_UPLOAD, RequestMethod.JSON);
        request.add("id", getArguments().getString("id"));
        request.add("code", 2);
        orders = new ArrayList<Order>();
        RequestExecutor.INTANCE.execute(request, new HttpListener<String>() {
            @Override
            public void onSucceed(Response<String> response) {

                Logger.i("Activity 接受到的响应码:" + response.getResponseCode());
                String str = response.get();
//                Logger.i("Activity 接受到的结果:" + str);
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
//                        Logger.i("11");
                        JSONArray jsonArray = jsonObject.optJSONArray("data");
                        try {
                            for (int i = 0; i < jsonArray.length(); i++){
                                Order order = new Order();
                                order.setId(jsonArray.getJSONObject(i).optString("orderId"));
                                order.setCustomerCompany(jsonArray.getJSONObject(i).optString("customer"));
                                order.setContact(jsonArray.getJSONObject(i).optString("contact"));
                                order.setContactTel(jsonArray.getJSONObject(i).optString("contactTel"));
                                order.setOrderStatus(jsonArray.getJSONObject(i).optString("orderStatus"));
                                order.setOrderNumber(jsonArray.getJSONObject(i).optString("orderNumber"));
                                order.setContractAmount(jsonArray.getJSONObject(i).optString("contractAmount"));

                                orders.add(order);

                            }
                            totalItem = jsonObject.optInt("totalItem");
                            pageNumber = (int)Math.ceil(totalItem / (float)pageSize);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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


//        orders = new ArrayList<Order>();
//        for (int i = 0; i < 20; i++){
//            Order order = new Order();
//            order.setCustomerCompany("分公司" + i);
//            order.setContact("威廉" + i);
//            order.setContactTel("1589955554" + i);
//            order.setOrderStatus(i+"");
//            order.setOrderNumber("201903" + i);
//            order.setContractAmount(3.42f+"");
//            orders.add(order);
//        }
//        return orders;
    }

    class OrderListAscynTask extends AsyncTask<Void, Void, List<Order>>{

        @Override
        protected List<Order> doInBackground(Void... voids) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            Logger.i("22");
            return orders;
        }

        @Override
        protected void onPostExecute(List<Order> orders) {
            super.onPostExecute(orders);
//            Logger.i("33");
            orderListAdapter = new OrderListAdapter(getActivity(), orders);
            listView.setAdapter(orderListAdapter);
        }
    }
}
