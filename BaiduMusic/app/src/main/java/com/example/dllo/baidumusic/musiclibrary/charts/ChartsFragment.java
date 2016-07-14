package com.example.dllo.baidumusic.musiclibrary.charts;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
public class ChartsFragment extends BaseFragment {
    private ListView chartsListView;
    private ChartsAdapter chartsAdapter;
    private RequestQueue requestQueue;
    private ChartsBean chartsBean;

    @Override
    public int setLayout() {
        return R.layout.fragment_charts;
    }


    @Override
    protected void intiView(View view) {
        chartsListView = (ListView) view.findViewById(R.id.charts_list_view);


    }

    @Override
    protected void initData() {
        chartsAdapter = new ChartsAdapter(context);
        requestQueue = MySingleton.getInstance(context).getRequestQueue();
        GsonRequest<ChartsBean> gsonRequest = new GsonRequest<ChartsBean>(Request.Method.GET,
                URLValues.CHARST, ChartsBean.class, new Response.Listener<ChartsBean>() {
            @Override
            public void onResponse(ChartsBean response) {
                chartsBean = response;
                chartsAdapter.setChartsBean(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        requestQueue.add(gsonRequest);
        chartsListView.setAdapter(chartsAdapter);

        //设置item的监听事件
        chartsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int type = chartsBean.getContent().get(position).getType();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.toChartsList(type);
            }
        });
    }
}
