package com.example.dllo.baidumusic.musiclibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseFragment;
import com.example.dllo.baidumusic.musiclibrary.charts.ChartsFragment;
import com.example.dllo.baidumusic.musiclibrary.mv.MvFragment;
import com.example.dllo.baidumusic.musiclibrary.radiostation.RadioStationFragment;
import com.example.dllo.baidumusic.musiclibrary.recommend.RecommendFragment;
import com.example.dllo.baidumusic.musiclibrary.songmenu.SongMenuFragment;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/17.
 */
public class MusicLibraryFragment extends BaseFragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MusicLibraryAdapter musicLibraryAdapter;
    private ArrayList<Fragment> fragments;
    private recommendMoreToSongMenu recommendMoreToSongMenu;

    @Override
    public int setLayout() {
        return R.layout.fragment_music_library;
    }


    @Override
    protected void intiView(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.music_library_tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.music_library_view_pager);


    }


    @Override
    protected void initData() {
        //注册广播
        recommendMoreToSongMenu = new recommendMoreToSongMenu();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.dllo.baidumusic.recommend_more_to_song_menu");
        context.registerReceiver(recommendMoreToSongMenu, intentFilter);

        musicLibraryAdapter = new MusicLibraryAdapter(getChildFragmentManager());
        fragments = new ArrayList<>();
        fragments.add(new RecommendFragment());
        fragments.add(new ChartsFragment());
        fragments.add(new SongMenuFragment());
        fragments.add(new RadioStationFragment());
        fragments.add(new MvFragment());
        musicLibraryAdapter.setFragments(fragments);
        viewPager.setAdapter(musicLibraryAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.BLACK, Color.rgb(0, 191, 225));
        tabLayout.setSelectedTabIndicatorColor(Color.rgb(0, 191, 225));


    }

    //广播:点击推荐页面更多跳转到歌单页面
    class recommendMoreToSongMenu extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            viewPager.setCurrentItem(2);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(recommendMoreToSongMenu);
    }
}
