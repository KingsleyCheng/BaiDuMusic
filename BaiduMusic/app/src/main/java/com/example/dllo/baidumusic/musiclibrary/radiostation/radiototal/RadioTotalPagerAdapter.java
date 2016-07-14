package com.example.dllo.baidumusic.musiclibrary.radiostation.radiototal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by dllo on 16/6/23.
 */
public class RadioTotalPagerAdapter extends FragmentPagerAdapter {
    private String[] titles = {"活动", "主题","天气", "心情", "语言", "年代", "曲风"};

    public RadioTotalPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return RadioTotalFragment.getInstance(position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
