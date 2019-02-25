package com.aooled_laptop.tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class FragmentActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mTabWeixin;
    private LinearLayout mTabFrd;
    private LinearLayout mTabAddress;
    private LinearLayout mTabSettings;

    private ImageButton mWeixinImg;
    private ImageButton mFrdImg;
    private ImageButton mAddressImg;
    private ImageButton mSettingsImg;

    private Fragment mTab01;
    private Fragment mTab02;
    private Fragment mTab03;
    private Fragment mTab04;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment);
        initView();
        initEvents();
        setSelect(0);
    }

    private void initView() {
        mTabWeixin = findViewById(R.id.id_tab_weixin);
        mTabFrd = findViewById(R.id.id_tab_frd);
        mTabAddress = findViewById(R.id.id_tab_address);
        mTabSettings = findViewById(R.id.id_tab_settings);

        mWeixinImg = findViewById(R.id.id_tab_weixin_img);
        mFrdImg = findViewById(R.id.id_tab_frd_img);
        mAddressImg = findViewById(R.id.id_tab_address_img);
        mSettingsImg = findViewById(R.id.id_tab_settings_img);

//        mTab01 = new WeixinFragment();
//        mTab02 = new FrdFragment();
//        mTab03 = new AddressFragment();
//        mTab04 = new SettingFragment();
    }

    private void initEvents() {
        mTabWeixin.setOnClickListener(this);
        mTabFrd.setOnClickListener(this);
        mTabAddress.setOnClickListener(this);
        mTabSettings.setOnClickListener(this);
    }

    private void setSelect(int i){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (i){
            case 0:
                if (mTab01 == null) {
                    mTab01 = new WeixinFragment();
                    transaction.add(R.id.id_content, mTab01);
                }
                else
                    transaction.show(mTab01);
                mWeixinImg.setImageResource(R.mipmap.tab_weixin_pressed);
                break;
            case 1:
                if (mTab02 == null) {
                    mTab02 = new FrdFragment();
                    transaction.add(R.id.id_content, mTab02);
                }
                else
                    transaction.show(mTab02);
                mFrdImg.setImageResource(R.mipmap.tab_find_frd_pressed);
                break;
            case 2:
                if (mTab03 == null) {
                    mTab03 = new AddressFragment();
                    transaction.add(R.id.id_content, mTab03);
                }
                else
                    transaction.show(mTab03);
                mAddressImg.setImageResource(R.mipmap.tab_address_pressed);
                break;
            case 3:
                if (mTab04 == null) {
                    mTab04 = new SettingFragment();
                    transaction.add(R.id.id_content, mTab04);
                }
                else
                    transaction.show(mTab04);
                mSettingsImg.setImageResource(R.mipmap.tab_settings_pressed);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if(mTab01 != null)
            transaction.hide(mTab01);
        if(mTab02 != null)
            transaction.hide(mTab02);
        if(mTab03 != null)
            transaction.hide(mTab03);
        if(mTab04 != null)
            transaction.hide(mTab04);

    }

    @Override
    public void onClick(View v) {
        resetImg();
        switch (v.getId()){
            case R.id.id_tab_weixin:
                setSelect(0);
                break;
            case R.id.id_tab_frd:
                setSelect(1);
                break;
            case R.id.id_tab_address:
                setSelect(2);
                break;
            case R.id.id_tab_settings:
                setSelect(3);
                break;
            default:
                break;
        }
    }

    private void resetImg() {
        mWeixinImg.setImageResource(R.mipmap.tab_weixin_normal);
        mFrdImg.setImageResource(R.mipmap.tab_find_frd_normal);
        mAddressImg.setImageResource(R.mipmap.tab_address_normal);
        mSettingsImg.setImageResource(R.mipmap.tab_settings_normal);
    }
}
