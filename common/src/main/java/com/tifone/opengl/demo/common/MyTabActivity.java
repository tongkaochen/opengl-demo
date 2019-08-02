package com.tifone.opengl.demo.common;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用的demo列表管理器
 */
public abstract class MyTabActivity extends AppCompatActivity implements TabHost.OnTabChangeListener {

    private ViewPager mViewPager;
    private TabHost mTabHost;
    private ViewPagerAdapter mAdapter;
    private List<Fragment> mFragments;
    private List<TabHost.TabSpec> mTabSpecs;
    private Drawable mDrawable;
    private Map<Integer, String> mTagMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabHost.setOnTabChangedListener(this);
        mTagMap = new HashMap<>();
        initViewPager();
        initTabHost();
    }
    protected void setFragments(List<Fragment> fragments) {
        mFragments = fragments;
        initTabHost();
    }
    private void initTabHost() {
        mTabSpecs = new ArrayList<>();
        int index = 0;
        for (Fragment fragment : mFragments) {
            Bundle bundle = fragment.getArguments();
            String title;
            if (bundle != null) {
                title = bundle.getString(Constants.KEY_FRAGMENT_TITLE);
            } else {
                title = fragment.getClass().getSimpleName();
            }
            TabHost.TabSpec spec = createSpec(index, title);
            mTabSpecs.add(spec);
            mTabHost.addTab(spec);
            index++;
        }
        mTabHost.setCurrentTab(defaultTab());
    }
    protected int defaultTab() {
        return 0;
    }
    protected void setTabDrawable(Drawable drawable) {
        mDrawable = drawable;
    }
    private TabHost.TabSpec createSpec(int tag, String title) {
        TabHost.TabSpec spec = mTabHost.newTabSpec(String.valueOf(tag));
        spec.setContent(R.id.tab_spec_content);
        spec.setIndicator(title, mDrawable == null? getDrawable(R.color.color_red) : mDrawable);
        return spec;
    }
//    private void initFragments() {
//        mFragments = new ArrayList<>();
//        mFragments.add(EventDispatchDemoFragment.create("Event demo"));
//    }
    public abstract List<Fragment> getFragments();
    private void createViewPagerAdapter() {
        mFragments = getFragments();
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mAdapter.setFragments(mFragments);
    }
    private void initViewPager() {
        createViewPagerAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(mOnPagerChangeListener);
    }
    private final ViewPager.OnPageChangeListener mOnPagerChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            mTabHost.setCurrentTabByTag(mTabSpecs.get(position).getTag());
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    @Override
    public void onTabChanged(String tabId) {
        logd("tabId = " + tabId);
        int currentId = getIndexForTag(tabId);
        if (currentId == -1) {
            return;
        }
        mViewPager.setCurrentItem(currentId, true);
    }
    private int getIndexForTag(String tag) {
        for (int i = 0; i < mTabSpecs.size(); i++) {
            TabHost.TabSpec spec = mTabSpecs.get(i);
            if (tag.equals(spec.getTag())) {
                return i;
            }
        }
        return -1;
    }
    private void logd(String msg) {
        Log.d("tifone", this.getLocalClassName() + ": " + msg);
    }
}
