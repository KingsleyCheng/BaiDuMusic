package com.example.dllo.baidumusic.musicplayservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dllo.baidumusic.MyApp;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.db.LiteOrmSingleton;
import com.example.dllo.baidumusic.eventbean.EventPlayBean;
import com.example.dllo.baidumusic.eventbean.EventTimeBean;
import com.example.dllo.baidumusic.eventbean.EventTimingClosure;
import com.example.dllo.baidumusic.main.MainActivity;
import com.example.dllo.baidumusic.mymusic.localmusic.LocalMusicBean;
import com.example.dllo.baidumusic.mymusic.recentplay.RecentPlayBean;
import com.example.dllo.baidumusic.urlvalues.URLValues;
import com.example.dllo.baidumusic.volley.MySingleton;
import com.google.gson.Gson;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dllo on 16/6/25.
 */
public class MusicPlayService extends Service {
    private MyBinder binder;
    private MediaPlayer mediaPlayer;
    private SongPlayBean songPlayBean;
    private RequestQueue requestQueue;
    private String songUrl;
    private RemoteViews remoteViews;
    private String songAuthor, songName, songImageUrl, songId;
    private int currentTime, maxTime;
    private static final int PLAY_LIST = 0;// 列表循环
    private static final int PLAY_SINGLE = 1;//单曲
    private static final int PLAY_RANDOM = 2;//随机
    private static final int PLAY_ORDER = 3;//顺序播放
    private int pattern;
    private boolean firstTime;
    private int index;
    private int loopState;
    private NotificationManager manager;
    private PlayBroadcast playBroadcast;
    private NextBroadcast nextBroadcast;
    private CloseBroadcast closeBroadcast;
    private PrevBroadcast prevBroadcast;


