package com.example.dllo.baidumusic.songplaypage.songimage;

import android.view.View;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseFragment;
import com.example.dllo.baidumusic.musicplayservice.SongPlayBean;
import com.example.dllo.baidumusic.songplaypage.PlayPageBean;
import com.example.dllo.baidumusic.volley.MySingleton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by dllo on 16/6/28.
 */
public class SongImageFragment extends BaseFragment {
    private ImageView authorImage;

    @Override
    public int setLayout() {
        return R.layout.fragment_song_play_image;
    }

    @Override
    protected void intiView(View view) {
        authorImage = (ImageView) view.findViewById(R.id.author_image);

    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        if (getActivity().getIntent().getParcelableExtra("playPageBean") != null) {
            PlayPageBean playPageBean = getActivity().getIntent().getParcelableExtra("playPageBean");
            String imageUrl = playPageBean.getImageUrl();
            MySingleton.getInstance(context).getImageLoader().get(imageUrl,
                    ImageLoader.getImageListener(authorImage, R.mipmap.img_minibar_default, R.mipmap.img_minibar_default));
        }
    }

    //EventBus接收消息设置播放页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setPlayPage(SongPlayBean songPlayBean) {
        MySingleton.getInstance(context).getImageLoader().get(songPlayBean.getSonginfo().getPic_premium(),
                ImageLoader.getImageListener(authorImage, R.mipmap.img_minibar_default, R.mipmap.img_minibar_default));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
