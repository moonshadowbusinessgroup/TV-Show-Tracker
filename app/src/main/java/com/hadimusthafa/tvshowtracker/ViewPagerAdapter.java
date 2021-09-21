package com.hadimusthafa.tvshowtracker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.json.JSONArray;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    int mNumOfTabs;
    JSONArray ja_one;

    public ViewPagerAdapter(FragmentManager fm, int NumOfTabs, int behavior, JSONArray ja_one) {
        super(fm, behavior);
        this.mNumOfTabs = NumOfTabs;
        this.ja_one = ja_one;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentOne(ja_one);
            case 1:
                return new FragmentTwo();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}