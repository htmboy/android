package com.aooled_laptop.appintro.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

    private OnScrollChangedListener onScrollChangedListener;
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollChangedListener != null){
            onScrollChangedListener.onScrollChange(t, oldl);
        }
    }



    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
    }

    public interface OnScrollChangedListener{
        void onScrollChange(int top, int oldTop);
    }
}
