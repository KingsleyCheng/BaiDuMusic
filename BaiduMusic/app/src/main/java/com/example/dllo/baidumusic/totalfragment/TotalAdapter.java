package com.example.dllo.baidumusic.totalfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/21.
 */
public class TotalAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;
    private String[] title = {"我的", "乐库", "K歌", "直播"};

    public void setFragments(ArrayList<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public TotalAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments != null && fragments.size() > 0 ? fragments.get(position) : null;
    }

    @Override
    public int getCount() {
        return fragments != null && fragments.size() > 0 ? fragments.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
