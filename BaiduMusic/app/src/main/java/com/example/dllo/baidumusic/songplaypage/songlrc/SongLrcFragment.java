package com.example.dllo.baidumusic.songplaypage.songlrc;

import android.view.View;

import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseFragment;
import com.example.dllo.baidumusic.eventbean.EventTimeBean;
import com.example.dllo.baidumusic.musicplayservice.SongPlayBean;
import com.example.dllo.baidumusic.songplaypage.PlayPageBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by dllo on 16/6/28.
 */
public class SongLrcFragment extends BaseFragment {
    private LyricView lyricView;

    @Override
    public int setLayout() {
        return R.layout.fragment_song_play_lrc;
    }

    @Override
    protected void intiView(View view) {
        lyricView = (LyricView) view.findViewById(R.id.playing_activity_lyricview);

    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        //刚刚跳转到播放界面传来的歌词
        if (getActivity().getIntent().getParcelableExtra("playPageBean") != null) {
            PlayPageBean playPageBean = getActivity().getIntent().getParcelableExtra("playPageBean");
            String lrcLink = playPageBean.getLrclink();
            loadLrc(lrcLink);
        }
    }

    public void loadLrc(String lrc) {
        lyricView.loadLrc(lrc);
    }

    public void onPublish(int progress) {
        if (lyricView.hasLrc()) {
            lyricView.updateTime(progress);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void SeeBarControl(SongPlayBean bean) {

        loadLrc(bean.getSonginfo().getLrclink());
    }

    //EventBus接收消息设置时间
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void radioPlayTime(EventTimeBean eventTimeBean) {
        onPublish(eventTimeBean.getCurrentTime());
    }

}
