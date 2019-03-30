package com.aooled_laptop.aooled;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aooled_laptop.aooled.error.ParseError;
import com.aooled_laptop.aooled.error.TimeoutError;
import com.aooled_laptop.aooled.error.URLError;
import com.aooled_laptop.aooled.error.UnknowHostError;
import com.aooled_laptop.aooled.model.Order;
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
import com.aooled_laptop.aooled.utils.TimestampUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

public class OrderAddActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, DatePicker.OnDateChangedListener, TextWatcher {
    private EditText contractNumber, goodsCount, method, price, payer, customer;
    private EditText alterAmount, sendTo, contact, contactTel, recieptBank, contractAmount, deposit;
    private EditText assurance, constructionAmount, constructionAccount;
    private TextView fillDate, tailDate, tail, orderNumber, assuranceDate, alterAmountLabel, assuranceLabel, assuranceDateLabel, constructionAmountLabel, constructionAccountLabel;
    private CheckBox isDistribution, isBorrowData, isAssurance, isConstruction, isSpecialOffer, isSimpleOrder, isAlterReciept, isNoticeDelivery, isContainTax;
    private int year, month, day;
    private StringBuffer date = new StringBuffer();
    private Order order = new Order();
    private String id;
    private String code;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_add);
        Toolbar toolbar = findViewById(R.id.toolbar_return);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        code = bundle.getString("code");
        init();
        checkOut();
        event();
        initDateTime();
        submit(false);
    }
    private void event(){
        findViewById(R.id.submit).setOnClickListener(this);
        isAssurance.setOnCheckedChangeListener(this);
        isConstruction.setOnCheckedChangeListener(this);
        isAlterReciept.setOnCheckedChangeListener(this);
//        fillDate.setOnClickListener(this);

        tailDate.setOnClickListener(this);
        assuranceDate.setOnClickListener(this);
        contractAmount.addTextChangedListener(this);
        deposit.addTextChangedListener(this);
    }

    private void init(){
        orderNumber = findViewById(R.id.orderNumber);
        contractNumber = findViewById(R.id.contractNumber);
        goodsCount = findViewById(R.id.goodsCount);
        method = findViewById(R.id.method);
        price = findViewById(R.id.price);
        payer = findViewById(R.id.payer);
        customer = findViewById(R.id.customer);
        alterAmount = findViewById(R.id.alterAmount);
        sendTo = findViewById(R.id.sendTo);
        contact = findViewById(R.id.contact);
        contactTel = findViewById(R.id.contactTel);
        recieptBank = findViewById(R.id.recieptBank);
        contractAmount = findViewById(R.id.contractAmount);
        deposit = findViewById(R.id.deposit);
        tail = findViewById(R.id.tail);
        tailDate = findViewById(R.id.tailDate);
        assurance = findViewById(R.id.assurance);
        assuranceDate = findViewById(R.id.assuranceDate);
        constructionAmount = findViewById(R.id.constructionAmount);
        constructionAccount = findViewById(R.id.constructionAccount);
        isDistribution = findViewById(R.id.isDistribution);
        isBorrowData = findViewById(R.id.isBorrowData);
        isAssurance = findViewById(R.id.isAssurance);
        isConstruction = findViewById(R.id.isConstruction);
        isSpecialOffer = findViewById(R.id.isSpecialOffer);
        isSimpleOrder = findViewById(R.id.isSimpleOrder);
        isAlterReciept = findViewById(R.id.isAlterReciept);
        isNoticeDelivery = findViewById(R.id.isNoticeDelivery);
        isContainTax = findViewById(R.id.isContainTax);
        fillDate = findViewById(R.id.fillDate);
        alterAmountLabel = findViewById(R.id.alterAmountLabel);
        assuranceLabel = findViewById(R.id.assuranceLabel);
        assuranceDateLabel = findViewById(R.id.assuranceDateLabel);
        constructionAmountLabel = findViewById(R.id.constructionAmountLabel);
        constructionAccountLabel = findViewById(R.id.constructionAccountLabel);
    }

    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void checkOut(){
        if(isAssurance.isChecked()){
            assurance.setVisibility(View.VISIBLE);
            assuranceLabel.setVisibility(View.VISIBLE);
            assuranceDate.setVisibility(View.VISIBLE);
            assuranceDateLabel.setVisibility(View.VISIBLE);
        }else{
            assurance.setVisibility(View.GONE);
            assuranceLabel.setVisibility(View.GONE);
            assuranceDate.setVisibility(View.GONE);
            assuranceDateLabel.setVisibility(View.GONE);
        }
        if(isConstruction.isChecked()){
            constructionAmount.setVisibility(View.VISIBLE);
            constructionAmountLabel.setVisibility(View.VISIBLE);
            constructionAccount.setVisibility(View.VISIBLE);
            constructionAccountLabel.setVisibility(View.VISIBLE);
        }else{
            constructionAmount.setVisibility(View.GONE);
            constructionAmountLabel.setVisibility(View.GONE);
            constructionAccount.setVisibility(View.GONE);
            constructionAccountLabel.setVisibility(View.GONE);
        }
        if (isAlterReciept.isChecked()) {
            alterAmount.setVisibility(View.VISIBLE);
            alterAmountLabel.setVisibility(View.VISIBLE);
        }else {
            alterAmount.setVisibility(View.GONE);
            alterAmountLabel.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case -1:
                finish();
                break;
            case R.id.submit:
                if(submitData()) {
                    submit(true);
                }
                break;
//            case R.id.fillDate:
//                showAlertDialog(fillDate);
//                break;
            case R.id.tailDate:
                showAlertDialog(tailDate);
                break;
            case R.id.assuranceDate:
                showAlertDialog(assuranceDate);
                break;
        }

    }

    public void submit(boolean isSubmit){
        Request<String> request = new StringRequest(Constants.URL_SUBMIT, RequestMethod.JSON);
        if (isSubmit) { // 提交数据
            request.add("code", "3");
            request.add("id", id);
            request.add("data", order.toJson());
        }else{ // 初始化数据
            request.add("code", "31");
            request.add("id", id);
            request.add("mark", code);
        }
        RequestExecutor.INTANCE.execute(request, new HttpListener<String>() {

            @Override
            public void onSucceed(Response<String> response) {
                JSONObject jsonObject = null;
                String str = response.get();
                Logger.i("Activity 接受到的结果:" + str);
                int code = 0;
                try {
                    jsonObject = new JSONObject(str);
                    code = jsonObject.optInt("code");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switch (code){
                    case 0:
                        Toast.makeText(OrderAddActivity.this, "数据错误, 请联系管理员", Toast.LENGTH_SHORT).show();
                        break;
                    case 1: // 提交数据反馈
                        Toast.makeText(OrderAddActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case 2: // 初始化找到记录时, 处理记录的数据
                        Toast.makeText(OrderAddActivity.this, "有未处理的记录", Toast.LENGTH_SHORT).show();
                        dealResponse(jsonObject.optString("data"));
                        break;
                    case 3: // 初始化没有找到记录, 则生成订单号
                        Toast.makeText(OrderAddActivity.this, "无记录", Toast.LENGTH_SHORT).show();
                        orderNumber.setText(jsonObject.optString("orderNumber"));
                        fillDate.setText(TimestampUtil.getCurrentTime(jsonObject.optString("fillDate")));
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

    public boolean submitData(){
        order.setId(id);
        order.setCode(code);
//        if ("".equals(getData(orderNumber)) || getData(orderNumber) == null)
//            return false;
        order.setOrderNumber(getData(orderNumber));

//        if ("".equals(getData(fillDate)) || getData(fillDate) == null){
//            Toast.makeText(this, "请填写", Toast.LENGTH_SHORT).show();
//            return false;
//        }

        order.setFillDate(getData(fillDate));

        if ("".equals(getData(contractNumber)) || getData(contractNumber) == null){
            Toast.makeText(this, "请填写合同号", Toast.LENGTH_SHORT).show();
            return false;
        }
        order.setContractNumber(getData(contractNumber));

        if ("".equals(getData(goodsCount)) || getData(goodsCount) == null){
            Toast.makeText(this, "请填写货物数量", Toast.LENGTH_SHORT).show();
            return false;
        }
        order.setGoodsCount(getData(goodsCount));

        if ("".equals(getData(method)) || getData(method) == null){
            Toast.makeText(this, "请填写发货方式", Toast.LENGTH_SHORT).show();
            return false;
        }
        order.setMethod(getData(method));

        if ("".equals(getData(price)) || getData(price) == null){
            Toast.makeText(this, "请填写发货价格", Toast.LENGTH_SHORT).show();
            return false;
        }
        order.setExpressPrice(getData(price));

        if ("".equals(getData(payer)) || getData(payer) == null){
            Toast.makeText(this, "请填写邮费支付方式", Toast.LENGTH_SHORT).show();
            return false;
        }
        order.setPayer(getData(payer));

        if ("".equals(getData(customer)) || getData(customer) == null){
            Toast.makeText(this, "请填写公司名称", Toast.LENGTH_SHORT).show();
            return false;
        }
        order.setCustomerCompany(getData(customer));

        if (getData(isAlterReciept)) {
            if ("".equals(getData(alterAmount)) || getData(alterAmount) == null){
                Toast.makeText(this, "请填写代收金额", Toast.LENGTH_SHORT).show();
                return false;
            }
            order.setAlterAmount(getData(alterAmount));
        }

        if ("".equals(getData(sendTo)) || getData(sendTo) == null){
            Toast.makeText(this, "请填写收货地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        order.setSendTo(getData(sendTo));

        if ("".equals(getData(contact)) || getData(contact) == null){
            Toast.makeText(this, "请填写联系人", Toast.LENGTH_SHORT).show();
            return false;
        }
        order.setContact(getData(contact));

        if ("".equals(getData(contactTel)) || getData(contactTel) == null){
            Toast.makeText(this, "请填写联系电话", Toast.LENGTH_SHORT).show();
            return false;
        }
        order.setContactTel(getData(contactTel));

        if ("".equals(getData(recieptBank)) || getData(recieptBank) == null){
            Toast.makeText(this, "请填写收款银行", Toast.LENGTH_SHORT).show();
            return false;
        }
        order.setRecieptBank(getData(recieptBank));

        if ("".equals(getData(contractAmount)) || getData(contractAmount) == null){
            Toast.makeText(this, "请填写合同金额", Toast.LENGTH_SHORT).show();
            return false;
        }
        order.setContractAmount(getData(contractAmount));

        if ("".equals(getData(deposit)) || getData(deposit) == null){
            Toast.makeText(this, "请填写定金", Toast.LENGTH_SHORT).show();
            return false;
        }
        order.setDeposit(getData(deposit));

//        if ("".equals(getData(tail)) || getData(tail) == null)
//            return false;
//        order.setTail(getData(tail));

        if ("".equals(getData(tailDate)) || getData(tailDate) == null){
            Toast.makeText(this, "请选择收款日期", Toast.LENGTH_SHORT).show();
            return false;
        }
        order.setTailDate(getData(tailDate));

        if (getData(isAssurance)) {
            if ("".equals(getData(assurance)) || getData(assurance) == null){
                Toast.makeText(this, "请填写质保金", Toast.LENGTH_SHORT).show();
                return false;
            }
            order.setAssurance(getData(assurance));
        }

        if (getData(isAssurance)) {
            if ("".equals(getData(assuranceDate)) || getData(assuranceDate) == null){
                Toast.makeText(this, "请选择质保金收取时间", Toast.LENGTH_SHORT).show();
                return false;
            }
            order.setAssuranceDate(getData(assuranceDate));
        }

        if (getData(isConstruction)) {
            if ("".equals(getData(constructionAmount)) || getData(constructionAmount) == null){
                Toast.makeText(this, "请填写施工款", Toast.LENGTH_SHORT).show();
                return false;
            }
            order.setContractAmount(getData(constructionAmount));
        }

        if (getData(isConstruction)) {
            if ("".equals(getData(constructionAccount)) || getData(constructionAccount) == null){
                Toast.makeText(this, "请填写施工款账号", Toast.LENGTH_SHORT).show();
                return false;
            }
            order.setConstructionAccount(getData(constructionAccount));
        }
        order.setDistribution(getData(isDistribution));
        order.setBorrowData(getData(isBorrowData));
        order.setAssurance(getData(isAssurance));
        order.setConstruction(getData(isConstruction));
        order.setSpecialOffer(getData(isSpecialOffer));
        order.setSimpleOrder(getData(isSimpleOrder));
        order.setAlterReciept(getData(isAlterReciept));
        order.setNoticeDelivery(getData(isNoticeDelivery));
        order.setContainTax(getData(isContainTax));


        return true;
    }

    public String getData(EditText editText){
        String text = "";
        text = editText.getText().toString().trim();
        if ("".equals(text) || text == null)
            editText.requestFocus();
        return text;
    }
    public String getData(TextView textView){
        String text = textView.getText().toString().trim();
        if ("".equals(text) || text == null)
            textView.requestFocus();
        return text;
    }
    public boolean getData(CheckBox checkBox){
        if(checkBox == isAssurance){
            String textDate = assuranceDate.getText().toString().trim();
            String text = assurance.getText().toString().trim();
            if ("".equals(text) || text == null) {
                assuranceDate.requestFocus();
            }
            else if("".equals(textDate) || textDate == null) {
                assurance.requestFocus();
            }
        }
        if(checkBox == isDistribution){
            String textAmount = constructionAmount.getText().toString();
            String textAccount = constructionAccount.getText().toString();
            if ("".equals(textAmount) || textAmount == null)
                constructionAmount.requestFocus();
            else if("".equals(textAccount) || textAccount == null)
                constructionAccount.requestFocus();
        }
        if(checkBox == isAlterReciept) {
            String textAlter = alterAmount.getText().toString();
            if ("".equals(textAlter) || textAlter == null)
                alterAmount.requestFocus();
        }

        return checkBox.isChecked();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        Toast.makeText(this, String.valueOf(Float.valueOf(contractAmount.getText().toString())), Toast.LENGTH_SHORT).show();
        float amount = "".equals(contractAmount.getText().toString()) ? 0 : Float.valueOf(contractAmount.getText().toString());
        float sit = "".equals(deposit.getText().toString()) ? 0 : Float.valueOf(deposit.getText().toString());
        if (amount == 0 || sit >= amount)
            tail.setText("0");
        else
            tail.setText(String.valueOf(amount - sit));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void showAlertDialog(TextView id){
        final TextView textViewId = id;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(OrderAddActivity.this, "选择日期", Toast.LENGTH_SHORT).show();
                if (date.length() > 0) { //清除上次记录的日期
                    date.delete(0, date.length());
                }
                textViewId.setText(date.append(String.valueOf(year)).append("/").append(String.valueOf(month + 1)).append("/").append(day));
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(this, R.layout.dialog_date, null);
        final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        dialog.setTitle("设置日期");
        dialog.setView(dialogView);
        dialog.show();
        //初始化日期监听事件
        datePicker.init(year, month, day, this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        checkOut();
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
    }

    public void dealResponse(String response){
        try {
            JSONObject jsonObject1 = new JSONObject(response);
            // 基本信息
            orderNumber.setText(jsonObject1.optString("orderNumber"));
            isDistribution.setChecked("0".equals(jsonObject1.optString("isDistribution")) ? false : true);
            isSpecialOffer.setChecked("0".equals(jsonObject1.optString("isSpecialOffer")) ? false : true);
            fillDate.setText(TimestampUtil.getCurrentTime(jsonObject1.optString("fillDate")));
            isSimpleOrder.setChecked("0".equals(jsonObject1.optString("isSimpleOrder")) ? false : true);
            contractNumber.setText(jsonObject1.optString("contractNumber"));
            isConstruction.setChecked("0".equals(jsonObject1.optString("isConstruction")) ? false : true);
            isBorrowData.setChecked("0".equals(jsonObject1.optString("isBorrowData")) ? false : true);
            goodsCount.setText(jsonObject1.optString("goodsCount"));
            isAssurance.setChecked("0".equals(jsonObject1.optString("isAssurance")) ? false : true);

            // 发货信息
            method.setText("null".equals(jsonObject1.optString("method")) ? "" : jsonObject1.optString("method"));
            price.setText("null".equals(jsonObject1.optString("price")) ? "" : jsonObject1.optString("price"));
            payer.setText("null".equals(jsonObject1.optString("payer")) ? "" : jsonObject1.optString("payer"));
            isAlterReciept.setChecked("0".equals(jsonObject1.optString("isAlterReciept")) || "null".equals(jsonObject1.optString("isAlterReciept")) ? false : true);
            alterAmount.setText("null".equals(jsonObject1.optString("alterAmount")) ? "" : jsonObject1.optString("alterAmount"));
            isNoticeDelivery.setChecked("0".equals(jsonObject1.optString("isNoticeDelivery")) || "null".equals(jsonObject1.optString("isNoticeDelivery")) ? false : true);
            sendTo.setText("null".equals(jsonObject1.optString("sendTo")) ? "" : jsonObject1.optString("sendTo"));
            customer.setText("null".equals(jsonObject1.optString("customer")) ? "" : jsonObject1.optString("customer"));
            contact.setText("null".equals(jsonObject1.optString("contact")) ? "" : jsonObject1.optString("contact"));
            contactTel.setText("null".equals(jsonObject1.optString("contactTel")) ? "" : jsonObject1.optString("contactTel"));

            // 收款信息
            recieptBank.setText("null".equals(jsonObject1.optString("recieptBank")) ? "" : jsonObject1.optString("recieptBank"));
            isContainTax.setChecked("0".equals(jsonObject1.optString("isContainTax")) || "null".equals(jsonObject1.optString("isContainTax")) ? false : true);
            deposit.setText("null".equals(jsonObject1.optString("deposit")) ? "" : jsonObject1.optString("deposit"));
            assurance.setText("null".equals(jsonObject1.optString("assurance")) ? "" : jsonObject1.optString("assurance"));
            assuranceDate.setText("null".equals(jsonObject1.optString("assuranceDate")) ? "" : TimestampUtil.getCurrentTime(jsonObject1.optString("assuranceDate")));
            constructionAmount.setText("null".equals(jsonObject1.optString("constructionAmount")) ? "" : jsonObject1.optString("constructionAmount"));
            constructionAccount.setText("null".equals(jsonObject1.optString("constructionAccount")) ? "" : jsonObject1.optString("constructionAccount"));
            tail.setText("null".equals(jsonObject1.optString("tail")) ? "" : jsonObject1.optString("tail"));
            tailDate.setText("null".equals(jsonObject1.optString("tailDate")) ? "" : TimestampUtil.getCurrentTime(jsonObject1.optString("tailDate")));
            contractAmount.setText("null".equals(jsonObject1.optString("contractAmount")) ? "" : jsonObject1.optString("contractAmount"));
            checkOut();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
