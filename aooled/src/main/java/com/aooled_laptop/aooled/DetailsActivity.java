package com.aooled_laptop.aooled;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.aooled_laptop.aooled.error.ParseError;
import com.aooled_laptop.aooled.error.TimeoutError;
import com.aooled_laptop.aooled.error.URLError;
import com.aooled_laptop.aooled.error.UnknowHostError;
import com.aooled_laptop.aooled.task.HttpListener;
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

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    TextView orderNumber, isDistribution, isSpecialOffer, fillDate, isSimpleOrder, contractNumber, isConstruction, isBorrowData, goodsCount, isAssurance, method;
    TextView price, payer, alterAmount, isNoticeDelivery, sendTo, customer, contact, contactTel, recieptBank, isContainTax, deposit, assurance, assuranceDate, constructionAmount;
    TextView constructionAccount, tail, tailDate, contractAmount, isAlterReciept;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.orderlist_details);
        findViewById(R.id.back).setOnClickListener(this);
        initView();
        String str = getIntent().getExtras().getString("orderId");
        setData(str);
    }
    public void initView(){

        orderNumber = findViewById(R.id.orderNumber);
        isDistribution = findViewById(R.id.isDistribution);
        isSpecialOffer = findViewById(R.id.isSpecialOffer);
        fillDate = findViewById(R.id.fillDate);
        isSimpleOrder = findViewById(R.id.isSimpleOrder);
        contractNumber = findViewById(R.id.contractNumber);
        isConstruction = findViewById(R.id.isConstruction);
        isBorrowData = findViewById(R.id.isBorrowData);
        price = findViewById(R.id.price);
        goodsCount = findViewById(R.id.goodsCount);
        isAssurance = findViewById(R.id.isAssurance);
        method = findViewById(R.id.method);
        payer = findViewById(R.id.payer);
        isAlterReciept = findViewById(R.id.isAlterReciept);
        alterAmount = findViewById(R.id.alterAmount);
        isNoticeDelivery = findViewById(R.id.isNoticeDelivery);
        sendTo = findViewById(R.id.sendTo);


        customer = findViewById(R.id.customer);
        contact = findViewById(R.id.contact);
        contactTel = findViewById(R.id.contactTel);
        recieptBank = findViewById(R.id.recieptBank);

        isContainTax = findViewById(R.id.isContainTax);
        deposit = findViewById(R.id.deposit);
        assurance = findViewById(R.id.assurance);
        assuranceDate = findViewById(R.id.assuranceDate);

        constructionAmount = findViewById(R.id.constructionAmount);
        constructionAccount = findViewById(R.id.constructionAccount);
        tail = findViewById(R.id.tail);
        tailDate = findViewById(R.id.tailDate);
        contractAmount = findViewById(R.id.contractAmount);




    }

    @Override
    public void onClick(View v) {
        finish();
    }

    public void setData(String id){
        Request<String> request = new StringRequest(Constants.URL_UPLOAD, RequestMethod.JSON);
        request.add("code", 22);
        request.add("orderId", id);
//        orders = new ArrayList<Order>();
        RequestExecutor.INTANCE.execute(request, new HttpListener<String>() {
            @Override
            public void onSucceed(Response<String> response) {
                JSONObject jsonObject = null;
                Logger.i("Activity 接受到的响应码:" + response.getResponseCode());
                String str = response.get();
                Logger.i("Activity 接受到的结果:" + str);
                int code = 0;
                try {
                    jsonObject = new JSONObject(str);
                    code = (int) jsonObject.opt("code");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switch (code){
                    case 0: Toast.makeText(DetailsActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                        orderNumber.setText(jsonObject1.optString("orderNumber"));
                        isDistribution.setText(jsonObject1.optString("isDistribution"));
                        isSpecialOffer.setText(jsonObject1.optString("isSpecialOffer"));
                        fillDate.setText(jsonObject1.optString("fillDate"));
                        isSimpleOrder.setText(jsonObject1.optString("isSimpleOrder"));
                        contractNumber.setText(jsonObject1.optString("contractNumber"));
                        isConstruction.setText(jsonObject1.optString("isConstruction"));
                        isBorrowData.setText(jsonObject1.optString("isBorrowData"));
                        goodsCount.setText(jsonObject1.optString("goodsCount"));
                        isAssurance.setText(jsonObject1.optString("isAssurance"));
                        method.setText(jsonObject1.optString("method"));
                        price.setText(jsonObject1.optString("price"));
                        payer.setText(jsonObject1.optString("payer"));

                        isAlterReciept.setText(jsonObject1.optString("isAlterReciept"));
                        alterAmount.setText(jsonObject1.optString("alterAmount"));
                        isNoticeDelivery.setText(jsonObject1.optString("isNoticeDelivery"));
                        sendTo.setText(jsonObject1.optString("sendTo"));
                        customer.setText(jsonObject1.optString("customer"));
                        contact.setText(jsonObject1.optString("contact"));
                        contactTel.setText(jsonObject1.optString("contactTel"));
                        recieptBank.setText(jsonObject1.optString("recieptBank"));
                        isContainTax.setText(jsonObject1.optString("isContainTax"));
                        deposit.setText(jsonObject1.optString("deposit"));
                        assurance.setText(jsonObject1.optString("assurance"));
                        assuranceDate.setText(jsonObject1.optString("assuranceDate"));
                        constructionAmount.setText(jsonObject1.optString("constructionAmount"));
                        constructionAccount.setText(jsonObject1.optString("constructionAccount"));
                        tail.setText(jsonObject1.optString("tail"));
                        tailDate.setText(jsonObject1.optString("tailDate"));
                        contractAmount.setText(jsonObject1.optString("contractAmount"));

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
