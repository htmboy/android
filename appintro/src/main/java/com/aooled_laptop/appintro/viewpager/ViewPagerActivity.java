package com.aooled_laptop.appintro.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.aooled_laptop.appintro.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends FragmentActivity {
    private ViewPager viewPager;
    private LinearLayout llindicator;

    private PagerAdapter adapter;
    private List<Fragment> fragments = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpage);
        viewPager = findViewById(R.id.viewPage);
        llindicator = findViewById(R.id.ll_indicator);

        // 创建Fragment
        for (int i = 0; i < 3; i++){
            ContentFragment fragment = new ContentFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int index, float v, int i1) {
                for (int i = 0; i< fragments.size(); i++){
                    llindicator.getChildAt(i).setBackgroundResource(index == i ? R.drawable.dot_focus : R.drawable.dot_normal);
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        initIndicator();
    }


    private  void initIndicator(){
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, width);
        lp.rightMargin = 2 * width;
        for (int i = 0; i< fragments.size(); i++){
            View view = new View(this);
            view.setId(i);
            view.setBackgroundResource(i == 0 ? R.drawable.dot_focus : R.drawable.dot_normal);
            view.setLayoutParams(lp);
            llindicator.addView(view, i);
        }
    }
}
