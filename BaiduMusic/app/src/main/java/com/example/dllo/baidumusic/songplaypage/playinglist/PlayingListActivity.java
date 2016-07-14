package com.example.dllo.baidumusic.songplaypage.playinglist;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseActivity;

/**
 * Created by dllo on 16/6/29.
 */
public class PlayingListActivity extends BaseActivity implements View.OnClickListener {
    private ListView listView;
    private PlayingListAdapter playPageListAdapter;
    private ImageView playPagePlayListMode;
    private TextView playPageListClearAll, playPageListReturn;


    @Override
    public int setLayout() {
        return R.layout.activity_playing_list;
    }

    @Override
    public void activityInitData() {
        listView = (ListView) findViewById(R.id.play_page_list_view);
        playPagePlayListMode = (ImageView) findViewById(R.id.play_page_play_list_mode);
        playPageListClearAll = (TextView) findViewById(R.id.play_page_list_clear_all);
        playPageListReturn = (TextView) findViewById(R.id.play_page_list_return);

        playPageListReturn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_page_list_return:
                finish();
                break;
        }
    }
}
