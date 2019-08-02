package com.tifone.opengl.demo.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TabHost;

/**
 * Create by Tifone on 2019/7/8.
 */
public class TabLayout extends TabHost {
    public TabLayout(Context context) {
        super(context);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        
    }
}
