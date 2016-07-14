package com.example.dllo.baidumusic.songplaypage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dllo.baidumusic.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/28.
 */
public class PlayPageAdapter extends FragmentPagerAdapter {
    private ArrayList<BaseFragment> fragments;

    public void setFragments(ArrayList<BaseFragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public PlayPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }
}
