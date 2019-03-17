package com.aooled_laptop.aooled;

import android.content.DialogInterface;
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

import java.util.Calendar;

public class OrderAddActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, DatePicker.OnDateChangedListener {
    private EditText orderNumber, contractNumber, goodsCount, method, price, payer, customer;
    private EditText alterAmount, sendTo, contact, contactTel, recieptBank, contractAmount, deposit;
    private EditText tail, assurance, constructionAmount, constructionAccount;
    private TextView fillDate, tailDate, assuranceDate, alterAmountLabel, assuranceLabel, assuranceDateLabel, constructionAmountLabel, constructionAccountLabel;
    private CheckBox isDistribution, isBorrowData, isAssurance, isConstruction, isSpecialOffer, isSimpleOrder, isAlterReciept, isNoticeDelivery, isContainTax;
    private int year, month, day, hour, minute;
    private StringBuffer date = new StringBuffer();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.order_add);
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
