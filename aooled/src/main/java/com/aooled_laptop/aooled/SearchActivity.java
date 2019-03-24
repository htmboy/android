package com.aooled_laptop.aooled;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.aooled_laptop.aooled.adapter.OrderListAdapter;
import com.aooled_laptop.aooled.error.ParseError;
import com.aooled_laptop.aooled.error.TimeoutError;
import com.aooled_laptop.aooled.error.URLError;
import com.aooled_laptop.aooled.error.UnknowHostError;
import com.aooled_laptop.aooled.fragment.OrderListFragment;
import com.aooled_laptop.aooled.model.Contact;
import com.aooled_laptop.aooled.model.Order;
import com.aooled_laptop.aooled.task.HttpListener;
import com.aooled_laptop.aooled.task.JsonRequest;
import com.aooled_laptop.aooled.task.Request;
import com.aooled_laptop.aooled.task.RequestExecutor;
import com.aooled_laptop.aooled.task.RequestMethod;
import com.aooled_laptop.aooled.task.Response;
import com.aooled_laptop.aooled.task.StringRequest;
import com.aooled_laptop.aooled.utils.Constants;
import com.aooled_laptop.aooled.utils.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private ListView listView;
    private String id;
    private List<Order> orders = new ArrayList<>();
    private OrderListAdapter orderListAdapter;
    private int totalItem;
    private boolean isDivPage;
    private int pageSize = 10;
    private int currentPage;
    private int pageNumber;
    private CharSequence charSequence;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        findViewById(R.id.back).setOnClickListener(this);
        listView = findViewById(R.id.searchListView);
        ((EditText)findViewById(R.id.search)).addTextChangedListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundleItem = new Bundle();
                bundleItem.putString("orderId", orders.get(position).getId());
                Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
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
                        submitData(false);

                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isDivPage = ((firstVisibleItem + visibleItemCount) == totalItemCount);
            }
        });
        orderListAdapter = new OrderListAdapter(this, orders);
        listView.setAdapter(orderListAdapter);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        orders.clear();
        currentPage = 1;
        if (s.length() == 0){
            orderListAdapter.notifyDataSetChanged();
        }else{
            charSequence = s;
            submitData(true);
        }


    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void submitData(final boolean mark){
        Request<String> request = new StringRequest(Constants.URL_UPLOAD, RequestMethod.JSON);
        request.add("id", id);
        request.add("code", 4);
        request.add("page", currentPage);
//        Toast.makeText(this, String.valueOf(currentPage), Toast.LENGTH_SHORT).show();
        request.add("search", charSequence.toString());
        RequestExecutor.INTANCE.execute(request, new HttpListener<String>() {
            JSONObject jsonObject;
            @Override
            public void onSucceed(Response<String> response) {

                Logger.i("Activity 接受到的响应码:" + response.getResponseCode());
                String str = response.get();
                Logger.i("Activity 接受到的结果:" + str);
                int code = 0;
                try {
                    jsonObject = new JSONObject(str);

                    code = (int)jsonObject.opt("code");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                switch (code){
                    case 0: Toast.makeText(SearchActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        if (mark)
                            totalItem = (int)jsonObject.opt("totalItem");
//                        Toast.makeText(SearchActivity.this, totalItem, Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonObject.optJSONArray("data");
                        try {
                            for (int i = 0; i < jsonArray.length(); i++){
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

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        pageNumber = (int)Math.ceil(totalItem / (float)pageSize);
                        orderListAdapter.notifyDataSetChanged();
                        break;
                    case 2:

                        Toast.makeText(SearchActivity.this, "无数据", Toast.LENGTH_SHORT).show();
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

}
