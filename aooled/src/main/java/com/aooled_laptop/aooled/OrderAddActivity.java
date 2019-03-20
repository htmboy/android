package com.aooled_laptop.aooled;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

public class OrderAddActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, DatePicker.OnDateChangedListener {
    private EditText orderNumber, contractNumber, goodsCount, method, price, payer, customer;
    private EditText alterAmount, sendTo, contact, contactTel, recieptBank, contractAmount, deposit;
    private EditText tail, assurance, constructionAmount, constructionAccount;
    private TextView fillDate, tailDate, assuranceDate, alterAmountLabel, assuranceLabel, assuranceDateLabel, constructionAmountLabel, constructionAccountLabel;
    private CheckBox isDistribution, isBorrowData, isAssurance, isConstruction, isSpecialOffer, isSimpleOrder, isAlterReciept, isNoticeDelivery, isContainTax;
    private int year, month, day, hour, minute;
    private StringBuffer date = new StringBuffer();
    private Order order = new Order();
    private String id;
    private String code;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.order_add);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        code = bundle.getString("code");
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        init();
        checkOut();
        event();
        initDateTime();
    }
    private void event(){
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.submit).setOnClickListener(this);
        isAssurance.setOnCheckedChangeListener(this);
        isConstruction.setOnCheckedChangeListener(this);
        isAlterReciept.setOnCheckedChangeListener(this);
        fillDate.setOnClickListener(this);

        tailDate.setOnClickListener(this);
        assuranceDate.setOnClickListener(this);
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
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
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
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                if(submitData()) {
//                    Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();


                    submit();

                }
                break;
            case R.id.fillDate:
                showAlertDialog(fillDate);
                break;
            case R.id.tailDate:
                showAlertDialog(tailDate);
                break;
            case R.id.assuranceDate:
                showAlertDialog(assuranceDate);
                break;
        }

    }

    public void submit(){
        Request<String> request = new StringRequest(Constants.URL_UPLOAD, RequestMethod.JSON);
        request.add("code", "3");
        request.add("data", order.toJson());
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
                    case 0: Toast.makeText(OrderAddActivity.this, "数据错误, 请联系管理员", Toast.LENGTH_SHORT).show();
                        break;
                    case 1: Toast.makeText(OrderAddActivity.this, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                        finish();
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
        if ("".equals(getData(orderNumber)) || getData(orderNumber) == null)
            return false;
        order.setOrderNumber(getData(orderNumber));

        if ("".equals(getData(fillDate)) || getData(fillDate) == null)
            return false;
        order.setFillDate(getData(fillDate));

        if ("".equals(getData(contractNumber)) || getData(contractNumber) == null)
            return false;
        order.setContractNumber(getData(contractNumber));

        if ("".equals(getData(goodsCount)) || getData(goodsCount) == null)
            return false;
        order.setGoodsCount(getData(goodsCount));

        if ("".equals(getData(method)) || getData(method) == null)
            return false;
        order.setMethod(getData(method));

        if ("".equals(getData(price)) || getData(price) == null)
            return false;
        order.setExpressPrice(getData(price));

        if ("".equals(getData(payer)) || getData(payer) == null)
            return false;
        order.setPayer(getData(payer));

        if ("".equals(getData(customer)) || getData(customer) == null)
            return false;
        order.setCustomerCompany(getData(customer));

        if (getData(isAlterReciept)) {
            if ("".equals(getData(alterAmount)) || getData(alterAmount) == null)
                return false;
            order.setAlterAmount(getData(alterAmount));
        }

        if ("".equals(getData(sendTo)) || getData(sendTo) == null)
            return false;
        order.setSendTo(getData(sendTo));

        if ("".equals(getData(contact)) || getData(contact) == null)
            return false;
        order.setContact(getData(contact));

        if ("".equals(getData(contactTel)) || getData(contactTel) == null)
            return false;
        order.setContactTel(getData(contactTel));

        if ("".equals(getData(recieptBank)) || getData(recieptBank) == null)
            return false;
        order.setRecieptBank(getData(recieptBank));

        if ("".equals(getData(contractAmount)) || getData(contractAmount) == null)
            return false;
        order.setContractAmount(getData(contractAmount));

        if ("".equals(getData(deposit)) || getData(deposit) == null)
            return false;
        order.setDeposit(getData(deposit));

        if ("".equals(getData(tail)) || getData(tail) == null)
            return false;
        order.setTail(getData(tail));

        if ("".equals(getData(tailDate)) || getData(tailDate) == null)
            return false;
        order.setTailDate(getData(tailDate));

        if (getData(isAssurance)) {
            if ("".equals(getData(assurance)) || getData(assurance) == null)
                return false;
            order.setAssurance(getData(assurance));
        }

        if (getData(isAssurance)) {
            if ("".equals(getData(assuranceDate)) || getData(assuranceDate) == null)
                return false;
            order.setAssuranceDate(getData(assuranceDate));
        }

        if (getData(isConstruction)) {
            if ("".equals(getData(constructionAmount)) || getData(constructionAmount) == null)
                return false;
            order.setContractAmount(getData(constructionAmount));
        }

        if (getData(isConstruction)) {
            if ("".equals(getData(constructionAccount)) || getData(constructionAccount) == null)
                return false;
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
}
