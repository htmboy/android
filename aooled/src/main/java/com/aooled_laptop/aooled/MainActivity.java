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
import android.widget.Adapter;
import android.widget.RadioButton;

import com.aooled_laptop.aooled.adapter.ViewPagerAdapter;
import com.aooled_laptop.aooled.fragment.ContactFragment;
import com.aooled_laptop.aooled.fragment.OrderListFragment;
import com.aooled_laptop.aooled.fragment.PerasonFragment;
import com.aooled_laptop.aooled.fragment.SetFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnClickListener {
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private RadioButton person, orderlist, contact, set;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private List<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        fragments = new ArrayList<>();
        fragments.add(new PerasonFragment());
        fragments.add(new OrderListFragment());
        fragments.add(new ContactFragment());
        fragments.add(new SetFragment());

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);

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
                setSelect(0);
                break;
            case R.id.orderlist:
                setSelect(1);
                break;
            case R.id.contact:
                setSelect(2);
                break;
            case R.id.set:
                setSelect(3);
                break;
            default:
                break;
        }
    }

    public void setSelect(int i){
        viewPager.setCurrentItem(i);
    }
}
