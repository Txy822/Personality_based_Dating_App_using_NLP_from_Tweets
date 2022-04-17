package com.txy822.android_personality_based_dating_app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class CustomViewPager extends ViewPager {
    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event){
//        never allow swiping to switch between pages
        return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
//        never allow swiping to switch between pages
        return false;
    }
}
