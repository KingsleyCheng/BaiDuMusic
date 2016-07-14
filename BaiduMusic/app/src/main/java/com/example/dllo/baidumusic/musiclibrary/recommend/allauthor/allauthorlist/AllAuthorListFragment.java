package com.example.dllo.baidumusic.musiclibrary.recommend.allauthor.allauthorlist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseFragment;
import com.example.dllo.baidumusic.main.MainActivity;
import com.example.dllo.baidumusic.volley.GsonRequest;
import com.example.dllo.baidumusic.volley.MySingleton;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by dllo on 16/6/30.
 */
public class AllAuthorListFragment extends BaseFragment {
    private ImageView allAuthorListReturn;
    private ListView listView;
    private AllAuthorListAdapter allAuthorListAdapter;
    private RequestQueue requestQueue;
    private TextView combinationName;
    private View listViewtitle;
    private AllAuthorListBean allAuthorListBean;

    @Override
    public int setLayout() {
        return R.layout.fragment_all_author_list;
    }

    @Override
    protected void intiView(View view) {
        allAuthorListReturn = (ImageView) view.findViewById(R.id.all_author_list_return);
        combinationName = (TextView) view.findViewById(R.id.combination_name);
        listView = (ListView) view.findViewById(R.id.all_author_list_list_view);
        listViewtitle = LayoutInflater.from(context).inflate(R.layout.item_all_author_list_title, null);

    }

    @Override
    protected void initData() {
        String type = getArguments().getString("allauthorlisttype");
        combinationName.setText(type);
        String url = getArguments().getString("allauthorlisturl");
        allAuthorListAdapter = new AllAuthorListAdapter(context);
        requestQueue = MySingleton.getInstance(context).getRequestQueue();
        GsonRequest<AllAuthorListBean> gsonRequest = new GsonRequest<AllAuthorListBean>(Request.Method.GET,
                url, AllAuthorListBean.class, new Response.Listener<AllAuthorListBean>() {
            @Override
            public void onResponse(AllAuthorListBean response) {
                allAuthorListBean = response;
                allAuthorListAdapter.setAllAuthorListBean(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(gsonRequest);
        listView.setAdapter(allAuthorListAdapter);
        listView.addHeaderView(listViewtitle);
        //跳转到歌手详细列表监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tingUid = allAuthorListBean.getArtist().get(position - 1).getTing_uid();
                String picUrl = allAuthorListBean.getArtist().get(position - 1).getAvatar_big();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.toAuthorDetailList(tingUid, picUrl);
            }
        });

        //退出本页面监听
        allAuthorListReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }
}
