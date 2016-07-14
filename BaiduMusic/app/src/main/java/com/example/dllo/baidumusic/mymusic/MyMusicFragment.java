package com.example.dllo.baidumusic.mymusic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseFragment;
import com.example.dllo.baidumusic.db.LiteOrmSingleton;
import com.example.dllo.baidumusic.main.MainActivity;
import com.example.dllo.baidumusic.mymusic.likesong.LikeSongBean;
import com.example.dllo.baidumusic.mymusic.localmusic.LocalMusicBean;
import com.example.dllo.baidumusic.mymusic.localmusic.ScanLocalMusic;
import com.example.dllo.baidumusic.mymusic.recentplay.RecentPlayBean;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by dllo on 16/6/17.
 */
public class MyMusicFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout myMusicRecentPlay;
    private LinearLayout myMusicLickSong;
    private LinearLayout myMusicLocal;
    private TextView recentPlaySum, likeSongSum, localMusicSum;
    private RecentPlaySumBrodcast recentPlaySumBrodcast;
    private LikeSongSumBrodcast likeSongSumBrodcast;
    private LocalMusicSumBrodcast localMusicSumBrodcast;
    private int sum;


    @Override
    public int setLayout() {
        return R.layout.fragment_my_music;
    }

    @Override
    protected void intiView(View view) {
        myMusicRecentPlay = (LinearLayout) view.findViewById(R.id.my_music_recent_play);
        myMusicLickSong = (LinearLayout) view.findViewById(R.id.my_music_like_song);
        myMusicLocal = (LinearLayout) view.findViewById(R.id.my_music_local);
        recentPlaySum = (TextView) view.findViewById(R.id.recent_play_sum);
        likeSongSum = (TextView) view.findViewById(R.id.like_song_sum);
        localMusicSum = (TextView) view.findViewById(R.id.local_music_sum);
        myMusicRecentPlay.setOnClickListener(this);
        myMusicLickSong.setOnClickListener(this);
        myMusicLocal.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        //注册广播
        recentPlaySumBrodcast = new RecentPlaySumBrodcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(context.getPackageName() + ".recentPlayRemove");
        filter.addAction(context.getPackageName() + ".recentPlayAdd");
        context.registerReceiver(recentPlaySumBrodcast, filter);
        likeSongSumBrodcast = new LikeSongSumBrodcast();
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction(context.getPackageName() + ".likeSongAdd");
        filter1.addAction(context.getPackageName() + ".likeSongCancle");
        context.registerReceiver(likeSongSumBrodcast, filter1);
        localMusicSumBrodcast = new LocalMusicSumBrodcast();
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction(context.getPackageName() + ".localMusicChange");
        filter2.addAction(context.getPackageName() + ".localMusicRemove");
        context.registerReceiver(localMusicSumBrodcast, filter2);


        recentPlaySum.setText(LiteOrmSingleton.getInstance().getLiteOrm()
                .query(RecentPlayBean.class).size() + "首");

        ScanLocalMusic.localMusic(context);
        localMusicSum.setText(LiteOrmSingleton.getInstance().getLiteOrm()
                .query(LocalMusicBean.class).size() + "首");

        BmobUser bmobUser = BmobUser.getCurrentUser(context);
        if (bmobUser != null) {
            BmobQuery<LikeSongBean> query = new BmobQuery<>();
            query.addWhereEqualTo("userName", BmobUser.getCurrentUser(context).getUsername());
            query.count(context, LikeSongBean.class, new CountListener() {
                @Override
                public void onSuccess(int i) {
                    likeSongSum.setText(LiteOrmSingleton.getInstance().getLiteOrm()
                            .query(LikeSongBean.class).size() + i + "首");
                }

                @Override
                public void onFailure(int i, String s) {

                }
            });

        } else {
            likeSongSum.setText(LiteOrmSingleton.getInstance().getLiteOrm()
                    .query(LikeSongBean.class).size() + "首");
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_music_recent_play:
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.toRecentPlayFragment();
                break;
            case R.id.my_music_like_song:
                MainActivity mainActivity1 = (MainActivity) getActivity();
                mainActivity1.toLikeSongFragment();
                break;
            case R.id.my_music_local:
                MainActivity mainActivity2 = (MainActivity) getActivity();
                mainActivity2.toLocalMusicFragment();
                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        context.unregisterReceiver(recentPlaySumBrodcast);
        context.unregisterReceiver(likeSongSumBrodcast);
        context.unregisterReceiver(localMusicSumBrodcast);
    }

    //接收广播跟新最近播放总数数据
    class RecentPlaySumBrodcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            recentPlaySum.setText(LiteOrmSingleton.getInstance().getLiteOrm()
                    .query(RecentPlayBean.class).size() + "首");
        }
    }

    class LikeSongSumBrodcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            BmobUser bmobUser = BmobUser.getCurrentUser(context);
            if (bmobUser != null) {
                BmobQuery<LikeSongBean> query = new BmobQuery<>();
                query.addWhereEqualTo("userName", BmobUser.getCurrentUser(context).getUsername());
                query.count(context, LikeSongBean.class, new CountListener() {
                    @Override
                    public void onSuccess(int i) {
                        likeSongSum.setText(LiteOrmSingleton.getInstance().getLiteOrm()
                                .query(LikeSongBean.class).size() + i + "首");
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });

            } else {
                likeSongSum.setText(LiteOrmSingleton.getInstance().getLiteOrm()
                        .query(LikeSongBean.class).size() + "首");
            }
        }
    }

    class LocalMusicSumBrodcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            localMusicSum.setText(LiteOrmSingleton.getInstance().getLiteOrm()
                    .query(LocalMusicBean.class).size() + "首");
        }
    }
}
