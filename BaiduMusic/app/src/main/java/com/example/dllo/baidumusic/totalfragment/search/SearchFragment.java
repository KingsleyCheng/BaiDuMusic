package com.example.dllo.baidumusic.totalfragment.search;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseFragment;
import com.example.dllo.baidumusic.musicplayservice.IndexEvent;
import com.example.dllo.baidumusic.musicplayservice.PlayingListManager;
import com.example.dllo.baidumusic.musicplayservice.SongIdEvent;
import com.example.dllo.baidumusic.songplaypage.playinglist.PlayingListBean;
import com.example.dllo.baidumusic.volley.GsonRequest;
import com.example.dllo.baidumusic.volley.MySingleton;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by dllo on 16/7/4.
 */
public class SearchFragment extends BaseFragment implements View.OnClickListener {
    private SeatchAdapter seatchAdapter;
    private ListView listView;
    private ImageView searchReturn, searchButton;
    private EditText searchEt;
    private RequestQueue requestQueue;
    private SearchBean searchBean;


    @Override
    public int setLayout() {
        return R.layout.fragment_search;
    }

    @Override
    protected void intiView(View view) {
        listView = (ListView) view.findViewById(R.id.search_list_view);
        searchReturn = (ImageView) view.findViewById(R.id.search_return);
        searchButton = (ImageView) view.findViewById(R.id.search_star_button);
        searchEt = (EditText) view.findViewById(R.id.search_etit_text);
        searchReturn.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seatchAdapter = new SeatchAdapter(context);
                String texts = searchEt.getText().toString();
                if (texts.isEmpty()) {
                    return;
                } else {
                    String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.search.merge&query=" + texts + "&page_size=50&page_no=1&type=-1&format=json&from=ios&version=5.2.5&from=ios&channel=appstore";
                    requestQueue = MySingleton.getInstance(context).getRequestQueue();
                    GsonRequest<SearchBean> gsonRequest = new GsonRequest<SearchBean>(Request.Method.GET, url, SearchBean.class,
                            new Response.Listener<SearchBean>() {
                                @Override
                                public void onResponse(SearchBean response) {
                                    searchBean = response;
                                    seatchAdapter.setSearchBean(response);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(gsonRequest);
                    listView.setAdapter(seatchAdapter);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(new SongIdEvent(searchBean.getResult().getSong_info().getSong_list().get(position).getSong_id()));
                EventBus.getDefault().post(new IndexEvent(position));
                for (SearchBean.ResultBean.SongInfoBean.SongListBean songListBean :
                        searchBean.getResult().getSong_info().getSong_list()) {
                    PlayingListManager.getInstance().getPlayingListBeen().add(
                            new PlayingListBean(songListBean.getTitle(), songListBean.getAuthor(), songListBean.getSong_id()));
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        getFragmentManager().popBackStack();

    }
}
