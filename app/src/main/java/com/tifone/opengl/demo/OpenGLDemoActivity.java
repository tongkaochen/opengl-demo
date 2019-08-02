package com.tifone.opengl.demo;

import android.support.v4.app.Fragment;

import com.tifone.opengl.demo.common.MyTabActivity;
import com.tifone.opengl.demo.hockey.HockeyFragment;

import java.util.ArrayList;
import java.util.List;

public class OpenGLDemoActivity extends MyTabActivity {

    @Override
    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HockeyFragment.create("Hockey ball"));
        return fragments;
    }
}
