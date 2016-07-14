package com.example.dllo.baidumusic.totalfragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dllo.baidumusic.MyApp;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseFragment;
import com.example.dllo.baidumusic.main.MainActivity;
import com.example.dllo.baidumusic.musiclibrary.MusicLibraryFragment;
import com.example.dllo.baidumusic.mymusic.MyMusicFragment;
import com.example.dllo.baidumusic.totalfragment.login.LoginImmediatelyActivity;

import java.util.ArrayList;

import cn.bmob.v3.BmobUser;

/**
 * Created by dllo on 16/6/21.
 */
public class TotalFragment extends BaseFragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TotalAdapter totalAdapter;
    private ArrayList<Fragment> fragments;
    private ImageView showSearchButton, totalLogin;
    private ChangeLoginBroadcast changeLoginBroadcast;

    @Override
    public int setLayout() {
        return R.layout.fragment_total;
    }

    @Override
    protected void intiView(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.total_tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.total_view_pager);
        showSearchButton = (ImageView) view.findViewById(R.id.show_search_button);
        totalLogin = (ImageView) view.findViewById(R.id.total_login);

    }

    @Override
    protected void initData() {

        BmobUser bmobUser = BmobUser.getCurrentUser(context);
        if (bmobUser == null) {
            totalLogin.setImageResource(R.mipmap.img_titlebar_login);
        } else {
            totalLogin.setImageResource(R.mipmap.bg_mymusic_face);
        }

        changeLoginBroadcast = new ChangeLoginBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(context.getPackageName() + ".changeLogin");
        context.registerReceiver(changeLoginBroadcast, filter);


        totalAdapter = new TotalAdapter(getActivity().getSupportFragmentManager());
        fragments = new ArrayList<>();
        fragments.add(new MyMusicFragment());
        fragments.add(new MusicLibraryFragment());
        totalAdapter.setFragments(fragments);
        viewPager.setAdapter(totalAdapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setTabTextColors(Color.rgb(176, 226, 225), Color.WHITE);
        showSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.toSearchFragment();
            }
        });
        totalLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginImmediatelyActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(changeLoginBroadcast);
    }

    //接收广播改变登录图标
    class ChangeLoginBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            BmobUser bmobUser = BmobUser.getCurrentUser(context);
            if (bmobUser == null) {
                totalLogin.setImageResource(R.mipmap.img_titlebar_login);
            } else {
                totalLogin.setImageResource(R.mipmap.bg_mymusic_face);
            }
        }
    }
}
