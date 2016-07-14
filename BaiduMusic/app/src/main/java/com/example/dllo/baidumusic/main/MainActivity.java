package com.example.dllo.baidumusic.main;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseActivity;
import com.example.dllo.baidumusic.db.LiteOrmSingleton;
import com.example.dllo.baidumusic.eventbean.EventPlayBean;
import com.example.dllo.baidumusic.musicplayservice.IndexEvent;
import com.example.dllo.baidumusic.musicplayservice.PlayingListManager;
import com.example.dllo.baidumusic.musicplayservice.SongIdEvent;
import com.example.dllo.baidumusic.mymusic.likesong.LikeSongFragment;
import com.example.dllo.baidumusic.mymusic.localmusic.LocalMusicFragment;
import com.example.dllo.baidumusic.mymusic.recentplay.RecentPlayFragment;
import com.example.dllo.baidumusic.songplaypage.PlayPageBean;
import com.example.dllo.baidumusic.musiclibrary.charts.chartslist.ChartsListFragment;
import com.example.dllo.baidumusic.musiclibrary.recommend.allauthor.AllAuthorFragment;
import com.example.dllo.baidumusic.musiclibrary.recommend.allauthor.allauthorlist.AllAuthorListFragment;
import com.example.dllo.baidumusic.musiclibrary.recommend.allauthor.allauthorlist.authorlistdetail.AuthorDetailListFragment;
import com.example.dllo.baidumusic.musiclibrary.songmenu.songmenulist.SongMenuListFragment;
import com.example.dllo.baidumusic.musicplayservice.MusicPlayService;
import com.example.dllo.baidumusic.musicplayservice.SongPlayBean;
import com.example.dllo.baidumusic.songplaypage.PlayPageActivity;
import com.example.dllo.baidumusic.songplaypage.playinglist.PlayingListBean;
import com.example.dllo.baidumusic.totalfragment.TotalFragment;
import com.example.dllo.baidumusic.totalfragment.search.SearchFragment;
import com.example.dllo.baidumusic.volley.MySingleton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Random;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private TotalFragment totalFragment;
    private SongMenuListFragment songMenuListFragment;
    private ChartsListFragment chartsListFragment;
    private AllAuthorFragment allAuthorFragment;
    private AllAuthorListFragment allAuthorListFragment;
    private AuthorDetailListFragment authorDetailListFragment;
    private TextView minibarTitleTv, minibarAuthorTv;
    private ImageView minibarIv, minibarPlay, minibarNext, minibarPlayingList;
    private MusicPlayService.MyBinder myBiner;
    private ServiceConnection serviceConnection;
    private boolean playState;
    private SongPlayBean songPlayBean;
    private PlayPageBean playPageBean;
    private PopupWindow popupWindow;
    private PopupWindowAdapter popupWindowAdapter;
    private FrameLayout frameLayout;
    private int pattern = 0;
    private SearchFragment searchFragment;
    private RecentPlayFragment recentPlayFragment;
    private LikeSongFragment likeSongFragment;
    private LocalMusicFragment localMusicFragment;



    @Override
    public int setLayout() {
        return R.layout.activity_main;

    }

    @Override
    public void activityInitData() {

        //注册EventBus
        EventBus.getDefault().register(this);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myBiner = (MusicPlayService.MyBinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        //绑定服务
        Intent service = new Intent(this, MusicPlayService.class);
        bindService(service, serviceConnection, BIND_AUTO_CREATE);

        //加载TatblFragment
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        totalFragment = new TotalFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, totalFragment).commit();


        minibarTitleTv = (TextView) findViewById(R.id.minibar_title);
        minibarAuthorTv = (TextView) findViewById(R.id.minibar_author);
        minibarIv = (ImageView) findViewById(R.id.minibar_iv);
        minibarPlay = (ImageView) findViewById(R.id.minibar_play);
        minibarNext = (ImageView) findViewById(R.id.minibar_next);
        minibarPlayingList = (ImageView) findViewById(R.id.minibar_playinglist);
        getSongPlayBean();

        minibarIv.setOnClickListener(this);
        minibarPlay.setOnClickListener(this);
        minibarNext.setOnClickListener(this);
        minibarPlayingList.setOnClickListener(this);


    }

    //接收EventBus接收数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void miniBar(SongPlayBean songPlayBean) {
        this.songPlayBean = songPlayBean;
        minibarTitleTv.setText(songPlayBean.getSonginfo().getTitle());
        minibarAuthorTv.setText(songPlayBean.getSonginfo().getAuthor());
        MySingleton.getInstance(this).getImageLoader().get(songPlayBean.getSonginfo().getPic_small()
                , ImageLoader.getImageListener(minibarIv, R.mipmap.bt_minibar_view
                        , R.mipmap.bt_minibar_view));
        playPageBean = new PlayPageBean(songPlayBean.getSonginfo().getTitle(),
                songPlayBean.getSonginfo().getAuthor(), songPlayBean.getSonginfo().getPic_premium(),
                songPlayBean.getSonginfo().getLrclink());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void minibarPlayBtn(EventPlayBean eventPlayBean) {
        if (eventPlayBean.isPlay()) {
            minibarPlay.setImageResource(R.mipmap.bt_minibar_pause_normal);
        } else {
            minibarPlay.setImageResource(R.mipmap.bt_minibar_play_normal);
        }
    }


    //跳转到歌单列表
    public void toSongMenuList(String listid) {
        songMenuListFragment = new SongMenuListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout,
                songMenuListFragment).addToBackStack(null).hide(totalFragment).commit();
        Bundle bundle = new Bundle();
        bundle.putString("listd", listid);
        songMenuListFragment.setArguments(bundle);
    }

    //跳转到排行榜列表
    public void toChartsList(int type) {
        chartsListFragment = new ChartsListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout,
                chartsListFragment).addToBackStack(null).hide(totalFragment).commit();
        Bundle bundle = new Bundle();
        bundle.putInt("chartstype", type);
        chartsListFragment.setArguments(bundle);
    }

    //跳转到全部歌手页面
    public void toAllAuthor() {
        allAuthorFragment = new AllAuthorFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout,
                allAuthorFragment).addToBackStack(null).hide(totalFragment).commit();
    }

    //跳转到歌手列表页面
    public void toAllAuthorList(String url, String type) {
        allAuthorListFragment = new AllAuthorListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout,
                allAuthorListFragment).addToBackStack(null).hide(allAuthorFragment).commit();
        Bundle bundle = new Bundle();
        bundle.putString("allauthorlisturl", url);
        bundle.putString("allauthorlisttype", type);
        allAuthorListFragment.setArguments(bundle);
    }

    //从歌手列表界面跳转到歌手详细列表
    public void toAuthorDetailList(String tingUid, String picUrl) {
        authorDetailListFragment = new AuthorDetailListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout,
                authorDetailListFragment).addToBackStack(null).hide(allAuthorListFragment).commit();


        Bundle bundle = new Bundle();
        bundle.putString("authordetaitinguid", tingUid);
        bundle.putString("picurl", picUrl);
        authorDetailListFragment.setArguments(bundle);
    }

    //从全部歌手界面跳转到歌手详细列表
    public void nextToAuthorDetailList(String tingUid, String picUrl) {
        authorDetailListFragment = new AuthorDetailListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout,
                authorDetailListFragment).addToBackStack(null).hide(allAuthorFragment).commit();

        Bundle bundle = new Bundle();
        bundle.putString("authordetaitinguid", tingUid);
        bundle.putString("picurl", picUrl);
        authorDetailListFragment.setArguments(bundle);
    }

    //跳转到搜索界面
    public void toSearchFragment() {
        searchFragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout,
                searchFragment).addToBackStack(null).hide(totalFragment).commit();
    }

    //跳转到最近播放列表界面
    public void toRecentPlayFragment() {
        recentPlayFragment = new RecentPlayFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout,
                recentPlayFragment).addToBackStack(null).hide(totalFragment).commit();
    }

    //跳转到我喜欢列表界面
    public void toLikeSongFragment() {
        likeSongFragment = new LikeSongFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout,
                likeSongFragment).addToBackStack(null).hide(totalFragment).commit();
    }

    //跳转到本地音乐列表界面
    public void toLocalMusicFragment() {
        localMusicFragment = new LocalMusicFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout,
                localMusicFragment).addToBackStack(null).hide(totalFragment).commit();
    }

    public void showPlayList() {

        popupWindowAdapter = new PopupWindowAdapter(this);
        //获取屏幕高度
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);


        View contentView = LayoutInflater.from(this).inflate(R.layout.popup_window_list, null, false);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT
                , displayMetrics.heightPixels / 4 * 3);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(frameLayout, 0, -displayMetrics.heightPixels / 4 * 3);

        ListView popListView = (ListView) contentView.findViewById(R.id.pop_list_view);
        TextView clearAll = (TextView) contentView.findViewById(R.id.pop_clear_all);
        final ImageView playPattern = (ImageView) contentView.findViewById(R.id.pop_play_pattern);
        RelativeLayout playListTv = (RelativeLayout) contentView.findViewById(R.id.pop_play_list_tv);


        popupWindowAdapter.setPlayingListBeen(PlayingListManager.getInstance().getPlayingListBeen());

        popListView.setAdapter(popupWindowAdapter);

        // 初始化播放模式
        switch (myBiner.getLoopState()) {
            case 0:
                playPattern.setImageResource(R.mipmap.bt_list_button_roundplay_normal);
                break;
            case 1:
                playPattern.setImageResource(R.mipmap.bt_list_roundsingle_normal);
                break;
            case 2:
                playPattern.setImageResource(R.mipmap.bt_list_random_normal);
                break;
            case 3:
                playPattern.setImageResource(R.mipmap.bt_list_order_normal);
                break;
        }

        // 点击播放
        popListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(new SongIdEvent(PlayingListManager.getInstance().getPlayingListBeen()
                        .get(position).getSongId()));
                EventBus.getDefault().post(new IndexEvent(position));
            }
        });

        // 点击删除
        popupWindowAdapter.setOnRemoveOnClickListener(new OnRemoveOnClickListener() {
            @Override
            public void onRemoveOnClick(int position) {
                PlayingListManager.getInstance().getPlayingListBeen().remove(position);
                popupWindowAdapter.setPlayingListBeen(PlayingListManager.getInstance().getPlayingListBeen());
                if (myBiner.isDelPlaying(position)) {

                    if (myBiner.getLoopState() == 2) {
                        Random random = new Random();
                        int pos = random.nextInt(PlayingListManager.getInstance().getPlayingListBeen().size());
                        EventBus.getDefault().post(new IndexEvent(pos));
                    } else if (myBiner.getLoopState() == 1) {
                        EventBus.getDefault().post(new IndexEvent(position));
                    } else {
                        EventBus.getDefault().post(new IndexEvent(position - 1));
                    }
                    myBiner.next(myBiner.getLoopState());
                }
            }
        });


        // 设置播放模式

        playPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pattern++;
                if (pattern > 3) {
                    pattern = 0;
                }
                myBiner.setLoopState(pattern);
                switch (pattern) {
                    case 0:
                        Toast.makeText(MainActivity.this, "列表循环", Toast.LENGTH_SHORT).show();
                        playPattern.setImageResource(R.mipmap.bt_list_button_roundplay_normal);
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "单曲循环", Toast.LENGTH_SHORT).show();
                        playPattern.setImageResource(R.mipmap.bt_list_roundsingle_normal);
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "随机播放", Toast.LENGTH_SHORT).show();
                        playPattern.setImageResource(R.mipmap.bt_list_random_normal);
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this, "顺序播放", Toast.LENGTH_SHORT).show();
                        playPattern.setImageResource(R.mipmap.bt_list_order_normal);
                        break;
                }
            }
        });

        // 清空全部
        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayingListManager.getInstance().getPlayingListBeen().clear();
                popupWindowAdapter.setPlayingListBeen(PlayingListManager.getInstance().getPlayingListBeen());
                myBiner.stopPlay();
            }
        });

        //返回
        playListTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }


    //点击两次退出程序
    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!totalFragment.isVisible()) {
            return super.onKeyDown(keyCode, event);
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }


    //重新显示TotalFragent
    public void showtotalFragment() {
        getSupportFragmentManager().beginTransaction().show(totalFragment).commit();
    }

    //mimibar播放监听事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.minibar_play:
                myBiner.pause();
                break;
            case R.id.minibar_next:
                if (PlayingListManager.getInstance().getPlayingListBeen().size() == 0) {

                } else {
                    myBiner.next(myBiner.getLoopState());
                }

                break;
            case R.id.minibar_iv:
                Intent intent = new Intent(this, PlayPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("playPageBean", playPageBean);
                intent.putExtras(bundle);
                intent.putExtra("pattern", myBiner.getLoopState());
                startActivity(intent);
                break;
            case R.id.minibar_playinglist:
                if (popupWindow == null || !popupWindow.isShowing()) {
                    showPlayList();
                } else {
                    popupWindow.dismiss();
                }
                break;
        }

    }

    // 获取上一次退出保存的最后一条播放信息
    public void getSongPlayBean() {
        SharedPreferences sharedPreferences = getSharedPreferences("playerInfo", MODE_PRIVATE);
        String title = sharedPreferences.getString("title", "百度音乐 听到极致");
        String author = sharedPreferences.getString("author", "");
        String songId = sharedPreferences.getString("songId", "");
        String photo = sharedPreferences.getString("photo", "");

        minibarTitleTv.setText(title);
        minibarAuthorTv.setText(author);
        MySingleton.getInstance(this).getImageLoader().get(photo,
                ImageLoader.getImageListener(minibarIv, R.mipmap.bt_minibar_view, R.mipmap.bt_minibar_view));
    }

    //在程序退出的时候保存最后一条播放的记录
    public void saveSongPlayBean() {
        if (songPlayBean != null) {
            SharedPreferences sp = getSharedPreferences("playerInfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("title", songPlayBean.getSonginfo().getTitle());
            editor.putString("author", songPlayBean.getSonginfo().getAuthor());
            editor.putString("photo", songPlayBean.getSonginfo().getPic_small());
            editor.putString("songId", songPlayBean.getSonginfo().getSong_id());
            editor.commit();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveSongPlayBean();
        EventBus.getDefault().unregister(this);
        //程序退出保存最好的播放列表记录,并保存
        LiteOrmSingleton.getInstance().getLiteOrm().deleteAll(PlayingListBean.class);
        LiteOrmSingleton.getInstance().getLiteOrm()
                .insert(PlayingListManager.getInstance().getPlayingListBeen());


    }

}
