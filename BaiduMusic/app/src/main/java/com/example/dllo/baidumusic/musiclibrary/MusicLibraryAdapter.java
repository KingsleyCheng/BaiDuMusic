package com.example.dllo.baidumusic.musiclibrary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/17.
 */
public class MusicLibraryAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;
    private String[] title = {"推荐", "排行", "歌单", "电台", "MV"};

    public void setFragments(ArrayList<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public MusicLibraryAdapter(FragmentManager fm) {
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
