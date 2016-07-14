package com.example.dllo.baidumusic.musiclibrary.mv;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseFragment;
import com.example.dllo.baidumusic.main.MainActivity;
import com.example.dllo.baidumusic.musiclibrary.songmenu.SongMenuAdapter;
import com.example.dllo.baidumusic.musiclibrary.songmenu.SongMenuBean;
import com.example.dllo.baidumusic.musiclibrary.songmenu.SongMenuOnClickListener;
import com.example.dllo.baidumusic.urlvalues.URLValues;
import com.example.dllo.baidumusic.volley.GsonRequest;
import com.example.dllo.baidumusic.volley.MySingleton;
import com.google.gson.Gson;

/**
 * Created by dllo on 16/6/17.
 */
public class MvFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private MvAdapter mvAdapter;
    private RequestQueue requestQueue;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GridLayoutManager gridLayoutManager;
    private MvBean mvBean;
    private int lastVisibleltem;
    private int num = 2;


    @Override
    public int setLayout() {
        return R.layout.fragment_mv;
    }


    @Override
    protected void intiView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.mv_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

    }

    @Override
    protected void initData() {
        mvAdapter = new MvAdapter(context);
        refresh();
        requestQueue = MySingleton.getInstance(context).getRequestQueue();
        GsonRequest<MvBean> gsonRequest = new GsonRequest<MvBean>(Request.Method.GET, URLValues.MVLIST1, MvBean.class,
                new Response.Listener<MvBean>() {
                    @Override
                    public void onResponse(MvBean response) {
                        mvBean = response;
                        mvAdapter.setMvBean(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(gsonRequest);
        recyclerView.setAdapter(mvAdapter);
        gridLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        //跳转到MV播放界面
        mvAdapter.setMvOnClickListener(new MvOnClickListener() {
            @Override
            public void mvOnClick(int position) {
                requestQueue = MySingleton.getInstance(context).getRequestQueue();
                String mvId = mvBean.getResult().getMv_list().get(position).getMv_id();
                StringRequest stringRequest = new StringRequest(URLValues.MV_PLAY1 + mvId + URLValues.MV_PLAY2,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Gson gson = new Gson();
                                MvPlayBean mvPlayBean = gson.fromJson(response, MvPlayBean.class);
                                String url = mvPlayBean.getResult().getVideo_info().getSourcepath();
                                String str = url.substring(31, url.length() - 1);
                                Intent intent = new Intent(context, MvPlayActivity.class);
                                intent.putExtra("mvPlayUrl", "http://www.yinyuetai.com/mv/video-url/" + str);
                                startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(stringRequest);
            }
        });


    }

    //上拉加载
    private void refresh() {
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                , 24, getResources().getDisplayMetrics()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleltem + 1 == mvAdapter.getItemCount()) {
                    String url = URLValues.MVLIST1 + num + URLValues.MVLIST2;

                    StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Gson gson = new Gson();
                            mvBean = gson.fromJson(response, MvBean.class);
                            swipeRefreshLayout.setRefreshing(false);
                            mvAdapter.addMvBean(mvBean);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    requestQueue.add(request);
                    swipeRefreshLayout.setRefreshing(true);
                    num += 1;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleltem = gridLayoutManager.findLastVisibleItemPosition();
            }
        });

    }
}
