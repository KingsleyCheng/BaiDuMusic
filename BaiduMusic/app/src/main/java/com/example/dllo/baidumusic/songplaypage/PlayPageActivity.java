package com.example.dllo.baidumusic.songplaypage;

import android.content.ComponentName;
import android.content.Intent;

import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.view.ViewPager;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseActivity;
import com.example.dllo.baidumusic.base.BaseFragment;

import com.example.dllo.baidumusic.eventbean.EventPlayBean;
import com.example.dllo.baidumusic.eventbean.EventTimeBean;
import com.example.dllo.baidumusic.musicplayservice.MusicPlayService;
import com.example.dllo.baidumusic.musicplayservice.PlayingListManager;
import com.example.dllo.baidumusic.musicplayservice.SongPlayBean;
import com.example.dllo.baidumusic.songplaypage.songimage.SongImageFragment;
import com.example.dllo.baidumusic.songplaypage.songlrc.SongLrcFragment;
import com.example.dllo.baidumusic.songplaypage.playinglist.PlayingListActivity;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by dllo on 16/6/28.
 */
public class PlayPageActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private ArrayList<BaseFragment> fragments;
    private PlayPageAdapter playPageAdapter;
    private SeekBar seekBar;
    private ImageView playPageReturn, playPageSceneplayWord, playPageList, playPagePlayMode;
    private ImageView playPagePrev, playPagePause, playPageNext;
    private TextView playPageTitle, plagPageAuthor;
    private SongLrcFragment authorLrcFragment;
    private SongImageFragment authorImageFragment;
    private ServiceConnection connection;
    private MusicPlayService.MyBinder myBinder;
    private int pattern;
    private TextView playPageStartTime, playPageMaxTime;
    private LinearLayout playPageLinearLayout;



    @Override
    public int setLayout() {
        return R.layout.activity_play_page;
    }

    @Override
    public void activityInitData() {

        EventBus.getDefault().register(this);
        viewPager = (ViewPager) findViewById(R.id.play_page_view_pager);
        seekBar = (SeekBar) findViewById(R.id.play_page_seekbar);
        playPageReturn = (ImageView) findViewById(R.id.play_page_return);
        playPageSceneplayWord = (ImageView) findViewById(R.id.play_page_sceneplay_word);
        playPageList = (ImageView) findViewById(R.id.play_page_list);
        playPageTitle = (TextView) findViewById(R.id.play_page_title);
        plagPageAuthor = (TextView) findViewById(R.id.play_page_author);
        playPagePlayMode = (ImageView) findViewById(R.id.play_page_play_mode);
        playPagePrev = (ImageView) findViewById(R.id.play_page_prev);
        playPagePause = (ImageView) findViewById(R.id.play_page_play_pause);
        playPageNext = (ImageView) findViewById(R.id.play_page_next);
        playPageStartTime = (TextView) findViewById(R.id.play_page_start_time);
        playPageMaxTime = (TextView) findViewById(R.id.play_page_max_time);
        playPageLinearLayout= (LinearLayout) findViewById(R.id.play_page_linear_layout);

        PlayPageBean playPageBean = getIntent().getParcelableExtra("playPageBean");
        if (playPageBean != null) {
            playPageTitle.setText(playPageBean.getTitle());
            plagPageAuthor.setText(playPageBean.getAuthor());
        }

        playPageReturn.setOnClickListener(this);
        playPageSceneplayWord.setOnClickListener(this);
        playPageList.setOnClickListener(this);
        playPagePrev.setOnClickListener(this);
        playPagePause.setOnClickListener(this);
        playPageNext.setOnClickListener(this);
        playPagePlayMode.setOnClickListener(this);

        fragments = new ArrayList<>();
        authorImageFragment = new SongImageFragment();
        authorLrcFragment = new SongLrcFragment();

        fragments.add(authorImageFragment);
        fragments.add(authorLrcFragment);
        playPageAdapter = new PlayPageAdapter(getSupportFragmentManager());
        playPageAdapter.setFragments(fragments);
        viewPager.setAdapter(playPageAdapter);
        viewPager.setCurrentItem(0);

        //监听viewPager页面变化,切换"图""词"图标
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (viewPager.getCurrentItem() == 1) {
                    playPageSceneplayWord.setImageResource(R.mipmap.bt_sceneplay_word_press);
                } else {
                    playPageSceneplayWord.setImageResource(R.mipmap.bt_sceneplay_picture_press);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //绑定服务
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myBinder = (MusicPlayService.MyBinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent serviceIntent = new Intent(this, MusicPlayService.class);
        bindService(serviceIntent, connection, BIND_AUTO_CREATE);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                myBinder.seekProgress(seekBar.getProgress());
            }
        });

        initPlayPage();


    }


    // 初始化播放页面
    public void initPlayPage() {

        pattern = getIntent().getIntExtra("pattern", 0);
        switch (pattern) {
            case 0:
                playPagePlayMode.setImageResource(R.mipmap.bt_widget_mode_cycle_press);
                break;
            case 1:
                playPagePlayMode.setImageResource(R.mipmap.bt_widget_mode_singlecycle_press);
                break;
            case 2:
                playPagePlayMode.setImageResource(R.mipmap.bt_widget_mode_shuffle_press);
                break;
            case 3:
                playPagePlayMode.setImageResource(R.mipmap.bt_widget_mode_order_press);
                break;
        }
    }

    //EventBus接收消息设置播放页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setPlayPage(SongPlayBean songPlayBean) {
        playPageTitle.setText(songPlayBean.getSonginfo().getTitle());
        plagPageAuthor.setText(songPlayBean.getSonginfo().getAuthor());
    }


    //EventBus接收消息设置时间
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void radioPlayTime(EventTimeBean eventTimeBean) {
        int maxTime = eventTimeBean.getMaxTime();
        int startTime = eventTimeBean.getCurrentTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        playPageMaxTime.setText(simpleDateFormat.format(maxTime));
        playPageStartTime.setText(simpleDateFormat.format(startTime));
        seekBar.setMax(eventTimeBean.getMaxTime());
        seekBar.setProgress(eventTimeBean.getCurrentTime());


    }

    //EventBus接收消息设置播放图标
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void PlayPageBtn(EventPlayBean eventPlayBean) {
        if (eventPlayBean.isPlay()) {
            playPagePause.setImageResource(R.mipmap.bt_widget_pause_press);
        } else {
            playPagePause.setImageResource(R.mipmap.bt_widget_play_press);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_page_sceneplay_word://"图""词"图标点击切换页面
                if (viewPager.getCurrentItem() == 1) {
                    viewPager.setCurrentItem(0);
                    playPageSceneplayWord.setImageResource(R.mipmap.bt_sceneplay_picture_press);
                } else {
                    viewPager.setCurrentItem(1);
                    playPageSceneplayWord.setImageResource(R.mipmap.bt_sceneplay_word_press);
                }
                break;
            // 返回
            case R.id.play_page_return:
                finish();
                break;

            // 下一曲
            case R.id.play_page_next:
                if (PlayingListManager.getInstance().getPlayingListBeen().size() != 0) {
                    myBinder.next(myBinder.getLoopState());
                }
                break;
            // 播放/暂停
            case R.id.play_page_play_pause:
                myBinder.pause();
                break;
            // 上一曲
            case R.id.play_page_prev:
                myBinder.playPre();
                break;
            // 显示播放列表
            case R.id.play_page_list:
                Intent intent = new Intent(this, PlayingListActivity.class);
                startActivity(intent);
                break;
            // 播放模式
            case R.id.play_page_play_mode:
                pattern++;
                if (pattern > 3) {
                    pattern = 0;
                }
                myBinder.setLoopState(pattern);
                switch (pattern) {
                    case 0:
                        Toast.makeText(PlayPageActivity.this, "列表循环", Toast.LENGTH_SHORT).show();
                        playPagePlayMode.setImageResource(R.mipmap.bt_widget_mode_cycle_press);
                        break;
                    case 1:
                        Toast.makeText(PlayPageActivity.this, "单曲循环", Toast.LENGTH_SHORT).show();
                        playPagePlayMode.setImageResource(R.mipmap.bt_widget_mode_singlecycle_press);
                        break;
                    case 2:
                        Toast.makeText(PlayPageActivity.this, "随机播放", Toast.LENGTH_SHORT).show();
                        playPagePlayMode.setImageResource(R.mipmap.bt_widget_mode_shuffle_press);
                        break;
                    case 3:
                        Toast.makeText(PlayPageActivity.this, "顺序播放", Toast.LENGTH_SHORT).show();
                        playPagePlayMode.setImageResource(R.mipmap.bt_widget_mode_order_press);
                        break;
                }
                break;
        }
    }

    // 显示正在播放列表
