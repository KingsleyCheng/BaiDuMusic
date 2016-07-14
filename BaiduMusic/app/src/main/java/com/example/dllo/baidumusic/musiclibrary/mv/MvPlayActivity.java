package com.example.dllo.baidumusic.musiclibrary.mv;

import android.app.Activity;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseActivity;

/**
 * Created by dllo on 16/7/5.
 */
public class MvPlayActivity extends BaseActivity {
    private VideoView videoView;

    @Override
    public int setLayout() {
        return R.layout.activity_mv_play;
    }

    @Override
    public void activityInitData() {
        videoView = (VideoView) findViewById(R.id.mv_play);
        String url = getIntent().getStringExtra("mvPlayUrl");
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(Uri.parse(url));
        //
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        videoView.setLayoutParams(layoutParams);
        videoView.start();

    }
}
