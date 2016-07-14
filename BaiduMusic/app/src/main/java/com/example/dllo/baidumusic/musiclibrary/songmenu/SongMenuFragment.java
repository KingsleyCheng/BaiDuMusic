package com.example.dllo.baidumusic.musiclibrary.songmenu;

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
import com.example.dllo.baidumusic.urlvalues.URLValues;
import com.example.dllo.baidumusic.volley.GsonRequest;
import com.example.dllo.baidumusic.volley.MySingleton;
import com.google.gson.Gson;

/**
 * Created by dllo on 16/6/17.
 */
public class SongMenuFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private SongMenuAdapter songMenuAdapter;
    private RequestQueue requestQueue;
    private SongMenuBean songMenuBean;
    private GridLayoutManager gridLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int lastVisibleltem;
    private int num = 2;

    @Override
    public int setLayout() {
        return R.layout.fragment_song_menu;
    }


    @Override
    protected void intiView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.song_menu_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);


    }

    @Override
    protected void initData() {

        songMenuAdapter = new SongMenuAdapter(context);
        refresh();
        requestQueue = MySingleton.getInstance(context).getRequestQueue();
        GsonRequest<SongMenuBean> gsonRequest = new GsonRequest<SongMenuBean>(Request.Method.GET,
                URLValues.SONG_MENU, SongMenuBean.class, new Response.Listener<SongMenuBean>() {
            @Override
            public void onResponse(SongMenuBean response) {
                songMenuBean = response;
                songMenuAdapter.setSongMenuBean(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(gsonRequest);
        recyclerView.setAdapter(songMenuAdapter);
        gridLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        //跳转到歌单列表界面
        songMenuAdapter.setSongMenuOnClickListener(new SongMenuOnClickListener() {
            @Override
            public void onSongMenuOnClick(int position) {
                MainActivity mainActivity = (MainActivity) getActivity();
                String listid = songMenuAdapter.getSongMenuBean().getContent().get(position).getListid();
                mainActivity.toSongMenuList(listid);
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
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleltem + 1 == songMenuAdapter.getItemCount()) {
                    String url = URLValues.SONG_MENU1 + num + URLValues.SONG_MENU2;

                    StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Gson gson = new Gson();
                            songMenuBean = gson.fromJson(response, SongMenuBean.class);
                            swipeRefreshLayout.setRefreshing(false);
                            songMenuAdapter.addSongMenuBean(songMenuBean);
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
