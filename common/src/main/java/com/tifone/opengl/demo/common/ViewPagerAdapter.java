package com.tifone.opengl.demo.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private HashMap<String, Fragment> mFragmentMap;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
    }
    public void setFragments(List<Fragment> fragments) {
        mFragments = fragments;
        notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int index) {
        if (index > mFragments.size()) {
            return null;
        }
        return mFragments.get(index);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
