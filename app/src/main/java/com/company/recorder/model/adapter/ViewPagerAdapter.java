package com.company.recorder.model.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.company.recorder.view.fragment.MusicFragment;
import com.company.recorder.view.fragment.RecorderFragment;



public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final int numOfTabs;

    public ViewPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RecorderFragment();
            case 1:
                return new MusicFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}