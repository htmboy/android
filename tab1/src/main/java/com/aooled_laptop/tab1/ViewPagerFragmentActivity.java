package com.aooled_laptop.tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager + FragmentPagerAdapter
 */
public class ViewPagerFragmentActivity extends android.support.v4.app.FragmentActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;

    private LinearLayout mTabWeixin;
    private LinearLayout mTabFrd;
    private LinearLayout mTabAddress;
    private LinearLayout mTabSettings;

    private ImageButton mImgWeixin;
    private ImageButton mImgFrd;
    private ImageButton mImgAddress;
    private ImageButton mImgSettings;

    private Fragment mTab01;
    private Fragment mTab02;
    private Fragment mTab03;
    private Fragment mTab04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.viewfragment);
        initView();
        initEvents();
        setSelect(1);
    }

    private void initView() {
        mViewPager = findViewById(R.id.viewpager);

        mTabWeixin = findViewById(R.id.id_tab_weixin);
        mTabFrd = findViewById(R.id.id_tab_frd);
        mTabAddress = findViewById(R.id.id_tab_address);
        mTabSettings = findViewById(R.id.id_tab_settings);

        mImgWeixin = findViewById(R.id.id_tab_weixin_img);
        mImgFrd = findViewById(R.id.id_tab_frd_img);
        mImgAddress = findViewById(R.id.id_tab_address_img);
        mImgSettings = findViewById(R.id.id_tab_settings_img);

        mFragments = new ArrayList<>();

        mTab01 = new WeixinFragment();
        mTab02 = new FrdFragment();
        mTab03 = new AddressFragment();
        mTab04 = new SettingFragment();

        mFragments.add(mTab01);
        mFragments.add(mTab02);
        mFragments.add(mTab03);
        mFragments.add(mTab04);
//        mAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public Fragment getItem(int i) {
//                return mFragments.get(i);
//            }
//
//            @Override
//            public int getCount() {
//                return mFragments.size();
//            }
//        };

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragments.get(i);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        mViewPager.setAdapter(mAdapter);
    }
//
    private void initEvents() {
        mTabWeixin.setOnClickListener(this);
        mTabFrd.setOnClickListener(this);
        mTabAddress.setOnClickListener(this);
        mTabSettings.setOnClickListener(this);
    }

    private void resetImg() {
        mImgWeixin.setImageResource(R.mipmap.tab_weixin_normal);
        mImgFrd.setImageResource(R.mipmap.tab_find_frd_normal);
        mImgAddress.setImageResource(R.mipmap.tab_address_normal);
        mImgSettings.setImageResource(R.mipmap.tab_settings_normal);
    }

//    @Override
    public void onClick(View v) {
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

    private void setSelect(int i) {
        resetImg();
//        mViewPager.setCurrentItem(i);
        switch (i){
            case 0:
                mImgWeixin.setImageResource(R.mipmap.tab_weixin_pressed);
                break;
            case 1:
                mImgFrd.setImageResource(R.mipmap.tab_find_frd_pressed);
                break;
            case 2:
                mImgAddress.setImageResource(R.mipmap.tab_address_pressed);
                break;
            case 3:
                mImgSettings.setImageResource(R.mipmap.tab_settings_pressed);
                break;
            default:
                break;
        }
    }
}
