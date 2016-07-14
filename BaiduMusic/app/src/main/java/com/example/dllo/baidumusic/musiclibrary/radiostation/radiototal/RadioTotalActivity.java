package com.example.dllo.baidumusic.musiclibrary.radiostation.radiototal;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseActivity;

/**
 * Created by dllo on 16/6/23.
 */
public class RadioTotalActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private RadioTotalPagerAdapter radioTotalPagerAdapter;
    private TabLayout tabLayout;
    private ImageView radioTotalClose;


    @Override
    public int setLayout() {
        return R.layout.activity_radio_total;
    }

    @Override
    public void activityInitData() {
        viewPager = (ViewPager) findViewById(R.id.radio_total_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.radio_total_tab_layout);
        radioTotalClose = (ImageView) findViewById(R.id.radio_total_close);
        radioTotalPagerAdapter = new RadioTotalPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(radioTotalPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.rgb(176, 226, 225), Color.WHITE);
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        radioTotalClose.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //关闭RadioTotal
        finish();
    }
}
