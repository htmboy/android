package com.aooled_laptop.aooled;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class OrderAddActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private EditText orderNumber, fillDate, contractNumber, goodsCount, method, price, payer, customer;
    private EditText alterAmount, sendTo, contact, contactTel, recieptBank, contractAmount, deposit;
    private EditText tail, tailDate, assurance, assuranceDate, constructionAmount, constructionAccount;
    private TextView alterAmountLabel, assuranceLabel, assuranceDateLabel, constructionAmountLabel, constructionAccountLabel;
    private CheckBox isDistribution, isBorrowData, isAssurance, isConstruction, isSpecialOffer, isSimpleOrder, isAlterReciept, isNoticeDelivery, isContainTax;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.order_add);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.submit).setOnClickListener(this);
        init();
        checkOut();
        event();
    }
    private void event(){
        isAssurance.setOnCheckedChangeListener(this);
        isConstruction.setOnCheckedChangeListener(this);
        isAlterReciept.setOnCheckedChangeListener(this);
    }

    private void init(){
        orderNumber = findViewById(R.id.orderNumber);
        fillDate = findViewById(R.id.fillDate);
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
        alterAmountLabel = findViewById(R.id.alterAmountLabel);
        assuranceLabel = findViewById(R.id.assuranceLabel);
        assuranceDateLabel = findViewById(R.id.assuranceDateLabel);
        constructionAmountLabel = findViewById(R.id.constructionAmountLabel);
        constructionAccountLabel = findViewById(R.id.constructionAccountLabel);
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
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        checkOut();
    }
}
