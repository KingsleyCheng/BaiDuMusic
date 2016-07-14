package com.example.dllo.baidumusic.musiclibrary.recommend;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseFragment;
import com.example.dllo.baidumusic.main.MainActivity;
import com.example.dllo.baidumusic.urlvalues.URLValues;
import com.example.dllo.baidumusic.volley.GsonRequest;
import com.example.dllo.baidumusic.volley.MySingleton;

/**
 * Created by dllo on 16/6/17.
 */
public class RecommendFragment extends BaseFragment {
    private RequestQueue requestQueue;
    private RecommendPagerAdapter pagerAdapter;
    private RecommendViewAdapter viewAdapter;
    private ImageView[] dotIv;
    private LinearLayout dots;
    private ViewPager viewPager;
    private TextView moreSongMenu;
    private RecyclerView recyclerView;
    private Handler handler;
    private Thread thread;
    private boolean thredAlive = true;
    private boolean userTouch = false;
    private int sleepTick;
    private RecommendViewBean recommendViewBean;
    private ImageView allAuthor;

    @Override
    public int setLayout() {
        return R.layout.fragment_recommend;
    }


    @Override
    protected void intiView(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.recommend_view_pager);
        dots = (LinearLayout) view.findViewById(R.id.recommend_viewgroup);
        moreSongMenu = (TextView) view.findViewById(R.id.recommend_more_song_menu);
        recyclerView = (RecyclerView) view.findViewById(R.id.recommend_recycler_view);
        allAuthor = (ImageView) view.findViewById(R.id.recommend_all_author);


    }

    @Override
    protected void initData() {
        pagerAdapter = new RecommendPagerAdapter();
        requestQueue = MySingleton.getInstance(context).getRequestQueue();
        //轮播图获取网络数据并设置
        GsonRequest<RecommendPagerBean> gsonRequest =
                new GsonRequest<RecommendPagerBean>(Request.Method.GET, URLValues.RECOMMEND_BANNER,
                        RecommendPagerBean.class, new Response.Listener<RecommendPagerBean>() {
                    @Override
                    public void onResponse(RecommendPagerBean response) {
                        pagerAdapter.setRecommendPagerBean(response);
                        //轮播图加点
                        dotIv = new ImageView[response.getPic().size()];
                        for (int i = 0; i < dotIv.length; i++) {
                            ImageView imageView = new ImageView(context);
                            if (i == 0) {
                                imageView.setImageResource(R.mipmap.ic_dot_default_selected);
                            } else {
                                imageView.setImageResource(R.mipmap.ic_dot_default_unselected);
                            }
                            dotIv[i] = imageView;
                            LinearLayout.LayoutParams layoutParams =
                                    new LinearLayout.LayoutParams(new
                                            ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT));
                            //设置点的view的左右边距
                            layoutParams.leftMargin = 10;
                            layoutParams.rightMargin = 10;
                            dots.addView(imageView, layoutParams);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(gsonRequest);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotIv.length; i++) {
                    if (i == position % dotIv.length) {
                        dotIv[i].setImageResource(R.mipmap.ic_dot_default_selected);
                    } else {
                        dotIv[i].setImageResource(R.mipmap.ic_dot_default_unselected);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                int item = viewPager.getCurrentItem();
                viewPager.setCurrentItem(item + 1);
                return false;
            }
        });
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (thredAlive) {
                        for (sleepTick = 0; sleepTick < 5; sleepTick++) {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (!userTouch) {
                                handler.sendEmptyMessage(0);
                            }
                        }
                    }

                }
            });
            thread.start();
        }
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    //触摸轮播图的时候
                    case MotionEvent.ACTION_DOWN:
                        userTouch = true;
                        break;
                    //手指离开轮播图的时候
                    case MotionEvent.ACTION_UP:
                        userTouch = false;
                        //每次当用抬起手指,就会重新开始计时
                        sleepTick = 0;
                        break;
                }
                return false;
            }
        });
        //获取歌单推荐列表
        viewAdapter = new RecommendViewAdapter(context);
        GsonRequest<RecommendViewBean> gsonRequestView = new GsonRequest<RecommendViewBean>(Request.Method.GET,
                URLValues.RECOMMEND_SONG_MENU, RecommendViewBean.class, new Response.Listener<RecommendViewBean>() {
            @Override
            public void onResponse(RecommendViewBean response) {
                recommendViewBean = response;
                viewAdapter.setRecommendViewBean(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(gsonRequestView);
        recyclerView.setAdapter(viewAdapter);
        GridLayoutManager manager = new GridLayoutManager(context, 3);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setFocusable(false);
        //点击更多跳转到歌单页面
        moreSongMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.recommend_more_song_menu:
                        Intent intent = new Intent("com.example.dllo.baidumusic.recommend_more_to_song_menu");
                        context.sendBroadcast(intent);
                        break;

                }

            }
        });

        //调转到歌单列表界面
        viewAdapter.setRecommendOnClickListener(new RecommendOnClickListener() {
            @Override
            public void recommendOnClick(int position) {
                String listid = recommendViewBean.getContent().getList().get(position).getListid();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.toSongMenuList(listid);
            }
        });

        //跳转到全部歌手页面
        allAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.toAllAuthor();
            }
        });

    }
}