//    public void showPlayList() {
//
//        popAdapter = new PlayPagePopAdapter(this);
//
//        //获取屏幕高度
//        WindowManager windowManager = getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        display.getMetrics(displayMetrics);
//        //获取通知栏高度
////        Rect outRect = new Rect();
////        getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
//
//        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_playpage_play_list, null);
//        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT
//                , ViewGroup.LayoutParams.MATCH_PARENT);
////        // 可获得焦点
////        popupWindow.setFocusable(true);
//
////        popupWindow.setBackgroundDrawable(new BitmapDrawable());
////        popupWindow.setAnimationStyle(R.style.contextMenuAnim);
////        popupWindow.showAtLocation(replaceLayout, Gravity.CENTER, 0,-300);
//
////        popupWindow.showAsDropDown(, 0, -displayMetrics.heightPixels / 4 * 3);
//
//        popupWindow.showAtLocation(linearLayout, Gravity.BOTTOM, 0, 0);
//        ListView popListView = (ListView) contentView.findViewById(R.id.pop_palypage_list_view);
//        TextView clearAll = (TextView) contentView.findViewById(R.id.pop_playpage_clear_all);
//        final ImageView playPattern = (ImageView) contentView.findViewById(R.id.pop_playpage_pattern);
//        TextView playListTv = (TextView) contentView.findViewById(R.id.pop_playpage_list_tv);
//        TextView closeTv = (TextView) contentView.findViewById(R.id.pop_playpage_close);
//
//        popAdapter.setPlayLists(PlayListManager.getInstance().getPlayList());
//
//        popListView.setAdapter(popAdapter);
//
//        // 初始化播放模式
//        switch (myBinder.getLoopState()) {
//            case 0:
//                playPattern.setImageResource(R.mipmap.bt_widget_mode_cycle_press);
//                break;
//            case 1:
//                playPattern.setImageResource(R.mipmap.bt_widget_mode_singlecycle_press);
//                break;
//            case 2:
//                playPattern.setImageResource(R.mipmap.bt_widget_mode_shuffle_press);
//                break;
//            case 3:
//                playPattern.setImageResource(R.mipmap.bt_widget_mode_order_press);
//                break;
//        }
//
//
//        // 点击播放
//        popListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                EventBus.getDefault().post(new SongIdEvent
//                        (PlayListManager.getInstance().getPlayList().get(position).getSongId()));
//                EventBus.getDefault().post(new IndexEvent(position));
//            }
//        });
//
//        // 点击删除
//        popAdapter.setOnDelClickListener(new PlayPagePopAdapter.OnDelClickListener() {
//            @Override
//            public void onDel(int position) {
//                PlayListManager.getInstance().getPlayList().remove(position);
//                popAdapter.setPlayLists(PlayListManager.getInstance().getPlayList());
//                if (playBinder.isDelPlaying(position)) {
//
//                    if (playBinder.getPattern() == 2) {
//                        Random random = new Random();
//                        int pos = random.nextInt(PlayListManager.getInstance().getPlayList().size());
//                        EventBus.getDefault().post(new IndexEvent(pos));
//                    } else if (playBinder.getPattern() == 1) {
//                        EventBus.getDefault().post(new IndexEvent(position));
//                    } else {
//                        EventBus.getDefault().post(new IndexEvent(position - 1));
//                    }
//                    playBinder.playNext(playBinder.getPattern());
//                }
//            }
//        });
//
//        // 设置播放模式
//        playPattern.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pattern++;
//                if (pattern > 3) {
//                    pattern = 0;
//                }
//                playBinder.setPattern(pattern);
//                switch (pattern) {
//                    case 0:
//                        Toast.makeText(PlayPageActivity.this, "列表循环", Toast.LENGTH_SHORT).show();
//                        playPattern.setImageResource(R.mipmap.bt_widget_mode_cycle_press);
//                        break;
//                    case 1:
//                        Toast.makeText(PlayPageActivity.this, "单曲循环", Toast.LENGTH_SHORT).show();
//                        playPattern.setImageResource(R.mipmap.bt_widget_mode_singlecycle_press);
//                        break;
//                    case 2:
//                        Toast.makeText(PlayPageActivity.this, "随机播放", Toast.LENGTH_SHORT).show();
//                        playPattern.setImageResource(R.mipmap.bt_widget_mode_shuffle_press);
//                        break;
//                    case 3:
//                        Toast.makeText(PlayPageActivity.this, "顺序播放", Toast.LENGTH_SHORT).show();
//                        playPattern.setImageResource(R.mipmap.bt_widget_mode_order_press);
//                        break;
//                }
//            }
//        });
//
//        // 清空全部
//        clearAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PlayListManager.getInstance().getPlayList().clear();
//                popAdapter.setPlayLists(PlayListManager.getInstance().getPlayList());
//                playBinder.stopPlay();
//            }
//        });
//
//        //返回
//        closeTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