    @Override
    public void onCreate() {
        super.onCreate();
        binder = new MyBinder();
        mediaPlayer = new MediaPlayer();
        EventBus.getDefault().register(this);
        //注册广播
        playBroadcast = new PlayBroadcast();
        IntentFilter playFilter = new IntentFilter();
        playFilter.addAction(getPackageName() + ".PLAY");
        registerReceiver(playBroadcast, playFilter);
        nextBroadcast = new NextBroadcast();
        IntentFilter nextFilter = new IntentFilter();
        nextFilter.addAction(getPackageName() + ".NEXT");
        registerReceiver(nextBroadcast, nextFilter);
        prevBroadcast = new PrevBroadcast();
        IntentFilter prevFilter = new IntentFilter();
        prevFilter.addAction(getPackageName() + ".PREV");
        registerReceiver(prevBroadcast, prevFilter);
        closeBroadcast = new CloseBroadcast();
        IntentFilter closeFilter = new IntentFilter();
        closeFilter.addAction(getPackageName() + ".CLOSE");
        registerReceiver(closeBroadcast, closeFilter);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                binder.next(loopState);
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    //接收服务播放歌曲
    @Subscribe
    public void palySelectSong(SongIdEvent songIdEvent) {
        String songId = songIdEvent.getSongId();
        songUrl = URLValues.SONG_PLAY1 + songId + URLValues.SONG_PLAY2;
        requestQueue = MySingleton.getInstance(this).getRequestQueue();
        StringRequest request = new StringRequest(songUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                response = response.replace("(", "");
                response = response.replace(")", "");
                response = response.replace(";", "");
                Gson gson = new Gson();
                songPlayBean = gson.fromJson(response, SongPlayBean.class);
                songUrl = songPlayBean.getBitrate().getFile_link();
                songAuthor = songPlayBean.getSonginfo().getAuthor();
                songName = songPlayBean.getSonginfo().getTitle();
                songImageUrl = songPlayBean.getSonginfo().getPic_small();
                binder.startPlay(songUrl);
                showNotification(songImageUrl, songName, songAuthor, R.mipmap.bt_notificationbar_pause);
                if (LiteOrmSingleton.getInstance().getLiteOrm().query(RecentPlayBean.class).size() == 0) {
                    LiteOrmSingleton.getInstance().getLiteOrm().insert(
                            new RecentPlayBean(songPlayBean.getSonginfo().getTitle(),
                                    songPlayBean.getSonginfo().getAuthor(),
                                    songPlayBean.getSonginfo().getSong_id()));
                } else {
                    QueryBuilder<RecentPlayBean> queryBuider = new QueryBuilder<>(RecentPlayBean.class);
                    queryBuider.whereEquals("songId", songPlayBean.getSonginfo().getSong_id());
                    if (LiteOrmSingleton.getInstance().getLiteOrm().query(queryBuider).size() == 0) {
                        LiteOrmSingleton.getInstance().getLiteOrm().insert(
                                new RecentPlayBean(songPlayBean.getSonginfo().getTitle(),
                                        songPlayBean.getSonginfo().getAuthor(), songPlayBean.getSonginfo().getSong_id())
                        );

                    }
                }
                //发送数据库增加的广播
                Intent intent = new Intent(getPackageName() + ".recentPlayAdd");
                sendBroadcast(intent);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);

    }

    //接收本地音乐数据
    @Subscribe
    private void localMusicPlay(LocalMusicBean localMusicBean) {
        binder.startPlaylocal(localMusicBean);
    }


    //显示通知栏
    private void showNotification(String songImageUrl, String songName, String songAuthor, int id) {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.icon);
        remoteViews = new RemoteViews(getPackageName(), R.layout.notification_view);
        remoteViews.setTextViewText(R.id.song_name, songName);
        remoteViews.setTextViewText(R.id.author, songAuthor);
        remoteViews.setImageViewResource(R.id.notificationbar_selector_play_pause, id);

        Intent intentPlay = new Intent(getPackageName() + ".PLAY");
        Intent intentNext = new Intent(getPackageName() + ".NEXT");
        Intent intentClose = new Intent(getPackageName() + ".CLOSE");
        Intent intentPrev = new Intent(getPackageName() + ".PREV");
        Intent intentShow = new Intent(this, MainActivity.class);

        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(this, 100001, intentPlay, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntentNext = PendingIntent.getBroadcast(this, 100001, intentNext, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntentClose = PendingIntent.getBroadcast(this, 100001, intentClose, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntentPrev = PendingIntent.getBroadcast(this, 100001, intentPrev, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntentShow = PendingIntent.getActivity(this, 100001, intentShow, PendingIntent.FLAG_CANCEL_CURRENT);

        remoteViews.setOnClickPendingIntent(R.id.notificationbar_selector_play_pause, pendingIntentPlay);
        remoteViews.setOnClickPendingIntent(R.id.notificationbar_next, pendingIntentNext);
        remoteViews.setOnClickPendingIntent(R.id.notificationbar_close, pendingIntentClose);
        remoteViews.setOnClickPendingIntent(R.id.notificationbar_prev, pendingIntentPrev);
        remoteViews.setOnClickPendingIntent(R.id.notification_layout, pendingIntentShow);
        builder.setContent(remoteViews);
        builder.setOngoing(true);
        Notification notification = builder.build();
        manager.notify(200016, notification);

        if (songImageUrl != null && songImageUrl.length() > 0) {
            Picasso.with(this).load(songImageUrl).into(remoteViews, R.id.notification_icon_iv, 200016, notification);
        } else {
            remoteViews.setImageViewResource(R.id.notification_icon_iv, R.mipmap.icon);
        }
    }

    private void playTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {

                        Thread.sleep(1000);
                        if (mediaPlayer.isPlaying()) {
                            currentTime = mediaPlayer.getCurrentPosition();
                            maxTime = mediaPlayer.getDuration();
                            EventBus.getDefault().post(new EventTimeBean(currentTime, maxTime));

                        }
                        EventBus.getDefault().post(new EventPlayBean(mediaPlayer.isPlaying()));
                        EventBus.getDefault().post(songPlayBean);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Subscribe
    public void index(IndexEvent indexEvent) {
        index = indexEvent.getIndex();
    }
    @Subscribe
    public void colseTime(EventTimingClosure timingClosure) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (manager != null) {
                    manager.cancel(200016);
                }
                stopSelf();
                System.exit(0);
            }
        },timingClosure.getTime()*60000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(playBroadcast);
        unregisterReceiver(nextBroadcast);
        unregisterReceiver(prevBroadcast);
        unregisterReceiver(closeBroadcast);
    }


    public class MyBinder extends Binder {
        //开始播放网络歌曲
        public void startPlay(String url) {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(MyApp.context, Uri.parse(url));
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            playTime();
            showNotification(songImageUrl, songName, songAuthor, R.mipmap.bt_notificationbar_pause);
        }

        //播放本地音乐
        public void startPlaylocal(LocalMusicBean localMusicBean) {

            try {
                mediaPlayer.reset();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(localMusicBean.getPath());
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String title = localMusicBean.getTitle();
            String author = localMusicBean.getAuthor();
        }

        //播放状态
        public boolean playState() {
            return mediaPlayer.isPlaying();
        }

        //设置循环模式
        public void setLoopState(int state) {
            loopState = state;
        }

        //获得循环模式
        public int getLoopState() {
            return loopState;
        }

        //暂停
        public void pause() {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                showNotification(songImageUrl, songName, songAuthor, R.mipmap.bt_notificationbar_play);
            } else {
                mediaPlayer.start();
                showNotification(songImageUrl, songName, songAuthor, R.mipmap.bt_notificationbar_pause);
            }
        }

        //停止播放
        public void stopPlay() {
            mediaPlayer.reset();
        }

        // 进度条
        public void seekProgress(int progress) {
            mediaPlayer.seekTo(progress);
        }

        //开始
        public void star() {
            mediaPlayer.start();
        }

        //下一首
        public void next(int pattern) {
            firstTime = true;
            switch (pattern) {
                // 列表循环
                case PLAY_LIST:
                    index++;
                    if (index >= PlayingListManager.getInstance().getPlayingListBeen().size()) {
                        index = 0;
                    }
                    palySelectSong(new SongIdEvent(PlayingListManager.getInstance().getPlayingListBeen().get(index).getSongId()));
                    break;
                // 单曲循环
                case PLAY_SINGLE:
                    palySelectSong(new SongIdEvent(PlayingListManager.getInstance().getPlayingListBeen().get(index).getSongId()));
                    break;
                // 随机播放
                case PLAY_RANDOM:
                    Random random = new Random();
                    index = random.nextInt(PlayingListManager.getInstance().getPlayingListBeen().size());
                    palySelectSong(new SongIdEvent(PlayingListManager.getInstance().getPlayingListBeen().get(index).getSongId()));
                    break;
                // 顺序播放
                case PLAY_ORDER:
                    index++;
                    if (index < PlayingListManager.getInstance().getPlayingListBeen().size()) {
                        palySelectSong(new SongIdEvent(PlayingListManager.getInstance().getPlayingListBeen().get(index).getSongId()));
                    } else {
                        mediaPlayer.stop();
                    }
                    break;
            }
            showNotification(songImageUrl, songName, songAuthor, R.mipmap.bt_notificationbar_pause);
        }

        // 播放上一首
        public void playPre() {
            if (index <= 0) {
                Toast.makeText(MyApp.context, "这是第一首", Toast.LENGTH_SHORT).show();
            } else {
                index--;
                palySelectSong(new SongIdEvent(PlayingListManager.getInstance().getPlayingListBeen().get(index).getSongId()));
            }
            showNotification(songImageUrl, songName, songAuthor, R.mipmap.bt_notificationbar_pause);
        }

        public boolean isDelPlaying(int position) {
            return index == position;
        }
    }

    class PlayBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                showNotification(songImageUrl, songName, songAuthor, R.mipmap.bt_notificationbar_play);
            } else {
                mediaPlayer.start();
                showNotification(songImageUrl, songName, songAuthor, R.mipmap.bt_notificationbar_pause);
            }
        }
    }

    //下一曲
    class NextBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            binder.next(pattern);
        }

    }

    //关闭程序并退出服务
    class CloseBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (manager != null) {
                manager.cancel(200016);
            }
            System.exit(0);
        }
    }

    //上一曲
    class PrevBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            binder.playPre();
        }
    }

}
