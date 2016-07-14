package com.example.dllo.baidumusic.musiclibrary.radiostation.radiototal.radioplay;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.eventbean.EventPlayBean;
import com.example.dllo.baidumusic.eventbean.EventTimeBean;
import com.example.dllo.baidumusic.musicplayservice.MusicPlayService;
import com.example.dllo.baidumusic.volley.MySingleton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/7/3.
 */
public class RadioPlayAdapter extends PagerAdapter {
    private TextView radioPlayTitle, radioPlayAuthor, radioPlayTime;
    private ImageView radioPlayIv, radioPlayButton;
    private ServiceConnection serviceConnection;
    private MusicPlayService.MyBinder myBinder;
    private List<View> viewList;
    private List<RadioPlayBean.ResultBean.SonglistBean> songlistBean;
    private Context context;
    private int pos;

    public void setPos(int pos) {
        this.pos = pos;
    }

    public RadioPlayAdapter(Context context) {
        this.context = context;
        EventBus.getDefault().register(this);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myBinder = (MusicPlayService.MyBinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent service = new Intent(context, MusicPlayService.class);
        context.bindService(service, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void setSonglistBean(List<RadioPlayBean.ResultBean.SonglistBean> songlistBean) {
        this.songlistBean = songlistBean;
        viewList = new ArrayList<>();
        for (RadioPlayBean.ResultBean.SonglistBean bean : songlistBean) {
            viewList.add(null);
        }
//        for (RadioPlayBean.ResultBean.SonglistBean bean : songlistBean) {
//           // view = new View(context);
//            view = LayoutInflater.from(context).inflate(R.layout.fragment_radio_play, null);
//            radioPlayIv = (ImageView) view.findViewById(R.id.radio_play_iv);
//            radioPlayTitle = (TextView) view.findViewById(R.id.radio_play_title);
//            radioPlayAuthor = (TextView) view.findViewById(R.id.radio_palay_author);
//            radioPlayTitle.setText(bean.getTitle());
//            radioPlayAuthor.setText(bean.getAuthor());
//            MySingleton.getInstance(context).getImageLoader().get(bean.getPic_huge(), ImageLoader.getImageListener(radioPlayIv,
//                    R.mipmap.default_radio_list_item_image, R.mipmap.default_radio_list_item_image));
//            viewList.add(view);
//        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return songlistBean == null ? 0 : songlistBean.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        RadioPlayBean.ResultBean.SonglistBean bean = songlistBean.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_radio_play, null);
        radioPlayIv = (ImageView) view.findViewById(R.id.radio_play_iv);
        radioPlayTitle = (TextView) view.findViewById(R.id.radio_play_title);
        radioPlayAuthor = (TextView) view.findViewById(R.id.radio_palay_author);
        radioPlayTitle.setText(bean.getTitle());
        radioPlayAuthor.setText(bean.getAuthor());
        MySingleton.getInstance(context).getImageLoader().get(bean.getPic_huge(), ImageLoader.getImageListener(radioPlayIv,
                R.mipmap.default_radio_list_item_image, R.mipmap.default_radio_list_item_image));
        radioPlayButton = (ImageView) view.findViewById(R.id.radio_play_button);
        radioPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBinder.pause();
            }
        });
        // view = viewList.get(position);
        container.addView(view);
        viewList.set(position, view);
        return view;
    }

    //EventBus接收消息设置时间
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void radioPlayTime(EventTimeBean eventTimeBean) {
        radioPlayTime = (TextView) viewList.get(pos).findViewById(R.id.radio_play_time);
        int maxTime = eventTimeBean.getMaxTime() - eventTimeBean.getCurrentTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        radioPlayTime.setText("-" + simpleDateFormat.format(maxTime));


    }

    //EventBus接收消息设置播放图标
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void radioPlayBtn(EventPlayBean eventPlayBean) {
        radioPlayButton = (ImageView) viewList.get(pos).findViewById(R.id.radio_play_button);
        if (eventPlayBean.isPlay()) {
            radioPlayButton.setImageResource(R.mipmap.scenario_drive_pause_button_normal);
        } else {
            radioPlayButton.setImageResource(R.mipmap.scenario_drive_play_button_normal);
        }

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (container.getChildAt(position) == object) {
            container.removeViewAt(position);
            viewList.set(position, null);
        }
        // EventBus.getDefault().unregister(this);
    }

}
