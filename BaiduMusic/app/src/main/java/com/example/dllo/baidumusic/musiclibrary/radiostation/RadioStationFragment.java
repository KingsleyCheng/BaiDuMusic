package com.example.dllo.baidumusic.musiclibrary.radiostation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dllo.baidumusic.MyApp;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseFragment;
import com.example.dllo.baidumusic.musiclibrary.radiostation.radiototal.RadioTotalActivity;

/**
 * Created by dllo on 16/6/17.
 */
public class RadioStationFragment extends BaseFragment {
    private TextView moreRadio;

    @Override
    public int setLayout() {
        return R.layout.fragment_radio_station;
    }


    @Override
    protected void intiView(View view) {
        moreRadio = (TextView) view.findViewById(R.id.more_radio);

    }

    @Override
    protected void initData() {
        moreRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApp.context, RadioTotalActivity.class);
                startActivity(intent);
            }
        });

    }
}
