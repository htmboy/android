package com.aooled_laptop.tab1;

import android.net.sip.SipSession;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    private List<View> mViews = new ArrayList<View>();

    // tab
    private LinearLayout mTabWeixin;
    private LinearLayout mTabFrd;
    private LinearLayout mTabAddress;
    private LinearLayout mTabSettings;

    // img
    private ImageButton mWeixinImg;
    private ImageButton mFrdImg;
    private ImageButton mAddressImg;
    private ImageButton mSettingsImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        initView();

        initEvents();
    }

    private void initEvents() {
        mTabWeixin.setOnClickListener(this);
        mTabFrd.setOnClickListener(this);
        mTabAddress.setOnClickListener(this);
        mTabSettings.setOnClickListener(this);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int currentItem = mViewPager.getCurrentItem();
                resetImg();
                switch (currentItem){
                    case 0:
                        mWeixinImg.setImageResource(R.mipmap.tab_weixin_pressed);
                        break;
                    case 1:
                        mFrdImg.setImageResource(R.mipmap.tab_find_frd_pressed);
                        break;
                    case 2:
                        mAddressImg.setImageResource(R.mipmap.tab_address_pressed);
                        break;
                    case 3:
                        mSettingsImg.setImageResource(R.mipmap.tab_settings_pressed);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initView() {
        mViewPager = findViewById(R.id.viewpager);
        mTabWeixin = findViewById(R.id.id_tab_weixin);
        mTabFrd = findViewById(R.id.id_tab_frd);
        mTabAddress = findViewById(R.id.id_tab_address);
        mTabSettings = findViewById(R.id.id_tab_settings);

        mWeixinImg = findViewById(R.id.id_tab_weixin_img);
        mFrdImg = findViewById(R.id.id_tab_frd_img);
        mAddressImg = findViewById(R.id.id_tab_address_img);
        mSettingsImg = findViewById(R.id.id_tab_settings_img);

        // 初始化布局
        LayoutInflater mInflater = LayoutInflater.from(this);

        View tab01 = mInflater.inflate(R.layout.tab01, null);
        View tab02 = mInflater.inflate(R.layout.tab02, null);
        View tab03 = mInflater.inflate(R.layout.tab03, null);
        View tab04 = mInflater.inflate(R.layout.tab04, null);

        mViews.add(tab01);
        mViews.add(tab02);
        mViews.add(tab03);
        mViews.add(tab04);

        mAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return mViews.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view =  mViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(mViews.get(position));
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }
        };

        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        resetImg();
        switch (v.getId()){
            case R.id.id_tab_weixin:
                mViewPager.setCurrentItem(0);
                mWeixinImg.setImageResource(R.mipmap.tab_weixin_pressed);
                break;
            case R.id.id_tab_frd:
                mViewPager.setCurrentItem(1);
                mFrdImg.setImageResource(R.mipmap.tab_find_frd_pressed);
                break;
            case R.id.id_tab_address:
                mViewPager.setCurrentItem(2);
                mAddressImg.setImageResource(R.mipmap.tab_address_pressed);
                break;
            case R.id.id_tab_settings:
                mViewPager.setCurrentItem(3);
                mSettingsImg.setImageResource(R.mipmap.tab_settings_pressed);
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
