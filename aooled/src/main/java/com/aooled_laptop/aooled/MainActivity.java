package com.aooled_laptop.aooled;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.aooled_laptop.aooled.adapter.ViewPagerAdapter;
import com.aooled_laptop.aooled.fragment.ContactFragment;
import com.aooled_laptop.aooled.fragment.OrderListFragment;
import com.aooled_laptop.aooled.fragment.PerasonFragment;
import com.aooled_laptop.aooled.fragment.SetFragment;
import com.aooled_laptop.aooled.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private RadioButton person, orderlist, contact, set;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private List<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        Bundle bundle = getIntent().getExtras();
        findViewById(R.id.add).setOnClickListener(this);

        fragments = new ArrayList<>();
        PerasonFragment perasonFragment = new PerasonFragment();
        perasonFragment.setArguments(bundle);
        fragments.add(perasonFragment);
        OrderListFragment orderListFragment = new OrderListFragment();
        orderListFragment.setArguments(bundle);
        fragments.add(orderListFragment);
        ContactFragment contactFragment = new ContactFragment();
        contactFragment.setArguments(bundle);
        fragments.add(contactFragment);
        fragments.add(new SetFragment());

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        getDate();

    }

    private void getDate() {
    }

    private void initView() {
        person = findViewById(R.id.person);
        orderlist = findViewById(R.id.orderlist);
        contact = findViewById(R.id.contact);
        set = findViewById(R.id.set);
        viewPager = findViewById(R.id.viewPager);

        person.setOnClickListener(this);
        orderlist.setOnClickListener(this);
        contact.setOnClickListener(this);
        set.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.person:
//                person.(R.mipmap.account72px);
//                person.setBackgroundResource(R.mipmap.account72px);
                setSelect(0);
                break;
            case R.id.orderlist:
//                orderlist.setBackgroundResource(R.mipmap.detail72px);
                setSelect(1);
                break;
            case R.id.contact:
//                contact.setBackgroundResource(R.mipmap.detail64px);
                setSelect(2);
                break;
            case R.id.set:
//                set.setBackgroundResource(R.mipmap.settings72px);
                setSelect(3);
                break;
            case R.id.add:

            default:
                break;
        }
    }

    public void setSelect(int i){
        viewPager.setCurrentItem(i);
    }


}
