package com.example.dllo.baidumusic.musiclibrary.radiostation.radiototal.radioplay;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseActivity;
import com.example.dllo.baidumusic.musiclibrary.radiostation.radiototal.RadioTotalActivity;
import com.example.dllo.baidumusic.musicplayservice.IndexEvent;
import com.example.dllo.baidumusic.musicplayservice.PlayingListManager;
import com.example.dllo.baidumusic.musicplayservice.SongIdEvent;
import com.example.dllo.baidumusic.songplaypage.playinglist.PlayingListBean;
import com.example.dllo.baidumusic.urlvalues.URLValues;
import com.example.dllo.baidumusic.volley.GsonRequest;
import com.example.dllo.baidumusic.volley.MySingleton;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by dllo on 16/7/3.
 */
public class RadioPlayActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private RadioPlayAdapter radioPlayAdapter;
    private RadioPlayBean radioPlayBean;
    private TextView radioPlayscame;
    private RequestQueue requestQueue;
    private ImageView radioPlayReturn;


    @Override
    public int setLayout() {
        return R.layout.activity_radio_play;


    }

    @Override
    public void activityInitData() {
        radioPlayscame = (TextView) findViewById(R.id.radio_play_scname);
        viewPager = (ViewPager) findViewById(R.id.radio_play_viewpager);
        radioPlayReturn = (ImageView) findViewById(R.id.radio_play_return);
        radioPlayReturn.setOnClickListener(this);
        radioPlayscame.setOnClickListener(this);

        viewPager.setOffscreenPageLimit(4);//设置ViewPager至多缓存4个Pager页面，防止多次加载
        viewPager.setPageMargin(20);//设置pager之间的间距
        viewPager.invalidate();//更新超出区域页面
        viewPager.postInvalidate();

        radioPlayscame.setText(getIntent().getStringExtra("sceneName"));

        radioPlayAdapter = new RadioPlayAdapter(this);
        requestQueue = MySingleton.getInstance(this).getRequestQueue();
        String sceneId = getIntent().getStringExtra("sceneId");
        String url = URLValues.RADIO_PLAY1 + sceneId + URLValues.RADIO_PLAY2;
        GsonRequest<RadioPlayBean> gsonRequest = new GsonRequest<RadioPlayBean>(Request.Method.GET, url, RadioPlayBean.class,
                new Response.Listener<RadioPlayBean>() {
                    @Override
                    public void onResponse(RadioPlayBean response) {
                        radioPlayBean = response;
                        radioPlayAdapter.setSonglistBean(radioPlayBean.getResult().getSonglist());
                        EventBus.getDefault().post(new SongIdEvent(radioPlayBean.getResult().getSonglist().get(0).getSong_id()));


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(gsonRequest);
        viewPager.setAdapter(radioPlayAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                EventBus.getDefault().post(new IndexEvent(position));
                for (RadioPlayBean.ResultBean.SonglistBean songListBean:radioPlayBean.getResult().getSonglist()) {
                    PlayingListManager.getInstance().getPlayingListBeen().add(
                            new PlayingListBean(songListBean.getTitle(),songListBean.getAuthor(),songListBean.getSong_id()));
                }
            }

            @Override
            public void onPageSelected(int position) {
                radioPlayAdapter.setPos(position);
                EventBus.getDefault().post(new SongIdEvent(radioPlayBean.getResult().getSonglist().get(position).getSong_id()));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio_play_return:
                finish();
                break;
            case R.id.radio_play_scname:
                Intent intent = new Intent(this, RadioTotalActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }
}
